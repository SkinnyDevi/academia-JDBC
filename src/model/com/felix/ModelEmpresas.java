package model.com.felix;

import Connection.ConectionBD;
import view.com.felix.ModificacionDatosEmpresas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ModelEmpresas {

    private Statement stmt;
    public static String[] titulos; // Usado para recoger titulos en otros dialogos

    public ModelEmpresas() {
        ConectionBD.OpenConn();
    }

    public DefaultTableModel CargaDatos(DefaultTableModel m) {
        titulos = new String[]{"id", "Nombre", "Creditos", "Tipo", "Curso", "Cuatrimestre", "Id Profesor", "Id Grado"};
        m = new DefaultTableModel(null, titulos);

        try {
            stmt = ConectionBD.getStmt();
            ResultSet rs = stmt.executeQuery("select * from asignatura");
            String[] fila = new String[8];

            while (rs.next()) {
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("creditos");
                fila[3] = rs.getString("tipo");
                fila[4] = rs.getString("curso");
                fila[5] = rs.getString("cuatrimestre");
                fila[6] = rs.getString("id_profesor");
                fila[7] = rs.getString("id_grado");
                m.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return m;
    }

    // Usado para cargar un modelo con una consulta especifica (Filtros y Ordenados)
    public DefaultTableModel CargaDatos(DefaultTableModel m, String sql) {
        titulos = new String[]{"id", "Nombre", "Creditos", "Tipo", "Curso", "Cuatrimestre", "Id Profesor", "Id Grado"};
        m = new DefaultTableModel(null, titulos);

        try {
            stmt = ConectionBD.getStmt();
            ResultSet rs = stmt.executeQuery(sql);
            String[] fila = new String[8];

            while (rs.next()) {
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("creditos");
                fila[3] = rs.getString("tipo");
                fila[4] = rs.getString("curso");
                fila[5] = rs.getString("cuatrimestre");
                fila[6] = rs.getString("id_profesor");
                fila[7] = rs.getString("id_grado");
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
                        "Eliminar?", "Eliminar Registro", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    DefaultTableModel asignaturas = new ModelEmpresas().CargaDatos(m);
                    String id = (String) asignaturas.getValueAt(selected, 0);
                    Statement sentencia = ConectionBD.getStmt();
                    sentencia.executeUpdate("delete from alumno_se_matricula_asignatura where id_asignatura = " + id);
                    sentencia.executeUpdate("delete from asignatura where id = " + id);
                    ModelEmpresas p = new ModelEmpresas();
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
                ModificacionDatosEmpresas modificacion = new ModificacionDatosEmpresas();
                DefaultTableModel asignaturas = new ModelEmpresas().CargaDatos(m);
                modificacion.setData(asignaturas, selected); // Cargar datos del registro seleccionado
                modificacion.setVisible(true);
                tabla.setModel(new ModelEmpresas().CargaDatos(m));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            showDialog("Ocurrio un error al modificar la asignatura");
        }
        ModelEmpresas a = new ModelEmpresas();
        tabla.setModel(a.CargaDatos(m));
    }
}
