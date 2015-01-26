package ca.ualberta.cs.travelexpensetracker;

import java.io.Serializable;
import java.sql.Date;

public class Item implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -968082381799652486L;
	protected String itemName;
	protected Date startdate;
	protected String category;
	protected Double amount;
	protected String unit;
	
	public Item(String iName){
		this.setiName(iName);
	}
	
	public void setiName(String iName) {
		this.itemName = iName;
		
	}

	public String getiName(){
		return this.itemName;
	}
	
	public String toString(){
		return getiName();
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}