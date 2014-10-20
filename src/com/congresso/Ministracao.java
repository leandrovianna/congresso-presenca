package com.congresso;

import java.util.Date;

public class Ministracao {

	private int id;
	private int palestraId;
	private Date data;
	
	public Ministracao(int id, int palestraId, Date data) {
		super();
		this.id = id;
		this.palestraId = palestraId;
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
		return palestraId;
	}

	public void setPalestra_id(int palestraId) {
		this.palestraId = palestraId;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
