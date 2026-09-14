package com.nikrebs.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nikrebs.fuente.Main;

class MainTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final java.io.InputStream originalIn = System.in;

	@BeforeEach
	void setUp() { //Captura de consola
		System.setOut(new PrintStream(outContent));
	} // void setUp

	@AfterEach
	void tearDown() { //Resetear consola
		System.setOut(originalOut);
		System.setIn(originalIn);
	} // void tearDown

	@Test
	void testMainFlujoCompleto() {
		String simulacionUsuario = 
				"-1\n" +		// Salida de emergencia para graciosos
				"2\n" +          // Menú Principal: [2] Agregar tarea
				"Aprender JUnit\n" + // Título de la tarea
				"n\n" +          // ¿Desea descripción? No
				"n\n" +          // ¿Es urgente? No
				"3\n" +          // Menú Principal: [3] Cambiar nombre de usuario
				"NicoTest\n" +   // Nuevo nombre
				"1\n" +          // Menú Principal: [1] Revisar tareas
				"1\n" +          // Seleccionar la tarea ID 1 para ver detalles
				"3\n" +          // Submenú Tarea: [3] Cambiar estado
				"5\n" + 		 // Submenú Tarea: [3] Cambiar urgencia
				"6\n" +          // Submenú Tarea: [6] Volver al menú principal
				// 8\n me ayudó a encontrar un mal input en revisarSeleccion! tenía el menú anterior que era solo hasta  5 xD
				"6\n";           // Menú Principal: [4] Salir
		ByteArrayInputStream in = new ByteArrayInputStream(simulacionUsuario.getBytes());
		System.setIn(in);

		Main.main(new String[]{});

		// Todo lo impreso de la consola se guarda aqui
		String salidaConsola = outContent.toString();

		//Textos esperados
		assertTrue(salidaConsola.contains("Nombre cambiado de Persona a NicoTest correctamente"));
		assertTrue(salidaConsola.contains("Estado cambiado de Pendiente a Terminada"));
		assertTrue(salidaConsola.contains("Esta tarea no es urgente!"));
		assertTrue(salidaConsola.contains("Debes seleccionarla como tarea urgente en su creacion!"));
		assertTrue(salidaConsola.contains("Cerrando app..."));
		assertTrue(salidaConsola.contains("Buena suerte:)"));
		
	} // void testMainFlujoCompleto
	
	@Test 
	void testMainCrearTareaUrgenteConDescripcion() {
	    String simulacionUsuario = 
	            "2\n" +          // Menú Principal: [2] Agregar tarea
	            "Pagar la luz\n" + // Título de la tarea
	            "y\n" +          // ¿Desea descripción? Sí
	            "Vence mañana\n" + // Descripción
	            "y\n" +          // ¿Es urgente? Sí
	            "1\n" +          // Menú Principal: [1] Revisar tareas
	            "1\n" +          // Seleccionar la tarea 1 para ver detalles
	            "1\n" +          // Submenú Tarea: [1] Editar título
	            "Pagar el agua\n" + // Nuevo título
	            "2\n" +          // Submenú Tarea: [2] Editar descripción
	            "Vence el 06/07\n" + // Nueva descripción
	            "3\n" +          // Submenú Tarea: [3] Cambiar estado (Pendiente a Terminada)
	            "3\n" +          // Submenú Tarea: [3] Cambiar estado de vuelta (Terminada a Pendiente)
	            "5\n" +          // Submenú Tarea: [5] Cambiar estado de urgencia
	            "4\n" +          // Submenú Tarea: [4] Eliminar tarea
	            "6\n";           // Menú Principal: [6] Salir

	    ByteArrayInputStream in = new ByteArrayInputStream(simulacionUsuario.getBytes());
	    System.setIn(in);

	    Main.main(new String[]{});

	    String salidaConsola = outContent.toString();
	    assertTrue(salidaConsola.contains("Describa la tarea"));
	    assertTrue(salidaConsola.contains("Título cambiado de 'Pagar la luz' a 'Pagar el agua'"));
	    assertTrue(salidaConsola.contains("Descripcion cambiada a 'Vence el 06/07'"));
	    assertTrue(salidaConsola.contains("Estado cambiado de Pendiente a Terminada"));
	    assertTrue(salidaConsola.contains("Estado cambiado de Terminada a Pendiente"));
	    assertTrue(salidaConsola.contains("Urgencia modificada correctamente!"));
	    assertTrue(salidaConsola.contains("Tarea removida correctamente."));
	    assertTrue(salidaConsola.contains("Cerrando app..."));
	} //testMainCrearTareaUrgenteConDescripcion

} // class MainTest