package com.enterat.interfaces;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
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
		// Recuperamos datos al rotar pantalla o al volver a esta activity
		SharedPreferences prefe = getSharedPreferences("guardado_profadd",Context.MODE_PRIVATE);
		
		// Recuperamos lo escrito en el edit-text
		String text_contenido = prefe.getString("contenido","");
		EditText contenido = (EditText)findViewById(R.id.contenido_t);
		contenido.setText(text_contenido);
		
		// Recuperamos el estado del check-box
		Boolean stat_check_box=prefe.getBoolean("stat_check_box",true);
		CheckBox check=(CheckBox) findViewById(R.id.check_all_class);
		if(stat_check_box==true){
			check.setChecked(true);
		}
		else if(stat_check_box==false){
			check.setChecked(false);
		}
		
		// Recuperamos fecha del date-picker
		Integer year=prefe.getInt("año",0);
		Integer month=prefe.getInt("mes",0);
		Integer day=prefe.getInt("dia",0);
		DatePicker date=(DatePicker) findViewById(R.id.DatePicker_t);
		// si la fecha esta a cero es que no hemos guardado nada y se quedara la que sale por defecto
		if(year==0){
		}
		else{
			date.updateDate(year,month,day);
		}
		
		// Recuperamos la posición del espinner
		Integer posicion=prefe.getInt("posicionsp",0);
		Spinner sp2 = (Spinner) findViewById(R.id.asignatura_Spinner_t);
		sp2.setSelection(posicion);
		
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

@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	SharedPreferences pref = getSharedPreferences("guardado_profadd",Context.MODE_PRIVATE);
	SharedPreferences.Editor editar=pref.edit( );
	
	// guardamos el texto del edit-text
	EditText contenido = (EditText)findViewById(R.id.contenido_t);
	String text_contenido = contenido.getText().toString();
	editar.putString("contenido",text_contenido);
	
	// guardamos el estado del check-box
	CheckBox check=(CheckBox) findViewById(R.id.check_all_class);
	if(check.isChecked()){
		editar.putBoolean("stat_check_box",true);
	}
	else if(check.isChecked()==false){
		editar.putBoolean("stat_check_box",false);
	}
	
	// guardamos la fecha del data_picke
	DatePicker date=(DatePicker) findViewById(R.id.DatePicker_t);
	Integer year=date.getYear();
	Integer month=date.getMonth();
	Integer day=date.getDayOfMonth();
	
	editar.putInt("año", year);
	editar.putInt("mes", month);
	editar.putInt("dia", day);
	
	// guardamos la posición de los spinner
	Spinner sp=(Spinner) findViewById(R.id.asignatura_Spinner_t);
	Integer posicion=sp.getPositionForView(sp);
	editar.putInt("posicionsp",posicion);
	
	editar.commit( );
	
}



}