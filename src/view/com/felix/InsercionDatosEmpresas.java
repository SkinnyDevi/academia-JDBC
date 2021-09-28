package view.com.felix;

import Connection.ConectionBD;

import javax.swing.*;

import java.awt.event.*;

import java.sql.SQLException;
import java.sql.Statement;

public class InsercionDatosEmpresas extends JDialog {
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

    public InsercionDatosEmpresas() {
        setTitle("Agregar Asignatura");
        setContentPane(contentPane);
        setSize(500, 500);
        setLocation(ViewEntrada.ancho / 3, ViewEntrada.alto / 4);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Statement sentencia = ConectionBD.getStmt();
                String nom, cred, tipo, curso, cuatri, idProf, idGr;
                boolean successful = true;

                nom = nombreText.getText();
                cred = creditosText.getText();
                tipo = tipoText.getText().toLowerCase();
                curso = cursoText.getText();
                cuatri = cuatrimestreText.getText();
                // Si el idProf es = "" idProf = null, si no idProf = texto entrada
                idProf = idProfeText.getText().equals("") ? null : idProfeText.getText();
                idGr = idGradoText.getText().toLowerCase();

                try {
                    String query = String.format("insert into asignatura values(null, '%s', '%s', '%s', '%s', '%s', %s, '%s')",
                            nom, cred, tipo, curso, cuatri, idProf, idGr);
                    sentencia.executeUpdate(query);
                } catch (SQLException throwables) {
                    successful = false;
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al insertar asignatura.");
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
}
