package view.com.grupo4aed;

import model.com.grupo4aed.ModelProgramasCursos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class FiltradoProgramasCursos extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField codProgramaCursoText;
    private JTextField tituloText;
    private JTextField direccionText;
    private JTextField codCursoText;
    private JTextField duracionText;
    private ArrayList<String> previousFilter = new ArrayList<>();
    private String previousQuery = "select * from programasCursos";

    public FiltradoProgramasCursos(JTable dbTable, DefaultTableModel dftm) {
        setTitle("Filtrado de Programas");
        setContentPane(contentPane);
        setSize(550, 550);
        setLocation(ViewEntrada.ancho / 3, ViewEntrada.alto / 4);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String codP, dur, titu;
                codP = codProgramaCursoText.getText();
                dur = duracionText.getText();
                titu = tituloText.getText();
                boolean where = false;

                String query = "select * from programasCursos";

                if (fieldIsEmpty(codP)) {
                    query += " where codProgramaCurso like '" + codP + "%'";
                    where = true;
                } else {
                    codP = "";
                }

                if (fieldIsEmpty(dur)) {
                    String q = "duracionCurso like '" + dur + "%'";
                    if (where) {
                        query += " and " + q;
                    } else {
                        query += " where " + q;
                        where = true;
                    }
                } else {
                    dur = "";
                }

                if (fieldIsEmpty(titu)) {
                    String q = "titulo like '" + titu + "%'";
                    if (where) {
                        query += " and " + q;
                    } else {
                        query += " where " + q;
                    }
                } else {
                    titu = "";
                }

                // Cargar al arraylist para especificar que este fue el filtro usado
                previousFilter.add(codP);
                previousFilter.add(dur);
                previousFilter.add(titu);

                System.out.println(query);
                ModelProgramasCursos programasCursos = new ModelProgramasCursos();
                dbTable.setModel(programasCursos.CargaDatos(dftm, query));
                previousQuery = query; // Cargamos a la consulta anterior para el ordenado
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { // Usado para recuperar el filtro en casp de cancelado
                String codP, dur, titu;
                codP = codProgramaCursoText.getText();
                dur = duracionText.getText();
                titu = tituloText.getText();
                previousFilter.add(codP);
                previousFilter.add(dur);
                previousFilter.add(titu);
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
            codProgramaCursoText.setText(pvData.get(0));
            duracionText.setText(pvData.get(1));
            tituloText.setText(pvData.get(2));
        }
    }
}
