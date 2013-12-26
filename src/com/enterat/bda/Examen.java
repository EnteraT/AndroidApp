/*
 * 2013-12-26 
 * Clase creada por Jose Chamorro
 * 
 * Clase para gestionar los examenes de la bda 
 */

package com.enterat.bda;

import java.util.Date;

public class Examen {

	//Atributos de clase
	private int id_examen;
	private Asignatura asignatrua;
	private Date fecha;
	private String contenido;
	
	//Getters and Setters
	public int getId_examen() {
		return id_examen;
	}
	public void setId_examen(int id_examen) {
		this.id_examen = id_examen;
	}
	public Asignatura getAsignatrua() {
		return asignatrua;
	}
	public void setAsignatrua(Asignatura asignatrua) {
		this.asignatrua = asignatrua;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
}