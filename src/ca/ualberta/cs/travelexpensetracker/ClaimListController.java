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
