package com.inforstack.openstack.controller.model;

public class PaginationModel {
	
	private int recordTotal;
	
	private int pageIndex;
	
	private int pageSize;

	private String html;

	public int getPageTotle() {
		return Double.valueOf(Math.ceil(recordTotal/pageSize)).intValue();
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public int getRecordTotal() {
		return recordTotal;
	}

	public void setRecordTotal(int recordTotal) {
		this.recordTotal = recordTotal;
	}
	
}
