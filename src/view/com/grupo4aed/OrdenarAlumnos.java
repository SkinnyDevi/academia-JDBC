package view.com.grupo4aed;

import model.com.grupo4aed.ModelAlumnos;
import model.com.grupo4aed.ModelCursos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class OrdenarAlumnos extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<String> filasCombo;
    private JCheckBox descendenteCheckBox;

    public OrdenarAlumnos(JTable dbTable, DefaultTableModel dftm, String pvQuery) {
        setContentPane(contentPane);
        setSize(400, 150);
        setLocation(ViewEntrada.ancho / 3, ViewEntrada.alto / 4);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        DefaultComboBoxModel<String> filas = new DefaultComboBoxModel<>();
        for (String t : ModelAlumnos.campos) {
            filas.addElement(t);
        }
        filasCombo.setModel(filas);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String c = (String) filasCombo.getSelectedItem();
                c = c.toLowerCase();
                String orderQ = pvQuery + " order by " + c;
                if (descendenteCheckBox.isSelected())
                    orderQ += " desc";
                System.out.println(orderQ);
                ModelCursos mc = new ModelCursos();
                dbTable.setModel(mc.CargaDatos(dftm, orderQ));
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
