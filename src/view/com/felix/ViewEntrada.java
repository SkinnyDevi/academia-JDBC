package view.com.felix;

import javax.swing.*;

public class ViewEntrada extends JFrame {
    public static final int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    private JPanel PanelEntrada;
    private JTable contentTable;
    private JButton empresasButton;
    private JButton filtrarButton;
    private JButton eliminarButton;
    private JButton agregarButton;
    private JButton editarButton;
    private JButton resetearFiltro;
    private JButton ordenadoButton;
    private JButton profesoresButton;

    public ViewEntrada() {
        super("Academia de Clases");
        setContentPane(PanelEntrada);
        setSize(ancho, alto);
    }

    public JPanel getPanelEntrada() {
        return PanelEntrada;
    }

    public JTable getContentTable() {
        return contentTable;
    }

    public JButton getEmpresasButton() {
        return empresasButton;
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

    public JButton getProfesoresButton() {
        return profesoresButton;
    }
}
