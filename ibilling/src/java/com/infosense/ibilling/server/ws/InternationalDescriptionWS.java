package com.infosense.ibilling.server.ws;

import com.infosense.ibilling.server.util.db.InternationalDescriptionDTO;

/**
 * InternationalDescriptionWS
 */
public class InternationalDescriptionWS {

    private String psudoColumn;
    private Integer languageId;
    private String content;

    public InternationalDescriptionWS() {
    }

    public InternationalDescriptionWS(Integer languageId, String content) {
        this.psudoColumn = "description";
        this.languageId = languageId;
        this.content = content;
    }

    public InternationalDescriptionWS(String psudoColumn, Integer languageId, String content) {
        this.psudoColumn = psudoColumn;
        this.languageId = languageId;
        this.content = content;
    }

    public InternationalDescriptionWS(InternationalDescriptionDTO description) {
        if (description.getId() != null) {
            this.psudoColumn = description.getId().getPsudoColumn();
            this.languageId = description.getId().getLanguageId();
        }
        this.content = description.getContent();
    }

    /**
     * Alias for {@link #getPsudoColumn()}
     * @return psudo-column label
     */
    public String getLabel() {
        return getPsudoColumn();
    }

    /**
     * Alias for {@link #setPsudoColumn(String)}
     * @param label psudo-column label string
     */
    public void setLabel(String label) {
        setPsudoColumn(label);
    }

    public String getPsudoColumn() {
        return psudoColumn;
    }

    public void setPsudoColumn(String psudoColumn) {
        this.psudoColumn = psudoColumn;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "InternationalDescriptionWS{"
               + ", psudoColumn='" + psudoColumn + '\''
               + ", languageId=" + languageId
               + ", content='" + content + '\''
               + '}';
    }
}
