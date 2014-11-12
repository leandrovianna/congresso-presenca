package com.congresso.serverModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.util.Log;

import com.congresso.DatabaseHelper;
import com.congresso.ImportarDadosActivity;
import com.congresso.dao.ParticipacaoDAOImpl;
import com.congresso.model.Participacao;
import com.google.gson.Gson;

public class ImportadoraDadosTask extends AsyncTask<String, String, Boolean>{

	private DatabaseHelper helper;
	private SQLiteDatabase db;
	private SimpleDateFormat dateFormatJson;
	private Gson gson;

	private ImportarDadosActivity ac;
	private ParticipacaoDAOImpl dao;


	@SuppressLint("SimpleDateFormat")
	public ImportadoraDadosTask(ImportarDadosActivity ac) {
		this.ac = ac;
		helper = new DatabaseHelper(ac);
		dateFormatJson = new SimpleDateFormat("dd-MM-yyyy"); //formato de data do Json
		gson = new Gson();
		dao = new ParticipacaoDAOImpl(ac);
	}



	@Override
	protected Boolean doInBackground(String... params) {
		return gravarDados(params[0]);
	}


	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		ac.retornoGravacaoDados(result);
	}


	private boolean gravarDados(String json) {
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

			ContentValues values = new ContentValues(); //criando o contentvalues que será usado
			String data;

			//fazendo processamento da data
			try {
				data = DateFormat.format("yyyy-MM-dd",
						dateFormatJson.parse(atividade.getDTHORA_INICIO()))
						.toString();

			} catch (ParseException e) {
				Log.e("ERRO: ImportadoraDados",
						"Erro no parser da data a partir do Json");
				return false;
			}
			Log.i("OK: ImportadoraDados", "Sucesso. Data a ser passado pro banco: "
					+ data);


			//verificar se a palestra NAO EXISTE
			if ((db.rawQuery("SELECT * FROM palestra WHERE _id = "+atividade.getCODATIVIDADE(), 
					null)).getCount() == 0) {

				//se a palestra nao existe, então vamos adiciona-la ao banco

				Log.d("ImportadoraDados", "Criando Palestra cod:"+atividade.getCODATIVIDADE());
				
				//criando registro Palestra no banco
				values.put("_id", Integer.parseInt(atividade.getCODATIVIDADE()));
				values.put("nome", atividade.getATIVIDADE());
				db.insert("palestra", null, values);
				values.clear();

				
				//criando registro Ministracao no banco
				values.put("_id", ministracaoIdCount);
				values.put("data", data);
				values.put("palestra_id",
						Integer.parseInt(atividade.getCODATIVIDADE()));
				db.insert("ministracao", null, values);
				values.clear();

				for (ParticipanteServer participante : atividade
						.getLISTA_PARTICIPANTES()) {

					if (participante == null)
						break;

					if ((db.rawQuery("SELECT * FROM participante WHERE inscricao = "+participante.getCODPARTICIPANTE(), 
							null)).getCount() == 0) {

						Log.d("ImportadoraDados", "Criando Participante cod:"+participante.getCODPARTICIPANTE());
						
						//criando registro Participante no banco
						values.put("inscricao",
								Integer.parseInt(participante.getCODPARTICIPANTE()));
						values.put("nome", participante.getNOME());
						db.insert("participante", null, values);
						values.clear();

					}

					//criando registro Participacao no banco
					values.put("_id", participacaoIdCount);
					values.put("ministracao_id", ministracaoIdCount);
					values.put("participante_inscricao",
							Integer.parseInt(participante.getCODPARTICIPANTE()));
					values.put("presenca", false);
					values.put("updated", false);
					db.insert("participacao", null, values);
					values.clear();

					participacaoIdCount++;
				}
			} else {
				//caso contrário, a palestra já existe no banco
				//então vamos criar uma ministracao, com a nova data

				//criando registro Ministracao no banco
				values.put("_id", ministracaoIdCount);
				values.put("data", data);
				values.put("palestra_id",
						Integer.parseInt(atividade.getCODATIVIDADE()));
				db.insert("ministracao", null, values);
				values.clear();

				for (ParticipanteServer participante : atividade
						.getLISTA_PARTICIPANTES()) {

					if (participante == null)
						break;

					//criando registro Participacao no banco
					values.put("_id", participacaoIdCount);
					values.put("ministracao_id", ministracaoIdCount);
					values.put("participante_inscricao",
							Integer.parseInt(participante.getCODPARTICIPANTE()));
					values.put("presenca", false);
					values.put("updated", false);
					db.insert("participacao", null, values);
					values.clear();

					participacaoIdCount++;
				}
			}

			ministracaoIdCount++;
		}

		//4a fase: reinserir participações antigas

		for (Participacao participacao : participacoesAntigas) {
			dao.updateParticipacao(participacao);
		}
		
		db.close();

		return true;
	}

}
