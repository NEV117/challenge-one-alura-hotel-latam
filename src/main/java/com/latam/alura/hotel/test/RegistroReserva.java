package com.latam.alura.hotel.test;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.latam.alura.hotel.dao.HuespedesDao;
import com.latam.alura.hotel.dao.ReservasDao;
import com.latam.alura.hotel.models.Huesped;
import com.latam.alura.hotel.models.Reserva;
import com.latam.alura.hotel.utils.JPAUtils;

public class RegistroReserva {

	public static void main(String[] args) {
		//Reserva ReservaTest = new Reserva();
		/*ReservaTest.setFechaEntrada(Date.valueOf("2022-01-10"));*/
		/*ReservaTest.setFechaSalida(Date.valueOf("2022-01-10"));
		BigDecimal valor = new BigDecimal("14.00");
        ReservaTest.setValor(valor);
        ReservaTest.setFormaPago("relacion que no hace nada xd");*/
        /*
		EntityManager em = JPAUtils.getEntityManager();
		
		ReservasDao reservaDao = new ReservasDao(em);
		
		em.getTransaction().begin();
		
		reservaDao.guardar(ReservaTest);
		
	    em.getTransaction().commit();
	    em.close();*/
        EntityManager em = JPAUtils.getEntityManager();

        em.getTransaction().begin();

        ReservasDao reservasDao = new ReservasDao(em);

        List<Reserva> reservas = reservasDao.getAll(); // Utiliza el método getAll del objeto reservasDao
        reservas.forEach(reserva -> System.out.println(reserva.getFormaPago()));

        em.getTransaction().commit();
        em.close();


	}

}
