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

package com.infosense.ibilling.server.order;

import com.infosense.ibilling.server.order.db.OrderLineDTO;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Usage represents a single customers usage of an item, or an item type over a
 * set date range (usually aligned with the customer's billing period).
 * 
 * @author Brian Cowdery
 * @since 16-08-2010
 */
public class Usage {
    private static Logger LOG = Logger.getLogger(Usage.class);

    private Integer userId;
    private Integer itemId;
    private Integer itemTypeId;
    private BigDecimal quantity;
    private BigDecimal amount;

    private Date startDate;
    private Date endDate;

    public Usage() {
    }

    public Usage(Integer userId, Integer itemId, Integer itemTypeId, BigDecimal quantity, BigDecimal amount, Date startDate, Date endDate) {
        this.userId = userId;
        this.itemId = itemId;
        this.itemTypeId = itemTypeId;
        this.quantity = quantity;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Usage(List<OrderLineDTO> lines, Integer userId, Integer itemId, Integer itemTypeId, Date startDate, Date endDate) {
        this.userId = userId;
        this.itemId = itemId;
        this.itemTypeId = itemTypeId;
        this.startDate = startDate;
        this.endDate = endDate;

        calculateUsage(lines);
    }

    public void calculateUsage(List<OrderLineDTO> lines) {
        quantity = BigDecimal.ZERO;
        amount = BigDecimal.ZERO;
        for (OrderLineDTO line : lines) {
            quantity = quantity.add(line.getQuantity());
            amount = amount.add(line.getAmount());
        }
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(Integer itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    /**
     * The total quantity, or "number of units" purchased
     * over the period.
     *
     * @return number of units purchased
     */
    public BigDecimal getQuantity() {
        return (quantity != null ? quantity : BigDecimal.ZERO);
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(BigDecimal quantity) {
        if (quantity != null) setQuantity(getQuantity().add(quantity));
    }

    public void subtractQuantity(BigDecimal quantity) {
        if (quantity != null) setQuantity(getQuantity().subtract(quantity));
    }
            
    /**
     * The total dollar amount of usage purchased over the period.
     *
     * @return total amount of usage in dollars
     */
    public BigDecimal getAmount() {
        return (amount != null ? amount : BigDecimal.ZERO);
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void addAmount(BigDecimal amount) {
        if (amount != null) setAmount(getAmount().add(amount));
    }

    public void subractAmount(BigDecimal amount) {
        if (amount != null) setAmount(getAmount().subtract(amount));
    }

    /**
     * Add the quantity and amount from a given order line.
     * 
     * @param line order line to add
     */
    public void addLine(OrderLineDTO line) {
        LOG.debug("Adding usage from line: " + line);
        addAmount(line.getAmount());
        addQuantity(line.getQuantity());
    }

    /**
     * Subtract the quantity and amount from a given order line.
     *
     * @param line order line to subtract
     */
    public void subtractLine(OrderLineDTO line) {
        LOG.debug("Subtracting usage from line: " + line);
        subractAmount(line.getAmount());
        subtractQuantity(line.getQuantity());

    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Usage{"
                + "itemId=" + itemId
                + ", itemTypeId=" + itemTypeId
                + ", quantity=" + getQuantity()
                + ", amount=" + getAmount()
                + ", startDate=" + startDate
                + ", endDate=" + endDate
                + '}';
    }
}
