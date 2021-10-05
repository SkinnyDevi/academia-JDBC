package view.com.felix;

import Connection.ConectionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Statement;

public class ModificacionDatosProfesores extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField dniText;
    private JTextField nombreText;
    private JTextField telefonoText;
    private JTextField direccionText;
    private JTextField apellidoText;
    private String tempDni;

    public ModificacionDatosProfesores() {
        setTitle("Modificar Profesor");
        setContentPane(contentPane);
        setSize(500, 500);
        setLocation(ViewEntrada.ancho / 3, ViewEntrada.alto / 4);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean successful = true;
                Statement sentencia = ConectionBD.getStmt();
                String nom, apel, tel, dir;
                nom = nombreText.getText();
                apel = apellidoText.getText();
                tel = telefonoText.getText();
                dir = direccionText.getText();

                try {
                    String query = String.format("update profesores " +
                                    "set nombre = '%s', " +
                                    "apellido = '%s', " +
                                    "telefono = '%s', " +
                                    "direccion = '%s' " +
                                    "where dni = '%s'",
                            nom, apel, tel, dir, tempDni);
                    sentencia.executeUpdate(query);
                } catch (SQLException throwables) {
                    successful = false;
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al modificar la profesores.");
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
        for (int i = 0; i < 4; i++)
            System.out.println(value(dftm, s, i));
        this.tempDni = value(dftm, s, 0);
        dniText.setText(value(dftm, s, 0));
        nombreText.setText(value(dftm, s, 1));
        apellidoText.setText(value(dftm, s, 2));
        telefonoText.setText(value(dftm, s, 3));
        direccionText.setText(value(dftm, s, 4));
    }

    private String value(DefaultTableModel dftm, int s, int i) {
        return (String) dftm.getValueAt(s, i);
    }
}
