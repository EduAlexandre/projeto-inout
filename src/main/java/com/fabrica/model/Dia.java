package com.fabrica.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Dia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idDia;
	
	private String dia;
	
	@ManyToOne
	@JoinColumn(name = "id_registro")
	private Registro registro;

	public Integer getIdDia() {
		return idDia;
	}

	public void setIdDia(Integer idDia) {
		this.idDia = idDia;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public Registro getRegistro() {
		return registro;
	}

	public void setRegistro(Registro registro) {
		this.registro = registro;
	}

	@Override
	public String toString() {
		return "Dia [idDia=" + idDia + ", dia=" + dia + ", registro=" + registro + "]";
	}
	
	
	
}
