package com.congresso.dao;

import java.util.List;

import com.congresso.model.Ministracao;

public interface MinistracaoDAO {

	public List<Ministracao> listarMinistracoesDeHoje();
	
	public List<Ministracao> listarMinistracoes();
	
	public boolean inserirMinistracao(Ministracao m);
	
	public boolean removerMinistracao(Ministracao m);
	
	public Ministracao buscarMinistracaoPorId(int id);
}
