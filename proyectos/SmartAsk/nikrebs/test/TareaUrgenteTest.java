package com.nikrebs.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.nikrebs.fuente.TareaUrgente;

import static org.junit.jupiter.api.Assertions.*;

public class TareaUrgenteTest {

	private TareaUrgente tareaUrgente;

	@BeforeEach
	public void setUp() {
		tareaUrgente = new TareaUrgente(2, "Entregar Proyecto", true);
	}

	@Test
	public void testConstructorAtributoHeredadoYPropio() {
		assertEquals(2, tareaUrgente.getID());
		assertEquals("Entregar Proyecto", tareaUrgente.getTituloTarea());
		assertTrue(tareaUrgente.getUrgente());
	}

	@Test
	public void testAlternarEstadoUrgencia() {
		assertTrue(tareaUrgente.getUrgente()); //primigeneo

		tareaUrgente.setUrgente();
		assertFalse(tareaUrgente.getUrgente()); // inverso

		tareaUrgente.setUrgente();
		assertTrue(tareaUrgente.getUrgente()); //inverso del inverso 
	}
	@Test
	void testMostrarTareaUrgente() {		
		java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
		System.setOut(new java.io.PrintStream(out));
		
		tareaUrgente.mostrarTarea();
		
		assertTrue(out.toString().contains("[URGENTE]"));
		assertTrue(out.toString().contains("Entregar Proyecto"));
		
		System.setOut(System.out);
	} // testMostrarTareaUrgente


}