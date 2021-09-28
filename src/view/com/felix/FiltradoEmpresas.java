package view.com.felix;

import model.com.felix.ModelEmpresas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;

import java.util.ArrayList;

public class FiltradoEmpresas extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nombreText;
    private JTextField creditosText;
    private JTextField cursoText;
    private JTextField cuatrimestreText;
    private JTextField idProfeText;
    private JTextField tipoText;
    private JTextField idGradoText;
    private JTextField idText;
    private JComboBox<String> operatorsCombo;
    private JRadioButton vacioRadio;
    private JRadioButton conProfeRadio;
    private JRadioButton genericoRadioButton;
    private ArrayList<String> previousFilter = new ArrayList<>();
    private String previousQuery = "select * from asignatura";

    public FiltradoEmpresas(JTable dbTable, DefaultTableModel dftm) {
        setTitle("Filtrado de Asignaturas");
        setContentPane(contentPane);
        setSize(550, 550);
        setLocation(ViewEntrada.ancho / 3, ViewEntrada.alto / 4);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // Operadores para campos numeros especificos
        DefaultComboBoxModel<String> operators = new DefaultComboBoxModel<>();
        String[] ops = {"=", "<", ">","<=", ">=", "!="};
        for (int i = 0; i < 6; i++)
            operators.addElement(ops[i]);
        operatorsCombo.setModel(operators);

        // Botones Radio para asignatura con o sin profesor
        ButtonGroup bg = new ButtonGroup();
        bg.add(genericoRadioButton);
        bg.add(vacioRadio);
        bg.add(conProfeRadio);

        genericoRadioButton.doClick(); // Radio seleccionado predeterminado

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id, nom, cred, tipo, curso, cuatri, idProf, idGr;
                id = idText.getText();
                nom = nombreText.getText();
                cred = creditosText.getText();
                tipo = tipoText.getText().toLowerCase();
                curso = cursoText.getText();
                cuatri = cuatrimestreText.getText();
                idProf = idProfeText.getText();
                idGr = idGradoText.getText();
                boolean where = false;

                String query = "select * from asignatura";

                if (fieldIsEmpty(id)) {
                    query += " where id = '" + id + "'";
                    where = true;
                } else {
                    id = "";
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

                if (fieldIsEmpty(cred)) {
                    String op = (String) operators.getSelectedItem();
                    String q = "creditos " + op + " " + cred;
                    if (where) {
                        query += " and " + q;
                    } else {
                        query += " where " + q;
                        where = true;
                    }
                } else {
                    cred = "";
                }

                if (fieldIsEmpty(tipo)) {
                    String q = "tipo = '" + tipo + "'";
                    if (where) {
                        query += " and " + q;
                    } else {
                        query += " where " + q;
                        where = true;
                    }
                } else {
                    tipo = "";
                }

                if (fieldIsEmpty(curso)) {
                    String q = "curso = " + curso;
                    if (where) {
                        query += " and " + q;
                    } else {
                        query += " where " + q;
                        where = true;
                    }
                } else {
                    curso = "";
                }

                if (fieldIsEmpty(cuatri)) {
                    String q = "cuatrimestre = '" + cuatri + "%'";
                    if (where) {
                        query += " and " + q;
                    } else {
                        query += " where " + q;
                        where = true;
                    }
                } else {
                    cuatri = "";
                }

                if (fieldIsEmpty(idProf)) {
                    if (where) {
                        query += " and ";
                    } else {
                        query += " where ";
                        where = true;
                    }

                    String q, subq;

                    if (vacioRadio.isSelected()) { // Filtrar si el campo no tiene profesor
                        subq = "select profesor.id_profesor from profesor where profesor.id_profesor = asignatura.id_profesor";
                        q = "not exists (" + subq + ")";
                    } else if (conProfeRadio.isSelected()) { // Filtrar si el campo tiene profesor
                        subq = "select profesor.id_profesor from profesor where profesor.id_profesor = asignatura.id_profesor";
                        q = "exists (" + subq + ")";
                    } else {
                        q = "id_profesor = '" + idProf + "%'";
                    }
                    query += q;
                } else {
                    idProf = "";
                }

                if (fieldIsEmpty(idGr)) {
                    String q = "id_grado = '" + idGr + "'";
                    if (where) {
                        query += " and " + q;
                    } else {
                        query += " where " + q;
                    }
                } else {
                    idGr = "";
                }

                // Cargar al arraylist para especificar que este fue el filtro usado
                previousFilter.add(id);
                previousFilter.add(nom);
                previousFilter.add(cred);
                previousFilter.add(tipo);
                previousFilter.add(curso);
                previousFilter.add(cuatri);
                previousFilter.add(idProf);
                previousFilter.add(idGr);
                previousFilter.add((String) operatorsCombo.getSelectedItem());
                previousFilter.add(String.valueOf(vacioRadio.isSelected()));
                previousFilter.add(String.valueOf(conProfeRadio.isSelected()));

                System.out.println(query);
                ModelEmpresas asignaturas = new ModelEmpresas();
                dbTable.setModel(asignaturas.CargaDatos(dftm, query));
                previousQuery = query; // Cargamos a la consulta anterior para el ordenado
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { // Usado para recuperar el filtro en casp de cancelado
                String id, nom, cred, tipo, curso, cuatri, idProf, idGr;
                id = idText.getText();
                nom = nombreText.getText();
                cred = creditosText.getText();
                tipo = tipoText.getText().toLowerCase();
                curso = cursoText.getText();
                cuatri = cuatrimestreText.getText();
                idProf = idProfeText.getText();
                idGr = idGradoText.getText();
                previousFilter.add(id);
                previousFilter.add(nom);
                previousFilter.add(cred);
                previousFilter.add(tipo);
                previousFilter.add(curso);
                previousFilter.add(cuatri);
                previousFilter.add(idProf);
                previousFilter.add(idGr);
                previousFilter.add((String) operatorsCombo.getSelectedItem());
                previousFilter.add(String.valueOf(vacioRadio.isSelected()));
                previousFilter.add(String.valueOf(conProfeRadio.isSelected()));
                onCancel();
            }
        });

        genericoRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idProfeText.setText("");
                idProfeText.setEnabled(true);
                idProfeText.setEditable(true);
            }
        });

        vacioRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idProfeText.setText("null");
                idProfeText.setEnabled(false);
                idProfeText.setEditable(false);
            }
        });

        conProfeRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idProfeText.setText("not null");
                idProfeText.setEnabled(false);
                idProfeText.setEditable(false);
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
            if (field.equals("--")) {
                return false;
            } else {
                return true;
            }
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
            idText.setText(pvData.get(0));
            nombreText.setText(pvData.get(1));
            creditosText.setText(pvData.get(2));
            tipoText.setText(pvData.get(3));
            cursoText.setText(pvData.get(4));
            cuatrimestreText.setText(pvData.get(5));
            idProfeText.setText(pvData.get(6));
            idGradoText.setText(pvData.get(7));

            operatorsCombo.setSelectedItem(pvData.get(8));

            boolean vacio = Boolean.parseBoolean(pvData.get(9));
            boolean conProfe = Boolean.parseBoolean(pvData.get(10));

            if (vacio)
                vacioRadio.doClick();

            if (conProfe)
                conProfeRadio.doClick();
        }
    }
}
