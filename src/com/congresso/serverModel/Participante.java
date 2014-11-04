package com.congresso.serverModel;

import java.io.Serializable;

public class Participante implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String CODPARTICIPANTE;
	private String NOME;
	private String CPF;

	public Participante() {
		
	}

	public String getCODPARTICIPANTE() {
		return CODPARTICIPANTE;
	}

	public void setCODPARTICIPANTE(String cODPARTICIPANTE) {
		CODPARTICIPANTE = cODPARTICIPANTE;
	}

	public String getNOME() {
		return NOME;
	}

	public void setNOME(String nOME) {
		NOME = nOME;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}
}
