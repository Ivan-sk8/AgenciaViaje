package com.trivago.agenciaviaje.vista;

import com.trivago.agenciaviaje.modelo.Turista;
import com.trivago.agenciaviaje.services.TuristaService;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Interfaz gráfica para el módulo de registro de turistas
 * @author ivan_
 */
public class TuristaFrame extends JFrame {
    
    private TuristaService turistaService;
    private JTextField txtCodigo, txtNombre, txtApellidos, txtDireccion, txtTelefono;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar, btnRefrescar;
    private JTable tablaTuristas;
    private TuristaTableModel modeloTabla;
    
    public TuristaFrame() {
        turistaService = new TuristaService();
        inicializarComponentes();
        cargarDatos();
    }
    
    private void inicializarComponentes() {
        setTitle("Gestión de Turistas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Panel principal con diseño horizontal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        
        // Panel izquierdo - Formulario
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), 
            "Datos del Turista", 
            TitledBorder.LEFT, 
            TitledBorder.TOP
        ));
        panelIzquierdo.setPreferredSize(new Dimension(280, 500));
        
        // Crear formulario con campos
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Campo Código
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Código:"), gbc);
        gbc.gridy = 1;
        txtCodigo = new JTextField(20);
        panelFormulario.add(txtCodigo, gbc);
        
        // Campo Nombre
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridy = 3;
        txtNombre = new JTextField(20);
        panelFormulario.add(txtNombre, gbc);
        
        // Campo Apellidos
        gbc.gridy = 4;
        panelFormulario.add(new JLabel("Apellidos:"), gbc);
        gbc.gridy = 5;
        txtApellidos = new JTextField(20);
        panelFormulario.add(txtApellidos, gbc);
        
        // Campo Dirección
        gbc.gridy = 6;
        panelFormulario.add(new JLabel("Dirección:"), gbc);
        gbc.gridy = 7;
        txtDireccion = new JTextField(20);
        panelFormulario.add(txtDireccion, gbc);
        
        // Campo Teléfono
        gbc.gridy = 8;
        panelFormulario.add(new JLabel("Teléfono:"), gbc);
        gbc.gridy = 9;
        txtTelefono = new JTextField(20);
        panelFormulario.add(txtTelefono, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 5, 5));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Botón Guardar (verde)
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(Color.GREEN);
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setPreferredSize(new Dimension(160, 30));
        
        // Botón Actualizar (azul)
        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBackground(Color.CYAN);
        btnActualizar.setForeground(Color.BLACK);
        btnActualizar.setPreferredSize(new Dimension(160, 30));
        
        // Botón Eliminar (rojo)
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(Color.RED);
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.setPreferredSize(new Dimension(160, 30));
        
        // Botón Limpiar (gris)
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBackground(Color.LIGHT_GRAY);
        btnLimpiar.setForeground(Color.BLACK);
        btnLimpiar.setPreferredSize(new Dimension(160, 30));
        
        // Botón Refrescar (gris)
        btnRefrescar = new JButton("Refrescar");
        btnRefrescar.setBackground(Color.LIGHT_GRAY);
        btnRefrescar.setForeground(Color.BLACK);
        btnRefrescar.setPreferredSize(new Dimension(160, 30));
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnRefrescar);
        
        // Agregar componentes al panel izquierdo
        panelIzquierdo.add(panelFormulario);
        panelIzquierdo.add(Box.createVerticalStrut(20));
        panelIzquierdo.add(panelBotones);
        
        // Panel derecho - Tabla
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), 
            "Lista de Turistas", 
            TitledBorder.LEFT, 
            TitledBorder.TOP
        ));
        
        // Crear tabla
        modeloTabla = new TuristaTableModel();
        tablaTuristas = new JTable(modeloTabla);
        tablaTuristas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaTuristas);
        panelDerecho.add(scrollPane, BorderLayout.CENTER);
        
        // Agregar paneles al panel principal
        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        panelPrincipal.add(panelDerecho, BorderLayout.CENTER);
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        // Configurar eventos
        configurarEventos();
        
        // Configurar ventana
        setSize(900, 600);
        setLocationRelativeTo(null);
    }
    
    private void configurarEventos() {
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarTurista();
            }
        });
        
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTurista();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarTurista();
            }
        });
        
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
        
        btnRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatos();
            }
        });
        
        // Evento para seleccionar fila de la tabla
        tablaTuristas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tablaTuristas.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    cargarDatosTurista(filaSeleccionada);
                }
            }
        });
    }
    
    private void guardarTurista() {
        try {
            if (validarCampos()) {
                Turista turista = new Turista();
                turista.setCodigoTurista(txtCodigo.getText().trim());
                turista.setNombre(txtNombre.getText().trim());
                turista.setApellidos(txtApellidos.getText().trim());
                turista.setDireccion(txtDireccion.getText().trim());
                turista.setTelefono(txtTelefono.getText().trim());
                
                turistaService.guardarTurista(turista);
                JOptionPane.showMessageDialog(this, "Turista guardado exitosamente");
                limpiarCampos();
                cargarDatos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
        }
    }
    
    private void actualizarTurista() {
        int filaSeleccionada = tablaTuristas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            try {
                if (validarCampos()) {
                    Turista turista = modeloTabla.getTuristaEnFila(filaSeleccionada);
                    turista.setCodigoTurista(txtCodigo.getText().trim());
                    turista.setNombre(txtNombre.getText().trim());
                    turista.setApellidos(txtApellidos.getText().trim());
                    turista.setDireccion(txtDireccion.getText().trim());
                    turista.setTelefono(txtTelefono.getText().trim());
                    
                    turistaService.actualizarTurista(turista);
                    JOptionPane.showMessageDialog(this, "Turista actualizado exitosamente");
                    limpiarCampos();
                    cargarDatos();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un turista para actualizar");
        }
    }
    
    private void eliminarTurista() {
        int filaSeleccionada = tablaTuristas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int respuesta = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar este turista?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION);
            
            if (respuesta == JOptionPane.YES_OPTION) {
                try {
                    Turista turista = modeloTabla.getTuristaEnFila(filaSeleccionada);
                    turistaService.eliminarTurista(turista.getId());
                    JOptionPane.showMessageDialog(this, "Turista eliminado exitosamente");
                    limpiarCampos();
                    cargarDatos();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un turista para eliminar");
        }
    }
    
    private void cargarDatosTurista(int fila) {
        Turista turista = modeloTabla.getTuristaEnFila(fila);
        txtCodigo.setText(turista.getCodigoTurista());
        txtNombre.setText(turista.getNombre());
        txtApellidos.setText(turista.getApellidos());
        txtDireccion.setText(turista.getDireccion());
        txtTelefono.setText(turista.getTelefono());
    }
    
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtApellidos.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        tablaTuristas.clearSelection();
    }
    
    private boolean validarCampos() {
        if (txtCodigo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El código es obligatorio");
            return false;
        }
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
            return false;
        }
        if (txtApellidos.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los apellidos son obligatorios");
            return false;
        }
        return true;
    }
    
    private void cargarDatos() {
        try {
            List<Turista> turistas = turistaService.obtenerTodosLosTuristas();
            modeloTabla.setTuristas(turistas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
        }
    }
    
    @Override
    public void dispose() {
        if (turistaService != null) {
            turistaService.cerrar();
        }
        super.dispose();
    }
}
