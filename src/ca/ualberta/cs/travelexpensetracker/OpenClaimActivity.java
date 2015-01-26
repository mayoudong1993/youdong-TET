package ca.ualberta.cs.travelexpensetracker;

import java.util.ArrayList;
import java.util.Collection;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;


public class OpenClaimActivity extends Activity {
	private ListView listView;
	private Button btm;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_item);
		
		ClaimListManager.initManager(this.getApplicationContext());
		
		Bundle extras = getIntent().getExtras();
		final int temp = extras.getInt("id");
		
		btm = (Button) findViewById(R.id.backToMain);
		listView = (ListView) findViewById(R.id.itemlistView);
		Collection<Item> Items = ClaimListController.getClaimList().getPosition(temp).getItem();
		final ArrayList<Item> items = new ArrayList<Item>(Items);
		final ArrayAdapter<Item> itemAdapter = new ArrayAdapter<Item>(this,
				android.R.layout.simple_list_item_1, items);
		listView.setAdapter(itemAdapter);
		
		ClaimListController.getClaimList().getPosition(temp).addListener(new Listener() {

			@Override
			public void update() {
				// TODO Auto-generated method stub
				items.clear();
				Collection<Item> Items = ClaimListController
						.getClaimList().getPosition(temp).getItem();
				items.addAll(Items);
				itemAdapter.notifyDataSetChanged();
			}
		});
		btm.setOnClickListener(new Back_click());
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				int pos     = position;
				Toast.makeText(OpenClaimActivity.this, "open a item"+pos,
						Toast.LENGTH_SHORT).show();
				
				Intent intent = new Intent(OpenClaimActivity.this,
						AddItemActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("pos", pos);
				intent.putExtra("id", temp);
				startActivity(intent);
			}

		});
		
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> adapterView,
					View view, int position, long id) {
				AlertDialog.Builder adb = new AlertDialog.Builder(
						OpenClaimActivity.this);
				adb.setMessage("Edit/Delete " + items.get(position).toString()
						+ "?");
				adb.setCancelable(true);
				final int finalPosition = position;

				adb.setNeutralButton("Edit", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(OpenClaimActivity.this,
								AddItemActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.putExtra("pos", finalPosition);
						intent.putExtra("id", temp);
						startActivity(intent);
					}
				});
				adb.setPositiveButton("Delete", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						AlertDialog.Builder adb = new AlertDialog.Builder(
								OpenClaimActivity.this);
						adb.setMessage("Are you sure to delete?");
						adb.setCancelable(true);
						adb.setNegativeButton("Delete", new OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Item item = items.get(finalPosition);
								ClaimListController.getClaimList().getPosition(temp).removeItem(
										item);
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
	
	private class Back_click implements OnClickListener, android.view.View.OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(OpenClaimActivity.this, MainActivity.class);
			startActivity(intent);
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			Intent intent = new Intent(OpenClaimActivity.this, MainActivity.class);
			startActivity(intent);
		}
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.open_claim, menu);
		return true;
	}

	public void addaitem(View v){
		//Toast.makeText(this, "add new item", Toast.LENGTH_SHORT).show();
		Bundle extras = getIntent().getExtras();
		int temp = extras.getInt("id");
		Intent intent = new Intent(OpenClaimActivity.this, AddItemActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("id", temp);
		startActivity(intent);
	}
}
