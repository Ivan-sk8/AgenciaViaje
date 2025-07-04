package com.trivago.agenciaviaje.vista;

import com.trivago.agenciaviaje.modelo.Sucursal;
import com.trivago.agenciaviaje.services.SucursalService;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Interfaz gráfica para el módulo de registro de sucursales
 * @author ivan_
 */
public class SucursalFrame extends JFrame {
    
    private SucursalService sucursalService;
    private JTextField txtCodigo, txtDireccion, txtTelefono;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar, btnRefrescar;
    private JTable tablaSucursales;
    private SucursalTableModel modeloTabla;
    
    public SucursalFrame() {
        sucursalService = new SucursalService();
        inicializarComponentes();
        cargarDatos();
    }
    
    private void inicializarComponentes() {
        setTitle("Gestión de Sucursales");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Panel principal con diseño horizontal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        
        // Panel izquierdo - Formulario
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), 
            "Datos de la Sucursal", 
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
        
        // Campo Dirección
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Dirección:"), gbc);
        gbc.gridy = 3;
        txtDireccion = new JTextField(20);
        panelFormulario.add(txtDireccion, gbc);
        
        // Campo Teléfono
        gbc.gridy = 4;
        panelFormulario.add(new JLabel("Teléfono:"), gbc);
        gbc.gridy = 5;
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
            "Lista de Sucursales", 
            TitledBorder.LEFT, 
            TitledBorder.TOP
        ));
        
        // Crear tabla
        modeloTabla = new SucursalTableModel();
        tablaSucursales = new JTable(modeloTabla);
        tablaSucursales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaSucursales);
        panelDerecho.add(scrollPane, BorderLayout.CENTER);
        
        // Agregar paneles al panel principal
        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        panelPrincipal.add(panelDerecho, BorderLayout.CENTER);
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        // Configurar eventos
        configurarEventos();
        
        // Configurar ventana
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
    
    private void configurarEventos() {
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarSucursal();
            }
        });
        
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarSucursal();
            }
        });
        
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarSucursal();
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
        tablaSucursales.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tablaSucursales.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    cargarDatosSucursal(filaSeleccionada);
                }
            }
        });
    }
    
    private void guardarSucursal() {
        try {
            if (validarCampos()) {
                Sucursal sucursal = new Sucursal();
                sucursal.setCodigoSucursal(txtCodigo.getText().trim());
                sucursal.setDireccion(txtDireccion.getText().trim());
                sucursal.setTelefono(txtTelefono.getText().trim());
                
                sucursalService.guardarSucursal(sucursal);
                JOptionPane.showMessageDialog(this, "Sucursal guardada exitosamente");
                limpiarCampos();
                cargarDatos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
        }
    }
    
    private void actualizarSucursal() {
        int filaSeleccionada = tablaSucursales.getSelectedRow();
        if (filaSeleccionada >= 0) {
            try {
                if (validarCampos()) {
                    Sucursal sucursal = modeloTabla.getSucursalEnFila(filaSeleccionada);
                    sucursal.setCodigoSucursal(txtCodigo.getText().trim());
                    sucursal.setDireccion(txtDireccion.getText().trim());
                    sucursal.setTelefono(txtTelefono.getText().trim());
                    
                    sucursalService.actualizarSucursal(sucursal);
                    JOptionPane.showMessageDialog(this, "Sucursal actualizada exitosamente");
                    limpiarCampos();
                    cargarDatos();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una sucursal para actualizar");
        }
    }
    
    private void eliminarSucursal() {
        int filaSeleccionada = tablaSucursales.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int respuesta = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar esta sucursal?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION);
            
            if (respuesta == JOptionPane.YES_OPTION) {
                try {
                    Sucursal sucursal = modeloTabla.getSucursalEnFila(filaSeleccionada);
                    sucursalService.eliminarSucursal(sucursal.getId());
                    JOptionPane.showMessageDialog(this, "Sucursal eliminada exitosamente");
                    limpiarCampos();
                    cargarDatos();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una sucursal para eliminar");
        }
    }
    
    private void cargarDatosSucursal(int fila) {
        Sucursal sucursal = modeloTabla.getSucursalEnFila(fila);
        txtCodigo.setText(sucursal.getCodigoSucursal());
        txtDireccion.setText(sucursal.getDireccion());
        txtTelefono.setText(sucursal.getTelefono());
    }
    
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        tablaSucursales.clearSelection();
    }
    
    private boolean validarCampos() {
        if (txtCodigo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El código es obligatorio");
            return false;
        }
        if (txtDireccion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La dirección es obligatoria");
            return false;
        }
        return true;
    }
    
    private void cargarDatos() {
        try {
            List<Sucursal> sucursales = sucursalService.obtenerTodasLasSucursales();
            modeloTabla.setSucursales(sucursales);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage());
        }
    }
    
    @Override
    public void dispose() {
        if (sucursalService != null) {
            sucursalService.cerrar();
        }
        super.dispose();
    }
}
