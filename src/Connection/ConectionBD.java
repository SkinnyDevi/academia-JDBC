package Connection;

import javax.swing.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConectionBD {

    private static Connection conn = null;
    private static final String url = "jdbc:mysql://localhost";
    private static final String port = "3306";
    private static final String user = "root";
    private static final String passwd = "baeElrincon";
    private static final String db = "academiaClases";
    private static Statement stmt;
    private static ResultSet rs;


    //Abrir la conexión de la BBDD
    public static void OpenConn() {

        // Primero se comprubea que carga el controlador
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar el controlador");
        }

        // Seguidamente se intenta establecer al oonexión
        try {
            String sUrl = url + ":" + port + "/" + db + "?zeroDateTimeBehavior=convertToNull";
            conn = DriverManager.getConnection(sUrl, user, passwd);
            System.out.println(sUrl);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error en la conexión");
        }

        // Por último se carga el objeto de la clase Statement que se utilizará para realizar las consultas
        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException ex) {
        }

    }

    public static Connection getConn() {
        return conn;
    }

    //Cuando se cierre la aplicación hay que cerrar la conexión a la BBDD
    public static void CloseConn() {
        try {
            JOptionPane.showMessageDialog(null, "Se cerró la conexión con la BBDD");
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static Statement getStmt() {
        return stmt;
    }
}
