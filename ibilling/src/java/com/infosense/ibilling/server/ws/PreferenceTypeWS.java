package com.infosense.ibilling.server.ws;

import java.io.Serializable;

import com.infosense.ibilling.server.util.db.PreferenceTypeDTO;

public class PreferenceTypeWS implements Serializable {

    private int id;
    private String description;
    private String defaultValue;

    public PreferenceTypeWS() {
    }

    public PreferenceTypeWS(PreferenceTypeDTO dto) {
        this.id = dto.getId();
        this.description = dto.getDescription();
        this.defaultValue = dto.getDefaultValue();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return "PreferenceTypeWS{"
               + "id=" + id
               + ", description='" + description + '\''
               + ", defaultValue='" + defaultValue + '\''
               + '}';
    }
}
