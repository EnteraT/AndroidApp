/*
 * 2013-12-26 
 * Clase creada por Jose Chamorro
 * 
 * Clase para gestionar que profesores imparten que asignaturas 
 */

package com.enterat.bda;

public class Imparte {

	//Atributos de clase
	private int id_imparte;
	private Profesor profesor;
	private Asignatura asignatura;
	
	//Getters and Setters
	public int getId_imparte() {
		return id_imparte;
	}
	public void setId_imparte(int id_imparte) {
		this.id_imparte = id_imparte;
	}
	public Profesor getProfesor() {
		return profesor;
	}
	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
	public Asignatura getAsignatura() {
		return asignatura;
	}
	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}
	
}