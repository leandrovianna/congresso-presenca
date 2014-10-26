package com.congresso.dao;

import java.util.List;

import com.congresso.Ministracao;

public interface MinistracaoDAO {

	public List<Ministracao> listarMinistracaoDeHoje();
	
	public boolean inserirMinistracao(Ministracao m);
	
	public boolean removerMinistracao(Ministracao m);
	
	public Ministracao buscarMinistracaoPorId(int id);
}
