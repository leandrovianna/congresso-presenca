package com.congresso;

public class Palestra {

	String nome;
	int id;
	
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
