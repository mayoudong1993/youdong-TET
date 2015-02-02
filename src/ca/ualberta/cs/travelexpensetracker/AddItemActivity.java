package ca.ualberta.cs.travelexpensetracker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;

public class AddItemActivity extends Activity implements OnClickListener {
	private TextView itemname;
	private Button fia;
	private Spinner unit;
	private TextView showDa;
	private TextView category;
	private TextView money;
	private Button pickdate;
	private int year = 2014;
	private int month = 11;
	private int day = 1;
	private String iunit;
	private Claim claim;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item);
		Bundle extras = getIntent().getExtras();

		pickdate = (Button) findViewById(R.id.setTheDate);
		pickdate.setOnClickListener(this);
		money = (TextView) findViewById(R.id.editExpense);
		showDa = (TextView) findViewById(R.id.editDate);
		itemname = (TextView) findViewById(R.id.editItemName);
		category = (TextView) findViewById(R.id.editCategory);
		unit = (Spinner) findViewById(R.id.setunit);
		fia = (Button) findViewById(R.id.finishAddAnItem);
		// get date for spinner
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				AddItemActivity.this, android.R.layout.simple_spinner_item,
				getData());
		unit.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				iunit = adapter.getItem(position);
				Toast.makeText(AddItemActivity.this, "sdf" + position,
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		ClaimListManager.initManager(this.getApplicationContext());
		final int temp = extras.getInt("id");
		if (extras.size() == 2) {
			final int pos = extras.getInt("pos");
			claim = ClaimListController.getClaimList().getPosition(temp);
			Item openeditem = claim.getPosition(pos);
			String iname = openeditem.getiName();
			String icategory = openeditem.getCategory();
			String imoney = openeditem.getBAmount();
			Date startdate = openeditem.getStartdate();
			iunit = openeditem.getUnit();

			adapter.remove(iunit);
			adapter.insert(iunit, 0);
			year = startdate.getYear();
			month = startdate.getMonth();
			day = startdate.getDate();
			itemname.setText(iname);
			category.setText(icategory);
			money.setText(imoney);
			showDate(year, month + 1, day);
			unit.setAdapter(adapter);
			fia.setOnClickListener(new UpdateItem());

		}
		if (extras.size() == 1) {
			unit.setAdapter(adapter);
			fia.setOnClickListener(new UaddItem());
			showDate(2014, 12, 1);
		}

	}

	private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
			showDate(arg1, arg2 + 1, arg3);
		}
	};

	// update an item
	public class UpdateItem implements OnClickListener {
		@SuppressWarnings("deprecation")
		public void onClick(View v) {
			if (claim.getState() == "approved") {
				Toast.makeText(AddItemActivity.this, "Can't edit",
						Toast.LENGTH_SHORT).show();
			} else {
				Bundle extras = getIntent().getExtras();
				int temp = extras.getInt("id");
				int pos = extras.getInt("pos");
				Item item1 = ClaimListController.getClaimList()
						.getPosition(temp).getPosition(pos);
				item1.getStartdate();
				item1.setiName(itemname.getText().toString());
				item1.setCategory(category.getText().toString());
				item1.setUnit(iunit);
				item1.setAmount(money.getText().toString());
				Date start = new Date();
				start.setDate(day);
				start.setYear(year);
				start.setMonth(month);
				item1.setStartdate(start);
				Intent intent = new Intent(AddItemActivity.this,
						OpenClaimActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("id", temp);
				ClaimListController.saveClaimList();
				startActivity(intent);
			}
		}

	}

	// add an item
	public class UaddItem implements OnClickListener {
		@SuppressWarnings("deprecation")
		public void onClick(View v) {
			Bundle extras = getIntent().getExtras();
			int temp = extras.getInt("id");
			Item newitem = new Item(itemname.getText().toString());
			newitem.setUnit(iunit);
			newitem.setCategory(category.getText().toString());
			newitem.setAmount(money.getText().toString());
			Date start = new Date();
			start.setDate(day);
			start.setYear(year);
			start.setMonth(month);
			newitem.setStartdate(start);
			ClaimListController.getClaimList().getPosition(temp)
					.addItem(newitem);
			Intent intent = new Intent(AddItemActivity.this,
					OpenClaimActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("id", temp);
			ClaimListController.saveClaimList();
			startActivity(intent);
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setTheDate:
			// show date picker dialog
			DatePickerDialog datePicker = new DatePickerDialog(
					AddItemActivity.this, new OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							showDate(year, monthOfYear + 1, dayOfMonth);
						}
					}, this.year, this.month, this.day);
			datePicker.show();
			break;
		}
	}

	// show date in textview
	public void showDate(int year, int month, int day) {
		this.year = year;
		this.month = month - 1;
		this.day = day;
		showDa.setText(new StringBuilder().append("Date:   ").append(day)
				.append("/").append(month).append("/").append(year));
	}

	// set up data for spinner
	private List<String> getData() {
		List<String> dataList = new ArrayList<String>();
		dataList.add("CAD");
		dataList.add("USD");
		dataList.add("EUR");
		dataList.add("GBP");
		return dataList;
	}
}
