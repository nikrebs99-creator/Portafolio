package com.nikrebs.fuente;


/**
 * Public class Tarea
 * @author Nicolás Krebs
 * @version 0.5
 *  */
public class Tarea {
	private int ID; //en teoría, que funcione tipo auto-increment
	private String tituloTarea;
	private boolean pendiente; //Por defecto, se crea una tarea incompleta
	private String descripcion;
	
	/**
	 * Constructor Tarea
	 * Por defecto, la tarea se inicializa con un estado pendiente (true) y una descripción vacía.
	 * el estado pendiente y la descripción se editan con setpendiente, setTerminada, setPendiente, setDescripcion
	 * @param ID - Identificador numérico de la Tarea
	 * @param tituloTarea - Titulo de la Tarea
	 */
	public Tarea(int ID, String tituloTarea) {
		this.ID = ID;
		this.tituloTarea = tituloTarea;
		this.pendiente = true;
		this.descripcion = "";
	}
	
	/**
	 * Cambia el ID INT de la tarea
	 * @param ID - para cambiar el numero id de la tarea
	 *  */
	public void setID(int ID) { //cambiar ID
		this.ID = ID;
	} //setID
	
	/**
	 * Retorna el ID en forma de INT
	 *  @return el ID en forma de INT
	 *  */
	public int getID() { // retorna ID como int
		return this.ID;
	}//getID


	/**
	 * Cambiar el titulo de la tarea
	 * @param tituloTarea - titulo de la tarea
	 *  */
	public void setTituloTarea(String tituloTarea) { //Cambiar Titulo Tarea
		this.tituloTarea = tituloTarea;
	}

	/**
	 * Estado de la tarea
	 * @return El estado pendiente como boolean
	 * */
	public boolean getPendiente() {
		return this.pendiente;
	}	
	
	/**
	 * Retorna estado de la this.pendiente en String
	 * @return "Pendiente" o "Terminada".
	 */
	public String getPendienteStr() {
		if(this.getPendiente()) {
			return("Pendiente");
		}
		else {
			return("Terminada");
		}
	}
	
	
	/**
	 * Marca la tarea como TERMINADA.
	 *  */
	public void TareaTerminada() { //marcar como terminada una tarea
		this.pendiente = false;
	}
	/** 
	 * Marca la tarea como PENDIENTE.
	 * */
	public void TareaPendiente() { //marcar como sin terminar una tarea
		this.pendiente = true;
	}
	
	/**
	 * Descripcion como String
	 * @return la descripcion de la tarea
	 *  */
	public String getDescripcion() { 
		return this.descripcion;
	}
	/** 
	 * Añade una descripcion para la tarea seleccionada
	 * @param descripcion - La descripcion de la tarea
	 * */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Devuele el titulo de la Tarea
	 * @return Titulo de la tarea
	 *  */
	public String getTituloTarea() {  // Devuelve el titulo de la tarea
		return tituloTarea;
	}
	
	/** 
	 * Imprime la tarea y su ID
	 * */
	public void mostrarTarea() {
	System.out.println("[" + this.getID() + "] " + this.getTituloTarea() + " - " + this.getPendienteStr());
	}
	
	


} //public class Tarea
