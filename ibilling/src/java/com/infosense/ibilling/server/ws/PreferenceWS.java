package com.infosense.ibilling.server.ws;

import java.io.Serializable;

import com.infosense.ibilling.server.util.db.PreferenceDTO;
import com.infosense.ibilling.server.util.db.PreferenceTypeDTO;

public class PreferenceWS implements Serializable {

    private Integer id;
    private PreferenceTypeWS preferenceType;
    private Integer tableId;
    private Integer foreignId;
    private String value;

    public PreferenceWS() {
    }

    public PreferenceWS(PreferenceTypeDTO preferenceType) {
        this.preferenceType = new PreferenceTypeWS(preferenceType);
    }

    public PreferenceWS(PreferenceDTO dto) {
        this.id = dto.getId();
        this.preferenceType = dto.getPreferenceType() != null ? new PreferenceTypeWS(dto.getPreferenceType()) : null;
        this.tableId = dto.getIbillingTable() != null ? dto.getIbillingTable().getId() : null;
        this.foreignId = dto.getForeignId();
        this.value = dto.getValue();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PreferenceTypeWS getPreferenceType() {
        return this.preferenceType;
    }

    public void setPreferenceType(PreferenceTypeWS preferenceType) {
        this.preferenceType = preferenceType;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }
    
    public Integer getForeignId() {
        return this.foreignId;
    }

    public void setForeignId(Integer foreignId) {
        this.foreignId = foreignId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PreferenceWS{"
               + "id=" + id
               + ", preferenceType=" + preferenceType
               + ", tableId=" + tableId
               + ", foreignId=" + foreignId
               + ", value='" + value + '\''
               + '}';
    }
}
