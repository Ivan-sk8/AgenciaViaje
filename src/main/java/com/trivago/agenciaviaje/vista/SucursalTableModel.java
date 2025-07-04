package com.trivago.agenciaviaje.vista;

import com.trivago.agenciaviaje.modelo.Sucursal;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de tabla para mostrar sucursales
 * @author ivan_
 */
public class SucursalTableModel extends AbstractTableModel {
    
    private List<Sucursal> sucursales;
    private String[] columnas = {"ID", "Código", "Dirección", "Teléfono"};
    
    public SucursalTableModel() {
        this.sucursales = new ArrayList<>();
    }
    
    @Override
    public int getRowCount() {
        return sucursales.size();
    }
    
    @Override
    public int getColumnCount() {
        return columnas.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Sucursal sucursal = sucursales.get(rowIndex);
        switch (columnIndex) {
            case 0: return sucursal.getId();
            case 1: return sucursal.getCodigoSucursal();
            case 2: return sucursal.getDireccion();
            case 3: return sucursal.getTelefono();
            default: return null;
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    public void setSucursales(List<Sucursal> sucursales) {
        this.sucursales = sucursales;
        fireTableDataChanged();
    }
    
    public Sucursal getSucursalEnFila(int fila) {
        return sucursales.get(fila);
    }
    
    public void agregarSucursal(Sucursal sucursal) {
        sucursales.add(sucursal);
        fireTableRowsInserted(sucursales.size() - 1, sucursales.size() - 1);
    }
    
    public void eliminarSucursal(int fila) {
        sucursales.remove(fila);
        fireTableRowsDeleted(fila, fila);
    }
}
