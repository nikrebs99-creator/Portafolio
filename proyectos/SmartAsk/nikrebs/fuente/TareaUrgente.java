package com.nikrebs.fuente;
/**
 * Clase hija de Tarea
 * @author Nicolás Krebs
 * @version 0.5
 *  */
public class TareaUrgente extends Tarea{
	
	private boolean urgente;
	
	/**
	 * Constructor de TareaUrgente, hija de Tarea
	 * @param id - Numero para manejo interno.
	 * @param tituloTarea - El titulo de la tarea.
	 * @param urgente - Afirma si es urgente o no, en ambos casos tiene  prioridad
	 * 
	 * */
	public TareaUrgente(int id, String tituloTarea, boolean urgente) {
		super(id, tituloTarea);
		this.urgente = urgente;
	} //public TareaUrgente
	
	/**
	 * Indica si es actualmente urgente o lo era anteriormente
	 * @return retorna boolean urgente
	 * */
	public boolean getUrgente() {
		return this.urgente;
	} // getUrgente
	
	/**
	 * Cambia Tarea.urgente a su opuesto. true -> false || false -> true
	 * */
	public void setUrgente() {
		this.urgente = !this.urgente;
	} //void setUrgente
	
	
/**
 * Muestra la Tarea con el añadido de urgente correspondiente
 *  */
	@Override
	public void mostrarTarea() {
		System.out.println("[" + this.getID() + "] " + " [URGENTE]" + this.getTituloTarea() + " - " + this.getPendienteStr());

	}

	
	
} // TareaUrgente extends

