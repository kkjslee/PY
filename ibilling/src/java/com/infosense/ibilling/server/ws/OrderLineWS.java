package com.infosense.ibilling.server.ws;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.infosense.ibilling.server.item.ItemDTOEx;

/**
 * OrderLineWS
 */
public class OrderLineWS implements Serializable {

    private int id;
    private Integer orderId;
    private String groupId;
    private String amount; // use strings instead of BigDecimal for WS compatibility
    @NotNull(message = "validation.error.null.quantity")
    private String quantity;
    private String price;
    private Date createDatetime;
    private int deleted;
    private String description;
    private Integer versionNum;
    private Boolean editable = null;

    //provisioning fields
    private Integer provisioningStatusId;
    private String provisioningRequestId;

    // other fields, non-persistent
    private String priceStr = null;
    private ItemDTOEx itemDto = null;
    private Integer typeId = null;
    private Boolean useItem = null;
    @NotNull(message = "validation.error.missing.item.id")
    private Integer itemId = null;

    public OrderLineWS() {
    }

    public OrderLineWS(Integer id, Integer itemId, String groupId, String description, BigDecimal amount, BigDecimal quantity,
                       BigDecimal price,
                       Date create, Integer deleted, Integer newTypeId, Boolean editable, Integer orderId,
                       Boolean useItem, Integer version, Integer provisioningStatusId, String provisioningRequestId) {
        setId(id);
        setItemId(itemId);
        setDescription(description);
        setGroupId(groupId);
        setAmount(amount);
        setQuantity(quantity);
        setPrice(price);
        setCreateDatetime(create);
        setDeleted(deleted);
        setTypeId(newTypeId);
        setEditable(editable);
        setOrderId(orderId);
        setUseItem(useItem);
        setVersionNum(version);
        setProvisioningStatusId(provisioningStatusId);
        setProvisioningRequestId(provisioningRequestId);

    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Boolean getUseItem() {
        return useItem == null ? new Boolean(false) : useItem;
    }

    public void setUseItem(Boolean useItem) {
        this.useItem = useItem;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getAmount() {
        return amount;
    }

    public BigDecimal getAmountAsDecimal() {
        return amount == null ? null : new BigDecimal(amount);
    }

    public void setAmountAsDecimal(BigDecimal amount) {
        setAmount(amount);
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = (amount != null ? amount.toString() : null);
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ItemDTOEx getItemDto() {
        return itemDto;
    }

    public void setItemDto(ItemDTOEx itemDto) {
        this.itemDto = itemDto;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getPrice() {
        return price;
    }

    public BigDecimal getPriceAsDecimal() {
        return price == null ? null : new BigDecimal(price);
    }

    public void setPriceAsDecimal(BigDecimal price) {
        setPrice(price);
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPrice(BigDecimal price) {
        this.price = (price != null ? price.toString() : null);
    }

    public String getPriceStr() {
        return priceStr;
    }

    public String getQuantity() {
        return quantity;
    }

    public BigDecimal getQuantityAsDecimal() {
        return quantity == null ? null : new BigDecimal(quantity);
    }

    public void setQuantityAsDecimal(BigDecimal quantity) {
        setQuantity(quantity);
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setQuantity(Integer quantity) {
        setQuantity(new BigDecimal(quantity));
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = (quantity != null ? quantity.toString() : null);
    }

    public Integer getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(Integer versionNum) {
        this.versionNum = versionNum;
    }

    /**
     * @return the provisioningStatusId
     */
    public Integer getProvisioningStatusId() {
        return provisioningStatusId;
    }

    /**
     * @param provisioningStatusId the provisioningStatusId to set
     */
    public void setProvisioningStatusId(Integer provisioningStatusId) {
        this.provisioningStatusId = provisioningStatusId;
    }

    /**
     * @return the provisioningRequestId
     */
    public String getProvisioningRequestId() {
        return provisioningRequestId;
    }

    /**
     * @param provisioningRequestId the provisioningRequestId to set
     */
    public void setProvisioningRequestId(String provisioningRequestId) {
        this.provisioningRequestId = provisioningRequestId;
    }

    @Override public String toString() {
        return "OrderLineWS{"
               + "id=" + id
               + ", groupId" + groupId + '\''
               + ", amount='" + amount + '\''
               + ", quantity='" + quantity + '\''
               + ", price='" + price + '\''
               + ", deleted=" + deleted
               + ", description='" + description + '\''
               + ", useItem=" + useItem
               + ", itemId=" + itemId
               + ", typeId=" + typeId
               + '}';
    }
}
