package com.congresso.serverModel;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.congresso.dao.ParticipacaoDAOImpl;
import com.google.gson.Gson;

public class ExportadoraDados {

	private ParticipacaoDAOImpl pDAO;
	private Gson gson;

	public ExportadoraDados(Context context) {
		pDAO = new ParticipacaoDAOImpl(context);
		gson = new Gson();
	}

	public String getJsonEvento(){

		List<Presenca> presencas = new ArrayList<Presenca>();

		presencas = pDAO.listarParticipacoesJson();
		
		String json = gson.toJson(presencas);

		return json;
	}
}
