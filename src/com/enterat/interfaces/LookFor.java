package com.enterat.interfaces;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.enterat.R;

public class LookFor extends Activity{
	
protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_look_for);
}


//Boton publicar
public void publicarClick2(View v) {
	
	Intent intent = new Intent(this, ProfesorAdd.class);
    startActivity(intent);
	
}


}
