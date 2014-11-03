package com.congresso;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String BANCO_DADOS = "congresso";
	private static final int VERSAO = 24;

	public DatabaseHelper(Context context) {
		super(context, BANCO_DADOS, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("CREATE TABLE palestra (_id INTEGER PRIMARY KEY, nome TEXT);");
		
		db.execSQL("CREATE TABLE ministracao (_id INTEGER PRIMARY KEY," +
				" data TEXT, palestra_id INTEGER, " +
				" FOREIGN KEY(palestra_id) REFERENCES palestra(_id));");
		//alterando campo data para tipo TEXT
		//assim vou salvar um valor como 2014-11-20 (yyyy-MM-dd)
		
		db.execSQL("CREATE TABLE participacao (_id INTEGER PRIMARY KEY, ministracao_id INTEGER," +
				" participante_inscricao INTEGER, presenca BOOLEAN, updated BOOLEAN," +
				" FOREIGN KEY(ministracao_id) REFERENCES ministracao(_id)," +
				" FOREIGN KEY(participante_inscricao) REFERENCES participante(inscricao));");
		
		db.execSQL("CREATE TABLE participante (inscricao INTEGER PRIMARY KEY, nome TEXT)");
		
		inserirDadosTeste(db); //inserindo dados para teste
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		inserirDadosTeste(db); //inserindo dados para teste
	}
	
	private void inserirDadosTeste(SQLiteDatabase db) {
		
		db.execSQL("DELETE FROM palestra;");
		db.execSQL("DELETE FROM ministracao;");
		db.execSQL("DELETE FROM participacao;");
		db.execSQL("DELETE FROM participante;");
		
		ContentValues values = new ContentValues();
		ContentValues ministracaoValues = new ContentValues();
		ContentValues participacoesValues = new ContentValues();
		
		//INSERINDO PALESTRAS E SUAS MINITRAÇÕES
		
		values.put("_id", 0);
		values.put("nome", "Desenvolvimento de Jogos");
		db.insert("palestra", null, values);
		
		ministracaoValues.put("_id", 0);
		ministracaoValues.put("data", "2014-11-03");
		ministracaoValues.put("palestra_id", 0);
		db.insert("ministracao", null, ministracaoValues);
		
		ministracaoValues.put("_id", 1);
		ministracaoValues.put("data", "2014-11-04");
		ministracaoValues.put("palestra_id", 0);
		db.insert("ministracao", null, ministracaoValues);
		
		values.put("_id", 1);
		values.put("nome", "Minicurso Arduino");
		db.insert("palestra", null, values);
		
		ministracaoValues.put("_id", 2);
		ministracaoValues.put("data", "2014-11-03");
		ministracaoValues.put("palestra_id", 1);
		db.insert("ministracao", null, ministracaoValues);
		
		ministracaoValues.put("_id", 3);
		ministracaoValues.put("data", "2014-11-04");
		ministracaoValues.put("palestra_id", 1);
		db.insert("ministracao", null, ministracaoValues);
		
		values.put("_id", 2);
		values.put("nome", "Introdução Ruby");
		db.insert("palestra", null, values);
		
		ministracaoValues.put("_id", 4);
		ministracaoValues.put("data", "2014-11-03");
		ministracaoValues.put("palestra_id", 2);
		db.insert("ministracao", null, ministracaoValues);

		values.clear();
		
		//INSERINDO OS PARTICIPANTES E SUAS PARTICIPAÇÕES
		
		values.put("inscricao", 1001);
		values.put("nome", "João da Silva");
		db.insert("participante", null, values);
		
		participacoesValues.put("_id", 0);
		participacoesValues.put("ministracao_id", 0);
		participacoesValues.put("participante_inscricao", 1001);
		participacoesValues.put("presenca", false);
		participacoesValues.put("updated", false);
		db.insert("participacao", null, participacoesValues);
		
		participacoesValues.put("_id", 1);
		participacoesValues.put("ministracao_id", 1);
		participacoesValues.put("participante_inscricao", 1001);
		participacoesValues.put("presenca", false);
		participacoesValues.put("updated", false);
		db.insert("participacao", null, participacoesValues);
		
		participacoesValues.put("_id", 2);
		participacoesValues.put("ministracao_id", 2);
		participacoesValues.put("participante_inscricao", 1001);
		participacoesValues.put("presenca", false);
		participacoesValues.put("updated", false);
		db.insert("participacao", null, participacoesValues);
		
		participacoesValues.put("_id", 3);
		participacoesValues.put("ministracao_id", 3);
		participacoesValues.put("participante_inscricao", 1001);
		participacoesValues.put("presenca", false);
		participacoesValues.put("updated", false);
		db.insert("participacao", null, participacoesValues);
		
		participacoesValues.put("_id", 4);
		participacoesValues.put("ministracao_id", 4);
		participacoesValues.put("participante_inscricao", 1001);
		participacoesValues.put("presenca", false);
		participacoesValues.put("updated", false);
		db.insert("participacao", null, participacoesValues);
		
		values.put("inscricao", 1002);
		values.put("nome", "Maria de Sousa Carvalho");
		db.insert("participante", null, values); //minist ids 2 e 3
		
		participacoesValues.put("_id", 5);
		participacoesValues.put("ministracao_id", 2);
		participacoesValues.put("participante_inscricao", 1002);
		participacoesValues.put("presenca", false);
		participacoesValues.put("updated", false);
		db.insert("participacao", null, participacoesValues);
		
		participacoesValues.put("_id", 6);
		participacoesValues.put("ministracao_id", 3);
		participacoesValues.put("participante_inscricao", 1002);
		participacoesValues.put("presenca", false);
		participacoesValues.put("updated", false);
		db.insert("participacao", null, participacoesValues);
	
		participacoesValues.put("_id", 7);
		participacoesValues.put("ministracao_id", 4);
		participacoesValues.put("participante_inscricao", 1002);
		participacoesValues.put("presenca", false);
		participacoesValues.put("updated", false);
		db.insert("participacao", null, participacoesValues);
	
		values.put("inscricao", 1003);
		values.put("nome", "Otavio Mesquita");
		db.insert("participante", null, values);
		//minist. ids 0, 1, 3 (jogos e ruby)
		
		participacoesValues.put("_id", 8);
		participacoesValues.put("ministracao_id", 0);
		participacoesValues.put("participante_inscricao", 1003);
		participacoesValues.put("presenca", false);
		participacoesValues.put("updated", false);
		db.insert("participacao", null, participacoesValues);
		
		participacoesValues.put("_id", 9);
		participacoesValues.put("ministracao_id", 1);
		participacoesValues.put("participante_inscricao", 1003);
		participacoesValues.put("presenca", false);
		participacoesValues.put("updated", false);
		db.insert("participacao", null, participacoesValues);
		
		participacoesValues.put("_id", 10);
		participacoesValues.put("ministracao_id", 2);
		participacoesValues.put("participante_inscricao", 1003);
		participacoesValues.put("presenca", false);
		participacoesValues.put("updated", false);
		db.insert("participacao", null, participacoesValues);
		
		participacoesValues.put("_id", 11);
		participacoesValues.put("ministracao_id", 3);
		participacoesValues.put("participante_inscricao", 1003);
		participacoesValues.put("presenca", false);
		participacoesValues.put("updated", false);
		db.insert("participacao", null, participacoesValues);
	}

}
