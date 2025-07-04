package com.trivago.agenciaviaje.services;

import com.trivago.agenciaviaje.modelo.Turista;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Servicio para gestionar operaciones CRUD de Turistas
 * @author ivan_
 */
public class TuristaService {
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    public TuristaService() {
        this.emf = Persistence.createEntityManagerFactory("AgenciaViajePU");
        this.em = emf.createEntityManager();
    }
    
    /**
     * Guardar un nuevo turista
     */
    public void guardarTurista(Turista turista) {
        try {
            em.getTransaction().begin();
            em.persist(turista);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }
    
    /**
     * Actualizar un turista existente
     */
    public void actualizarTurista(Turista turista) {
        try {
            em.getTransaction().begin();
            em.merge(turista);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }
    
    /**
     * Eliminar un turista
     */
    public void eliminarTurista(Long id) {
        try {
            em.getTransaction().begin();
            Turista turista = em.find(Turista.class, id);
            if (turista != null) {
                em.remove(turista);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }
    
    /**
     * Buscar turista por ID
     */
    public Turista buscarTuristaPorId(Long id) {
        return em.find(Turista.class, id);
    }
    
    /**
     * Buscar turista por código
     */
    public Turista buscarTuristaPorCodigo(String codigo) {
        TypedQuery<Turista> query = em.createQuery(
            "SELECT t FROM Turista t WHERE t.codigoTurista = :codigo", Turista.class);
        query.setParameter("codigo", codigo);
        List<Turista> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }
    
    /**
     * Obtener todos los turistas
     */
    public List<Turista> obtenerTodosLosTuristas() {
        TypedQuery<Turista> query = em.createQuery("SELECT t FROM Turista t", Turista.class);
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
