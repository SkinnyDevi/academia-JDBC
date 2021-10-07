package view.com.grupo4aed;

import model.com.grupo4aed.ModelEmpresas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;

import java.util.ArrayList;

public class FiltradoEmpresas extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nombreText;
    private JTextField telefonoText;
    private JTextField direccionText;
    private JTextField cifText;
    private ArrayList<String> previousFilter = new ArrayList<>();
    private String previousQuery = "select * from empresas";

    public FiltradoEmpresas(JTable dbTable, DefaultTableModel dftm) {
        setTitle("Filtrado de Empresas");
        setContentPane(contentPane);
        setSize(550, 550);
        setLocation(ViewEntrada.ancho / 3, ViewEntrada.alto / 4);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cif, nom, tel, dir;
                cif = cifText.getText();
                nom = nombreText.getText();
                tel = telefonoText.getText();
                dir = direccionText.getText();
                boolean where = false;

                String query = "select * from empresas";

                if (fieldIsEmpty(cif)) {
                    query += " where cif like '" + cif + "%'";
                    where = true;
                } else {
                    cif = "";
                }

                if (fieldIsEmpty(nom)) {
                    String q = "nombre like '" + nom + "%'";
                    if (where) {
                        query += " and " + q;
                    } else {
                        query += " where " + q;
                        where = true;
                    }
                } else {
                    nom = "";
                }

                if (fieldIsEmpty(tel)) {
                    String q = "telefono like '" + tel + "%'";
                    if (where) {
                        query += " and " + q;
                    } else {
                        query += " where " + q;
                        where = true;
                    }
                } else {
                    tel = "";
                }

                if (fieldIsEmpty(dir)) {
                    String q = "direccion like '" + dir + "%'";
                    if (where) {
                        query += " and " + q;
                    } else {
                        query += " where " + q;
                    }
                } else {
                    dir = "";
                }

                // Cargar al arraylist para especificar que este fue el filtro usado
                previousFilter.add(cif);
                previousFilter.add(nom);
                previousFilter.add(tel);
                previousFilter.add(dir);

                System.out.println(query);
                ModelEmpresas empresas = new ModelEmpresas();
                dbTable.setModel(empresas.CargaDatos(dftm, query));
                previousQuery = query; // Cargamos a la consulta anterior para el ordenado
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { // Usado para recuperar el filtro en casp de cancelado
                String id, nom, tel, dir;
                id = cifText.getText();
                nom = nombreText.getText();
                tel = telefonoText.getText();
                dir = direccionText.getText();
                previousFilter.add(id);
                previousFilter.add(nom);
                previousFilter.add(tel);
                previousFilter.add(dir);
                onCancel();
            }
        });
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private boolean fieldIsEmpty(String field) { // Usado para determinar si el campo esta vacio
        if (field.equals("") || field == null) {
            return false;
        } else {
            return !field.equals("--");
        }
    }

    public String getPreviousQuery() {
        return previousQuery;
    }

    public ArrayList<String> getPreviousFilter() {
        return previousFilter;
    }

    public void setData(ArrayList<String> pvData) { // Cargar datos del filtro anterior al filtro nuevo
        if (!(pvData.size() == 0)) {
            cifText.setText(pvData.get(0));
            nombreText.setText(pvData.get(1));
            telefonoText.setText(pvData.get(2));
            direccionText.setText(pvData.get(3));
        }
    }
}
