package ca.ualberta.cs.travelexpensetracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class ClaimList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2034141455508874244L;

	protected ArrayList<Claim> claimList;
	protected transient ArrayList<Listener> listeners;

	public ClaimList() {
		claimList = new ArrayList<Claim>();
		listeners = new ArrayList<Listener>();
	}

	private ArrayList<Listener> getListeners() {
		if (listeners == null) {
			listeners = new ArrayList<Listener>();
		}
		return listeners;
	}

	public Collection<Claim> getClaimList() {
		return claimList;
	}

	public void addClaim(Claim newClaim) {
		claimList.add(newClaim);
		notifyListener();
	}

	private void notifyListener() {
		for (Listener listener : listeners) {
			listener.update();
		}

	}

	public void removeClaim(Claim oldClaim) {
		claimList.remove(oldClaim);
		notifyListener();
	}

	public void addListener(Listener l) {
		getListeners().add(l);

	}

	public Claim getPosition(int position) {
		return claimList.get(position);
	}

	public void removeListener(Listener l) {
		getListeners().remove(l);

	}
	
	public int size() {
		return claimList.size();
		}
}
