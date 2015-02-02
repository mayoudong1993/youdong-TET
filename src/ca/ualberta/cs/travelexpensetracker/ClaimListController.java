package ca.ualberta.cs.travelexpensetracker;

import java.io.IOException;

public class ClaimListController {
	private static ClaimList claimlist = null;

	static public ClaimList getClaimList() {
		if (claimlist == null) {
			try {
				claimlist = ClaimListManager.getManager().loadClaimList();
				claimlist.addListener(new Listener() {
					public void update() {
						saveClaimList();
					}
				});
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(
						"Could not deserialize StudentList from StudentListManager");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(
						"Could not deserialize StudentList from StudentListManager");
			}
		}
		return claimlist;
	}
	//sort claim list by date
	static public void sort() {
		for (int i = 0; i < (claimlist.size() - 1); i++) {
			for (int j = i; j < (claimlist.size() - 1 - i); j++) {
				if (claimlist.getPosition(j).calculatedate() > claimlist
						.getPosition(j+1).calculatedate()) {
					Claim bigger = claimlist.getPosition(j);
					claimlist.removeClaim(bigger);
					claimlist.addClaim(bigger);
				}else{
					Claim bigger = claimlist.getPosition(j+1);
					claimlist.removeClaim(bigger);
					claimlist.addClaim(bigger);
				}
				
			}
		}
	}
	//Save Claim list
	static public void saveClaimList() {
		try {
			ClaimListManager.getManager().saveClaimList(getClaimList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(
					"Could not deserialize StudentList from StudentListManager");
		}
	}

	public void addClaim(Claim claim) {
		getClaimList().addClaim(claim);
	}
}
