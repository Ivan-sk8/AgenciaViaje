package com.trivago.agenciaviaje.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Clase que representa un vuelo en el sistema
 * @author ivan_
 */
@Entity
public class Vuelo implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String numeroVuelo;
    private Date fecha;
    private String hora;
    private String origen;
    private String destino;
    private int plazasTotales;
    private int plazasDisponibles;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turista_id")
    private Turista turista;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;
    
    // Constructores
    public Vuelo() {}
    
    public Vuelo(String numeroVuelo, Date fecha, String hora, String origen, String destino, 
                 int plazasTotales, int plazasDisponibles) {
        this.numeroVuelo = numeroVuelo;
        this.fecha = fecha;
        this.hora = hora;
        this.origen = origen;
        this.destino = destino;
        this.plazasTotales = plazasTotales;
        this.plazasDisponibles = plazasDisponibles;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNumeroVuelo() { return numeroVuelo; }
    public void setNumeroVuelo(String numeroVuelo) { this.numeroVuelo = numeroVuelo; }
    
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    
    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
    
    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }
    
    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }
    
    public int getPlazasTotales() { return plazasTotales; }
    public void setPlazasTotales(int plazasTotales) { this.plazasTotales = plazasTotales; }
    
    public int getPlazasDisponibles() { return plazasDisponibles; }
    public void setPlazasDisponibles(int plazasDisponibles) { this.plazasDisponibles = plazasDisponibles; }
    
    public Turista getTurista() { return turista; }
    public void setTurista(Turista turista) { this.turista = turista; }
    
    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }
    
    @Override
    public String toString() {
        return "Vuelo{" +
                "id=" + id +
                ", numeroVuelo='" + numeroVuelo + '\'' +
                ", fecha=" + fecha +
                ", hora='" + hora + '\'' +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", plazasTotales=" + plazasTotales +
                ", plazasDisponibles=" + plazasDisponibles +
                '}';
    }
}
