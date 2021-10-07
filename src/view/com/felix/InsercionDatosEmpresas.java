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
    private JTextField telefonoText;
    private JTextField direccionText;
    private JTextField cifText;

    public InsercionDatosEmpresas() {
        setTitle("Agregar Empresa");
        setContentPane(contentPane);
        setSize(500, 500);
        setLocation(ViewEntrada.ancho / 3, ViewEntrada.alto / 4);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Statement sentencia = ConectionBD.getStmt();
                String cif, nom, tel, dir;
                boolean successful = true;

                cif = cifText.getText();
                nom = nombreText.getText();
                tel = telefonoText.getText();
                dir = direccionText.getText();

                try {
                    String query = String.format("insert into empresas values('%s', '%s', '%s', '%s')",
                            cif, nom, tel, dir);
                    sentencia.executeUpdate(query);
                } catch (SQLException throwables) {
                    successful = false;
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al insertar la empresa.");
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
