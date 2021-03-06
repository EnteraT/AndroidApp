package com.enterat.interfaces;

import com.enterat.R;
import com.enterat.bda.Imparte;
import com.enterat.bda.Padre;
import com.enterat.bda.Usuario;
import com.enterat.services.Conexion;
import com.enterat.util.Constantes;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private String asignaturas = "";
	private String nombre	   = "";
	private String apellidos   = "";
	private String nombrePadre		= "";
	private String apellidosPadre	= "";
	private String nombreAlumno		= "";
	private String apellidosAlumno	= "";
	private int cursoAlumno			= 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// comentario nuevo
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		//Restaura el formulario
		if(Usuario.isLoggedIn()){
			
			TextView textUser = (TextView) findViewById(R.id.userEditText);
			TextView textPass = (TextView) findViewById(R.id.passEditText);
			Button btLogIn = (Button) findViewById(R.id.b_login);
			Button btLogOut = (Button) findViewById(R.id.b_logout);
			
			textUser.setEnabled(false);
			textPass.setEnabled(false);
			btLogIn.setText(getResources().getString(R.string.bt_continuar));
			btLogOut.setEnabled(true);
			
		} else {
			
			TextView textUser = (TextView) findViewById(R.id.userEditText);
			TextView textPass = (TextView) findViewById(R.id.passEditText);
			Button btLogIn = (Button) findViewById(R.id.b_login);
			Button btLogOut = (Button) findViewById(R.id.b_logout);
			
			textUser.setEnabled(true);
			textPass.setEnabled(true);
			btLogIn.setText(getResources().getString(R.string.bt_login));
			btLogOut.setEnabled(false);
		}
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		
		if(Usuario.isLoggedIn()){
			TextView textUser = (TextView) findViewById(R.id.userEditText);
			TextView textPass = (TextView) findViewById(R.id.passEditText);
			Button btLogIn = (Button) findViewById(R.id.b_login);
			Button btLogOut = (Button) findViewById(R.id.b_logout);
			
			textUser.setEnabled(false);
			textPass.setEnabled(false);
			btLogIn.setText(getResources().getString(R.string.bt_continuar));
			btLogOut.setEnabled(true);
			
		} else {
			TextView textUser = (TextView) findViewById(R.id.userEditText);
			TextView textPass = (TextView) findViewById(R.id.passEditText);
			Button btLogIn = (Button) findViewById(R.id.b_login);
			Button btLogOut = (Button) findViewById(R.id.b_logout);
			
			textUser.setEnabled(true);
			textPass.setEnabled(true);

			textUser.setText("");
			textPass.setText("");
			
			btLogIn.setText(getResources().getString(R.string.bt_login));
			btLogOut.setEnabled(false);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if(Usuario.isLoggedIn()){
			TextView textUser = (TextView) findViewById(R.id.userEditText);
			TextView textPass = (TextView) findViewById(R.id.passEditText);
			Button btLogIn = (Button) findViewById(R.id.b_login);
			Button btLogOut = (Button) findViewById(R.id.b_logout);
			
			textUser.setEnabled(false);
			textPass.setEnabled(false);
			btLogIn.setText(getResources().getString(R.string.bt_continuar));
			btLogOut.setEnabled(true);
		} else {
			TextView textUser = (TextView) findViewById(R.id.userEditText);
			TextView textPass = (TextView) findViewById(R.id.passEditText);
			Button btLogIn = (Button) findViewById(R.id.b_login);
			Button btLogOut = (Button) findViewById(R.id.b_logout);
			
			textUser.setEnabled(true);
			textPass.setEnabled(true);
			
			textUser.setText("");
			textPass.setText("");
			
			btLogIn.setText(getResources().getString(R.string.bt_login));
			btLogOut.setEnabled(false);
		}
	}

	public void loginClick(View v) {

		// Comprobar que hay conexion a INTERNET !!!
		if (Conexion.isConnected(LoginActivity.this)) {
			if (Usuario.isLoggedIn()) {
				
				//Recuperar datos del Login
				Usuario user = recuperarPreferenciasLogIn();
				
				Intent intent = null;

				switch (user.getTipo()) {
				case Constantes.PROFESOR:
					intent = new Intent(this, ProfesorMainActivity.class);
					break;
				case Constantes.PADRE:
					intent = new Intent(this, PadresMainActivity.class);
					break;
				}
				if (intent != null)
					startActivity(intent);
			}
			else{
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
		} else {
			// Mostrar error de conexi�n
			showNoConnectionWarning();
		}
	}
	
	public void logoutClick(View v) {
		if(Conexion.desconectarse()==0){
			borrarPreferenciasLogIn();
			
			TextView textUser = (TextView) findViewById(R.id.userEditText);
			TextView textPass = (TextView) findViewById(R.id.passEditText);
			Button btLogIn = (Button) findViewById(R.id.b_login);
			Button btLogOut = (Button) findViewById(R.id.b_logout);
			
			textUser.setEnabled(true);
			textPass.setEnabled(true);
			
			textUser.setText("");
			textPass.setText("");
			btLogIn.setText(getResources().getString(R.string.bt_login));
			btLogOut.setEnabled(false);
			
			textUser.requestFocus();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	public void showNoConnectionWarning() {
		// Mostrar TOAST --> NO INTERNET !!!
		Toast.makeText(this,getResources().getString(R.string.msg_sin_conexion),Toast.LENGTH_SHORT).show();
	}

	//
	private Usuario recuperarPreferenciasLogIn() {

		//
		SharedPreferences preferences = getSharedPreferences("LogIn",Context.MODE_PRIVATE);

		//
		Usuario user = new Usuario();
		user.setIdUsuario(preferences.getInt("idUsuario", 0));
		user.setUser(preferences.getString("usuario", ""));
		user.setPassword(preferences.getString("password", ""));
		user.setTipo(preferences.getInt("tipo", 0));
		
		asignaturas = preferences.getString("asignaturas", "");
		nombre		= preferences.getString("nombre", "");
		apellidos	= preferences.getString("apellidos", "");
		
		nombrePadre 	= preferences.getString("nombrePadre", "");
		apellidosPadre	= preferences.getString("apellidosPadre", "");
		nombreAlumno	= preferences.getString("nombreAlumno", "");
		apellidosAlumno = preferences.getString("apellidosAlumno", "");
		cursoAlumno		= preferences.getInt("cursoAlumno", 0);

		return user;
	}

	//
	private void guardarPreferenciasLogIn(Usuario user) {

		//
		SharedPreferences preferences = getSharedPreferences("LogIn",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();

		//
		editor.putInt("idUsuario", user.getIdUsuario());
		editor.putString("usuario", user.getUser());
		editor.putString("password", user.getPassword());
		editor.putInt("tipo", user.getTipo());
		
		editor.putString("asignaturas", asignaturas);
		editor.putString("nombre", nombre);
		editor.putString("apellidos", apellidos);
		
		editor.putString("nombrePadre", nombrePadre);
		editor.putString("apellidosPadre", apellidosPadre);
		editor.putString("nombreAlumno", nombreAlumno);
		editor.putString("apellidosAlumno", apellidosAlumno);
		editor.putInt("cursoAlumno", cursoAlumno);

		editor.commit();
	}
	
	private void borrarPreferenciasLogIn(){
		SharedPreferences preferences = getSharedPreferences("LogIn",Context.MODE_PRIVATE);
	
		// Comprueba que el usuario que se va a desconectar es el que esta
		// guardado en SharedPreferences
		SharedPreferences.Editor editor = preferences.edit();
	
		// Borra las SharedPreferences
		editor.clear();
		editor.commit();
	}
	
	// **********************************************************************
	// TAREA ASINCRONA --> LOG IN
	// **********************************************************************
	private class LogInAsyncTask extends AsyncTask<Void, Void, Usuario> {

		private String user, pass;
		private Context context;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Usuario usuario) {
			super.onPostExecute(usuario);
						
			String mensaje;
			
			//Si existe el usuario
			if(usuario != null){
				
				//Crear mensaje login OK
				mensaje = getResources().getString(R.string.msg_login_ok);
								
				//Dependiendo del tipo de usario...
				if(usuario.getTipo() == Constantes.PROFESOR){
					//Guardar las asignaturas que imparte dicho profesor...
					Imparte imparte = new Imparte();
					asignaturas = imparte.queImparteProfesorPorIdUsuario( usuario.getIdUsuario() );
					
					//..as� como su nombre y apellidos
					nombre    = imparte.getProfesor().getNombre();
					apellidos = imparte.getProfesor().getApellidos();
					
					//...mostrar men� de Profesor...
					Intent intent = new Intent(context, ProfesorMainActivity.class);
			        startActivity(intent);
				}
				else{
					if(usuario.getTipo() == Constantes.PADRE){
						
						//Guardar datos del padre y del alumno...
						Padre padre = new Padre();						
						padre.obtenerDatosPadreAlumnoPorIdUsuario( usuario.getIdUsuario() );
						
						nombrePadre		= padre.getNombre();
						apellidosPadre	= padre.getApellidos();
						nombreAlumno	= padre.getAlumno().getNombre();
						apellidosAlumno	= padre.getAlumno().getApellidos();
						cursoAlumno		= padre.getAlumno().getCurso().getId_curso();
						
						//...o mostrar men� de Padre
						Intent intent = new Intent(context, PadresMainActivity.class);
				        startActivity(intent);
					}
				}
				
				//Guardar datos en Preferences
				guardarPreferenciasLogIn( usuario );
			}
			else			
			{
				//Si no existe el usuario... crear mensaje de error
				mensaje = getResources().getString(R.string.msg_login_no_ok);
			}
			
			//Mostrar mensaje
			Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
		}
		
		@Override
		protected Usuario doInBackground(Void... arg0) {			
			//Realizar identificacion con el servidor
			return Usuario.identificarse(user, pass);
		}

		// Atributos necesarios para conectar con el servidor
		public void setContext(Context context) {
			this.context = context;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public void setPassword(String pass) {
			this.pass = pass;
		}
	}

}