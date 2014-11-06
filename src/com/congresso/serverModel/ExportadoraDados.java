package com.congresso.serverModel;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.congresso.dao.ParticipacaoDAOImpl;
import com.congresso.model.Participacao;

public class ExportadoraDados {

	private ParticipacaoDAOImpl pDAO;


	public ExportadoraDados(Context context) {
		pDAO = new ParticipacaoDAOImpl(context);
	}

	public JSONObject getJsonEvento(){

		List<Participacao> participacoes = pDAO.listarParticipacao();

		JSONObject jObj = new JSONObject();
		JSONArray jArr = new JSONArray();

		for(Participacao participacao : participacoes){
			try {
				JSONObject aux = new JSONObject();
				aux.put("cod_participante", participacao.getParticipante().getInscricao());
				aux.put("cod_atividade", participacao.getMinistracao().getPalestra().getId());
				jArr.put(aux);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		try {
			jObj.put("participacoes", jArr);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jObj;

	}
}
