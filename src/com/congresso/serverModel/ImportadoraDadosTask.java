package com.congresso.serverModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.util.Log;

import com.congresso.ImportarDadosActivity;
import com.congresso.dao.DatabaseHelper;
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
	}



	@Override
	protected Boolean doInBackground(String... params) {
		return gravarDados(params[0]);
	}


	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);

		dao.close();
		db.close();

		ac.retornoGravacaoDados(result);
	}


	private boolean gravarDados(String json) {
		db = helper.getWritableDatabase();
		dao = new ParticipacaoDAOImpl(ac);

		json = json.replace("\\/", "-");
		Evento evento = gson.fromJson(json, Evento.class);
		
		if (evento == null) {
			Log.e("ImportadoraDadosTask", "ERRO: Leitura do Json - O objeto evento é null");
			return false;
		}

		//1a fase: guardar as participações
		List<Participacao> participacoesAntigas = dao.listarParticipacoesComPresenca();

		//2a fase: excluir tudo
		db.execSQL("DELETE FROM palestra;");
		db.execSQL("DELETE FROM ministracao;");
		db.execSQL("DELETE FROM participacao;");
		db.execSQL("DELETE FROM participante;");


		try {
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
				Log.i("ImportadoraDadosTask", "Data a ser passado pro banco: "+data);

				Cursor cursor = db.rawQuery("SELECT palestra._id FROM palestra WHERE _id = "+
							atividade.getCODATIVIDADE(), null);
				
				//verificar se a palestra NAO EXISTE
				if (cursor.getCount() == 0) {
					
					//limpando cursor da memória, esses resultados não são mais necessário
					cursor.close();

					//se a palestra nao existe, então vamos adiciona-la ao banco

					Log.i("ImportadoraDadosTask", "Criando Palestra cod:"+atividade.getCODATIVIDADE());

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

						Cursor cursor2 = db.rawQuery("SELECT participante.inscricao FROM participante WHERE inscricao = "+
											participante.getCODPARTICIPANTE(), null);
						
						if (cursor2.getCount() == 0) {
							
							//limpando cursor da memória, esses resultados não são mais necessário
							cursor2.close();
							
							Log.i("ImportadoraDadosTask", "Criando Participante cod:"+participante.getCODPARTICIPANTE());

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
					
					Log.i("ImportadoraDadosTask", "Palestra cod:"+atividade.getCODATIVIDADE()+" ja existe no banco!");

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
		} catch (Exception e) {
			e.printStackTrace();

			Log.e("ImportadoraDadosTask", "ERRO: Ocorreu um erro na 3ª etapa - inserção dos dados no banco");
			
		} finally {

			//4a fase: reinserir participações antigas

			for (Participacao participacao : participacoesAntigas) {
				Log.i("ImportadoraDadosTask", "Backup: Atualizando Participacao");
				Log.i("ImportadoraDadosTask", "ministracao_id = "+participacao.getMinistracao().getId()+
						" participante nome:"+participacao.getParticipante().getNome()+
						" inscricao:"+participacao.getParticipante().getInscricao());

				dao.updateParticipacao(participacao);
			}
			
			Log.i("ImportadoraDadosTask", "Importação Terminada.");
		}

		return true;
	}

}
