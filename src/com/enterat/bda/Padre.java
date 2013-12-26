/*
 * 2013-12-26 
 * Clase creada por Jose Chamorro
 * 
 * Clase para gestionar los padres de la bda 
 */

package com.enterat.bda;

public class Padre {

	//Atributos de clase
	private int id_padre;
	private String nombre;
	private String apellidos;
	private Usuario usuario;
	
	//Getters and Setters
	public int getId_padre() {
		return id_padre;
	}
	public void setId_padre(int id_padre) {
		this.id_padre = id_padre;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}