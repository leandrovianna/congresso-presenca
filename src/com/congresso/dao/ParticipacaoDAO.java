package com.congresso.dao;

import java.util.List;

import com.congresso.model.Ministracao;
import com.congresso.model.Participacao;

public interface ParticipacaoDAO {

	public List<Participacao> listarParticipacao();
	
	public boolean inserirParticipacao(Participacao participacao);
	
	public boolean removerParticipacao(Participacao participacao);
	
	public boolean updateParticipacao(Participacao participacao);
		
	public Participacao buscarParticipacaoPorInscricaoMinistracao(int inscricao, Ministracao ministracao);

}
