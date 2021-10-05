package view.com.felix;

import model.com.felix.ModelEmpresas;
import model.com.felix.ModelProfesores;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class FiltradoProfesores extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nombreText;
    private JTextField telefonoText;
    private JTextField direccionText;
    private JTextField dniText;
    private JTextField apellidoText;
    private ArrayList<String> previousFilter = new ArrayList<>();
    private String previousQuery = "select * from profesores";

    public FiltradoProfesores(JTable dbTable, DefaultTableModel dftm) {
        setTitle("Filtrado de Profesores");
        setContentPane(contentPane);
        setSize(550, 550);
        setLocation(ViewEntrada.ancho / 3, ViewEntrada.alto / 4);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String dni, nom, apel, tel, dir;
                dni = dniText.getText();
                nom = nombreText.getText();
                apel = apellidoText.getText();
                tel = telefonoText.getText();
                dir = direccionText.getText();
                boolean where = false;

                String query = "select * from profesores";

                if (fieldIsEmpty(dni)) {
                    query += " where dni like '" + dni + "%'";
                    where = true;
                } else {
                    dni = "";
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

                if (fieldIsEmpty(apel)) {
                    String q = "apellido like '" + apel + "%'";
                    if (where) {
                        query += " and " + q;
                    } else {
                        query += " where " + q;
                        where = true;
                    }
                } else {
                    apel = "";
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
                previousFilter.add(dni);
                previousFilter.add(nom);
                previousFilter.add(apel);
                previousFilter.add(tel);
                previousFilter.add(dir);

                System.out.println(query);
                ModelProfesores profesores = new ModelProfesores();
                dbTable.setModel(profesores.CargaDatos(dftm, query));
                previousQuery = query; // Cargamos a la consulta anterior para el ordenado
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { // Usado para recuperar el filtro en casp de cancelado
                String dni, nom, apel, tel, dir;
                dni = dniText.getText();
                nom = nombreText.getText();
                apel = apellidoText.getText();
                tel = telefonoText.getText();
                dir = direccionText.getText();
                previousFilter.add(dni);
                previousFilter.add(nom);
                previousFilter.add(apel);
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
            dniText.setText(pvData.get(0));
            nombreText.setText(pvData.get(1));
            apellidoText.setText(pvData.get(2));
            telefonoText.setText(pvData.get(3));
            direccionText.setText(pvData.get(4));
        }
    }
}
