package Controller.com.felix;

import Connection.ConectionBD;
import model.com.felix.ModelEmpresas;
import model.com.felix.ModelProfesores;
import view.com.felix.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class ControllerProfesores implements ActionListener, MouseListener, WindowListener, KeyListener {
    private final ViewEntrada frentrada = new ViewEntrada();
    private final JPanel mainPanel = frentrada.getPanelEntrada();
    private final JTable contentTable = frentrada.getContentTable();
    private final JButton empresasButton = frentrada.getEmpresasButton();
    private final JButton profesoresButton = frentrada.getProfesoresButton();
    private final JButton filtrarButton = frentrada.getFiltrarButton();
    private final JButton resetearFiltroButton = frentrada.getResetearFiltro();
    private final JButton eliminarButton = frentrada.getEliminarButton();
    private final JButton agregarButton = frentrada.getAgregarButton();
    private final JButton editarButton = frentrada.getEditarButton();
    private final JButton ordenarButton = frentrada.getOrdenadoButton();
    private ArrayList<String> prevFilter = new ArrayList<>();
    private final DefaultTableModel m = null;
    private String prevQuery = "select * from profesores";

    public ControllerProfesores() {
        IniciarVentana();
        IniciarEventos();
        PrepararBaseDatos();
    }

    public void IniciarVentana() {
        frentrada.setVisible(true);
    }

    public void IniciarEventos() {
        empresasButton.addActionListener(this);
        profesoresButton.addActionListener(this);
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
        ModelProfesores entrada = new ModelProfesores();
        contentTable.setModel(entrada.CargaDatos(m));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String entrada = e.getActionCommand();

        switch (entrada) {
            case "Resetear Filtro": // Limpia el filtro y consulta anterior
                prevFilter = new ArrayList<>();
                prevQuery = "select * from profesores";
            case "Profesores":
                ModelProfesores profesores = new ModelProfesores();
                contentTable.setModel(profesores.CargaDatos(m));
                break;
            case "Empresas":
                try {
                    frentrada.dispose();
                    new ControllerEmpresas();
                } catch (NullPointerException nullP) {
                    nullP.printStackTrace();
                }
                break;
            case "Abrir Opciones de Filtrado":
                FiltradoProfesores fp = new FiltradoProfesores(contentTable, m);
                fp.setData(prevFilter); // Carga los datos del filtro anterior
                fp.setVisible(true);
                prevFilter = fp.getPreviousFilter();
                prevQuery = fp.getPreviousQuery();
                break;
            case "Añadir":
                InsercionDatosProfesores insercion = new InsercionDatosProfesores();
                insercion.setVisible(true);
                ModelProfesores mpAdd = new ModelProfesores();
                contentTable.setModel(mpAdd.CargaDatos(m));
                break;
            case "Editar":
                ModelProfesores mpEd = new ModelProfesores();
                mpEd.editarRegistro(contentTable, m);
                break;
            case "Eliminar":
                ModelProfesores mpE = new ModelProfesores();
                mpE.eliminarRegistro(contentTable, m);
                break;
            case "Ordenar":
                DefaultTableModel profesoresDFTM = new ModelProfesores().CargaDatos(m, prevQuery);
                OrdenarProfesores op = new OrdenarProfesores(contentTable, profesoresDFTM, prevQuery);
                op.setVisible(true);
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
