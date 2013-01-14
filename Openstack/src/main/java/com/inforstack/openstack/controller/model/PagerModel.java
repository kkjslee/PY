package com.inforstack.openstack.controller.model;

import java.util.ArrayList;
import java.util.List;

/**
 * data list should be filtered first if necessary, this class just supply the pager function
 * 
 * @author rqshao
 * 
 * @param <T>
 */
public class PagerModel<T> {
  // pageIndex
  private int pageNo = 1;
  private int pageSize = 10;
  // total records number
  private int totalRecord = 0;
  // total page number
  private int totalPages = 0;
  private List<T> data;

  public PagerModel(List<T> data) {
    this.data = data;
    initPageUtil();
  }

  public PagerModel(List<T> data, int pageSize) {
    this.data = data;
    this.pageSize = pageSize;
    initPageUtil();
  }

  private void initPageUtil() {
    if (data != null) {
      totalRecord = data.size();
      totalPages = (totalRecord + pageSize - 1) / pageSize;
    }
  }

  public int getPageNo() {
    return pageNo;
  }

  public int getPageSize() {
    return pageSize;
  }

  public int getTotalRecord() {
    return totalRecord;
  }

  public int getTotalPages() {
    return totalPages;
  }

  // pageIndex is from 1 not 0
  public List<T> getPagedData(int pageIndex) {
    if (data == null || data.size() == 0) {
      return null;
    }
    if (pageIndex < 0 || pageIndex > totalPages) {
      return null;
    }
    List<T> ret = new ArrayList<T>();
    int index = 0;
    int first = (pageIndex - 1) * pageSize;
    for (int i = 0; i < pageSize; i++) {
      index = first + i;
      if (index < data.size()) {
        ret.add(data.get(index));
      } else {
        break;
      }
    }
    return ret;
  }

  public static void main(String[] args) {
    List<String> al = new ArrayList<String>();
    for (int i = 0; i < 25; i++) {
      al.add("AA" + (i + 1));
    }
    PagerModel<String> page = new PagerModel<String>(al);
    List<String> ret = page.getPagedData(3);
    for (int i = 0; i < ret.size(); i++) {
      System.out.println(ret.get(i));
    }
    System.out.println(page.getTotalPages());
  }
}
