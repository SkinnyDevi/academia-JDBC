package model.com.felix;

import Connection.ConectionBD;
import view.com.felix.ModificacionDatosAlumnos;
import view.com.felix.ModificacionDatosCursos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ModelAlumnos {

    private Statement stmt;
    public static String[] campos; // Usado para recoger campos en otros dialogos

    public ModelAlumnos() {
        ConectionBD.OpenConn();
    }

    public DefaultTableModel CargaDatos(DefaultTableModel m) {
        campos = new String[]{"DNI", "Nombre", "Apellido", "Telefono", "Edad", "Direccion"};
        m = new DefaultTableModel(null, campos);

        try {
            stmt = ConectionBD.getStmt();
            ResultSet rs = stmt.executeQuery("select * from alumnos");
            String[] fila = new String[6];

            while (rs.next()) {
                fila[0] = rs.getString("dni");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("apellido");
                fila[3] = rs.getString("telefono");
                fila[4] = rs.getString("edad");
                fila[5] = rs.getString("direccion");
                m.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return m;
    }

    // Usado para cargar un modelo con una consulta especifica (Filtros y Ordenados)
    public DefaultTableModel CargaDatos(DefaultTableModel m, String sql) {
        campos = new String[]{"DNI", "Nombre", "Apellido", "Telefono", "Edad", "Direccion"};
        m = new DefaultTableModel(null, campos);

        try {
            stmt = ConectionBD.getStmt();
            ResultSet rs = stmt.executeQuery(sql);
            String[] fila = new String[6];

            while (rs.next()) {
                fila[0] = rs.getString("dni");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("apellido");
                fila[3] = rs.getString("telefono");
                fila[4] = rs.getString("edad");
                fila[5] = rs.getString("direccion");
                m.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return m;
    }

    private void showDialog(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

    public void eliminarRegistro(JTable tabla, DefaultTableModel m) {
        int selected;
        try {
            selected = tabla.getSelectedRow();
            if (selected == -1) {
                showDialog("No se ha seleccionado ningun registro");
            } else {
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Eliminar alumno?", "Eliminar Registro", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    DefaultTableModel alumnos = new ModelAlumnos().CargaDatos(m);
                    String dni = (String) alumnos.getValueAt(selected, 0);
                    Statement sentencia = ConectionBD.getStmt();
                    sentencia.executeUpdate(String.format("delete from alumnos where dni = '%s'", dni));
                    ModelAlumnos p = new ModelAlumnos();
                    tabla.setModel(p.CargaDatos(m));
                }
            }
        } catch (Exception exc) {
            showDialog("Error al eliminar registro.");
            exc.printStackTrace();
        }
    }

    public void editarRegistro(JTable tabla, DefaultTableModel m) {
        int selected = tabla.getSelectedRow();
        try {
            if (selected == -1) {
                showDialog("No se ha seleccionado ningun registro");
            } else {
                ModificacionDatosAlumnos modificacion = new ModificacionDatosAlumnos();
                DefaultTableModel alumnos = new ModelAlumnos().CargaDatos(m);
                modificacion.setData(alumnos, selected); // Cargar datos del registro seleccionado
                modificacion.setVisible(true);
                tabla.setModel(new ModelAlumnos().CargaDatos(m));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            showDialog("Ocurrio un error al modificar el alumno");
        }
        ModelAlumnos a = new ModelAlumnos();
        tabla.setModel(a.CargaDatos(m));
    }
}
