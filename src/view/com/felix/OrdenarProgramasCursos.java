package view.com.felix;

import model.com.felix.ModelCursos;
import model.com.felix.ModelProgramasCursos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class OrdenarProgramasCursos extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<String> filasCombo;
    private JCheckBox descendenteCheckBox;

    public OrdenarProgramasCursos(JTable dbTable, DefaultTableModel dftm, String pvQuery) {
        setContentPane(contentPane);
        setSize(400, 150);
        setLocation(ViewEntrada.ancho / 3, ViewEntrada.alto / 4);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        DefaultComboBoxModel<String> filas = new DefaultComboBoxModel<>();
        for (String t : ModelProgramasCursos.campos) {
            filas.addElement(t);
        }
        filasCombo.setModel(filas);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String c = (String) filasCombo.getSelectedItem();
                switch(c) {
                    case "Codigo Programa Curso":
                        c = "codProgramaCurso";
                        break;
                    case "Duracion (Horas)":
                        c = "duracionCurso";
                        break;
                }
                String orderQ = pvQuery + " order by " + c;
                if (descendenteCheckBox.isSelected())
                    orderQ += " desc";
                System.out.println(orderQ);
                ModelProgramasCursos mpc = new ModelProgramasCursos();
                dbTable.setModel(mpc.CargaDatos(dftm, orderQ));
                onOK();
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
