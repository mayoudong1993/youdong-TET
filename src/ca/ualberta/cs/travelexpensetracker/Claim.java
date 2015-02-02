package ca.ualberta.cs.travelexpensetracker;

import java.io.Serializable;
import java.util.Date;
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
	protected String state;
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
		return getName()+ "     " +getState();
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

	public void setStartdate(Date sd) {
		this.startdate = sd;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date ed) {
		this.enddate = ed;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public int calculatedate(){
		@SuppressWarnings("deprecation")
		int i = getStartdate().getDate() + getStartdate().getMonth()*100 +getStartdate().getYear()*10000;
		return i;
	}
	
	public int size(){
		return itemList.size();
		
	}
	
	public String summary(){
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		int i = 0;
		for (; i < size() ; i++){
			if (getPosition(i).getUnit().equals( "CAD")){
				a += getPosition(i).getAmount();
			}else if(getPosition(i).getUnit().equals("USD")){
				b += getPosition(i).getAmount();
			}else if(getPosition(i).getUnit().equals("EUR")){
				c += getPosition(i).getAmount();
			}else if(getPosition(i).getUnit().equals("GBP")){
				d += getPosition(i).getAmount();
			}
		}
		return "TotalSpend:  \n"+a+" CAD\n"+b+" USD\n"+c+" EUR\n"+d+" GBP\n";
		
	}
}
