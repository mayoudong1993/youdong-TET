package ca.ualberta.cs.travelexpensetracker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddItemActivity extends Activity {
	private TextView itemname;
	private Button fia;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item);
		Bundle extras = getIntent().getExtras();
		itemname = (TextView) findViewById(R.id.editItemName);
		fia = (Button) findViewById(R.id.finishAddAnItem);
		ClaimListManager.initManager(this.getApplicationContext());
		final int temp = extras.getInt("id");
		if (extras.size() == 2) {
			final int pos = extras.getInt("pos");
			Item openeditem = ClaimListController.getClaimList().getPosition(temp).getPosition(pos);
			String iname = openeditem.getiName();
			Toast.makeText(this, iname, Toast.LENGTH_SHORT).show();
			itemname.setText(iname);
			fia.setOnClickListener(new UpdateItem());
		}
		if (extras.size() == 1){
			fia.setOnClickListener(new UaddItem());
		}

	}
	public class UpdateItem implements OnClickListener{
		public void onClick(View v) {
			itemname = (EditText) findViewById(R.id.editItemName);
			Bundle extras = getIntent().getExtras();
			int temp = extras.getInt("id");
			int pos = extras.getInt("pos");
			ClaimListController.getClaimList().getPosition(temp).getPosition(pos).setiName(itemname.getText().toString());
			Intent intent = new Intent(AddItemActivity.this,
					OpenClaimActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("id", temp);
			ClaimListController.saveClaimList();
			startActivity(intent);	
			
		}
		
	}
	
	public class UaddItem implements OnClickListener{
		public void onClick(View v) {
			itemname = (EditText) findViewById(R.id.editItemName);
			Bundle extras = getIntent().getExtras();
			int temp = extras.getInt("id");
			Item newitem = new Item(itemname.getText().toString());
			ClaimListController.getClaimList().getPosition(temp).addItem(newitem);
			Intent intent = new Intent(AddItemActivity.this,
					OpenClaimActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("id", temp);
			ClaimListController.saveClaimList();
			startActivity(intent);	
		}
	}
	
	public void saveItem(View v) {
		Toast.makeText(this, "addItemClaim", Toast.LENGTH_SHORT).show();
		itemname = (EditText) findViewById(R.id.editItemName);
		Bundle extras = getIntent().getExtras();
		int temp = extras.getInt("id");
		Item newitem = new Item(itemname.getText().toString());
		ClaimListController.getClaimList().getPosition(temp).addItem(newitem);
		Intent intent = new Intent(AddItemActivity.this,
				OpenClaimActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("id", temp);
		startActivity(intent);
	}

}
