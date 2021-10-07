package view.com.felix;

import model.com.felix.ModelCursos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class FiltradoAlumnos extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nombreText;
    private JTextField telefonoText;
    private JTextField direccionText;
    private JTextField dniText;
    private JTextField apellidoText;
    private JTextField edadText;
    private ArrayList<String> previousFilter = new ArrayList<>();
    private String previousQuery = "select * from alumnos";

    public FiltradoAlumnos(JTable dbTable, DefaultTableModel dftm) {
        setTitle("Filtrado de Cursos");
        setContentPane(contentPane);
        setSize(550, 550);
        setLocation(ViewEntrada.ancho / 3, ViewEntrada.alto / 4);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String codC, codP, fI, fF;
                codC = dniText.getText();
                codP = nombreText.getText();
                fI = apellidoText.getText();
                fF = telefonoText.getText();
                boolean where = false;

                String query = "select * from cursos";

                if (fieldIsEmpty(codC)) {
                    query += " where codCurso like '" + codC + "%'";
                    where = true;
                } else {
                    codC = "";
                }

                if (fieldIsEmpty(codP)) {
                    String q = "codProgramaCurso like '" + codP + "%'";
                    if (where) {
                        query += " and " + q;
                    } else {
                        query += " where " + q;
                        where = true;
                    }
                } else {
                    codP = "";
                }

                if (fieldIsEmpty(fI)) {
                    String q = "fechaInicioCurso like '" + fI + "%'";
                    if (where) {
                        query += " and " + q;
                    } else {
                        query += " where " + q;
                        where = true;
                    }
                } else {
                    fI = "";
                }

                if (fieldIsEmpty(fF)) {
                    String q = "fechaFinCurso like '" + fF + "%'";
                    if (where) {
                        query += " and " + q;
                    } else {
                        query += " where " + q;
                        where = true;
                    }
                } else {
                    fF = "";
                }

                // Cargar al arraylist para especificar que este fue el filtro usado
                previousFilter.add(codC);
                previousFilter.add(codP);
                previousFilter.add(fI);
                previousFilter.add(fF);

                System.out.println(query);
                ModelCursos cursos = new ModelCursos();
                dbTable.setModel(cursos.CargaDatos(dftm, query));
                previousQuery = query; // Cargamos a la consulta anterior para el ordenado
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { // Usado para recuperar el filtro en casp de cancelado
                String codC, codP, fI, fF;
                codC = dniText.getText();
                codP = nombreText.getText();
                fI = apellidoText.getText();
                fF = telefonoText.getText();
                previousFilter.add(codC);
                previousFilter.add(codP);
                previousFilter.add(fI);
                previousFilter.add(fF);
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
        }
    }
}
