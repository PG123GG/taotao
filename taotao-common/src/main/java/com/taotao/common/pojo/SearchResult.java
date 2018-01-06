package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable{

	private Long pagerows;
	private Long recordCount;
	private List<SearchItem> itemList;
	
	
	public List<SearchItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
	public Long getPagerows() {
		return pagerows;
	}
	public void setPagerows(Long pagerows) {
		this.pagerows = pagerows;
	}
	public Long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}
}
