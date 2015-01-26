package ca.ualberta.cs.travelexpensetracker;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

public class Claim implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4936166458224246171L;
	protected String claimName; 
	protected Date startdate;
	protected Date enddate;
	protected String des;
	
	protected ArrayList<Item> itemList;
	protected transient ArrayList<Listener> listenersss;
	
	public Claim(String string){
		this.claimName = string;
		itemList = new ArrayList<Item>();
		listenersss = new ArrayList<Listener>();
	}
	
	private ArrayList<Listener> getListeners() {
		if (listenersss == null) {
			listenersss = new ArrayList<Listener>();
		}
		return listenersss;
	}
	
	public Collection<Item> getItem(){
		return itemList;
	}
	public String getName(){
		return claimName;
	}
	
	public void setName(String s){
		claimName = s;
	}
	
	public void addItem(Item newItem){
		itemList.add(newItem);
		notifyListener();
	}
	
	private void notifyListener() {
		for (Listener listener : listenersss) {
			listener.update();
		}
	}
	
	public void removeItem(Item oldItem){
		itemList.remove(oldItem);
		notifyListener();
	}

	public String toString(){
		return getName();
	}
	
	public void addListener(Listener l) {
		getListeners().add(l);

	}

	public Item getPosition(int position) {
		return itemList.get(position);
	}

	public void removeListener(Listener l) {
		getListeners().remove(l);

	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
}
