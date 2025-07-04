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
    private JButton btnGuardar, btnActualizar, btnEliminar, btnBuscar, btnLimpiar;
    private JTable tablaTuristas;
    private TuristaTableModel modeloTabla;
    
    public TuristaFrame() {
        turistaService = new TuristaService();
        inicializarComponentes();
        cargarDatos();
    }
    
    private void inicializarComponentes() {
        setTitle("Gestión de Turistas - Agencia de Viajes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Código
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1;
        txtCodigo = new JTextField(15);
        panelFormulario.add(txtCodigo, gbc);
        
        // Nombre
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(15);
        panelFormulario.add(txtNombre, gbc);
        
        // Apellidos
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(new JLabel("Apellidos:"), gbc);
        gbc.gridx = 1;
        txtApellidos = new JTextField(15);
        panelFormulario.add(txtApellidos, gbc);
        
        // Dirección
        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulario.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        txtDireccion = new JTextField(15);
        panelFormulario.add(txtDireccion, gbc);
        
        // Teléfono
        gbc.gridx = 0; gbc.gridy = 4;
        panelFormulario.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        txtTelefono = new JTextField(15);
        panelFormulario.add(txtTelefono, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnGuardar = new JButton("Guardar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        btnLimpiar = new JButton("Limpiar");
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnLimpiar);
        
        // Tabla
        modeloTabla = new TuristaTableModel();
        tablaTuristas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaTuristas);
        
        // Agregar componentes al frame
        add(panelFormulario, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        
        // Eventos
        configurarEventos();
        
        pack();
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
        
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarTurista();
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
        try {
            Turista turista = new Turista();
            turista.setCodigoTurista(txtCodigo.getText().trim());
            turista.setNombre(txtNombre.getText().trim());
            turista.setApellidos(txtApellidos.getText().trim());
            turista.setDireccion(txtDireccion.getText().trim());
            turista.setTelefono(txtTelefono.getText().trim());
            
            if (validarDatos(turista)) {
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
                Turista turista = modeloTabla.getTuristaEnFila(filaSeleccionada);
                turista.setCodigoTurista(txtCodigo.getText().trim());
                turista.setNombre(txtNombre.getText().trim());
                turista.setApellidos(txtApellidos.getText().trim());
                turista.setDireccion(txtDireccion.getText().trim());
                turista.setTelefono(txtTelefono.getText().trim());
                
                if (validarDatos(turista)) {
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
    
    private void buscarTurista() {
        String codigo = txtCodigo.getText().trim();
        if (!codigo.isEmpty()) {
            try {
                Turista turista = turistaService.buscarTuristaPorCodigo(codigo);
                if (turista != null) {
                    cargarDatosTurista(turista);
                } else {
                    JOptionPane.showMessageDialog(this, "Turista no encontrado");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al buscar: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese un código para buscar");
        }
    }
    
    private void cargarDatosTurista(int fila) {
        Turista turista = modeloTabla.getTuristaEnFila(fila);
        cargarDatosTurista(turista);
    }
    
    private void cargarDatosTurista(Turista turista) {
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
    }
    
    private boolean validarDatos(Turista turista) {
        if (turista.getCodigoTurista().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El código es obligatorio");
            return false;
        }
        if (turista.getNombre().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
            return false;
        }
        if (turista.getApellidos().isEmpty()) {
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
