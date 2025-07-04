package com.trivago.agenciaviaje.services;

import com.trivago.agenciaviaje.modelo.Sucursal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Servicio para gestionar operaciones CRUD de Sucursales
 * @author ivan_
 */
public class SucursalService {
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    public SucursalService() {
        this.emf = Persistence.createEntityManagerFactory("AgenciaViajePU");
        this.em = emf.createEntityManager();
    }
    
    /**
     * Guardar una nueva sucursal
     */
    public void guardarSucursal(Sucursal sucursal) {
        try {
            em.getTransaction().begin();
            em.persist(sucursal);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }
    
    /**
     * Actualizar una sucursal existente
     */
    public void actualizarSucursal(Sucursal sucursal) {
        try {
            em.getTransaction().begin();
            em.merge(sucursal);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }
    
    /**
     * Eliminar una sucursal
     */
    public void eliminarSucursal(Long id) {
        try {
            em.getTransaction().begin();
            Sucursal sucursal = em.find(Sucursal.class, id);
            if (sucursal != null) {
                em.remove(sucursal);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }
    
    
    /**
     * Obtener todas las sucursales
     */
    public List<Sucursal> obtenerTodasLasSucursales() {
        TypedQuery<Sucursal> query = em.createQuery("SELECT s FROM Sucursal s", Sucursal.class);
        return query.getResultList();
    }
    
    /**
     * Cerrar conexiones
     */
    public void cerrar() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
