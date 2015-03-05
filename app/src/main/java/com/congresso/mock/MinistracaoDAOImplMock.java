package com.congresso.mock;

import java.util.Date;
import java.util.List;

import com.congresso.dao.MinistracaoDAO;
import com.congresso.model.Ministracao;
import com.congresso.model.Palestra;

public class MinistracaoDAOImplMock implements MinistracaoDAO {

	public MinistracaoDAOImplMock() {
		
	}

	@Override
	public boolean inserirMinistracao(Ministracao m) {
		return true;
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

	@Override
	public List<Ministracao> listarMinistracoesDeHoje() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ministracao> listarMinistracoes() {
		// TODO Auto-generated method stub
		return null;
	}

}
