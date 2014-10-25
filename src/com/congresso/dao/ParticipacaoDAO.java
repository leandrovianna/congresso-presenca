package com.congresso.dao;

import java.util.List;

import com.congresso.Participacao;

public interface ParticipacaoDAO {

	public List<Participacao> listarParticipacao();
	
	public void inserirParticipacao(Participacao participacao);
	
	public boolean removerParticipacao(Participacao participacao);
	
	public Participacao buscarParticipacaoPorId(int id);
	
}
