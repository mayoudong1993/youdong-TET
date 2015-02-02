package ca.ualberta.cs.travelexpensetracker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.view.View.OnClickListener;

public class Add_ClaimActivity extends Activity implements OnClickListener {
	private TextView ecn;
	private Button fca;
	private TextView des;
	private int year = 2015;
	private int month = 0;
	private int day = 1;
	private TextView startDateView;
	private TextView endDateView;
	private Button sd;
	private Button ed;
	private Date start = new Date();
	private Date end = new Date();
	private int State = 0;
	private Spinner istate;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_claim);
		ClaimListManager.initManager(this.getApplicationContext());
		ecn = (TextView) findViewById(R.id.editClaimName);
		sd = (Button) findViewById(R.id.setStartDate);
		sd.setOnClickListener(this);
		ed = (Button) findViewById(R.id.setEndDate);
		ed.setOnClickListener(this);
		fca = (Button) findViewById(R.id.finilAddClaim);
		des = (TextView) findViewById(R.id.editDes);
		startDateView = (TextView) findViewById(R.id.showStart);
		endDateView = (TextView) findViewById(R.id.showEnd);
		istate = (Spinner) findViewById(R.id.stateSpinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				Add_ClaimActivity.this, android.R.layout.simple_spinner_item,
				getData());
		istate.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(Add_ClaimActivity.this,
						parent.getItemAtPosition(position).toString(),
						Toast.LENGTH_SHORT).show();
				if (parent.getItemAtPosition(position).toString() == "approved"){
					State = 1; 
				}else{
					State = 0;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			int temp = extras.getInt("id");
			Claim openedclaim = ClaimListController.getClaimList().getPosition(
					temp);
			String name = openedclaim.getName();
			String claimdes = openedclaim.getDes();
			State = openedclaim.getState();
			String astr = adapter.getItem(State);
			adapter.remove(astr);
			adapter.insert(astr, 0);
			istate.setAdapter(adapter);
			start = openedclaim.getStartdate();
			day = start.getDate();
			year = start.getYear();
			month = start.getMonth();
			showDate(year, month + 1, day);
			end = openedclaim.getEnddate();
			day = end.getDate();
			year = end.getYear();
			month = end.getMonth();
			showEndDate(year, month + 1, day);
			ecn.setText(name);
			des.setText(claimdes);
			fca.setOnClickListener(new UpdateClaim(openedclaim));

		}
		if (extras == null) {
			fca.setOnClickListener(new AddClaim());
			showDate(year, month + 1, day);
			showEndDate(year, month + 1, day);
			istate.setAdapter(adapter);
		}
	}

	private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {

			showDate(arg1, arg2 + 1, arg3);
			showEndDate(arg1, arg2 + 1, arg3);
		}
	};

	@SuppressWarnings("deprecation")
	public void showDate(int year, int month, int day) {
		start.setDate(day);
		start.setMonth(month - 1);
		start.setYear(year);
		startDateView.setText(new StringBuilder().append(day).append("/")
				.append(month).append("/").append(year));
	}

	@SuppressWarnings("deprecation")
	public void showEndDate(int year, int month, int day) {
		end.setDate(day);
		end.setMonth(month - 1);
		end.setYear(year);
		endDateView.setText(new StringBuilder().append(day).append("/")
				.append(month).append("/").append(year));
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
			Claim newclaim = new Claim(ecn.getText().toString());
			newclaim.setDes(des.getText().toString());
			newclaim.setStartdate(start);
			newclaim.setEnddate(end);
			clc.addClaim(newclaim);
			ClaimListController.sort();
			ClaimListController.saveClaimList();
			Intent intent = new Intent(Add_ClaimActivity.this,
					MainActivity.class);
			startActivity(intent);
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setStartDate:
			DatePickerDialog datePicker = new DatePickerDialog(
					Add_ClaimActivity.this, new OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							showDate(year, monthOfYear + 1, dayOfMonth);
						}
					}, this.year, this.month, this.day);
			datePicker.show();
			break;

		case R.id.setEndDate:
			DatePickerDialog datePicker2 = new DatePickerDialog(
					Add_ClaimActivity.this, new OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							showEndDate(year, monthOfYear + 1, dayOfMonth);
						}
					}, this.year, this.month, this.day);
			datePicker2.show();
			break;
		}

	}

	private class UpdateClaim implements OnClickListener {
		private Claim claim;

		public UpdateClaim(Claim claim) {
			this.setClaim(claim);
		}

		public void onClick(View v) {
			if (State == 1) {
				Toast.makeText(Add_ClaimActivity.this, "Can't change",
						Toast.LENGTH_SHORT).show();
				this.getClaim().setState(State);
			} else {
				this.getClaim().setState(State);
				this.getClaim().setName(ecn.getText().toString());
				this.getClaim().setDes(des.getText().toString());
				this.getClaim().setStartdate(start);
				this.getClaim().setEnddate(end);
				ClaimListController.sort();
				ClaimListController.saveClaimList();
				Intent intent = new Intent(Add_ClaimActivity.this,
						MainActivity.class);
				startActivity(intent);
			}
		}

		public Claim getClaim() {
			return claim;
		}

		public void setClaim(Claim claim) {
			this.claim = claim;
		}
	}

	private List<String> getData() {
		List<String> dataList = new ArrayList<String>();
		dataList.add("returned");
		dataList.add("approved");
		return dataList;
	}
}
