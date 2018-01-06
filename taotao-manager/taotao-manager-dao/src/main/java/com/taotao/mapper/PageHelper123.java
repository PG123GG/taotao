package com.taotao.mapper;

public class PageHelper123 {

	private Integer pageNo;
	private Integer pageSize;
	public PageHelper123() {
		super();
	}
	public PageHelper123(Integer pageNo, Integer pageSize) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
