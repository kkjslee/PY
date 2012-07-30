package com.infosense.ibilling.server.ws;

import java.io.Serializable;

import com.infosense.ibilling.server.mediation.IMediationSessionBean;

/**
 * Web-service compatible representation of the value map returned by
 * {@link IMediationSessionBean#getMediationRecordsByMediationProcess(Integer)}.
 *
 * This class is necessary as Apache CXF (JAXB) does not handle Maps and a custom JAXB binding
 * might not be supported by SOAP clients.
 */
public class RecordCountWS implements Serializable {

    private Integer statusId;
    private Long count;

    public RecordCountWS() {
    }

    public RecordCountWS(Integer statusId, Long count) {
        this.statusId = statusId;
        this.count = count;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "RecordCountWS{"
               + "statusId=" + statusId
               + ", count=" + count
               + '}';
    }
}
