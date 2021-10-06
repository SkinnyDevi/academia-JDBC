package view.com.felix;

import Connection.ConectionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Statement;

public class ModificacionDatosProgramasCursos extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField codProgramaCursoText;
    private JTextField duracionText;
    private JTextField tituloText;
    private String tempCodProgramaCurso;

    public ModificacionDatosProgramasCursos() {
        setTitle("Modificar Programa");
        setContentPane(contentPane);
        setSize(500, 500);
        setLocation(ViewEntrada.ancho / 3, ViewEntrada.alto / 4);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean successful = true;
                Statement sentencia = ConectionBD.getStmt();
                String dur, titu;
                dur = duracionText.getText();
                titu = tituloText.getText();

                try {
                    String query = String.format("update programaCursos " +
                                    "duracion = '%s', " +
                                    "titulo = '%s' " +
                                    "where codProgramaCurso = '%s'",
                            dur, titu, tempCodProgramaCurso);
                    sentencia.executeUpdate(query);
                } catch (SQLException throwables) {
                    successful = false;
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al modificar el programa curso.");
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
        for (int i = 0; i < 3; i++)
            System.out.println(value(dftm, s, i));
        this.tempCodProgramaCurso = value(dftm, s, 0);
        codProgramaCursoText.setText(value(dftm, s, 0));
        duracionText.setText(value(dftm, s, 1));
        tituloText.setText(value(dftm, s, 2));
    }

    private String value(DefaultTableModel dftm, int s, int i) {
        return (String) dftm.getValueAt(s, i);
    }
}
