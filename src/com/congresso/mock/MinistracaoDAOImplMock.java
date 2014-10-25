package com.congresso.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.congresso.Ministracao;
import com.congresso.Palestra;
import com.congresso.dao.MinistracaoDAO;

public class MinistracaoDAOImplMock implements MinistracaoDAO {

	public MinistracaoDAOImplMock() {
		
	}

	@Override
	public List<Ministracao> listarMinistracaoDeHoje() {
		
		List<Ministracao> ministracoes = new ArrayList<Ministracao>();
		
		Ministracao m = new Ministracao(0, new Palestra("Desenvolvimento de Jogos", 0), new Date());
		Ministracao m2 = new Ministracao(1, new Palestra("Introducao ao Ruby", 1), new Date());
		Ministracao m3 = new Ministracao(2, new Palestra("Curso Arduino", 2), new Date());
		Ministracao m4 = new Ministracao(3, new Palestra("Curso Arduino", 2), new Date());
		Ministracao m5 = new Ministracao(4, new Palestra("Curso Arduino", 2), new Date());
		
		ministracoes.add(m);
		ministracoes.add(m2);
		ministracoes.add(m3);
		ministracoes.add(m4);
		ministracoes.add(m5);
		
		return ministracoes;
	}

	@Override
	public void inserirMinistracao(Ministracao m) {
		
	}

	@Override
	public boolean removerMinistracao(Ministracao m) {
		return true;
	}

	@Override
	public Ministracao buscarMinistracaoPorId(int id) {
		
		Ministracao m = new Ministracao(0, new Palestra("Desenvolvimento de Jogos", 0), new Date());
		
		return m;
	}

}
