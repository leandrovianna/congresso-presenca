package com.congresso.serverModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Log;

import com.congresso.DatabaseHelper;
import com.congresso.dao.ParticipacaoDAOImpl;
import com.congresso.model.Participacao;
import com.google.gson.Gson;

public class ImportadoraDados {

	private DatabaseHelper helper;
	private SQLiteDatabase db;
	private SimpleDateFormat dateFormatJson;
	private Gson gson;
	
	private ParticipacaoDAOImpl dao;
	
	
	@SuppressLint("SimpleDateFormat")
	public ImportadoraDados(Context context) {
		helper = new DatabaseHelper(context);
		dateFormatJson = new SimpleDateFormat("dd-MM-yyyy"); //formato de data do Json
		gson = new Gson();
		dao = new ParticipacaoDAOImpl(context);
	}
	
	public boolean gravarDados(String json) {
		db = helper.getWritableDatabase();
		
		json = json.replace("\\/", "-");
		Evento evento = gson.fromJson(json, Evento.class);
		
		//1a fase: guardar as participações
		List<Participacao> participacoesAntigas = dao.listarParticipacoesComPresenca();

		//2a fase: excluir tudo
		db.execSQL("DELETE FROM palestra;");
		db.execSQL("DELETE FROM ministracao;");
		db.execSQL("DELETE FROM participacao;");
		db.execSQL("DELETE FROM participante;");
		
		
		//3a fase: incluir os dados do json
		int ministracaoIdCount = 0; //contador para os ids das ministracoes
		int participacaoIdCount = 0; //contador para os ids das participacoes

		for (Atividade atividade : evento.getLISTA_ATIVIDADES()) {

			if (atividade == null)
				break;

			ContentValues values = new ContentValues();

			//criando registro Palestra no banco
			values.put("_id", Integer.parseInt(atividade.getCODATIVIDADE()));
			values.put("nome", atividade.getATIVIDADE());
			db.insert("palestra", null, values);
			values.clear();

			String data;
			try {
				data = DateFormat.format("yyyy-MM-dd",	dateFormatJson.parse(atividade.getDTHORA_INICIO()))
							.toString();

			} catch (ParseException e) {
				Log.e("ERRO: ImportadoraDados", "Erro no parser da data a partir do Json");
				return false;
			}
			
			Log.i("OK: ImportadoraDados", "Data a ser passado pro banco: "+data);
			
			//criando registro Ministracao no banco
			values.put("_id", ministracaoIdCount);
			values.put("data", data);
			values.put("palestra_id", Integer.parseInt(atividade.getCODATIVIDADE()));
			db.insert("ministracao", null, values);
			values.clear();
			
			//criando registro de Participações desta Atividade no banco
			
			for (ParticipanteServer participante : atividade.getLISTA_PARTICIPANTES()) {
				
				if (participante == null)
					break;
				
				//criando registro Participante no banco
				values.put("inscricao", Integer.parseInt(participante.getCODPARTICIPANTE()));
				values.put("nome", participante.getNOME());
				db.insert("participante", null, values);
				values.clear();
				
				//criando registro Participacao no banco
				values.put("_id", participacaoIdCount);
				values.put("ministracao_id", ministracaoIdCount);
				values.put("participante_inscricao", Integer.parseInt(participante.getCODPARTICIPANTE()));
				values.put("presenca", false);
				values.put("updated", false);
				db.insert("participacao", null, values);
				values.clear();
				
				participacaoIdCount++;
			}
			
			ministracaoIdCount++;
		}
		
		//4a fase: reinserir participações antigas
		
		for (Participacao participacao : participacoesAntigas) {
			dao.updateParticipacao(participacao);
		}
		
		return true;
	}

}
