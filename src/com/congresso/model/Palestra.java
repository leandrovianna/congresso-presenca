package com.congresso.model;

import java.io.Serializable;

public class Palestra implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private int id;
	
	public Palestra(String nome, int id) {
		super();
		this.nome = nome;
		this.id = id;
	}

	public Palestra() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
