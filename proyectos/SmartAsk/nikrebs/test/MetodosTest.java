package com.nikrebs.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nikrebs.fuente.Metodos;
import com.nikrebs.fuente.Tarea;
import com.nikrebs.fuente.TareaUrgente;
//el \n es el enter del usuario xd por eso el nextLine(); trolleaba
class MetodosTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        // Redirigir salida de consola
        System.setOut(new PrintStream(outContent));
    } // setUpStreams

    @AfterEach
    void restoreStreams() {
        // Restaurar la consola
        System.setOut(originalOut);
    } // restoreStreams

    @Test
    void testConstructorMetodo() {
    	Metodos utilidades = new Metodos();
    	assertNotNull(utilidades);
    }
    
    @Test
    void testMostrarMenuPrincipal() {
        String inputSimulado = "1\n";
        ByteArrayInputStream in = new ByteArrayInputStream(inputSimulado.getBytes());
        Scanner sc = new Scanner(in);
        
        String resultado = Metodos.mostrarMenuPrincipal(sc, "Nico");
        
        assertEquals("1", resultado);
        assertTrue(outContent.toString().contains("Que deseas hacer ahora, Nico"));
    } // testMostrarMenuPrincipal
    @Test
	void testMostrarMenuPrincipalOpcionInvalida() { //para cuando falle, era más facil que incluirlo en un solo test 
		String inputSimulado = "9\n1\n";
		ByteArrayInputStream in = new ByteArrayInputStream(inputSimulado.getBytes());
		Scanner sc = new Scanner(in);
		String resultado = Metodos.mostrarMenuPrincipal(sc, "Nico");
		assertEquals("1", resultado);
		assertTrue(outContent.toString().contains("Intente nuevamente"));
	} // testMostrarMenuPrincipalOpcionInvalida

    @Test
    void testRevisarSeleccionOpcionValida() {
        // Simulamos que el usuario selecciona el índice 2 (que se traduce en el índice 1 real)
        String inputSimulado = "2\n"; 
        ByteArrayInputStream in = new ByteArrayInputStream(inputSimulado.getBytes());
        Scanner sc = new Scanner(in);
        
        int resultado = Metodos.revisarSeleccion(sc, 5, sc.next());
        
        assertEquals(1, resultado); // 2 - 1 = 1
    } // testRevisarSeleccionOpcionValida

    @Test
    void testRevisarSeleccionSalidaEmergencia() {
        // Simulamos que el usuario escribe -1
        Scanner sc = new Scanner(System.in); // "-1" con salva de chistosos y de hacer muchas más pruebas, jej
        int resultado = Metodos.revisarSeleccion(sc, 5, "-1");
        
        assertEquals(-1, resultado);
    } // testRevisarSeleccionSalidaEmergencia
    @Test
	void testRevisarSeleccionFueraDeRango() {
		// El scanner leerá "1" en la segunda iteración (después del fallo)
		String inputSimulado = "1\n"; 
		ByteArrayInputStream in = new ByteArrayInputStream(inputSimulado.getBytes());
		Scanner sc = new Scanner(in);
		
		int resultado = Metodos.revisarSeleccion(sc, 5, "10"); //fuera de rango
		assertEquals(0, resultado);
		assertTrue(outContent.toString().contains("Opción fuera de rango. Intente nuevamente."));
	} // testRevisarSeleccionFueraDeRango
    @Test
	void testRevisarSelLetrasInesperadas() {
		String inputSimulado = "2\n"; //case2
		ByteArrayInputStream in = new ByteArrayInputStream(inputSimulado.getBytes());
		Scanner sc = new Scanner(in);
		int resultado = Metodos.revisarSeleccion(sc, 5, "abc"); //entra en catch

		assertEquals(1, resultado);
		assertTrue(outContent.toString().contains("Entrada no válida, ingrese un número válido."));
	} // testRevisarSeleccionLetrasInesperadas
     
    @Test
    void testOrdenarAscYDesc() {
        ArrayList<Tarea> lista = new ArrayList<>();
        Tarea t1 = new Tarea(2, "Tarea 2");
        TareaUrgente tu1 = new TareaUrgente(1, "Tarea Urgente 1", true);
        Tarea t3 = new Tarea(3, "Tarea 3");
        
        lista.add(t1);
        lista.add(tu1);
        lista.add(t3);
        
        // Probamos Ascendente
        ArrayList<Tarea> ordenadaAsc = Metodos.ordenarAsc(lista);
        assertEquals(1, ordenadaAsc.get(0).getID()); // Urgente primero
        assertEquals(2, ordenadaAsc.get(1).getID()); // Normales por ID
        assertEquals(3, ordenadaAsc.get(2).getID());
        
        // Probamos Descendente
        ArrayList<Tarea> ordenadaDesc = Metodos.ordenarDesc(lista);
        assertEquals(1, ordenadaDesc.get(0).getID()); // Urgente primero
        assertEquals(3, ordenadaDesc.get(1).getID()); // Normales
        assertEquals(2, ordenadaDesc.get(2).getID());
    } // testOrdenarAscYDesc

    @Test
    void testMostrarTareas() {
        ArrayList<Tarea> lista = new ArrayList<>();
        lista.add(new Tarea(1, "Comprar pan"));
        lista.add(new TareaUrgente(2, "Pagar luz", true));
        
        Metodos.mostrarTareas(lista);
        
        String consola = outContent.toString();
        assertTrue(consola.contains("Comprar pan - Pendiente"));
        assertTrue(consola.contains("[URGENTE] Pagar luz - Pendiente"));
    } // testMostrarTareas

} // class MetodosTest