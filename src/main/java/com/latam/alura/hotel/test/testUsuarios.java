package com.latam.alura.hotel.test;

import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.hotel.dao.ReservasDao;
import com.latam.alura.hotel.dao.UsuariosDao;
import com.latam.alura.hotel.models.Reserva;
import com.latam.alura.hotel.models.Usuarios;
import com.latam.alura.hotel.utils.JPAUtils;

public class testUsuarios {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 EntityManager em = JPAUtils.getEntityManager();

	     em.getTransaction().begin();

	     UsuariosDao usuariosDao = new UsuariosDao(em);

	     // Create a new user instance
		 Usuarios newUser = new Usuarios();
		  
		 //Crear Usuarios Nuevos
		 newUser.setId(4);
		 newUser.setNombre("Administrador");
		 newUser.setContrasenia("123qwe"); // Set the plaintext password
	
		 // Save the new user with the hashed password
		 usuariosDao.save(newUser);
		  
		 //Validar Contrasenia encriptada
		 //boolean isValidLogin = usuariosDao.checkCredentials("Nico", "1234");
	     
	     //System.out.println(isValidLogin);
	     

	     //Get all nombre usuarios
	     //List<Usuarios> reservas = usuDao.getAll(); // Utiliza el método getAll del objeto reservasDao
	     //reservas.forEach(reserva -> System.out.println(reserva.getNombre()));
	     
	     em.getTransaction().commit();
	     em.close();

	}

}
