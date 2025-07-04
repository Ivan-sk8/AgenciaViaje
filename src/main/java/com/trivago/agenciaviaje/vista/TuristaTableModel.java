package com.trivago.agenciaviaje.vista;

import com.trivago.agenciaviaje.modelo.Turista;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de tabla para mostrar turistas
 * @author ivan_
 */
public class TuristaTableModel extends AbstractTableModel {
    
    private List<Turista> turistas;
    private String[] columnas = {"ID", "Código", "Nombre", "Apellidos", "Dirección", "Teléfono"};
    
    public TuristaTableModel() {
        this.turistas = new ArrayList<>();
    }
    
    @Override
    public int getRowCount() {
        return turistas.size();
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
        Turista turista = turistas.get(rowIndex);
        switch (columnIndex) {
            case 0: return turista.getId();
            case 1: return turista.getCodigoTurista();
            case 2: return turista.getNombre();
            case 3: return turista.getApellidos();
            case 4: return turista.getDireccion();
            case 5: return turista.getTelefono();
            default: return null;
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    public void setTuristas(List<Turista> turistas) {
        this.turistas = turistas;
        fireTableDataChanged();
    }
    
    public Turista getTuristaEnFila(int fila) {
        return turistas.get(fila);
    }
    
    public void agregarTurista(Turista turista) {
        turistas.add(turista);
        fireTableRowsInserted(turistas.size() - 1, turistas.size() - 1);
    }
    
    public void eliminarTurista(int fila) {
        turistas.remove(fila);
        fireTableRowsDeleted(fila, fila);
    }
}
