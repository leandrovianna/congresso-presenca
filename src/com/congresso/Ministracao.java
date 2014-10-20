package com.congresso;

import java.util.Date;

public class Ministracao {

	int id;
	int palestra_id;
	Date data;
	
	public Ministracao(int id, int palestra_id, Date data) {
		super();
		this.id = id;
		this.palestra_id = palestra_id;
		this.data = data;
	}

	public Ministracao() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPalestra_id() {
		return palestra_id;
	}

	public void setPalestra_id(int palestra_id) {
		this.palestra_id = palestra_id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
