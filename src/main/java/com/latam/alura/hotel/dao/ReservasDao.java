package com.latam.alura.hotel.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.latam.alura.hotel.models.Huesped;
import com.latam.alura.hotel.models.Reserva;

public class ReservasDao {
	private EntityManager em;

	public ReservasDao(EntityManager em) {
		super();
		this.em = em;
	}
	
	public void guardar(Reserva reserva) {
		this.em.persist(reserva);
	}
	
	public void  update(Reserva reserva) {
		em.merge(reserva);
	}
	

	public Reserva buscarPorId(int id) {
        return em.find(Reserva.class, id);
    }
	public void eliminar(Reserva reserva) {
	    em.remove(reserva);
	}

	
	public List<Reserva> getAll(){
    	String jpql = "SELECT r FROM Reserva r"; 
    	return em.createQuery(jpql, Reserva.class).getResultList();
    	
    }
	
	public List<Reserva> consultaPorBusqueda(String entrada) {
		String jpql = "SELECT h FROM Reserva h WHERE " +
		                "(YEAR(h.fechaEntrada) = :ano OR " +
		                "YEAR(h.fechaSalida) = :ano OR " +
		                "h.valor = :valor OR " +
		                "h.formaPago LIKE :formaPago OR " +
		                "CAST(h.id AS string) = :id)";
		  
		  TypedQuery<Reserva> query = em.createQuery(jpql, Reserva.class);
		  
		  int ano = -1;
		  BigDecimal valor = null;
		  String formaPago = null;
		  String idString = null;
		  
		  try {
		      ano = Integer.parseInt(entrada);
		  } catch (NumberFormatException e) {
		      // Ignore the exception if parsing as integer fails
		  }
		  
		  try {
		      valor = new BigDecimal(entrada);
		  } catch (NumberFormatException e) {
		      // Ignore the exception if parsing as BigDecimal fails
		  }
		  
		  try {
		      idString = entrada;
		  } catch (NumberFormatException e) {
		      // Ignore the exception if parsing as String fails
		  }
		  
		  query.setParameter("ano", ano);
		  query.setParameter("valor", valor);
		  query.setParameter("formaPago", "%" + entrada + "%");
		  query.setParameter("id", idString);
		  
		  return query.getResultList();
	}





	

}
