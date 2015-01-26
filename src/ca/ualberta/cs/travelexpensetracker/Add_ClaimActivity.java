package ca.ualberta.cs.travelexpensetracker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class Add_ClaimActivity extends Activity {
	private TextView ecn;
	private Button fca;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_claim);

		ClaimListManager.initManager(this.getApplicationContext());
		ecn = (TextView) findViewById(R.id.editClaimName);
		fca = (Button) findViewById(R.id.finilAddClaim);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			int temp = extras.getInt("id");
			Claim openedclaim = ClaimListController.getClaimList().getPosition(temp);
			String name = openedclaim.getName();
			ecn.setText(name);
			fca.setOnClickListener(new UpdateClaim(openedclaim));

		}

		if (extras == null) {
			fca.setOnClickListener(new AddClaim());
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add__claim, menu);
		return true;
	}

	private class AddClaim implements OnClickListener {
		public void onClick(View v) {
			
			ClaimListController clc = new ClaimListController();
			EditText claimname = (EditText) findViewById(R.id.editClaimName);
			clc.addClaim(new Claim(claimname.getText().toString()));
			Intent intent = new Intent(Add_ClaimActivity.this,
					MainActivity.class);
			startActivity(intent);
		}
	}
	
	private class UpdateClaim implements OnClickListener {
		private Claim claim;
		
		public UpdateClaim(Claim claim){
			this.setClaim(claim);
		}
		
		public void onClick(View v) {
			EditText claimname = (EditText) findViewById(R.id.editClaimName);
			this.getClaim().setName(claimname.getText().toString());
			Intent intent = new Intent(Add_ClaimActivity.this,
					MainActivity.class);
			startActivity(intent);
		}

		public Claim getClaim() {
			return claim;
		}

		public void setClaim(Claim claim) {
			this.claim = claim;
		}
	}

}
