package com.nikrebs.fuente;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
/**
 * Contiene los métodos y menús para aplicación SmartTask.
 * Valida entradas y ordena las listas de Tareas.
 * 
 * @author Nicolás Krebs
 * @version 0.5
 */
public class Metodos {

	/**
	 * Public Metodos
	 * */
	public Metodos() {
		
	}
	
/**
 * Muestra el menú principal de la app.
 * @param sc - Scanner
 * @param nombre - Nombre de usuario
 * @return seleccion - opcion seleccionada del menu
 * */
public static String mostrarMenuPrincipal(Scanner sc, String nombre) { // Metodo que imprime menu principal y pide numero
		while (true) {
			System.out.println("\n ------ Menu ------");
			System.out.println("Que deseas hacer ahora, " + nombre);
			System.out.println("\n[1] Revisar tareas");
			System.out.println("[2] Agregar Tarea");
			System.out.println("[3] Cambiar nombre de usuario");
			System.out.println("[4] Ordenar de manera Ascendente");
			System.out.println("[5] Ordenar de manera Descendente");
			System.out.println("[6] Salir\n");
			System.out.print("Introduzca el numero de lo que desea hacer: ");
			String seleccion = sc.next();
			switch(seleccion) {
			case "1", "2", "3", "4", "5", "6":
				return(seleccion);
			default:
				System.out.println("Intente nuevamente");
				break;
			} //switch seleccion
		} //while true
	} //public static void mostrarMenuPrincipal

/**
 * Muestra la lista de tareas de manera ordenada, dandole prioridad a las urgentes
 * @param listaTareas - ArrayList de objetos Tarea
 * */
	public static void mostrarTareas(ArrayList<Tarea> listaTareas) {
		int contador = 1;
		for(Tarea tarea : listaTareas) {
			if(tarea instanceof TareaUrgente){
				TareaUrgente tareaUrgenteConvertida = (TareaUrgente) tarea;
				if(tareaUrgenteConvertida.getUrgente()) {
					System.out.println("[" + contador + "] " + "[URGENTE] " + tarea.getTituloTarea() + " - " + tarea.getPendienteStr());
					contador += 1;
					continue;
				} //Si la tarea urgente deja de ser urgente, ya no es prioridad en la lista
			} // if TareaUrgente
			System.out.println("[" + contador + "] " + tarea.getTituloTarea() + " - " + tarea.getPendienteStr());
			contador += 1;
		}//for
	} //mostrarTareas

	
	
	/**
	 * Revisa que el numero ingresado por el usuario esté en el rango necesario
	 * 
	 * @param sc - El objeto Scanner que se use de antemano
	 * @param rango - Cantidad de opciones
	 * @param seleccion -  Cadena que ingresa el usuario
	 * @return El índice validado como número entero (Siendo 0 el mínimo para su uso en índices), o -1 si el usuario selecciona salir.
	 */
	public static int revisarSeleccion(Scanner sc, int rango, String seleccion) {
		while (true) {
			//Salida de emergencia
			if (seleccion.equals("-1")) {
				return -1;
			} //if 

			try {
				int seleccionNumero = Integer.parseInt(seleccion);
				int indiceReal = seleccionNumero - 1;

				if (indiceReal >= 0 && indiceReal < rango) {
					return indiceReal;
				} //if 
				else {
					System.out.println("Opción fuera de rango. Intente nuevamente.");
				} //else
			} //try
			catch (NumberFormatException e) {
				System.out.println("Entrada no válida, ingrese un número válido.");
			} //catch1

			// Pedir nueva entrada para la siguiente iteración
			seleccion = sc.next();
		} //while

	}// private static int revisarSeleccion

	/** 
	 * Ordena una lista de tareas por su ID ASCENDENTEMENTE
	 * @param listaTareas que se quiere ordenar ASC
	 * @return listaTareas ordenada ASCENDENTEMENTE
	 * */
	
	public static ArrayList<Tarea> ordenarAsc(ArrayList<Tarea> listaTareas){
		ArrayList<Tarea> listaUrgentes = new ArrayList<Tarea>();
		ArrayList<Tarea> listaNormales = new ArrayList<Tarea>();
		ArrayList<Tarea> listaTareasOrdenadasAsc = new ArrayList<Tarea>();

		for(Tarea tarea : listaTareas) {
			if(tarea instanceof TareaUrgente) {
				listaUrgentes.add(tarea);
			} //if urgente
			else {
				listaNormales.add(tarea);
			}
			} //for separador
		listaUrgentes.sort(Comparator.comparingInt(Tarea::getID));
		listaNormales.sort(Comparator.comparingInt(Tarea::getID));
	
		for(Tarea tarea : listaUrgentes) {
			listaTareasOrdenadasAsc.add(tarea);	
			}
		for(Tarea tarea : listaNormales) {
			listaTareasOrdenadasAsc.add(tarea);
		}
		return(listaTareasOrdenadasAsc);
				}
	
	/** 
	 * Ordena una lista de tareas por su ID DESCENDENTE
	 * @param listaTareas que se quiere ordenar DESC

	 * @return listaTareas ordenada DESCENDENTE
	 * */
	public static ArrayList<Tarea> ordenarDesc(ArrayList<Tarea> listaTareas){
		ArrayList<Tarea> listaUrgentes = new ArrayList<Tarea>();
		ArrayList<Tarea> listaNormales = new ArrayList<Tarea>();
		ArrayList<Tarea> listaTareasOrdenadasDesc = new ArrayList<Tarea>();

		for(Tarea tarea : listaTareas) {
			if(tarea instanceof TareaUrgente) {
				listaUrgentes.add(tarea);
			} //if urgente
			else {
				listaNormales.add(tarea);
			}
			} //for separador
		listaUrgentes.sort(Comparator.comparingInt(Tarea::getID).reversed());
		listaNormales.sort(Comparator.comparingInt(Tarea::getID).reversed());
	
		for(Tarea tarea : listaUrgentes) {
			listaTareasOrdenadasDesc.add(tarea);	
			}
		for(Tarea tarea : listaNormales) {
			listaTareasOrdenadasDesc.add(tarea);
		}
		return(listaTareasOrdenadasDesc);
				}




}//main


