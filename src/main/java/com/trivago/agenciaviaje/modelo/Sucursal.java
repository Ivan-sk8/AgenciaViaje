package com.trivago.agenciaviaje.modelo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Clase que representa una sucursal de la agencia de viajes
 * @author ivan_
 */
@Entity
public class Sucursal implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String codigoSucursal;
    private String direccion;
    private String telefono;
    
    // Constructores
    public Sucursal() {}
    
    public Sucursal(String codigoSucursal, String direccion, String telefono) {
        this.codigoSucursal = codigoSucursal;
        this.direccion = direccion;
        this.telefono = telefono;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCodigoSucursal() { return codigoSucursal; }
    public void setCodigoSucursal(String codigoSucursal) { this.codigoSucursal = codigoSucursal; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    @Override
    public String toString() {
        return "Sucursal{" +
                "id=" + id +
                ", codigoSucursal='" + codigoSucursal + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
