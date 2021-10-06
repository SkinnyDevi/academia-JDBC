package view.com.felix;

import Connection.ConectionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.SQLException;
import java.sql.Statement;

public class ModificacionDatosCursos extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField codCursoText;
    private JTextField codProgramaCursoText;
    private JTextField fechaInicioText;
    private JTextField fechaFinText;
    private String tempCodCurso;

    public ModificacionDatosCursos() {
        setTitle("Modificar Curso");
        setContentPane(contentPane);
        setSize(500, 500);
        setLocation(ViewEntrada.ancho / 3, ViewEntrada.alto / 4);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean successful = true;
                Statement sentencia = ConectionBD.getStmt();
                String codP, fI, fF;
                codP = codProgramaCursoText.getText();
                fI = fechaInicioText.getText();
                fF = fechaFinText.getText();

                try {
                    String query = String.format("update cursos " +
                                    "set codProgramaCurso = '%s', " +
                                    "fechaInicioCurso = '%s', " +
                                    "fechaFinCurso = '%s' " +
                                    "where codCurso = '%s'",
                            codP, fI, fF, tempCodCurso);
                    sentencia.executeUpdate(query);
                } catch (SQLException throwables) {
                    successful = false;
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al modificar el curso.");
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
        this.tempCodCurso = value(dftm, s, 0);
        codCursoText.setText(value(dftm, s, 0));
        codProgramaCursoText.setText(value(dftm, s, 1));
        fechaInicioText.setText(value(dftm, s, 2));
        fechaFinText.setText(value(dftm, s, 3));
    }

    private String value(DefaultTableModel dftm, int s, int i) {
        return (String) dftm.getValueAt(s, i);
    }
}
