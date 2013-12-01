package com.enterat.interfaces;

import com.enterat.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Toast;

public class ProfesorExamsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profesor_exams);
	}

	public void publicarControlClick(View v) {
		
		//Mostrar Toast OK
		Toast.makeText(this, getResources().getString(R.string.publicar_control_ok_txt), Toast.LENGTH_LONG).show();
				
		//Cerrar Activity
		this.finish();
	}
	
}