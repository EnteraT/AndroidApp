package com.enterat.interfaces;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.enterat.R;

public class ProfesorAdd extends Activity{
	
protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profesor_add);
		Spinner sp = (Spinner) findViewById(R.id.Tipo_Spinner_t);
		
		SharedPreferences pref = getSharedPreferences("datos", this.MODE_PRIVATE);
		String a = pref.getString("type", " ");
		
		if(a=="Anuncio")
			sp.setSelection(1);
		else if(a=="Tarea")
			sp.setSelection(2);
		else if(a=="Incidencia")
			sp.setSelection(0);
		else if(a=="Examen")
			sp.setSelection(3);
		else
			;
}

//Boton publicar
public void publicarClick(View v) {
	
	
	//Este es el bucle para controlar que esta checkeado el checkbox
	CheckBox check=(CheckBox) findViewById(R.id.check_all_class);
	if(check.isChecked()){
	Toast.makeText(getApplicationContext(),"Se ha publicado correctamente",Toast.LENGTH_LONG).show();	
	}
	else if(check.isChecked()==false){
	Intent intent = new Intent(this, LookFor.class);
    startActivity(intent);
	}
}


}