package com.congresso;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String BANCO_DADOS = "congresso";
	private static final int VERSAO = 1;
	
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
				" data DATE, palestra_id INTEGER, " +
				" FOREIGN KEY(palestra_id) REFERENCES palestra(_id));");
		
		db.execSQL("CREATE TABLE participacao (_id INTEGER PRIMARY KEY, ministracao_id INTEGER," +
				" participante_inscricao INTEGER, presenca BOOLEAN, updated BOOLEAN," +
				" FOREIGN KEY(ministracao_id) REFERENCES ministracao(_id)," +
				" FOREIGN KEY(participante_inscricao) REFERENCES participante(inscricao));");
		
		db.execSQL("CREATE TABLE participante (inscricao INTEGER PRIMARY KEY, nome TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
