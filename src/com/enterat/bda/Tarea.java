/*
 * 2013-12-26 
 * Clase creada por Jose Chamorro
 * 
 * Clase para gestionar las tareas de la bda 
 */

package com.enterat.bda;

import java.util.Date;

public class Tarea {

	//Atributos de clase
	private int id_tarea;
	private Asignatura asignatrua;
	private Date fecha;
	private String contenido;
	private String observaciones;
	
	//Getters and Setters
	public int getId_tarea() {
		return id_tarea;
	}
	public void setId_tarea(int id_tarea) {
		this.id_tarea = id_tarea;
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
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
}