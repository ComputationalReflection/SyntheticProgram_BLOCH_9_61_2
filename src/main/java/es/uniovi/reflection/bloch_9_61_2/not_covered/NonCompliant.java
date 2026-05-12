package es.uniovi.reflection.bloch_9_61_2.not_covered;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NonCompliant {

    public void initClause() throws BusinessException {
        try (Connection c = DriverManager.getConnection("jdbc:demo")) {
            c.createStatement().executeQuery("SELECT * FROM tabla");
        } catch (SQLException e) {
            BusinessException newE = new BusinessException("No se pudo consultar la BD"); // ❌
            newE.initCause(null);
            throw newE;
        }
    }

    public void usedButNoSuperCause() throws OtherBusinessException {
        try (Connection c = DriverManager.getConnection("jdbc:demo")) {
            c.createStatement().executeQuery("SELECT * FROM tabla");
        } catch (SQLException e) {
           throw new OtherBusinessException(e, "No se pudo consultar la BD"); // ❌
        }
    }

    static class BusinessException extends Exception { public BusinessException(String msg){super(msg);} }
    static class OtherBusinessException extends Exception { public OtherBusinessException(Exception cause, String msg){super(msg);} }

 }
