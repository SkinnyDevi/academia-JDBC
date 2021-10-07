package view.com.grupo4aed;

import Connection.ConectionBD;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Statement;

public class InsercionDatosCursos extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField codProgramaCursoText;
    private JTextField fechaInicioText;
    private JTextField fechaFinText;

    public InsercionDatosCursos() {
        setTitle("Agregar Curso");
        setContentPane(contentPane);
        setSize(500, 500);
        setLocation(ViewEntrada.ancho / 3, ViewEntrada.alto / 4);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Statement sentencia = ConectionBD.getStmt();
                String codP, fI, fF;
                boolean successful = true;

                codP = codProgramaCursoText.getText();
                fI = fechaInicioText.getText();
                fF = fechaFinText.getText();

                try {
                    String query = String.format("insert into cursos values(null, '%s', '%s', '%s')",
                            codP, fI, fF);
                    sentencia.executeUpdate(query);
                } catch (SQLException throwables) {
                    successful = false;
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al insertar el curso.");
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
