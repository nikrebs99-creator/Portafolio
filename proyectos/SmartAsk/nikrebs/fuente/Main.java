package com.nikrebs.fuente;

import java.util.ArrayList;
import java.util.Scanner;


/**
 * Clase principal que hace posible SmarTask
 * @author Nicolás Krebs
 * @version 0.5
 *  */
public class Main {
	
	/**
	 * Constructor por defecto, necesario para el javadoc
	 * */
	public Main() {
		
	}
	
	/**
	 * Método que da inicio a la aplicación
	 * Bucle de Menu principal e interacción con el usuario
	 * 
	 * @param args - Argumentos
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String nombre = "Persona";
		int contadorDeIDs = 1;
		ArrayList<Tarea> listaTareas = new ArrayList<>();
		boolean encendido = true;

		while(encendido) {
			String seleccionMenu = Metodos.mostrarMenuPrincipal(sc, nombre);
			switch(seleccionMenu) {
			case "1": // Revisar tareas

				/* 
				 * sortear ASC y DESC por ID
				 * */

				System.out.println(" --- Lista de tareas de " + nombre + " --- ");
				System.out.println(" ");
				System.out.println("ID - Titulo - Estado");
				System.out.println(" ");
				Metodos.mostrarTareas(listaTareas); //imprime las tareas de forma enumerada del 1 al maximo.
				System.out.println(" ");
				System.out.println("Para volver atrás, escriba -1");
				System.out.print("Seleccione una tarea para ver detalles: ");
				String seleccionRevisar = sc.next(); 
				int seleccionRevisada = Metodos.revisarSeleccion(sc, listaTareas.size(), seleccionRevisar);
				//seleccionRevisada devuelve el indice en la lista.
				if(seleccionRevisada == -1) {
					break; //rompe el switch y vuelve al menu principal
				} // if
				else {
					boolean tareando = true; //Conjugación de estar haciendo tareas
					while(tareando) {
						System.out.println("[ID] Titulo - Estado");
						System.out.println(" ");
						System.out.println("[" + listaTareas.get(seleccionRevisada).getID() + "] " + listaTareas.get(seleccionRevisada).getTituloTarea() + " - " +listaTareas.get(seleccionRevisada).getPendienteStr());
						/* 
						 * El ID mostrado aqui no es llamado por el usuario, es solo evidencia de que existe y está.
						 * Se usa para cosas internas como el sort por tiempo. Un ID menor implica si o si que una tarea fue creada antes.
						 * Por lo que un sort con ID's es equivalente a ordenar por tiempo
						 * Asc Desde las más antigua a la más nueva
						 * Desc Desde la más nueva a la más antigua
						 * */
						System.out.println("Descripción: ");
						System.out.println(listaTareas.get(seleccionRevisada).getDescripcion());
						System.out.println(" "); // Por lo visto, \n me estaba generando conflicto con los sc.nextLine()
						System.out.println("¿Deseas hacer algo con esta tarea?");
						System.out.println(" ");
						System.out.println("[1] Editar titulo de la tarea");
						System.out.println("[2] Editar descipción de la tarea");
						System.out.println("[3] Cambiar estado de la tarea");
						System.out.println("[4] Eliminar tarea");
						System.out.println("[5] Cambiar estado de urgencia");
						System.out.println("[6] Volver al menu principal");
						String seleccionTareaSeleccionada = sc.next();
						int tareaSeleccionadaRevisada = Metodos.revisarSeleccion(sc, 6, seleccionTareaSeleccionada);
						if(tareaSeleccionadaRevisada == -1) { // No te pases de listo. (-1 es la salida de otro menú, esto es en caso la intente nuevamente

							tareando = false;
							System.out.println(" ");
							System.out.println("Volviendo al menú de tareas...");
							break; // sale del primer switch y vuelve al menú principal
						} //if  (esto es un pseudo catch?)
						String nuevoTitulo;
						String nuevaDescripcion;

						switch(tareaSeleccionadaRevisada + 1) { //se le suma uno porque el método devuelve el potencial índice
						case 1: // Cambiar Titulo
							sc.nextLine();
							System.out.println("¿Cual es el nuevo título?");
							nuevoTitulo = sc.nextLine();
							System.out.println("Título cambiado de '" + listaTareas.get(seleccionRevisada).getTituloTarea() + "' a '" + nuevoTitulo + "'");
							listaTareas.get(seleccionRevisada).setTituloTarea(nuevoTitulo);
							break;
						case 2: //Cambiar Descripcion
							System.out.println("¿Cual es la nueva Descripción?");
							sc.nextLine();
							nuevaDescripcion = sc.nextLine();
							System.out.println("Descripcion cambiada a '" + nuevaDescripcion + "'");
							listaTareas.get(seleccionRevisada).setDescripcion(nuevaDescripcion);
							break;
						case 3: // Cambiar pendiente
							if(listaTareas.get(seleccionRevisada).getPendiente()) { 
							    listaTareas.get(seleccionRevisada).TareaTerminada();
							    System.out.println("Estado cambiado de Pendiente a Terminada");
							} // if
							else {
							    listaTareas.get(seleccionRevisada).TareaPendiente(); 
							    System.out.println("Estado cambiado de Terminada a Pendiente");
							} // else
							break;

						case 4: //Eliminar tarea
							listaTareas.remove(seleccionRevisada);
							System.out.println("Tarea removida correctamente.");
							System.out.println(" ");
							tareando = false;
							break;
						case 5: // cambiar urgencia
							if(listaTareas.get(seleccionRevisada) instanceof TareaUrgente){
								TareaUrgente tareaUrgenteConvertida = (TareaUrgente) listaTareas.get(seleccionRevisada);
								tareaUrgenteConvertida.setUrgente();
								System.out.println("Urgencia modificada correctamente!");
							}
							else {
								System.out.println("Esta tarea no es urgente!");
								System.out.println("Debes seleccionarla como tarea urgente en su creacion!");
							}
							break;
						case 6: // Salir al menu principal
							System.out.println("Volviendo al menú principal...");
							tareando = false;
							break;
						
						default:
							System.out.println("Volviendo al menú...");
							break;


						} //switch(tareaSeleccionadaRevisada)
					} // else
				} //while tareando
				break; // switch Revisar tareas
			case "2": // Agregar Tareas
				System.out.print("Titulo de la tarea a agregar: ");
				sc.nextLine();
				String nombreNuevaTarea = sc.nextLine();
				String nuevaDescripcion = null;
				System.out.print("Desea agregar descripcion? [y/n] ");
				String preguntaDescripcion = sc.next();
				if(preguntaDescripcion.equalsIgnoreCase("y")) {
					System.out.println("Describa la tarea: ");
					sc.nextLine();
					nuevaDescripcion = sc.nextLine();					
				}//if Descripcion
				System.out.print("¿Es urgente? [y/n] ");
				String preguntaUrgencia = sc.next();
				if(preguntaUrgencia.equalsIgnoreCase("y")) {
					TareaUrgente nuevaTareaUrgente = new TareaUrgente(contadorDeIDs, nombreNuevaTarea, true);
					if(nuevaDescripcion != null) {
						nuevaTareaUrgente.setDescripcion(nuevaDescripcion);
					} //if
					listaTareas.add(nuevaTareaUrgente);
					contadorDeIDs += 1;
				} //else responde a urgente?
				else {
					Tarea nuevaTarea = new Tarea(contadorDeIDs, nombreNuevaTarea);
					if(nuevaDescripcion != null) {
						nuevaTarea.setDescripcion(nuevaDescripcion);
					}
					listaTareas.add(nuevaTarea);
					contadorDeIDs += 1;


				} //else 

				break; // Agregar Tareas
			case "3": // Cambiar nombre de usuario
				System.out.print("Escriba nuevo nombre de usuario: ");
				sc.nextLine();
				String nuevoNombre = sc.nextLine(); // En estricto rigor, esto sobra, podría ser nombre directamente
				// Pero puede ser de utilidad más adelante
				System.out.println("Nombre cambiado de " + nombre + " a " + nuevoNombre + " correctamente");
				nombre = nuevoNombre;
				break; // Cambiar nombre de usuario
			case "4": // Ordenar Ascendente
				listaTareas = Metodos.ordenarAsc(listaTareas);
				System.out.println("Tareas ordenadas ASCENDENTEMENTE correctamente!");
				break;
			case "5": //Ordenar Descendente
				listaTareas = Metodos.ordenarDesc(listaTareas);
				System.out.println("Tareas ordenadas DESCENDENTEMENTE correctamente!");
				break;
			case "6": // Salir
				System.out.println("Cerrando app...");
				System.out.println("Buena suerte:)");
				encendido = false;
				break; // Salir
			default:
				System.out.println("Opción inválida. Intente nuevamente.");
				break;
			} //switch seleccionMenu
		} //while encendido
	} //static void main
}//public class main




