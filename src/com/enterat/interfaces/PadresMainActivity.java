package com.enterat.interfaces;

import com.enterat.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class PadresMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);				
		setContentView(R.layout.activity_padres_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = new MenuInflater(this);
		inflater.inflate(R.menu.menu_main, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		
			case R.id.menu_settings:
				//startActivity(new Intent(this, AlumneSettingsActivity.class));
			break;		

		default:
			break;
		}
		return true;
	}	
	
	public void infoClick(View v) {
		//
	}	
	
	public void asignaturasClick(View v) {
		//
	}	
	
	public void tasksClick(View v) {
		startActivity(new Intent(this, PadresTasksActivity.class));
	}
	
	public void examClick(View v) {
		startActivity(new Intent(this, PadresExamsActivity.class));
	}	
	
	public void notesClick(View v) {
		//
	}
	
	public void calendarioClick(View v) {
		//
	}	
	
	public void faltasClick(View v) {
		//
	}	
	
	public void notificationClick(View v) {
		//
	}
	
	public void webClick(View v) {
		String url = "http://www.escolaeldrac.com";
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}
	
	public void facebookClick(View v) {
		String url = "http://www.facebook.com/escolaeldrac";
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}
}