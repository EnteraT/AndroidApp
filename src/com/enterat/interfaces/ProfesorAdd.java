package com.enterat.interfaces;

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
import com.enterat.bda.Alumno;
import com.enterat.bda.Asignatura;
import com.enterat.bda.Examen;
import com.enterat.bda.Tarea;
import com.enterat.util.Constantes;

public class ProfesorAdd extends Activity{

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profesor_add);
		Spinner sp = (Spinner) findViewById(R.id.Tipo_Spinner_t);

		SharedPreferences pref = getSharedPreferences("datos", this.MODE_PRIVATE);
		String a = pref.getString("type", " ");

		if(a=="Anuncio")
			sp.setSelection(Constantes.SP_ANUNCIO);
		else if(a=="Tarea")
			sp.setSelection(Constantes.SP_TAREA);
		else if(a=="Incidencia")
			sp.setSelection(Constantes.SP_INCIDENCIA);
		else if(a=="Examen")
			sp.setSelection(Constantes.SP_EXAMEN);
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
		Integer year=prefe.getInt("a�o",0);
		Integer month=prefe.getInt("mes",0);
		Integer day=prefe.getInt("dia",0);
		DatePicker date=(DatePicker) findViewById(R.id.DatePicker_t);
		// si la fecha esta a cero es que no hemos guardado nada y se quedara la que sale por defecto
		if(year==0){
		}
		else{
			date.updateDate(year,month,day);
		}

		// Recuperamos la posici�n del espinner
		Integer posicion=prefe.getInt("posicionsp",0);
		Spinner sp2 = (Spinner) findViewById(R.id.asignatura_Spinner_t);
		sp2.setSelection(posicion);

	}

	//Boton publicar
	public void publicarClick(View v) {


		//Este es el bucle para controlar que esta checkeado el checkbox
		CheckBox check=(CheckBox) findViewById(R.id.check_all_class);
		if(check.isChecked()){

			//TODO recuperar bien el objeto asignatura
			Asignatura asignatura = new Asignatura();
			asignatura.setId_asignatura(1);
			
			//TODO recuperar bien el objeto alumno
			Alumno alumno = new Alumno();
			alumno.setId_alumno(1);
			
			EditText conten  = (EditText)findViewById(R.id.contenido_t);
			String contenido = conten.getText().toString();				
			
			DatePicker fechaPicker = (DatePicker) findViewById(R.id.DatePicker_t);								
			
	        Integer dobYear  = fechaPicker.getYear();
	        Integer dobMonth = fechaPicker.getMonth();
	        Integer dobDate  = fechaPicker.getDayOfMonth();
	        
	        StringBuilder sb = new StringBuilder();
	        
	        String separacion1 = "-";
	        String separacion2 = "-";		        
	        if (dobMonth < 10) separacion1 = "-0";
	        if (dobDate < 10)  separacion2 = "-0";
	        
	        sb.append(dobYear.toString()).append(separacion1).append(dobMonth.toString()).append(separacion2).append(dobDate.toString());
	        String fecha = sb.toString();		        
	        
			//TODO crear campo observaciones ??? en la bda est� el campo...
			String observaciones = "";
			
			//Recuperar el tipo de lo que queremos insertar
			Spinner sp = (Spinner) findViewById(R.id.Tipo_Spinner_t);			
			int tipo = sp.getSelectedItemPosition();				
			
			switch(tipo) {
			case Constantes.SP_ANUNCIO: 
				//Anuncio
		        
				Toast.makeText(getApplicationContext(),"Anuncio publicado correctamente", Toast.LENGTH_LONG).show();
				
				break;
			case Constantes.SP_TAREA: 
				//Tarea
				Tarea tarea = new Tarea();				
				
				tarea.setId_tarea(0);
				tarea.setAsignatura(asignatura);
				tarea.setAlumno(alumno);
				tarea.setContenido(contenido);
				tarea.setFecha(fecha);
				tarea.setObservaciones(observaciones);
				tarea.setLeido(0);				
				
				//Insertar Tarea...
				if (tarea.insertarTarea() == 0){
					//ERROR
					Toast.makeText(getApplicationContext(),"Tarea NO publicada", Toast.LENGTH_LONG).show();
				}
				else{
					//INSERTADA OK
					Toast.makeText(getApplicationContext(),"Tarea publicada correctamente", Toast.LENGTH_LONG).show();					
				}
				
				break;
			case Constantes.SP_INCIDENCIA: 
				//Incidencia
				
				Toast.makeText(getApplicationContext(),"Incidencia publicada correctamente", Toast.LENGTH_LONG).show();
				
				break;
			case Constantes.SP_EXAMEN: 
				//Examen
			
				Examen examen = new Examen();				
				
				examen.setId_examen(0);
				examen.setAsignatura(asignatura);
				examen.setAlumno(alumno);
				examen.setContenido(contenido);
				examen.setFecha(fecha);
				examen.setLeido(0);				
				
				//Insertar Examen...
				if (examen.insertarExamen() == 0){
					//ERROR
					Toast.makeText(getApplicationContext(),"Examen NO publicado", Toast.LENGTH_LONG).show();
				}
				else{
					//INSERTADO OK
					Toast.makeText(getApplicationContext(),"Examen publicado correctamente", Toast.LENGTH_LONG).show();					
				}
				
				break;			 
			default: 
				//error !!
				break;
			}			
				
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

		editar.putInt("a�o", year);
		editar.putInt("mes", month);
		editar.putInt("dia", day);

		// guardamos la posici�n de los spinner
		Spinner sp=(Spinner) findViewById(R.id.asignatura_Spinner_t);
		Integer posicion=sp.getPositionForView(sp);
		editar.putInt("posicionsp",posicion);

		editar.commit( );

	}

}