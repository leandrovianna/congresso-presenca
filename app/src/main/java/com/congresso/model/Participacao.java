package com.congresso.model;

import java.io.Serializable;

public class Participacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Ministracao ministracao;
	private Participante participante;
	private boolean presenca;
	private boolean updated;
	
	public Participacao(Ministracao ministracao, Participante participante,
			boolean presenca, boolean updated) {
		this.ministracao = ministracao;
		this.participante = participante;
		this.presenca = presenca;
		this.updated = updated;
	}
	
	public Participacao() {}

	public Ministracao getMinistracao() {
		return ministracao;
	}

	public void setMinistracao(Ministracao ministracao) {
		this.ministracao = ministracao;
	}

	public Participante getParticipante() {
		return participante;
	}

	public void setParticipante(Participante participante) {
		this.participante = participante;
	}

	public boolean isPresenca() {
		return presenca;
	}

	public void setPresenca(boolean presenca) {
		this.presenca = presenca;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}

	public void setPresenca(int presenca) {
		if (presenca == 0)
			this.presenca = false;
		else
			this.presenca = true;
		
	}

	public void setUpdated(int updated) {
		if (updated == 0)
			this.updated = false;
		else
			this.updated = true;
		
	}
}
