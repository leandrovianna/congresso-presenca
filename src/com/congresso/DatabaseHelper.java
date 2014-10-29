package com.congresso;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String BANCO_DADOS = "congresso";
	private static final int VERSAO = 13;
	
	public static class Palestra {
		public static final String TABELA = "palestra";
		public static final String _ID = "_id";
		public static final String NOME = "nome";
		
		public static final String[] COLUNAS = new String[]{_ID, NOME};
	}
	
	public static class Ministracao {
		public static final String TABELA = "ministracao";
		public static final String _ID = "_id";
		public static final String DATA = "data";
		public static final String PALESTRA_ID = "palestra_id";
		
		public static final String[] COLUNAS = new String[]{_ID, DATA, PALESTRA_ID};
	}
	
	public static class Participacao {
		public static final String TABELA = "participacao";
		public static final String _ID = "_id";
		public static final String MINISTRACAO_ID = "ministracao_id";
		public static final String PARTICIPANTE_INSCRICAO = "participante_inscricao";
		public static final String PRESENCA = "presenca";
		public static final String UPDATED = "updated";
		
		public static final String[] COLUNAS = 
				new String[]{_ID, MINISTRACAO_ID, PARTICIPANTE_INSCRICAO, PRESENCA, UPDATED};
	}
	
	public static class Participante {
		public static final String TABELA = "participante";
		public static final String INSCRICAO = "inscricao";
		public static final String NOME = "nome";
		
		public static final String[] COLUNAS = new String[]{INSCRICAO, NOME};
	}

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
		
		values.put(DatabaseHelper.Palestra._ID, 0);
		values.put(DatabaseHelper.Palestra.NOME, "Desenvolvimento de Jogos");
		db.insert(DatabaseHelper.Palestra.TABELA, null, values);
		
		ministracaoValues.put(DatabaseHelper.Ministracao._ID, 0);
		ministracaoValues.put(DatabaseHelper.Ministracao.DATA, "2014-10-29");
		ministracaoValues.put(DatabaseHelper.Ministracao.PALESTRA_ID, 0);
		db.insert(DatabaseHelper.Ministracao.TABELA, null, ministracaoValues);
		
		ministracaoValues.put(DatabaseHelper.Ministracao._ID, 1);
		ministracaoValues.put(DatabaseHelper.Ministracao.DATA, "2014-10-30");
		ministracaoValues.put(DatabaseHelper.Ministracao.PALESTRA_ID, 0);
		db.insert(DatabaseHelper.Ministracao.TABELA, null, ministracaoValues);
		
		values.put(DatabaseHelper.Palestra._ID, 1);
		values.put(DatabaseHelper.Palestra.NOME, "Minicurso Arduino");
		db.insert(DatabaseHelper.Palestra.TABELA, null, values);
		
		ministracaoValues.put(DatabaseHelper.Ministracao._ID, 2);
		ministracaoValues.put(DatabaseHelper.Ministracao.DATA, "2014-10-29");
		ministracaoValues.put(DatabaseHelper.Ministracao.PALESTRA_ID, 1);
		db.insert(DatabaseHelper.Ministracao.TABELA, null, ministracaoValues);
		
		values.put(DatabaseHelper.Palestra._ID, 2);
		values.put(DatabaseHelper.Palestra.NOME, "Introdução Ruby");
		db.insert(DatabaseHelper.Palestra.TABELA, null, values);
		
		ministracaoValues.put(DatabaseHelper.Ministracao._ID, 3);
		ministracaoValues.put(DatabaseHelper.Ministracao.DATA, "2014-10-29");
		ministracaoValues.put(DatabaseHelper.Ministracao.PALESTRA_ID, 2);
		db.insert(DatabaseHelper.Ministracao.TABELA, null, ministracaoValues);

		values.clear();
		
		//INSERINDO OS PARTICIPANTES E SUAS PARTICIPAÇÕES
		
		values.put(DatabaseHelper.Participante.INSCRICAO, 1001);
		values.put(DatabaseHelper.Participante.NOME, "João da Silva");
		db.insert(DatabaseHelper.Participante.TABELA, null, values);
		
		participacoesValues.put(DatabaseHelper.Participacao._ID, 0);
		participacoesValues.put(DatabaseHelper.Participacao.MINISTRACAO_ID, 0);
		participacoesValues.put(DatabaseHelper.Participacao.PARTICIPANTE_INSCRICAO, 1001);
		participacoesValues.put(DatabaseHelper.Participacao.PRESENCA, false);
		participacoesValues.put(DatabaseHelper.Participacao.UPDATED, false);
		db.insert(DatabaseHelper.Participacao.TABELA, null, participacoesValues);
		
		participacoesValues.put(DatabaseHelper.Participacao._ID, 1);
		participacoesValues.put(DatabaseHelper.Participacao.MINISTRACAO_ID, 1);
		participacoesValues.put(DatabaseHelper.Participacao.PARTICIPANTE_INSCRICAO, 1001);
		participacoesValues.put(DatabaseHelper.Participacao.PRESENCA, false);
		participacoesValues.put(DatabaseHelper.Participacao.UPDATED, false);
		db.insert(DatabaseHelper.Participacao.TABELA, null, participacoesValues);
		
		participacoesValues.put(DatabaseHelper.Participacao._ID, 2);
		participacoesValues.put(DatabaseHelper.Participacao.MINISTRACAO_ID, 2);
		participacoesValues.put(DatabaseHelper.Participacao.PARTICIPANTE_INSCRICAO, 1001);
		participacoesValues.put(DatabaseHelper.Participacao.PRESENCA, false);
		participacoesValues.put(DatabaseHelper.Participacao.UPDATED, false);
		db.insert(DatabaseHelper.Participacao.TABELA, null, participacoesValues);
		
		participacoesValues.put(DatabaseHelper.Participacao._ID, 3);
		participacoesValues.put(DatabaseHelper.Participacao.MINISTRACAO_ID, 3);
		participacoesValues.put(DatabaseHelper.Participacao.PARTICIPANTE_INSCRICAO, 1001);
		participacoesValues.put(DatabaseHelper.Participacao.PRESENCA, false);
		participacoesValues.put(DatabaseHelper.Participacao.UPDATED, false);
		db.insert(DatabaseHelper.Participacao.TABELA, null, participacoesValues);
		
		values.put(DatabaseHelper.Participante.INSCRICAO, 1002);
		values.put(DatabaseHelper.Participante.NOME, "Maria de Sousa Carvalho");
		db.insert(DatabaseHelper.Participante.TABELA, null, values); //minist ids 2 e 3
		
		participacoesValues.put(DatabaseHelper.Participacao._ID, 4);
		participacoesValues.put(DatabaseHelper.Participacao.MINISTRACAO_ID, 2);
		participacoesValues.put(DatabaseHelper.Participacao.PARTICIPANTE_INSCRICAO, 1002);
		participacoesValues.put(DatabaseHelper.Participacao.PRESENCA, false);
		participacoesValues.put(DatabaseHelper.Participacao.UPDATED, false);
		db.insert(DatabaseHelper.Participacao.TABELA, null, participacoesValues);
	
		participacoesValues.put(DatabaseHelper.Participacao._ID, 5);
		participacoesValues.put(DatabaseHelper.Participacao.MINISTRACAO_ID, 3);
		participacoesValues.put(DatabaseHelper.Participacao.PARTICIPANTE_INSCRICAO, 1002);
		participacoesValues.put(DatabaseHelper.Participacao.PRESENCA, false);
		participacoesValues.put(DatabaseHelper.Participacao.UPDATED, false);
		db.insert(DatabaseHelper.Participacao.TABELA, null, participacoesValues);
	
		values.put(DatabaseHelper.Participante.INSCRICAO, 1003);
		values.put(DatabaseHelper.Participante.NOME, "Otavio Mesquita");
		db.insert(DatabaseHelper.Participante.TABELA, null, values);
		//minist. ids 0, 1, 3 (jogos e ruby)
		
		participacoesValues.put(DatabaseHelper.Participacao._ID, 6);
		participacoesValues.put(DatabaseHelper.Participacao.MINISTRACAO_ID, 0);
		participacoesValues.put(DatabaseHelper.Participacao.PARTICIPANTE_INSCRICAO, 1003);
		participacoesValues.put(DatabaseHelper.Participacao.PRESENCA, false);
		participacoesValues.put(DatabaseHelper.Participacao.UPDATED, false);
		db.insert(DatabaseHelper.Participacao.TABELA, null, participacoesValues);
		
		participacoesValues.put(DatabaseHelper.Participacao._ID, 7);
		participacoesValues.put(DatabaseHelper.Participacao.MINISTRACAO_ID, 1);
		participacoesValues.put(DatabaseHelper.Participacao.PARTICIPANTE_INSCRICAO, 1003);
		participacoesValues.put(DatabaseHelper.Participacao.PRESENCA, false);
		participacoesValues.put(DatabaseHelper.Participacao.UPDATED, false);
		db.insert(DatabaseHelper.Participacao.TABELA, null, participacoesValues);
		
		participacoesValues.put(DatabaseHelper.Participacao._ID, 8);
		participacoesValues.put(DatabaseHelper.Participacao.MINISTRACAO_ID, 3);
		participacoesValues.put(DatabaseHelper.Participacao.PARTICIPANTE_INSCRICAO, 1003);
		participacoesValues.put(DatabaseHelper.Participacao.PRESENCA, false);
		participacoesValues.put(DatabaseHelper.Participacao.UPDATED, false);
		db.insert(DatabaseHelper.Participacao.TABELA, null, participacoesValues);
		
		//Add 3 participantes
		//3 palestras (jogos [2 ministracoes], arduino [1 minist.] e ruby [1 minist.]
		//Add 9 participações
	}

}
