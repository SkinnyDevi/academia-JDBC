package Controller.com.felix;

import Connection.ConectionBD;
import model.com.felix.ModelEmpresas;
import view.com.felix.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;

import java.util.ArrayList;

public class ControllerEmpresas implements ActionListener, MouseListener, WindowListener, KeyListener {
    private final ViewEntrada frentrada = new ViewEntrada();
    private final JPanel mainPanel = frentrada.getPanelEntrada();
    private final JTable contentTable = frentrada.getContentTable();
    private final JButton empresasButton = frentrada.getEmpresasButton();
    private final JButton filtrarButton = frentrada.getFiltrarButton();
    private final JButton resetearFiltroButton = frentrada.getResetearFiltro();
    private final JButton eliminarButton = frentrada.getEliminarButton();
    private final JButton agregarButton = frentrada.getAgregarButton();
    private final JButton editarButton = frentrada.getEditarButton();
    private final JButton ordenarButton = frentrada.getOrdenadoButton();
    private ArrayList<String> prevFilter = new ArrayList<>();
    private final DefaultTableModel m = null;
    private String prevQuery = "select * from empresas";

    public ControllerEmpresas() {
        IniciarVentana();
        IniciarEventos();
        PrepararBaseDatos();
    }

    public void IniciarVentana() {
        frentrada.setVisible(true);
    }

    public void IniciarEventos() {
        empresasButton.addActionListener(this);
        filtrarButton.addActionListener(this);
        resetearFiltroButton.addActionListener(this);
        agregarButton.addActionListener(this);
        editarButton.addActionListener(this);
        eliminarButton.addActionListener(this);
        ordenarButton.addActionListener(this);
        mainPanel.addMouseListener(this);
        contentTable.addMouseListener(this);
        frentrada.addWindowListener(this);
    }

    public void PrepararBaseDatos() {
        ModelEmpresas entrada = new ModelEmpresas();
        contentTable.setModel(entrada.CargaDatos(m));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String entrada = e.getActionCommand();

        switch (entrada) {
            case "Resetear Filtro": // Limpia el filtro y consulta anterior
                prevFilter = new ArrayList<>();
                prevQuery = "select * from empresas";
            case "Empresas":
                ModelEmpresas empresas = new ModelEmpresas();
                contentTable.setModel(empresas.CargaDatos(m));
                break;
            case "NuevaTabla":
                try {
                    frentrada.dispose();
                    //new ControladorNuevaTabla();
                } catch (NullPointerException nullP) {
                    nullP.printStackTrace();
                }
                break;
            case "Abrir Opciones de Filtrado":
                FiltradoEmpresas fe = new FiltradoEmpresas(contentTable, m);
                fe.setData(prevFilter); // Carga los datos del filtro anterior
                fe.setVisible(true);
                prevFilter = fe.getPreviousFilter();
                prevQuery = fe.getPreviousQuery();
                break;
            case "Añadir":
                InsercionDatosEmpresas insercion = new InsercionDatosEmpresas();
                insercion.setVisible(true);
                ModelEmpresas meAdd = new ModelEmpresas();
                contentTable.setModel(meAdd.CargaDatos(m));
                break;
            case "Editar":
                ModelEmpresas meEd = new ModelEmpresas();
                meEd.editarRegistro(contentTable, m);
                break;
            case "Eliminar":
                ModelEmpresas meE = new ModelEmpresas();
                meE.eliminarRegistro(contentTable, m);
                break;
            case "Ordenar":
                DefaultTableModel empresasDFTM = new ModelEmpresas().CargaDatos(m, prevQuery);
                OrdenarEmpresas oe = new OrdenarEmpresas(contentTable, empresasDFTM, prevQuery);
                oe.setVisible(true);
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("Ha salido del programa");
        frentrada.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ConectionBD.CloseConn();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
