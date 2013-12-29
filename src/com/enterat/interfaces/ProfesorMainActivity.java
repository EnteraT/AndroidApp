package com.enterat.interfaces;

import com.enterat.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class ProfesorMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);				
		setContentView(R.layout.activity_profesor_main);
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
				//startActivity(new Intent(this, ProfesorSettingsActivity.class));
			break;		

		default:
			break;
		}
		return true;
	}	
	
	public void publicarAnuncio(View v) {
		guardar(this,"Anuncio");
		startActivity(new Intent(this, ProfesorAdd.class));
	}
	
	public void publicarExamen(View v) {
		guardar(this,"Examen");
		startActivity(new Intent(this, ProfesorAdd.class));
	}	
	
	public void publicarIncidencia(View v) {
		guardar(this,"Incidencia");
		startActivity(new Intent(this, ProfesorAdd.class));
	}
	
	public void publicarTarea(View v) {
		guardar(this,"Tarea");
		startActivity(new Intent(this, ProfesorAdd.class));
	}
	
	public void verAsignatura(View v) {
		startActivity(new Intent(this, AsignaturaActivity.class));
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
	public void guardar(Context c,String s) {
        SharedPreferences preferencias=getSharedPreferences("datos",c.MODE_PRIVATE);
        Editor editor=preferencias.edit();
        editor.putString("type", s);
        editor.commit();
        finish();
    }
}