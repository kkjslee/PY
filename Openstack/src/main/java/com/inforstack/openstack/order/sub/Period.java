package com.inforstack.openstack.order.sub;

import java.math.BigDecimal;
import java.util.Date;

public class Period {
	
	private Date start;
	
	private Date end;
	
	private Date periodEnd;

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Date getPeriodEnd() {
		return periodEnd;
	}

	public void setPeriodEnd(Date periodEnd) {
		this.periodEnd = periodEnd;
	}
	
	public boolean wholePeriod(){
		return periodEnd.equals(end);
	}
	
	public BigDecimal percentInPeriod(){
		return new BigDecimal(end.getTime()-start.getTime()).divide(
				new BigDecimal(periodEnd.getTime()-start.getTime()));
	}
}
