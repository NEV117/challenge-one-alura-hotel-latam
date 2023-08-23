package com.latam.alura.hotel.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.hotel.models.Huesped;
import com.latam.alura.hotel.models.Reserva;

public class HuespedesDao {

	private EntityManager em;

	public HuespedesDao(EntityManager em) {
		super();
		this.em = em;
	}
	
	public void guardar(Huesped huesped) {
		this.em.persist(huesped);
	}
	

	public void  update(Huesped huesped) {
		em.merge(huesped);
	}
	
	public Huesped buscarPorId(int id) {
        return em.find(Huesped.class, id);
    }

    public void eliminar(Huesped huesped) {
        em.remove(huesped);
    }
    
    public List<Huesped> getAll(){
    	String jpql = "SELECT h FROM Huesped h"; 
    	return em.createQuery(jpql, Huesped.class).getResultList();
    	
    }
    
    public List<Huesped> consultaPorColumnas(String nombre){
        String jpql = "SELECT P FROM Huesped P WHERE P.nombre LIKE :nombre " +
                      "OR P.apellido LIKE :nombre " +
                      "OR P.nacionalidad LIKE :nombre " +
                      "OR P.id LIKE :nombre " +
                      "OR P.telefono LIKE :nombre";
        return em.createQuery(jpql, Huesped.class)
                 .setParameter("nombre", "%" + nombre + "%") // Agrega % al principio y al final para buscar coincidencias parciales
                 .getResultList();
    }



}
