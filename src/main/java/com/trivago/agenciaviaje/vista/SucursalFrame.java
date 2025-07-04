package com.trivago.agenciaviaje.vista;

import com.trivago.agenciaviaje.modelo.Sucursal;
import com.trivago.agenciaviaje.services.SucursalService;
import javax.swing.*;
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
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
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
        
        // Panel superior con formulario
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Datos de la Sucursal"));
        
        // Panel de campos
        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 5, 5));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelCampos.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panelCampos.add(txtCodigo);
        
        panelCampos.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panelCampos.add(txtDireccion);
        
        panelCampos.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelCampos.add(txtTelefono);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        
        panelSuperior.add(panelCampos);
        panelSuperior.add(panelBotones);
        
        // Panel inferior con tabla
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBorder(BorderFactory.createTitledBorder("Lista de Sucursales"));
        
        modeloTabla = new SucursalTableModel();
        tablaSucursales = new JTable(modeloTabla);
        tablaSucursales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tablaSucursales);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        panelInferior.add(scrollPane, BorderLayout.CENTER);
        
        // Agregar paneles al frame
        add(panelSuperior, BorderLayout.NORTH);
        add(panelInferior, BorderLayout.CENTER);
        
        // Configurar eventos
        configurarEventos();
        
        setSize(650, 450);
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
        if (validarCampos()) {
            try {
                Sucursal sucursal = new Sucursal();
                sucursal.setCodigoSucursal(txtCodigo.getText().trim());
                sucursal.setDireccion(txtDireccion.getText().trim());
                sucursal.setTelefono(txtTelefono.getText().trim());
                
                sucursalService.guardarSucursal(sucursal);
                JOptionPane.showMessageDialog(this, "Sucursal guardada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarDatos();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void actualizarSucursal() {
        int filaSeleccionada = tablaSucursales.getSelectedRow();
        if (filaSeleccionada >= 0) {
            if (validarCampos()) {
                try {
                    Sucursal sucursal = modeloTabla.getSucursalEnFila(filaSeleccionada);
                    sucursal.setCodigoSucursal(txtCodigo.getText().trim());
                    sucursal.setDireccion(txtDireccion.getText().trim());
                    sucursal.setTelefono(txtTelefono.getText().trim());
                    
                    sucursalService.actualizarSucursal(sucursal);
                    JOptionPane.showMessageDialog(this, "Sucursal actualizada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarDatos();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una sucursal de la tabla para actualizar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void eliminarSucursal() {
        int filaSeleccionada = tablaSucursales.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int respuesta = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de que desea eliminar esta sucursal?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (respuesta == JOptionPane.YES_OPTION) {
                try {
                    Sucursal sucursal = modeloTabla.getSucursalEnFila(filaSeleccionada);
                    sucursalService.eliminarSucursal(sucursal.getId());
                    JOptionPane.showMessageDialog(this, "Sucursal eliminada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarDatos();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una sucursal de la tabla para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        tablaSucursales.clearSelection();
    }
    
    private boolean validarCampos() {
        if (txtCodigo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El código es obligatorio", "Error de validación", JOptionPane.ERROR_MESSAGE);
            txtCodigo.requestFocus();
            return false;
        }
        if (txtDireccion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La dirección es obligatoria", "Error de validación", JOptionPane.ERROR_MESSAGE);
            txtDireccion.requestFocus();
            return false;
        }
        return true;
    }
    
    private void cargarDatosSucursal(int fila) {
        Sucursal sucursal = modeloTabla.getSucursalEnFila(fila);
        txtCodigo.setText(sucursal.getCodigoSucursal());
        txtDireccion.setText(sucursal.getDireccion());
        txtTelefono.setText(sucursal.getTelefono() != null ? sucursal.getTelefono() : "");
    }
    
    private void cargarDatos() {
        try {
            List<Sucursal> sucursales = sucursalService.obtenerTodasLasSucursales();
            modeloTabla.setSucursales(sucursales);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
