package com.latam.alura.hotel.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.latam.alura.hotel.models.Reserva;
import com.latam.alura.hotel.models.Usuarios;

public class UsuariosDao {

	private EntityManager em;

	public UsuariosDao(EntityManager em) {
		super();
		this.em = em;
	}
	
	public void guardar(Usuarios usuario) {
		this.em.persist(usuario);
	}
	
	public void  update(Usuarios usuario) {
		em.merge(usuario);
	}
	

	public Usuarios buscarPorId(int id) {
        return em.find(Usuarios.class, id);
    }
	public void eliminar(Usuarios usuario) {
	    em.remove(usuario);
	}

	public void save(Usuarios usuario) {
	    String hashedPassword = hashPassword(usuario.getContrasenia());
	    usuario.setContrasenia(hashedPassword);
	    em.persist(usuario);
	}

	public boolean checkCredentials(String username, String password) {
	    String jpql = "SELECT u FROM Usuarios u WHERE u.nombre = :username";
	    TypedQuery<Usuarios> query = em.createQuery(jpql, Usuarios.class)
	            .setParameter("username", username);

	    List<Usuarios> users = query.getResultList();

	    if (!users.isEmpty()) {
	        Usuarios user = users.get(0);
	        return checkPassword(password, user.getContrasenia());
	    }
	    return false;
	}

	private String hashPassword(String password) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        byte[] hashBytes = md.digest(password.getBytes());

	        StringBuilder hexString = new StringBuilder();
	        for (byte b : hashBytes) {
	            String hex = Integer.toHexString(0xff & b);
	            if (hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	private boolean checkPassword(String inputPassword, String storedHash) {
	    String hashedInput = hashPassword(inputPassword);
	    return hashedInput != null && hashedInput.equals(storedHash);
	}


}
