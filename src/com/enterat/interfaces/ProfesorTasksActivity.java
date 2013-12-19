package com.enterat.interfaces;

import com.enterat.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Toast;

public class ProfesorTasksActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profesor_write);
	}

	public void publicarDeureClick(View v) {
		
		//Mostrar Toast OK
		Toast.makeText(this, getResources().getString(R.string.publicar_deure_ok_txt), Toast.LENGTH_LONG).show();
		
		//Cerrar Activity
		this.finish();
	}
	
}