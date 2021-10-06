package model.com.felix;

import Connection.ConectionBD;
import view.com.felix.ModificacionDatosProfesores;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ModelProgramasCursos {

    private Statement stmt;
    public static String[] campos; // Usado para recoger campos en otros dialogos

    public ModelProgramasCursos() {
        ConectionBD.OpenConn();
    }

    public DefaultTableModel CargaDatos(DefaultTableModel m) {
        campos = new String[]{"Codigo Programa Curso", "Duracion (Horas)", "Titulo"};
        m = new DefaultTableModel(null, campos);

        try {
            stmt = ConectionBD.getStmt();
            ResultSet rs = stmt.executeQuery("select * from programasCursos");
            String[] fila = new String[3];

            while (rs.next()) {
                fila[0] = rs.getString("codProgramaCurso");
                fila[1] = rs.getString("duracionCurso");
                fila[2] = rs.getString("titulo");
                m.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return m;
    }

    // Usado para cargar un modelo con una consulta especifica (Filtros y Ordenados)
    public DefaultTableModel CargaDatos(DefaultTableModel m, String sql) {
        campos = new String[]{"Codigo Programa Curso", "Duracion (Horas)", "Titulo"};
        m = new DefaultTableModel(null, campos);

        try {
            stmt = ConectionBD.getStmt();
            ResultSet rs = stmt.executeQuery(sql);
            String[] fila = new String[3];

            while (rs.next()) {
                fila[0] = rs.getString("codProgramaCurso");
                fila[1] = rs.getString("duracionCurso");
                fila[2] = rs.getString("titulo");
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
                        "Eliminar Programa?", "Eliminar Registro", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    DefaultTableModel programasCursos = new ModelProgramasCursos().CargaDatos(m);
                    String codProgramasCurso = (String) programasCursos.getValueAt(selected, 0);
                    Statement sentencia = ConectionBD.getStmt();
                    sentencia.executeUpdate(String.format("delete from programasCursos where codProgramaCurso = '%s'", codProgramasCurso));
                    ModelProgramasCursos p = new ModelProgramasCursos();
                    tabla.setModel(p.CargaDatos(m));
                }
            }
        } catch (Exception exc) {
            showDialog("Error al eliminar registro.");
            if (exc.getMessage().contains("Cannot delete or update")) {
                showDialog("No se ha podido eliminar la entrada \ndado que existen datos en la tabla 'Cursos' \nrelacionados a este.");
            }
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
                DefaultTableModel profesores = new ModelProgramasCursos().CargaDatos(m);
                modificacion.setData(profesores, selected); // Cargar datos del registro seleccionado
                modificacion.setVisible(true);
                tabla.setModel(new ModelProgramasCursos().CargaDatos(m));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            showDialog("Ocurrio un error al modificar el programa");
        }
        ModelProgramasCursos a = new ModelProgramasCursos();
        tabla.setModel(a.CargaDatos(m));
    }
}
