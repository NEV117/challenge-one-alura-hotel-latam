package com.latam.alura.hotel.test;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import com.latam.alura.hotel.dao.HuespedesDao;
import com.latam.alura.hotel.models.Huesped;
import com.latam.alura.hotel.utils.JPAUtils;


public class RegistroHuesped {

	public static void main(String[] args) {
		/*Huesped HuespedTest = new Huesped();
		HuespedTest.setNombre("test Desde Java DAO");
		HuespedTest.setApellido("insert despues de un delete");
		HuespedTest.setFechaNacimiento(Date.valueOf("2022-01-10"));
		HuespedTest.setNacionalidad("Colombiano");
		HuespedTest.setTelefono("1235");
		
		EntityManager em = JPAUtils.getEntityManager();
		
		HuespedesDao huespedDao = new HuespedesDao(em);
		
		em.getTransaction().begin();
		
		huespedDao.guardar(HuespedTest); //guardar
	    em.getTransaction().commit();
	    em.close(); //comentar linea para hacer el delete
	    */
	    //delete x id
        /*em.getTransaction().begin();
        
        HuespedesDao huespedDao2 = new HuespedesDao(em);
        
        // Buscar el objeto por su ID
        Huesped huespedToDelete = huespedDao2.buscarPorId(2); // Cambia "buscarPorId" al método adecuado en tu DAO
        
        if (huespedToDelete != null) {
            em.remove(huespedToDelete); // Marcar para eliminar
            em.flush(); // Sincronizar cambios y eliminar
        }
        
        em.getTransaction().commit();
        em.close();*/
	    
	    // buscar por id
	    EntityManager em = JPAUtils.getEntityManager();

		em.getTransaction().begin();
		
		HuespedesDao huespedDao = new HuespedesDao(em);
		
		//Huesped resultHuesped = huespedDao.buscarPorId(1); //Buscar por ID
		//System.out.println(resultHuesped.getNombre());
		/*
		List<Huesped> huespedes = huespedDao.getAll(); //getAll
		huespedes.forEach(prod -> System.out.println(prod.getApellido()));
		 */
		List<Huesped> huespedes = huespedDao.consultaPorColumnas("test Desde Java 2");
		huespedes.forEach(prod -> System.out.println(prod.getApellido()));
		
	    em.getTransaction().commit();
	    em.close();
		
	}

}
