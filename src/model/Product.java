package model;

import java.util.HashMap;

public class Product {
    private int id;
    private int itemnum;
    private int buyamount;
    HashMap<String,String> map ;
    private String buydate;
    
    

	public HashMap<String, String> getMap() {
		return map;
	}
	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getItemnum() {
		return itemnum;
	}
	public void setItemnum(int itemnum) {
		this.itemnum = itemnum;
	}
	public int getBuyamount() {
		return buyamount;
	}
	public void setBuyamount(int buyamount) {
		this.buyamount = buyamount;
	}
	public String getBuydate() {
		return buydate;
	}
	public void setBuydate(String buydate) {
		this.buydate = buydate;
	}




}
