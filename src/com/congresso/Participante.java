package com.congresso;

public class Participante {

	int inscricao;
	String nome;
	
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
