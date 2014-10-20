package com.congresso;

public class Participacao {

	int ministracao_id;
	int participante_inscricao;
	int id;
	boolean presenca;
	boolean updated;
	
	public Participacao(int ministracao_id, int participante_inscricao, int id,
			boolean presenca, boolean updated) {
		super();
		this.ministracao_id = ministracao_id;
		this.participante_inscricao = participante_inscricao;
		this.id = id;
		this.presenca = presenca;
		this.updated = updated;
	}

	public Participacao() {
		super();
	}

	public int getMinistracao_id() {
		return ministracao_id;
	}

	public void setMinistracao_id(int ministracao_id) {
		this.ministracao_id = ministracao_id;
	}

	public int getParticipante_inscricao() {
		return participante_inscricao;
	}

	public void setParticipante_inscricao(int participante_inscricao) {
		this.participante_inscricao = participante_inscricao;
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
