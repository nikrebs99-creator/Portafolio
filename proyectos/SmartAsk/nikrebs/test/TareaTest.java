/**
 * 
 */
package com.nikrebs.test;

import static org.junit.jupiter.api.Assertions.*;

//import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nikrebs.fuente.Tarea;
/**
 * 
 */
class TareaTest {

	private Tarea t;

	@BeforeEach
	void setUp() throws Exception {
		t = new Tarea(1, "Titulo");
		t.setDescripcion("Lorem Ipsum bla bla bla");
	} //void setUp
	
	@Test
	void testGetTitutlo() {
		String esperado = "Titulo";
		assertEquals(esperado, t.getTituloTarea());
	}  //void testGetTitulo
 
	@Test
	void testSetTitulo() {
		t.setTituloTarea("Cambiazo");
		String esperado = "Cambiazo";
		assertEquals(esperado, t.getTituloTarea());
	} //testSetTitulo
	
	@Test
	void testGetDescription() {
		String esperado = "Lorem Ipsum bla bla bla";
		assertEquals(esperado, t.getDescripcion());
	} //void testGetDescription
	
	@Test
	void testGetPendiente() {
		boolean esperado = true;
		String esperado1 = "Pendiente";
		String esperado2 = "Terminada";
		assertEquals(esperado, t.getPendiente());
		assertEquals(esperado1, t.getPendienteStr());
		t.TareaTerminada();
		assertEquals(esperado2, t.getPendienteStr());

		
	} //void testGetPendiente
	
	@Test
	void testTareaPendiente() {
		t.TareaPendiente();
		assertEquals(true, t.getPendiente());
	}
	
	@Test
	void testTareaTerminada() {
		t.TareaTerminada();
		assertEquals(false, t.getPendiente());		
	}
	
	@Test
	void testGetID() {
		int esperado = 1;
		assertEquals(esperado, t.getID());
	}
	
	@Test
	void testSetID() {
		int esperado = 999;
		t.setID(999);
		assertEquals(esperado, t.getID());

	}
	@Test
	void testMostrarTarea() {
		// con java.io.ByteArrayOutputStream robamos el output
		java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream(); //la documentación de este metodo no está chistosa
		System.setOut(new java.io.PrintStream(out)); // No entiendo del todo bien como funciona, pero funciona
		
		
		t.mostrarTarea();
		
		assertTrue(out.toString().contains("[1] Titulo - Pendiente"));
		
		System.setOut(System.out); //y esto para que la consola no explote
	} // testMostrarTarea
	
	
} // TareaTest
