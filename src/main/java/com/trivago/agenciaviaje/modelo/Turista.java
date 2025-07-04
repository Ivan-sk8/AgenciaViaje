package com.trivago.agenciaviaje.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa a un turista en el sistema
 * @author ivan_
 */
@Entity
public class Turista implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String codigoTurista;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String telefono;
    
    @OneToMany(mappedBy = "turista", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vuelo> vuelos = new ArrayList<>();
    
    // Constructores
    public Turista() {}
    
    public Turista(String codigoTurista, String nombre, String apellidos, String direccion, String telefono) {
        this.codigoTurista = codigoTurista;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCodigoTurista() { return codigoTurista; }
    public void setCodigoTurista(String codigoTurista) { this.codigoTurista = codigoTurista; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public List<Vuelo> getVuelos() { return vuelos; }
    public void setVuelos(List<Vuelo> vuelos) { this.vuelos = vuelos; }
    
    @Override
    public String toString() {
        return "Turista{" +
                "id=" + id +
                ", codigoTurista='" + codigoTurista + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
