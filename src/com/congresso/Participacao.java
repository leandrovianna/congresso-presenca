package com.congresso;

public class Participacao {

	private int ministracaoId;
	private int participanteInscricao;
	private int id;
	private boolean presenca;
	private boolean updated;
	
	public Participacao(int ministracaoId, int participanteInscricao, int id,
			boolean presenca, boolean updated) {
		super();
		this.ministracaoId = ministracaoId;
		this.participanteInscricao = participanteInscricao;
		this.id = id;
		this.presenca = presenca;
		this.updated = updated;
	}

	public Participacao() {
		super();
	}

	public int getMinistracaoId() {
		return ministracaoId;
	}

	public void setMinistracaoId(int ministracaoId) {
		this.ministracaoId = ministracaoId;
	}

	public int getParticipanteInscricao() {
		return participanteInscricao;
	}

	public void setParticipanteInscricao(int participanteInscricao) {
		this.participanteInscricao = participanteInscricao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

}
