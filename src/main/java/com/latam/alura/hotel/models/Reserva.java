package com.latam.alura.hotel.models;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="reservas")
public class Reserva {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int Id;
	private Date fechaEntrada;
	private Date fechaSalida;
	@Column(columnDefinition="DECIMAL(8,4)")
    private java.math.BigDecimal valor = new java.math.BigDecimal(0);
	private String formaPago;
	/*
	@ManyToOne(fetch=FetchType.LAZY)
	private Huesped huesped;*/
	
	

	//constructor
	public Reserva() {
		
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public Date getFechaEntrada() {
		return fechaEntrada;
	}
	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}
	public Date getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	public java.math.BigDecimal getValor() {
		return valor;
	}
	public void setValor(java.math.BigDecimal valor) {
		this.valor = valor;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	
	

}
