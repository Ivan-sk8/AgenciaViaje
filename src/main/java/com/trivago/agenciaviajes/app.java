package com.trivago.agenciaviajes;

import com.trivago.agenciaviaje.vista.TuristaFrame;
import com.trivago.agenciaviaje.vista.SucursalFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase principal de la aplicación de la Agencia de Viajes
 * @author ivan_
 */
public class app {
    
    public static void main(String[] args) {
        // Configurar Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Crear y mostrar el menú principal
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
    
    /**
     * Clase interna para el menú principal
     */
    static class MenuPrincipal extends JFrame {
        
        public MenuPrincipal() {
            inicializarComponentes();
        }
        
        private void inicializarComponentes() {
            setTitle("Sistema de Gestión - Agencia de Viajes");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            
            // Panel principal
            JPanel panelPrincipal = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            
            // Título
            JLabel lblTitulo = new JLabel("SISTEMA DE GESTIÓN - AGENCIA DE VIAJES");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
            gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
            panelPrincipal.add(lblTitulo, gbc);
            
            // Botón para gestión de turistas
            JButton btnTuristas = new JButton("Gestión de Turistas");
            btnTuristas.setPreferredSize(new Dimension(200, 50));
            btnTuristas.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new TuristaFrame().setVisible(true);
                }
            });
            gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
            panelPrincipal.add(btnTuristas, gbc);
            
            // Botón para gestión de sucursales
            JButton btnSucursales = new JButton("Gestión de Sucursales");
            btnSucursales.setPreferredSize(new Dimension(200, 50));
            btnSucursales.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new SucursalFrame().setVisible(true);
                }
            });
            gbc.gridx = 1; gbc.gridy = 1;
            panelPrincipal.add(btnSucursales, gbc);
            
            // Botón para salir
            JButton btnSalir = new JButton("Salir");
            btnSalir.setPreferredSize(new Dimension(100, 30));
            btnSalir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
            panelPrincipal.add(btnSalir, gbc);
            
            add(panelPrincipal, BorderLayout.CENTER);
            
            pack();
            setLocationRelativeTo(null);
            setResizable(false);
        }
    }
}
