package ca.ualberta.cs.travelexpensetracker;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -968082381799652486L;
	protected String itemName;
	protected Date startdate;
	protected String category;
	protected int amount;
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

	public void setStartdate(Date date) {
		this.startdate = date;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBAmount() {
		String myset = "";
		try {
			myset = Integer.toString(amount);
			} catch(NumberFormatException nfe) {
			// Handle parse error.
			}
		return myset;
	}

	public int getAmount() {
		return this.amount;
	}
	
	public void setAmount(String amount) {
		int myset = 0;
		try {
			myset = Integer.parseInt(amount);

			} catch(NumberFormatException nfe) {
			// Handle parse error.
		}
		this.amount = myset;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}