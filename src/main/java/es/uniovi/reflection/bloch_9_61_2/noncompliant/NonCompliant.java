package es.uniovi.reflection.bloch_9_61_2.noncompliant;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NonCompliant {

    // 1. Reemplaza IOException pero no pasa la causa
    public void leerArchivo() throws RuntimeException {
        try {
            Files.readAllLines(Path.of("data.txt"));
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo archivo" + e.getMessage());  // ❌ sin cause
        }
    }


    // 3. Error al parsear JSON, se pierde la causa
    public void parsearJson() throws IllegalArgumentException {
        try {
            Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Entrada no válida", null); // ❌
        }
    }

    // 4. Error de red envuelto incorrectamente
    public void conectarServidor() throws RuntimeException {
        try (Socket s = new Socket("localhost", 9999)) {
        } catch (IOException e) {
            IOException anotherE = null;
            throw new RuntimeException("Fallo conectando al servidor", anotherE); // ❌
        }
    }

    // 5. Error en lógica de negocio, vuelve a lanzar sin cause
    public void procesarOperacion() throws CustomOperationException {
        try {
            String s = null;
            s.length();
        } catch (NullPointerException e) {
            throw new CustomOperationException("Operación inválida"); // ❌
        }
    }

  static class CustomOperationException extends Exception { public CustomOperationException(String msg){super(msg);} }
}
