package view.com.grupo4aed;

import Connection.ConectionBD;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Statement;

public class InsercionDatosAlumnos extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField dniText;
    private JTextField nombreText;
    private JTextField apellidoText;
    private JTextField telefonoText;
    private JTextField edadText;
    private JTextField direccionText;

    public InsercionDatosAlumnos() {
        setTitle("Agregar Alumno");
        setContentPane(contentPane);
        setSize(500, 500);
        setLocation(ViewEntrada.ancho / 3, ViewEntrada.alto / 4);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Statement sentencia = ConectionBD.getStmt();
                String dni, nom, apel, tel, edad, dir;
                boolean successful = true;

                dni = dniText.getText();
                nom = nombreText.getText();
                apel = apellidoText.getText();
                tel = telefonoText.getText();
                edad = edadText.getText();
                dir = direccionText.getText();

                try {
                    String query = String.format("INSERT INTO alumnos VALUES('%s', '%s', '%s', '%s', '%s', '%s')",
                            dni, nom, apel, tel, edad, dir);
                    sentencia.executeUpdate(query);
                } catch (SQLException throwables) {
                    successful = false;
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al insertar el alumno.");
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
