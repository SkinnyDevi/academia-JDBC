package model.com.felix;

import Connection.ConectionBD;
import view.com.felix.ModificacionDatosEmpresas;
import view.com.felix.ModificacionDatosProfesores;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ModelProfesores {

    private Statement stmt;
    public static String[] campos; // Usado para recoger campos en otros dialogos

    public ModelProfesores() {
        ConectionBD.OpenConn();
    }

    public DefaultTableModel CargaDatos(DefaultTableModel m) {
        campos = new String[]{"DNI", "Nombre", "Apellido", "Telefono", "Direccion"};
        m = new DefaultTableModel(null, campos);

        try {
            stmt = ConectionBD.getStmt();
            ResultSet rs = stmt.executeQuery("select * from profesores");
            String[] fila = new String[5];

            while (rs.next()) {
                fila[0] = rs.getString("dni");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("apellido");
                fila[3] = rs.getString("telefono");
                fila[4] = rs.getString("direccion");
                m.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return m;
    }

    // Usado para cargar un modelo con una consulta especifica (Filtros y Ordenados)
    public DefaultTableModel CargaDatos(DefaultTableModel m, String sql) {
        campos = new String[]{"DNI", "Nombre", "Apellido", "Telefono", "Direccion"};
        m = new DefaultTableModel(null, campos);

        try {
            stmt = ConectionBD.getStmt();
            ResultSet rs = stmt.executeQuery(sql);
            String[] fila = new String[5];

            while (rs.next()) {
                fila[0] = rs.getString("dni");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("apellido");
                fila[3] = rs.getString("telefono");
                fila[4] = rs.getString("direccion");
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
                        "Eliminar profesor?", "Eliminar Registro", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    DefaultTableModel profesores = new ModelProfesores().CargaDatos(m);
                    String dni = (String) profesores.getValueAt(selected, 0);
                    Statement sentencia = ConectionBD.getStmt();
                    sentencia.executeUpdate(String.format("delete from profesores where dni = '%s'", dni));
                    ModelProfesores p = new ModelProfesores();
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
                ModificacionDatosProfesores modificacion = new ModificacionDatosProfesores();
                DefaultTableModel profesores = new ModelProfesores().CargaDatos(m);
                modificacion.setData(profesores, selected); // Cargar datos del registro seleccionado
                modificacion.setVisible(true);
                tabla.setModel(new ModelProfesores().CargaDatos(m));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            showDialog("Ocurrio un error al modificar el profesor");
        }
        ModelProfesores a = new ModelProfesores();
        tabla.setModel(a.CargaDatos(m));
    }
}
