package view.com.company;

import Connecion.ConectionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;

import java.sql.SQLException;
import java.sql.Statement;

public class ModificacionDatosEmpresas extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField idText;
    private JTextField nombreText;
    private JTextField creditosText;
    private JTextField cursoText;
    private JTextField cuatrimestreText;
    private JTextField idProfeText;
    private JTextField tipoText;
    private JTextField idGradoText;
    private String tempId;

    public ModificacionDatosEmpresas() {
        setTitle("Modificar Asignatura");
        setContentPane(contentPane);
        setSize(500, 500);
        setLocation(ViewEntrada.ancho / 3, ViewEntrada.alto / 4);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean successful = true;
                Statement sentencia = ConectionBD.getStmt();
                String nom, cred, tipo, curso, cuatri, idProf, idGr;
                nom = nombreText.getText();
                cred = creditosText.getText();
                tipo = tipoText.getText();
                curso = cursoText.getText();
                cuatri = cuatrimestreText.getText();
                // Si el idProf es = "" idProf = null, si no idProf = texto entrada
                idProf = idProfeText.getText().equals("") ? null : idProfeText.getText();
                idGr = idGradoText.getText().toLowerCase();

                try {
                    String query = String.format("update asignatura " +
                                    "set nombre = '%s', " +
                                    "creditos = '%s', " +
                                    "tipo = '%s', " +
                                    "curso = '%s', " +
                                    "cuatrimestre = '%s', " +
                                    "id_profesor = %s, " +
                                    "id_grado = '%s' " +
                                    "where id = %s",
                            nom, cred, tipo, curso, cuatri, idProf, idGr, tempId);
                    sentencia.executeUpdate(query);
                } catch (SQLException throwables) {
                    successful = false;
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al insertar la asignatura.");
                }
                if (successful) {
                    onOK();
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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

    // Usado para cargar los datos del registro seleccionado
    public void setData(DefaultTableModel dftm, int s) {
        for (int i = 0; i < 7; i++)
            System.out.println(value(dftm, s, i));
        this.tempId = value(dftm, s, 0);
        idText.setText(value(dftm, s, 0));
        nombreText.setText(value(dftm, s, 1));
        creditosText.setText(value(dftm, s, 2));
        tipoText.setText(value(dftm, s, 3));
        cursoText.setText(value(dftm, s, 4));
        cuatrimestreText.setText(value(dftm, s, 5));
        idProfeText.setText(value(dftm, s, 6));
        idGradoText.setText(value(dftm, s, 7));
    }

    private String value(DefaultTableModel dftm, int s, int i) {
        return (String) dftm.getValueAt(s, i);
    }
}
