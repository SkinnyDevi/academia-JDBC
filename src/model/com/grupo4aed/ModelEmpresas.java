package model.com.grupo4aed;

import Connection.ConectionBD;
import view.com.grupo4aed.ModificacionDatosEmpresas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ModelEmpresas {

    private Statement stmt;
    public static String[] campos; // Usado para recoger campos en otros dialogos

    public ModelEmpresas() {
        ConectionBD.OpenConn();
    }

    public DefaultTableModel CargaDatos(DefaultTableModel m) {
        campos = new String[]{"CIF", "Nombre", "Telefono", "Direccion"};
        m = new DefaultTableModel(null, campos);

        try {
            stmt = ConectionBD.getStmt();
            ResultSet rs = stmt.executeQuery("select * from empresas");
            String[] fila = new String[4];

            while (rs.next()) {
                fila[0] = rs.getString("cif");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("telefono");
                fila[3] = rs.getString("direccion");
                m.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return m;
    }

    // Usado para cargar un modelo con una consulta especifica (Filtros y Ordenados)
    public DefaultTableModel CargaDatos(DefaultTableModel m, String sql) {
        campos = new String[]{"CIF", "Nombre", "Telefono", "Direccion"};
        m = new DefaultTableModel(null, campos);

        try {
            stmt = ConectionBD.getStmt();
            ResultSet rs = stmt.executeQuery(sql);
            String[] fila = new String[4];

            while (rs.next()) {
                fila[0] = rs.getString("cif");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("telefono");
                fila[3] = rs.getString("direccion");
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
                        "Eliminar empresa?", "Eliminar Registro", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    DefaultTableModel empresas = new ModelEmpresas().CargaDatos(m);
                    String cif = (String) empresas.getValueAt(selected, 0);
                    Statement sentencia = ConectionBD.getStmt();
                    sentencia.executeUpdate(String.format("delete from empresas where cif = '%s'", cif));
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
                DefaultTableModel empresas = new ModelEmpresas().CargaDatos(m);
                modificacion.setData(empresas, selected); // Cargar datos del registro seleccionado
                modificacion.setVisible(true);
                tabla.setModel(new ModelEmpresas().CargaDatos(m));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            showDialog("Ocurrio un error al modificar la empresa");
        }
        ModelEmpresas a = new ModelEmpresas();
        tabla.setModel(a.CargaDatos(m));
    }
}
