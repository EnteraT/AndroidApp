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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	// Variable temporal para bypasear el LOGIN
	boolean ByPass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// comentario nuevo
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// Bypasear el LOGIN, hasta que funcione
		ByPass = true;

		//Restaura el formulario
		if(Conexion.isLoggedIn()){
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
		
		// Recuperar datos del usuario...
		Usuario user = recuperarPreferenciasLogIn();

		// ...si existe
		if (user.getIdUsuario() != 0) {

			// LogInAsyncTask task = new LogInAsyncTask();
			//
			// task.setUser( user.getUser() );
			// task.setPassword( user.getPassword() );
			// task.setContext( LoginActivity.this );
			// task.execute();

			Intent intent = null;

			switch (user.getTipo()) {
			case Conexion.PROFESOR:
				intent = new Intent(this, ProfesorMainActivity.class);
				break;
			case Conexion.PADRE:
				intent = new Intent(this, PadresMainActivity.class);
				break;
			}
			if (intent != null)
				startActivity(intent);

			// Se destruye esta actividad
			//finish();
		}
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		
		if(Conexion.isLoggedIn()){
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
		
		if(Conexion.isLoggedIn()){
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

		// Comprobar que hay conexi�n a INTERNET !!!
		if (Conexion.isConnected(LoginActivity.this)) {

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

			String msg;
			
			if (usuario.getIdUsuario() != 0) {

				guardarPreferenciasLogIn(usuario);

				msg = getResources().getString(R.string.msg_login_ok);

				Intent intent = null;

				switch (usuario.getTipo()) {
				case Conexion.PROFESOR:
					intent = new Intent(context, ProfesorMainActivity.class);
					break;
				case Conexion.PADRE:
					intent = new Intent(context, PadresMainActivity.class);
					break;
				}
				if (intent != null)
					startActivity(intent);

				// Se destruye esta actividad
				//finish();
			} else {
				msg = getResources().getString(R.string.msg_login_no_ok);
			}

			Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

			//
			super.onPostExecute(usuario);
		}

		@Override
		protected Usuario doInBackground(Void... arg0) {

			if (ByPass == false) {
				// Realizar identificaci�n con el servidor
				return Conexion.identificarse(user, pass);

			} else {

				Usuario FakeUser = new Usuario();
				TextView textUser = (TextView) findViewById(R.id.userEditText);
				TextView textPass = (TextView) findViewById(R.id.passEditText);

				final String user = textUser.getText().toString();
				final String pass = textPass.getText().toString();

				//
				FakeUser.setIdUsuario(0);
				
				if (user.equals("profesor")){
					FakeUser.setIdUsuario(1111);
					FakeUser.setTipo(Conexion.PROFESOR);
					FakeUser.setUser("FakeProfesor");
					FakeUser.setPassword("fakeprofesor");
					Conexion.setLoggedIn(true);
				}

				if (user.equals("padre")) {
					FakeUser.setIdUsuario(9999);
					FakeUser.setTipo(Conexion.PADRE);
					FakeUser.setUser("FakePadre");
					FakeUser.setPassword("fakepadre");
					Conexion.setLoggedIn(true);
				}

				return FakeUser;
			}
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