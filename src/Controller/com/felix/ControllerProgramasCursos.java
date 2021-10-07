package Controller.com.felix;

import Connection.ConectionBD;
import model.com.felix.ModelCursos;
import model.com.felix.ModelProgramasCursos;
import view.com.felix.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class ControllerProgramasCursos implements ActionListener, MouseListener, WindowListener, KeyListener {
    private final ViewEntrada frentrada = new ViewEntrada();
    private final JPanel mainPanel = frentrada.getPanelEntrada();
    private final JTable contentTable = frentrada.getContentTable();
    private final JButton empresasButton = frentrada.getEmpresasButton();
    private final JButton profesoresButton = frentrada.getProfesoresButton();
    private final JButton programasCursosButton = frentrada.getProgramasCursosButton();
    private final JButton cursosButton = frentrada.getCursosButton();
    private final JButton alunmosButton = frentrada.getAlumnosButton();;
    private final JButton filtrarButton = frentrada.getFiltrarButton();
    private final JButton resetearFiltroButton = frentrada.getResetearFiltro();
    private final JButton eliminarButton = frentrada.getEliminarButton();
    private final JButton agregarButton = frentrada.getAgregarButton();
    private final JButton editarButton = frentrada.getEditarButton();
    private final JButton ordenarButton = frentrada.getOrdenadoButton();
    private ArrayList<String> prevFilter = new ArrayList<>();
    private final DefaultTableModel m = null;
    private String prevQuery = "select * from programasCursos";

    public ControllerProgramasCursos() {
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
        cursosButton.addActionListener(this);
        programasCursosButton.addActionListener(this);
        alunmosButton.addActionListener(this);
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
        ModelProgramasCursos entrada = new ModelProgramasCursos();
        contentTable.setModel(entrada.CargaDatos(m));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String entrada = e.getActionCommand();

        switch (entrada) {
            case "Resetear Filtro": // Limpia el filtro y consulta anterior
                prevFilter = new ArrayList<>();
                prevQuery = "select * from programasCursos";
            case "Programas":
                ModelProgramasCursos cursos = new ModelProgramasCursos();
                contentTable.setModel(cursos.CargaDatos(m));
                break;
            case "Alumnos":
                try {
                    frentrada.dispose();
                    new ControllerAlumnos();
                } catch (NullPointerException nullP) {
                    nullP.printStackTrace();
                }
                break;
            case "Empresas":
                try {
                    frentrada.dispose();
                    new ControllerEmpresas();
                } catch (NullPointerException nullP) {
                    nullP.printStackTrace();
                }
                break;
            case "Cursos":
                try {
                    frentrada.dispose();
                    new ControllerCursos();
                } catch (NullPointerException nullP) {
                    nullP.printStackTrace();
                }
                break;
            case "Profesores":
                try {
                    frentrada.dispose();
                    new ControllerProfesores();
                } catch (NullPointerException nullP) {
                    nullP.printStackTrace();
                }
                break;
            case "Abrir Opciones de Filtrado":
                FiltradoProgramasCursos fpc = new FiltradoProgramasCursos(contentTable, m);
                fpc.setData(prevFilter); // Carga los datos del filtro anterior
                fpc.setVisible(true);
                prevFilter = fpc.getPreviousFilter();
                prevQuery = fpc.getPreviousQuery();
                break;
            case "Añadir":
                InsercionDatosProgramasCursos insercion = new InsercionDatosProgramasCursos();
                insercion.setVisible(true);
                ModelProgramasCursos mpcAdd = new ModelProgramasCursos();
                contentTable.setModel(mpcAdd.CargaDatos(m));
                break;
            case "Editar":
                ModelProgramasCursos mpcEd = new ModelProgramasCursos();
                mpcEd.editarRegistro(contentTable, m);
                break;
            case "Eliminar":
                ModelProgramasCursos mpcE = new ModelProgramasCursos();
                mpcE.eliminarRegistro(contentTable, m);
                break;
            case "Ordenar":
                DefaultTableModel programasCursosDFTM = new ModelProgramasCursos().CargaDatos(m, prevQuery);
                OrdenarProgramasCursos opc = new OrdenarProgramasCursos(contentTable, programasCursosDFTM, prevQuery);
                opc.setVisible(true);
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
