package com.congresso.serverModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.congresso.DatabaseHelper;

public class ImportadoraDados {

	private DatabaseHelper helper;
	private SQLiteDatabase db;
	private SimpleDateFormat dateFormat;
	
	
	public ImportadoraDados(Context context) {
		helper = new DatabaseHelper(context);
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}
	
	public boolean gravarDados(Evento evento) {
		
		db = helper.getWritableDatabase();
		
		//limpar dados presentes no banco
		db.execSQL("DELETE FROM palestra;");
		db.execSQL("DELETE FROM ministracao;");
		db.execSQL("DELETE FROM participacao;");
		db.execSQL("DELETE FROM participante;");
		
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
				data = dateFormat.format(dateFormat.parse(atividade.getDTHORA_INICIO()));
			} catch (ParseException e) {
				data = atividade.getDTHORA_INICIO();
			}
			
			Log.i("ImportadoraDados", "Data a ser passado pro banco: "+data);
			
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
		
		return true;
	}

}
