package Controller.com.felix;

import Connection.ConectionBD;
import model.com.felix.ModelAlumnos;
import model.com.felix.ModelCursos;
import view.com.felix.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class ControllerAlumnos implements ActionListener, MouseListener, WindowListener, KeyListener {
    private final ViewEntrada frentrada = new ViewEntrada();
    private final JPanel mainPanel = frentrada.getPanelEntrada();
    private final JTable contentTable = frentrada.getContentTable();
    private final JButton empresasButton = frentrada.getEmpresasButton();
    private final JButton profesoresButton = frentrada.getProfesoresButton();
    private final JButton cursosButton = frentrada.getCursosButton();
    private final JButton alumnosButton = frentrada.getAlumnosButton();
    private final JButton programasCursosButton = frentrada.getProgramasCursosButton();
    private final JButton filtrarButton = frentrada.getFiltrarButton();
    private final JButton resetearFiltroButton = frentrada.getResetearFiltro();
    private final JButton eliminarButton = frentrada.getEliminarButton();
    private final JButton agregarButton = frentrada.getAgregarButton();
    private final JButton editarButton = frentrada.getEditarButton();
    private final JButton ordenarButton = frentrada.getOrdenadoButton();
    private ArrayList<String> prevFilter = new ArrayList<>();
    private final DefaultTableModel m = null;
    private String prevQuery = "select * from alumnos";

    public ControllerAlumnos() {
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
        alumnosButton.addActionListener(this);
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
        ModelAlumnos entrada = new ModelAlumnos();
        contentTable.setModel(entrada.CargaDatos(m));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String entrada = e.getActionCommand();

        switch (entrada) {
            case "Resetear Filtro": // Limpia el filtro y consulta anterior
                prevFilter = new ArrayList<>();
                prevQuery = "select * from alumnos";
            case "Alumnos":
                ModelAlumnos alumnos = new ModelAlumnos();
                contentTable.setModel(alumnos.CargaDatos(m));
                break;
            case "Cursos":
                try {
                    frentrada.dispose();
                    new ControllerCursos();
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
            case "Profesores":
                try {
                    frentrada.dispose();
                    new ControllerProfesores();
                } catch (NullPointerException nullP) {
                    nullP.printStackTrace();
                }
                break;
            case "Programas":
                try {
                    frentrada.dispose();
                    new ControllerProgramasCursos();
                } catch (NullPointerException nullP) {
                    nullP.printStackTrace();
                }
                break;
            case "Abrir Opciones de Filtrado":
                FiltradoAlumnos fa = new FiltradoAlumnos(contentTable, m);
                fa.setData(prevFilter); // Carga los datos del filtro anterior
                fa.setVisible(true);
                prevFilter = fa.getPreviousFilter();
                prevQuery = fa.getPreviousQuery();
                break;
            case "Añadir":
                InsercionDatosAlumnos insercion = new InsercionDatosAlumnos();
                insercion.setVisible(true);
                ModelAlumnos maAdd = new ModelAlumnos();
                contentTable.setModel(maAdd.CargaDatos(m));
                break;
            case "Editar":
                ModelAlumnos maEd = new ModelAlumnos();
                maEd.editarRegistro(contentTable, m);
                break;
            case "Eliminar":
                ModelAlumnos maE = new ModelAlumnos();
                maE.eliminarRegistro(contentTable, m);
                break;
            case "Ordenar":
                DefaultTableModel alumnosDFTM = new ModelAlumnos().CargaDatos(m, prevQuery);
                OrdenarAlumnos oa = new OrdenarAlumnos(contentTable, alumnosDFTM, prevQuery);
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
