package com.congresso.model;

import java.io.Serializable;

public class Participante implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int inscricao;
	private String nome;
	
	public Participante(int inscricao, String nome) {
		super();
		this.inscricao = inscricao;
		this.nome = nome;
	}

	public Participante() {
		super();
	}

	public int getInscricao() {
		return inscricao;
	}

	public void setInscricao(int inscricao) {
		this.inscricao = inscricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
