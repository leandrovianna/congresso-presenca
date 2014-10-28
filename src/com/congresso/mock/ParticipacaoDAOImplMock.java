package com.congresso.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.congresso.Ministracao;
import com.congresso.Palestra;
import com.congresso.Participacao;
import com.congresso.Participante;
import com.congresso.dao.ParticipacaoDAO;

public class ParticipacaoDAOImplMock implements ParticipacaoDAO {

	public ParticipacaoDAOImplMock() {
		
	}

	@Override
	public List<Participacao> listarParticipacao() {
		
		List<Participacao> participacoes = new ArrayList<Participacao>();
		
		Ministracao m = new Ministracao(0, new Palestra("Desenvolvimento de Jogos", 0), new Date());
		
		Participacao p1 = new Participacao(m, new Participante(0100, "José Maria"), 0, true, true);
		Participacao p2 = new Participacao(m, new Participante(0102, "Luis Henrique"), 1, true, true);
		Participacao p3 = new Participacao(m, new Participante(0103, "Paulo"), 2, false, true);
		
		participacoes.add(p1);
		participacoes.add(p2);
		participacoes.add(p3);
		
		return participacoes;
	}

	@Override
	public boolean inserirParticipacao(Participacao participacao) {
		return true;
	}

	@Override
	public boolean removerParticipacao(Participacao participacao) {
		return true;
	}

	@Override
	public Participacao buscarParticipacaoPorId(int id) {
		Ministracao m = new Ministracao(0, new Palestra("Desenvolvimento de Jogos", 0), new Date());
		Participacao p = new Participacao(m, new Participante(0100, "José Maria"), id, true, true);
		
		return p;
	}

}
