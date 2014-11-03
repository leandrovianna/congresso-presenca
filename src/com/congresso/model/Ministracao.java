package com.congresso.model;

import java.util.Date;

public class Ministracao {

	private int id;
	private Palestra palestra;
	private Date data;
	
	public Ministracao(int id, Palestra palestra, Date data) {
		this.id = id;
		this.palestra = palestra;
		this.data = data;
	}
	
	public Ministracao() {}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Palestra getPalestra() {
		return palestra;
	}
	
	public void setPalestra(Palestra palestra) {
		this.palestra = palestra;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
}
