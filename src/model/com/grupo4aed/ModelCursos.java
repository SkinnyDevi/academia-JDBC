package model.com.grupo4aed;

import Connection.ConectionBD;
import view.com.grupo4aed.ModificacionDatosCursos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ModelCursos {

    private Statement stmt;
    public static String[] campos; // Usado para recoger campos en otros dialogos

    public ModelCursos() {
        ConectionBD.OpenConn();
    }

    public DefaultTableModel CargaDatos(DefaultTableModel m) {
        campos = new String[]{"Codigo Curso", "Codigo Programa Curso", "Fecha Inicio Curso", "Fecha Fin Curso"};
        m = new DefaultTableModel(null, campos);

        try {
            stmt = ConectionBD.getStmt();
            ResultSet rs = stmt.executeQuery("select * from cursos");
            String[] fila = new String[4];

            while (rs.next()) {
                fila[0] = rs.getString("codCurso");
                fila[1] = rs.getString("codProgramaCurso");
                fila[2] = rs.getString("fechaInicioCurso");
                fila[3] = rs.getString("fechaFinCurso");
                m.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return m;
    }

    // Usado para cargar un modelo con una consulta especifica (Filtros y Ordenados)
    public DefaultTableModel CargaDatos(DefaultTableModel m, String sql) {
        campos = new String[]{"Codigo Curso", "Codigo Programa Curso", "Fecha Inicio Curso", "Fecha Fin Curso"};
        m = new DefaultTableModel(null, campos);

        try {
            stmt = ConectionBD.getStmt();
            ResultSet rs = stmt.executeQuery(sql);
            String[] fila = new String[5];

            while (rs.next()) {
                fila[0] = rs.getString("codCurso");
                fila[1] = rs.getString("codProgramaCurso");
                fila[2] = rs.getString("fechaInicioCurso");
                fila[3] = rs.getString("fechaFinCurso");
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
                        "Eliminar curso?", "Eliminar Registro", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    DefaultTableModel cursos = new ModelCursos().CargaDatos(m);
                    String codCurso = (String) cursos.getValueAt(selected, 0);
                    Statement sentencia = ConectionBD.getStmt();
                    sentencia.executeUpdate(String.format("delete from cursos where codCurso = '%s'", codCurso));
                    ModelCursos p = new ModelCursos();
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
                ModificacionDatosCursos modificacion = new ModificacionDatosCursos();
                DefaultTableModel cursos = new ModelCursos().CargaDatos(m);
                modificacion.setData(cursos, selected); // Cargar datos del registro seleccionado
                modificacion.setVisible(true);
                tabla.setModel(new ModelCursos().CargaDatos(m));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            showDialog("Ocurrio un error al modificar el curso");
        }
        ModelCursos a = new ModelCursos();
        tabla.setModel(a.CargaDatos(m));
    }
}
