package es.uniovi.reflection.bloch_9_61_2.compliant;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Compliant {

    // 1. Incluye la causa directamente en el constructor
    public void leerArchivo() throws RuntimeException {
        try {
            Files.readAllLines(Path.of("data.txt"));
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo archivo", e);  // ✔ con cause
        }
    }

    // 2. BusinessException recibe la causa mediante initCause()
    public void consultarDB() throws BusinessException {
        try (Connection c = DriverManager.getConnection("jdbc:demo")) {
            c.createStatement().executeQuery("SELECT * FROM tabla");
        } catch (SQLException e) {
            BusinessException ex = new BusinessException("No se pudo consultar la BD");
            ex.initCause(e); // ✔ con initCause
            throw ex;
        }
    }

    // 3. Reenvuelve NumberFormatException correctamente
    public void parsearJson() throws IllegalArgumentException {
        try {
            Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            throw new MyRuntimeEx("Entrada no válida", e); // ✔
        }
    }

    // 4. Error de red envuelto con cause
    public void conectarServidor() throws RuntimeException {
        try (Socket s = new Socket("localhost", 9999)) {
        } catch (IOException e) {
            throw new RuntimeException("Fallo conectando al servidor", e); // ✔
        }
    }

    // 5. Error en lógica de negocio preservando causa
    public void procesarOperacion() throws CustomOperationException {
        try {
            String s = null;
            s.length();
        } catch (NullPointerException e) {
            throw new CustomOperationException("Operación inválida","", 0, e); // ✔
        }
    }

    // Excepciones de ejemplo
    static class BusinessException extends Exception { public BusinessException(String msg){super(msg);} }
    static class MyRuntimeEx extends RuntimeException { public MyRuntimeEx(String msg, Throwable cause){super(msg, cause);} }

    static class CustomOperationException extends Exception {
        public CustomOperationException(String msg, String otherArg, int intArg, Throwable cause){ super(msg, cause); }
        public CustomOperationException(String msg){ super(msg); }
    }
}
