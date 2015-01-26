package ca.ualberta.cs.travelexpensetracker;

import java.util.ArrayList;
import java.util.Collection;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ClaimListManager.initManager(this.getApplicationContext());
		
		ListView listView = (ListView) findViewById(R.id.listOfClaim);
		Collection<Claim> claims = ClaimListController.getClaimList()
				.getClaimList();
		final ArrayList<Claim> list = new ArrayList<Claim>(claims);
		final ArrayAdapter<Claim> claimAdapter = new ArrayAdapter<Claim>(this,
				android.R.layout.simple_list_item_1, list);
		listView.setAdapter(claimAdapter);

		ClaimListController.getClaimList().addListener(new Listener() {

			@Override
			public void update() {
				// TODO Auto-generated method stub
				list.clear();
				Collection<Claim> claimList = ClaimListController
						.getClaimList().getClaimList();
				list.addAll(claimList);
				claimAdapter.notifyDataSetChanged();
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				int itemPosition     = position;
				Toast.makeText(MainActivity.this, "open a Claim"+itemPosition,
						Toast.LENGTH_SHORT).show();
				
				Intent intent = new Intent(MainActivity.this,
						OpenClaimActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("id", itemPosition);
				startActivity(intent);
			}

		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> adapterView,
					View view, int position, long id) {
				AlertDialog.Builder adb = new AlertDialog.Builder(
						MainActivity.this);
				adb.setMessage("Edit/Delete " + list.get(position).toString()
						+ "?");
				adb.setCancelable(true);
				final int finalPosition = position;

				adb.setNeutralButton("Edit", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(MainActivity.this,
								Add_ClaimActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.putExtra("id", finalPosition);
						startActivity(intent);
					}
				});
				adb.setPositiveButton("Delete", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						AlertDialog.Builder adb = new AlertDialog.Builder(
								MainActivity.this);
						adb.setMessage("Are you sure to delete?");
						adb.setCancelable(true);
						adb.setNegativeButton("Delete", new OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Claim claim = list.get(finalPosition);
								ClaimListController.getClaimList().removeClaim(
										claim);
							}
						});
						adb.setPositiveButton("Cancel", new OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}

						});
						adb.show();
					}
				});
				adb.setNegativeButton("Cancel", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				adb.show();
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void editClaims(MenuItem manu) {
		Toast.makeText(this, "editClaims", Toast.LENGTH_SHORT).show();
	}

	public void addNewClaim(View v) {
		Toast.makeText(this, "addNewClaim", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(MainActivity.this, Add_ClaimActivity.class);
		startActivity(intent);
	}
}
