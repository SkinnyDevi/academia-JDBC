package Controler.com.felix;

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
    private final JTable tablaDatos = frentrada.getTable1();
    private final JButton asignaturasButton = frentrada.getAsignaturasButton();
    private final JButton personasButton = frentrada.getPersonasButton();
    private final JButton filtrarButton = frentrada.getFiltrarButton();
    private final JButton resetearFiltroButton = frentrada.getResetearFiltro();
    private final JButton eliminarButton = frentrada.getEliminarButton();
    private final JButton agregarButton = frentrada.getAgregarButton();
    private final JButton editarButton = frentrada.getEditarButton();
    private final JButton ordenarButton = frentrada.getOrdenadoButton();
    private ArrayList<String> prevFilter = new ArrayList<>();
    private final DefaultTableModel m = null;
    private String prevQuery = "select * from asignatura";

    public ControllerEmpresas() {
        IniciarVentana();
        IniciarEventos();
        PrepararBaseDatos();
    }

    public void IniciarVentana() {
        frentrada.setVisible(true);
    }

    public void IniciarEventos() {
        asignaturasButton.addActionListener(this);
        personasButton.addActionListener(this);
        filtrarButton.addActionListener(this);
        resetearFiltroButton.addActionListener(this);
        agregarButton.addActionListener(this);
        editarButton.addActionListener(this);
        eliminarButton.addActionListener(this);
        ordenarButton.addActionListener(this);
        mainPanel.addMouseListener(this);
        tablaDatos.addMouseListener(this);
        frentrada.addWindowListener(this);
    }

    public void PrepararBaseDatos() {
        ModelEmpresas entrada = new ModelEmpresas();
        tablaDatos.setModel(entrada.CargaDatos(m));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String entrada = e.getActionCommand();

        switch (entrada) {
            case "Resetear Filtro": // Limpia el filtro y consulta anterior
                prevFilter = new ArrayList<>();
                prevQuery = "select * from asignatura";
            case "Asignaturas":
                ModelEmpresas asignatura = new ModelEmpresas();
                tablaDatos.setModel(asignatura.CargaDatos(m));
                break;
            case "Personas":
                try {
                    frentrada.dispose();
                    //new ControllerPersonas();
                } catch (NullPointerException nullP) {
                    nullP.printStackTrace();
                }
                break;
            case "Abrir Opciones de Filtrado":
                FiltradoEmpresas fa = new FiltradoEmpresas(tablaDatos, m);
                fa.setData(prevFilter); // Carga los datos del filtro anterior
                fa.setVisible(true);
                prevFilter = fa.getPreviousFilter();
                prevQuery = fa.getPreviousQuery();
                break;
            case "Añadir":
                InsercionDatosEmpresas insercion = new InsercionDatosEmpresas();
                insercion.setVisible(true);
                ModelEmpresas pAdd = new ModelEmpresas();
                tablaDatos.setModel(pAdd.CargaDatos(m));
                break;
            case "Editar":
                ModelEmpresas maed = new ModelEmpresas();
                maed.editarRegistro(tablaDatos, m);
                break;
            case "Eliminar":
                ModelEmpresas mae = new ModelEmpresas();
                mae.eliminarRegistro(tablaDatos, m);
                break;
            case "Ordenar":
                DefaultTableModel asignaturas = new ModelEmpresas().CargaDatos(m, prevQuery);
                OrdenarEmpresas oa = new OrdenarEmpresas(tablaDatos, asignaturas, prevQuery);
                oa.setVisible(true);
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
