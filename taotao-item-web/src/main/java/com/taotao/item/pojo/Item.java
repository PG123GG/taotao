package com.taotao.item.pojo;

import org.springframework.util.StringUtils;

import com.taotao.pojo.TbItem;

public class Item extends TbItem {

	public Item() {
		// TODO Auto-generated constructor stub
	}
	
	public Item(TbItem tbItem){
		this.setId(tbItem.getId());
		this.setTitle(tbItem.getTitle());
		this.setSellPoint(tbItem.getSellPoint());
		this.setPrice(tbItem.getPrice());
		this.setNum(tbItem.getNum());
		this.setBarcode(tbItem.getBarcode());
		this.setCid(tbItem.getCid());
		this.setStatus(tbItem.getStatus());
		this.setCreated(tbItem.getCreated());
		this.setUpdated(tbItem.getUpdated());
	}
	
	public String[] getImages(){
		if( !StringUtils.isEmpty(this.getImage()) ){
			return this.getImage().split(",");
		}
		return null;
	}
	
}
