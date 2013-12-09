package com.enterat.interfaces;

import com.enterat.R;
import com.enterat.bda.Usuario;
import com.enterat.services.Conexion;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//comentario a modificar
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		//Recuperar datos del usuario...
		Usuario user = recuperarPreferenciasLogIn();
		
		//...si existe
		if(user.getIdUsuario() != 0){
		
			//LogInAsyncTask task = new LogInAsyncTask();
			//
			//task.setUser( user.getUser() );
			//task.setPassword( user.getPassword() );
			//task.setContext( LoginActivity.this );
			//task.execute();
			
			if(user.getTipo() == Conexion.PROFESOR){
				Intent intent = new Intent(this, ProfesorMainActivity.class);
		        startActivity(intent);
			}
			else{
				if(user.getTipo() == Conexion.PADRE){
					Intent intent = new Intent(this, PadresMainActivity.class);
			        startActivity(intent);
				}
			}
			
			//Se destruye esta actividad
			finish();
		}
	}

	public void loginClick(View v) {
		
		//Comprobar que hay conexi�n a INTERNET !!!
		if (Conexion.isConnected( LoginActivity.this )) {
			
			TextView textUser = (TextView) findViewById(R.id.userEditText);
			TextView textPass = (TextView) findViewById(R.id.passEditText);
			
			final String user = textUser.getText().toString();
			final String pass = textPass.getText().toString();
			
			//
			LogInAsyncTask task = new LogInAsyncTask();
			task.setUser(user);
			task.setPassword(pass);
			task.setContext(LoginActivity.this);
			task.execute();
		}
		else{
			//Mostrar error de conexi�n
			showNoConnectionWarning();										
		}	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}	

	public void showNoConnectionWarning() {		
		//Mostrar TOAST --> NO INTERNET !!!	
		Toast.makeText(this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();		
	}
	
	//
	private Usuario recuperarPreferenciasLogIn() {
    	
		//
        SharedPreferences preferences = getSharedPreferences("LogIn", Context.MODE_PRIVATE);
        
        //
        Usuario user = new Usuario();
        user.setIdUsuario( preferences.getInt("idUsuario", 0) );
        user.setUser( preferences.getString("usuario", "") );
        user.setPassword( preferences.getString("password", "") );
        user.setTipo( preferences.getInt("tipo", 0) );  

        return user;
    }

	//
    private void guardarPreferenciasLogIn(Usuario user) {
    	
        //
        SharedPreferences preferences = getSharedPreferences("LogIn", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        //
        editor.putInt("idUsuario", user.getIdUsuario() );
        editor.putString("usuario", user.getUser() );
        editor.putString("password", user.getPassword());
        editor.putInt("tipo", user.getTipo() );

        editor.commit();
    }
    
	//**********************************************************************
	// TAREA AS�NCRONA --> LOG IN
	//**********************************************************************
	private class LogInAsyncTask extends AsyncTask <Void, Void, Usuario>{

		private String user, pass;
		private Context context;
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
		}
		
		@Override
		protected void onPostExecute(Usuario usuario) {

			String mensaje;
			//
			if(usuario.getIdUsuario() != 0){
				
				guardarPreferenciasLogIn( usuario );
				
				mensaje = getResources().getString(R.string.login_ok_txt);
								
				if(usuario.getTipo() == Conexion.PROFESOR){
					Intent intent = new Intent(context, ProfesorMainActivity.class);
			        startActivity(intent);
				}
				else{
					if(usuario.getTipo() == Conexion.PADRE){
						Intent intent = new Intent(context, PadresMainActivity.class);
				        startActivity(intent);
					}
				}
				
				//Se destruye esta actividad
				finish();
			}
			else			
			{
				mensaje = getResources().getString(R.string.login_no_ok_txt);
			}
			
			Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();			
		
			//
			super.onPostExecute(usuario);			
		}

		@Override
		protected Usuario doInBackground(Void... arg0) {			
			//Realizar identificaci�n con el servidor
			return Conexion.identificarse(user, pass);
		}
		
		//Atributos necesarios para conectar con el servidor
		public void setContext(Context context){
			this.context = context;
		}
		public void setUser(String user){
			this.user = user;
		}
		public void setPassword(String pass){
			this.pass = pass;
		}
	}
	
}