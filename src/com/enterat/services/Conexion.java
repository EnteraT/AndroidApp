package com.enterat.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.enterat.services.Conexion;

public class Conexion extends Activity {

	// Constantes para indicar el tipo de usuario de la aplicacion
	public static final int PROFESOR = 1;
	public static final int PADRE = 2;
	public static final int ADMIN = 32769;
	
	public static boolean LoggedIn;

	public static boolean isLoggedIn() {
		return LoggedIn;
	}

	public static void setLoggedIn(boolean loggedIn) {
		LoggedIn = loggedIn;
	}

	// URL del directorio donde se encuentran los servicios del servidor
	private final static String url = "http://www.appservices.eshost.es/servicioweb/";

	// Comprobar si hay conexion a INTERNET !!!
	public static boolean isConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		return ((info != null) && info.isConnected());
	}

	//Metodo generico para obtener JSON para cualquier servicio
	public static JSONObject obtenerJsonDelServicio(List<NameValuePair> pairs, String servicio) throws ClientProtocolException, IOException, JSONException {
		
		HttpClient client = new DefaultHttpClient();		
		JSONObject json   = null;		
				
		HttpPost request = new HttpPost(url + servicio);		
		request.setHeader("Accept","application/json");	
		request.setEntity(new UrlEncodedFormEntity(pairs));
		
		HttpResponse response = client.execute(request);
		HttpEntity entity 	  = response.getEntity(); 
		String responseString = null;
		
		if (entity != null) { 
			InputStream stream = entity.getContent(); 
			BufferedReader reader = new BufferedReader(	new InputStreamReader(stream) ); 
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) { 
				//sb.append(line + "\n");
				sb.append(line);
			}		 
			stream.close(); 
			responseString = sb.toString();	

			//			
			if(responseString.startsWith("[") && responseString.endsWith("]")){
				responseString = responseString.substring(1, responseString.length()-1);
			}
			
			//
			json = new JSONObject(responseString);			
		}	
		
		return json;	
	}

	public static int desconectarse() {
//		SharedPreferences preferences = getSharedPreferences("LogIn",Context.MODE_PRIVATE);
//
//		// Comprueba que el usuario que se va a desconectar es el que esta
//		// guardado en SharedPreferences
//		SharedPreferences.Editor editor = preferences.edit();
//
//		// Borra las SharedPreferences
//		editor.clear();
//		editor.commit();

		// Si procede, cerrar las conexiones activas
		setLoggedIn(false);
		
		// Todo OK
		return 0;
	}
}