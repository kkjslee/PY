/*
 * JBILLING CONFIDENTIAL
 * _____________________
 *
 * [2003] - [2012] Enterprise jBilling Software Ltd.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Enterprise jBilling Software.
 * The intellectual and technical concepts contained
 * herein are proprietary to Enterprise jBilling Software
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden.
 */

package com.infosense.ibilling.server.invoice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.List;

import org.apache.log4j.Logger;

import javax.sql.rowset.CachedRowSet;

import com.infosense.ibilling.common.SessionInternalError;
import com.infosense.ibilling.common.Util;
import com.infosense.ibilling.server.invoice.db.InvoiceDTO;
import com.infosense.ibilling.server.notification.NotificationBL;
import com.infosense.ibilling.server.process.BillingProcessBL;
import com.infosense.ibilling.server.process.db.PaperInvoiceBatchDAS;
import com.infosense.ibilling.server.process.db.PaperInvoiceBatchDTO;
import com.infosense.ibilling.server.util.Constants;
import com.infosense.ibilling.server.util.PreferenceBL;
import com.infosense.ibilling.server.util.audit.EventLogger;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PRAcroForm;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.SimpleBookmark;

/**
 * @author Emil
 */
public class PaperInvoiceBatchBL {
    private PaperInvoiceBatchDTO batch = null;
    private static final Logger LOG = Logger.getLogger(PaperInvoiceBatchBL.class);
    private EventLogger eLogger = null;
    private PaperInvoiceBatchDAS batchHome = null;

    public PaperInvoiceBatchBL(Integer batchId) {
        init();
        set(batchId);
    }
    
    public PaperInvoiceBatchBL(PaperInvoiceBatchDTO batch) {
        init();
        this.batch = batch;
    }

    public PaperInvoiceBatchBL() {
        init();
    }

    private void init() {
        eLogger = EventLogger.getInstance();
        batchHome = new PaperInvoiceBatchDAS();
    }

    public PaperInvoiceBatchDTO getEntity() {
        return batch;
    }
    
    public void set(Integer id) {
        batch = batchHome.find(id);
    }

    /**
     * This method will create a record if there's none for the given
     * process id, otherwise it will return the existing one
     * @param processId
     * @return
     */
    public PaperInvoiceBatchDTO createGet(Integer processId) {
        BillingProcessBL process = new BillingProcessBL(processId);
        batch = process.getEntity().getPaperInvoiceBatch();
        if (batch == null) {
            PreferenceBL preference = new PreferenceBL();
            preference.set(process.getEntity().getEntity().getId(), 
                    Constants.PREFERENCE_PAPER_SELF_DELIVERY);
            batch = batchHome.create(new Integer(0), preference.getEntity().getIntValue());
            process.getEntity().setPaperInvoiceBatch(batch);
        }
        return batch;
    }

    /**
     * Will take all the files generated by the process and 'paste' them
     * into a big one, deleting the originals.
     * This then will facilitate the printing of a batch.
     */
    public void compileInvoiceFilesForProcess(Integer entityId) 
            throws DocumentException, IOException {
        String filePrefix = Util.getSysProp("base_dir") + "invoices/" + 
            entityId + "-";
        // now go through each of the invoices
        // first - sort them
        List invoices = new ArrayList(batch.getInvoices());
        Collections.sort(invoices, new InvoiceEntityComparator());
        Integer[] invoicesIds = new Integer[invoices.size()];


        for (int f = 0; f < invoices.size(); f++) {
            InvoiceDTO invoice = (InvoiceDTO) invoices.get(f);
            invoicesIds[f] = invoice.getId();
        }
        
        compileInvoiceFiles(filePrefix, new Integer(batch.getId()).toString(), entityId,
                invoicesIds);
    }

    /**
     * Takes a list of invoices and replaces the individual PDF files for one
     * single PDF in the destination directory.
     * @param destination
     * @param prefix
     * @param entityId
     * @param invoices
     * @throws PdfFormatException
     * @throws IOException
     */
    public void compileInvoiceFiles(String destination, String prefix,
            Integer entityId, Integer[] invoices)
            throws DocumentException, IOException {
        
        String filePrefix = Util.getSysProp("base_dir") + "invoices/"
                + entityId + "-";
        String outFile = destination + prefix + "-batch.pdf";

        
        int pageOffset = 0;
        ArrayList master = new ArrayList();
        Document document = null;
        PdfCopy  writer = null;
        for(int f = 0; f < invoices.length ; f++) {
            // we create a reader for a certain document
            PdfReader reader = new PdfReader(filePrefix + invoices[f] + "-invoice.pdf");
            reader.consolidateNamedDestinations();
            // we retrieve the total number of pages
            int numberOfPages = reader.getNumberOfPages();
            List bookmarks = SimpleBookmark.getBookmark(reader);
            if (bookmarks != null) {
                if (pageOffset != 0)
                    SimpleBookmark.shiftPageNumbers(bookmarks, pageOffset, null);
                master.addAll(bookmarks);
            }
            pageOffset += numberOfPages;
            
            if (f == 0) {
                // step 1: creation of a document-object
                document = new Document(reader.getPageSizeWithRotation(1));
                // step 2: we create a writer that listens to the document
                writer = new PdfCopy(document, new FileOutputStream(outFile));
                // step 3: we open the document
                document.open();
            }
            // step 4: we add content
            PdfImportedPage page;
            for (int i = 0; i < numberOfPages; ) {
                ++i;
                page = writer.getImportedPage(reader, i);
                writer.addPage(page);
            }
            PRAcroForm form = reader.getAcroForm();
            if (form != null)
                writer.copyAcroForm(reader);
            
            //release and delete 
            writer.freeReader(reader);
            reader.close();
            File file = new File(filePrefix + invoices[f] + "-invoice.pdf");
            file.delete();
        }
        if (!master.isEmpty())
            writer.setOutlines(master);
        // step 5: we close the document
        if (document != null) {
            document.close();
        } else {
            LOG.warn("document == null");
        }

        LOG.debug("PDF batch file is ready " + outFile);
    }

    
    public void sendEmail() {
        Integer entityId = batch.getProcess().getEntity().getId();
        
        PreferenceBL prefBL = new PreferenceBL();
        prefBL.set(entityId, Constants.PREFERENCE_PAPER_SELF_DELIVERY);
        Boolean selfDelivery = new Boolean(prefBL.getInt() == 1);
        // If the entity doesn't want to delivery the invoices, then
        // sapienter has to. Entity 1 is always sapienter.
        Integer pritingEntity;
        if (!selfDelivery.booleanValue()) {
            pritingEntity = new Integer(1);
        } else {
            pritingEntity = entityId;
        }
        try {
            NotificationBL.sendSapienterEmail(pritingEntity, "invoice_batch",
                    Util.getSysProp("base_dir") + "invoices/" + entityId + 
                    "-" + batch.getId() + "-batch.pdf", null);
        } catch (Exception e) {
            LOG.error("Could no send the email with the paper invoices " +
                    "for entity " + entityId, e);
        } 
    }
    
    public String generateFile(CachedRowSet cachedRowSet, Integer entityId, 
            String realPath) throws SQLException,
            SessionInternalError, DocumentException,
            IOException {
        NotificationBL notif = new NotificationBL();
        List invoices = new ArrayList();

        int generated = 0;
        while (cachedRowSet.next()) {
            Integer invoiceId = new Integer(cachedRowSet.getInt(1));
            InvoiceBL invoice = new InvoiceBL(invoiceId);
            LOG.debug("Generating paper invoice " + invoiceId);
            notif.generatePaperInvoiceAsFile(invoice.getEntity());
            invoices.add(invoiceId);
            
            // no more than 1000 invoices at a time, please
            generated++;
            if (generated >= 1000) break;
        }

        if (generated > 0) {
            // merge all these files into a single one
            String hash = String.valueOf(System.currentTimeMillis());
            Integer[] invoicesIds = new Integer[invoices.size()];
            invoices.toArray(invoicesIds);
            compileInvoiceFiles(realPath.substring(0, 
                    realPath.indexOf("_FILE_NAME_")) + "/", 
                    entityId + "-" + hash, entityId, invoicesIds);
    
            return entityId + "-" + hash + "-batch.pdf";
        } else {
            // there was no rows in that query ...
            return null;
        }
    }
    
}
