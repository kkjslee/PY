package com.infosense.ibilling.server.mediation.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.mortbay.log.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import com.infosense.ibilling.server.item.PricingField;
import com.infosense.ibilling.server.item.PricingField.Type;
import com.infosense.ibilling.server.mediation.Record;
import com.infosense.ibilling.server.pluggableTask.admin.ParameterDescription;

public class MySQLReaderFor21 extends MySQLReader{
	private static final Logger LOG = Logger.getLogger(MySQLReaderFor21.class);
	
	 private JdbcTemplate prTemplate;
	 private List<String> accumulateColumns = new ArrayList<String>();
	 private String selectCols;
	 
	 public static final ParameterDescription PARAMETER_SELECT_COLUMNS = 
	    	new ParameterDescription("select_columns", false, ParameterDescription.Type.STR);
	 public static final ParameterDescription PARAMETER_ACCUMULATE_COLUMNS = 
	    	new ParameterDescription("accumulate_columns", false, ParameterDescription.Type.STR);
	 
	 {
			descriptions.add(PARAMETER_SELECT_COLUMNS);
			descriptions.add(PARAMETER_ACCUMULATE_COLUMNS);
	}
	 
	 @Override
	 public boolean validate(List<String> messages) {
		boolean result = super.validate(messages);
        try {
            init();
        } catch (Exception ex) {
            LOG.error("Exception during reader plugin validation", ex);
            messages.add(ex.getMessage());
            return false;
        }
        return result;
	 }
	 
	 private void init(){
		 selectCols = getParameter(PARAMETER_BATCH_SIZE.getName(), "*");
		 String accumulateCols = getParameter(PARAMETER_ACCUMULATE_COLUMNS.getName(), "*");
		 
		 for(String col : accumulateCols.split(",")){
			 if(StringUtils.isNotBlank(col)){
				 accumulateColumns.add(col);
			 }
		 }
		 
		 this.prTemplate = new JdbcTemplate(getDataSource());
	     this.prTemplate.setMaxRows(1);
	 }
	 
	 @Override
	 protected String getSelectColumns(){
		 return selectCols;
	 }
	 
	 @Override
	 protected void processRecord(Record record){
		 String uuid = null;
		 for(PricingField pf : record.getFields()){
			 if("vmUUID".equals(pf.getName())){
				 uuid = pf.getStrValue();
				 break;
			 }
		 }
		 
		 if(uuid==null) LOG.error("Cannot find UUID for record : "+record);
		 
		 Map<String, Integer> values = new HashMap<String, Integer>();
		 if(uuid != null){
			 SqlRowSet rows = this.prTemplate.queryForRowSet(lastRecordSql(uuid));
			 
			 if(rows.next()){
				 SqlRowSetMetaData meta = rows.getMetaData();
				 for(int i=0;i<meta.getColumnCount();i++){
					 try{
						 values.put(meta.getColumnName(i + 1), rows.getInt(i + 1));
					 }catch(Exception e){
						 Log.warn("Column "+ meta.getColumnName(i + 1) + " is not an Integer Column.");
					 }
				 }
			 }else{
				 LOG.info("No record found for UUID : "+uuid); 
			 }
		 }
		 
		 for(PricingField pf : record.getFields()){
			 String name = pf.getName();
			 if(pf.getType().equals(Type.INTEGER) && values.containsKey(name)){
				 pf.setIntValue(pf.getIntValue()-values.get(name));
			 }
		 }
	 }
	 
	 private String lastRecordSql(String uuid){
		 StringBuilder sb = new StringBuilder("select ");
		 
		 if(accumulateColumns.size()>0){
			 for(int i=0; i<accumulateColumns.size();i++){
				 if(i>0){
					 sb.append(",");
				 }
				 sb.append(accumulateColumns.get(i));
			 }
		 }else{
			 sb.append("*");
		 }
		 sb.append(" FROM ")
		 		.append(getTableName())
		 		.append(" WHERE vmUUID=").append(uuid)
		 		.append(getKeyColumns().get(0)).append(" <= ").append(getLastId()).append(" ")
		 		.append(" AND ")
		   		.append("order by ").append(getKeyColumns().get(0)).append(" desc");
		  return sb.toString();
	 }
	
}
