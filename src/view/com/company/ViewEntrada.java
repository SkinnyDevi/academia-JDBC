package view.com.company;

import javax.swing.*;

public class ViewEntrada extends JFrame {
    public static final int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    private JPanel PanelEntrada;
    private JTable table1;
    private JButton asignaturasButton;
    private JButton personasButton;
    private JButton filtrarButton;
    private JButton eliminarButton;
    private JButton agregarButton;
    private JButton editarButton;
    private JButton resetearFiltro;
    private JButton ordenadoButton;

    public ViewEntrada() {
        super("Gestion Universidad");
        setContentPane(PanelEntrada);
        setSize(ancho, alto);
    }

    public JPanel getPanelEntrada() {
        return PanelEntrada;
    }

    public JTable getTable1() {
        return table1;
    }

    public JButton getAsignaturasButton() {
        return asignaturasButton;
    }

    public JButton getPersonasButton() {
        return personasButton;
    }

    public JButton getFiltrarButton() {
        return filtrarButton;
    }

    public JButton getEliminarButton() {
        return eliminarButton;
    }

    public JButton getAgregarButton() {
        return agregarButton;
    }

    public JButton getEditarButton() {
        return editarButton;
    }

    public JButton getResetearFiltro() {
        return resetearFiltro;
    }

    public JButton getOrdenadoButton() {
        return ordenadoButton;
    }
}
