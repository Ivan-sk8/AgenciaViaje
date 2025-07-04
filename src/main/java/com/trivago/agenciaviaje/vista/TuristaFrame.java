package com.trivago.agenciaviaje.vista;

import com.trivago.agenciaviaje.modelo.Turista;
import com.trivago.agenciaviaje.services.TuristaService;
import javax.swing.*;
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
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
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
        
        // Panel superior con formulario
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Datos del Turista"));
        
        // Panel de campos
        JPanel panelCampos = new JPanel(new GridLayout(5, 2, 5, 5));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelCampos.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panelCampos.add(txtCodigo);
        
        panelCampos.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelCampos.add(txtNombre);
        
        panelCampos.add(new JLabel("Apellidos:"));
        txtApellidos = new JTextField();
        panelCampos.add(txtApellidos);
        
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
        panelInferior.setBorder(BorderFactory.createTitledBorder("Lista de Turistas"));
        
        modeloTabla = new TuristaTableModel();
        tablaTuristas = new JTable(modeloTabla);
        tablaTuristas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tablaTuristas);
        scrollPane.setPreferredSize(new Dimension(600, 200));
        panelInferior.add(scrollPane, BorderLayout.CENTER);
        
        // Agregar paneles al frame
        add(panelSuperior, BorderLayout.NORTH);
        add(panelInferior, BorderLayout.CENTER);
        
        // Configurar eventos
        configurarEventos();
        
        setSize(650, 500);
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
        if (validarCampos()) {
            try {
                Turista turista = new Turista();
                turista.setCodigoTurista(txtCodigo.getText().trim());
                turista.setNombre(txtNombre.getText().trim());
                turista.setApellidos(txtApellidos.getText().trim());
                turista.setDireccion(txtDireccion.getText().trim());
                turista.setTelefono(txtTelefono.getText().trim());
                
                turistaService.guardarTurista(turista);
                JOptionPane.showMessageDialog(this, "Turista guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarDatos();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void actualizarTurista() {
        int filaSeleccionada = tablaTuristas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            if (validarCampos()) {
                try {
                    Turista turista = modeloTabla.getTuristaEnFila(filaSeleccionada);
                    turista.setCodigoTurista(txtCodigo.getText().trim());
                    turista.setNombre(txtNombre.getText().trim());
                    turista.setApellidos(txtApellidos.getText().trim());
                    turista.setDireccion(txtDireccion.getText().trim());
                    turista.setTelefono(txtTelefono.getText().trim());
                    
                    turistaService.actualizarTurista(turista);
                    JOptionPane.showMessageDialog(this, "Turista actualizado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarDatos();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un turista de la tabla para actualizar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void eliminarTurista() {
        int filaSeleccionada = tablaTuristas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int respuesta = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de que desea eliminar este turista?", 
                "Confirmar eliminación", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (respuesta == JOptionPane.YES_OPTION) {
                try {
                    Turista turista = modeloTabla.getTuristaEnFila(filaSeleccionada);
                    turistaService.eliminarTurista(turista.getId());
                    JOptionPane.showMessageDialog(this, "Turista eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarDatos();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un turista de la tabla para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
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
            JOptionPane.showMessageDialog(this, "El código es obligatorio", "Error de validación", JOptionPane.ERROR_MESSAGE);
            txtCodigo.requestFocus();
            return false;
        }
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio", "Error de validación", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
            return false;
        }
        if (txtApellidos.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los apellidos son obligatorios", "Error de validación", JOptionPane.ERROR_MESSAGE);
            txtApellidos.requestFocus();
            return false;
        }
        return true;
    }
    
    private void cargarDatosTurista(int fila) {
        Turista turista = modeloTabla.getTuristaEnFila(fila);
        txtCodigo.setText(turista.getCodigoTurista());
        txtNombre.setText(turista.getNombre());
        txtApellidos.setText(turista.getApellidos());
        txtDireccion.setText(turista.getDireccion() != null ? turista.getDireccion() : "");
        txtTelefono.setText(turista.getTelefono() != null ? turista.getTelefono() : "");
    }
    
    private void cargarDatos() {
        try {
            List<Turista> turistas = turistaService.obtenerTodosLosTuristas();
            modeloTabla.setTuristas(turistas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
