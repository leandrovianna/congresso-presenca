package com.congresso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MinistracaoDAO {

	private DatabaseHelper helper;
	private SQLiteDatabase db;
	
	public MinistracaoDAO(Context context) {
		
		helper = new DatabaseHelper(context);
	}
	
	private SQLiteDatabase getDb() {
		if (db == null) {
			db = helper.getWritableDatabase();
		}
		
		return db;
	}
	
	public void close() {
		helper.close();
	}
	
	public List<Ministracao> listar() {
		
		List<Ministracao> ministracoes = new ArrayList<Ministracao>();
		
		//Fazendo a busca no banco
		Cursor cursor = getDb().query(DatabaseHelper.Ministracao.TABELA, DatabaseHelper.Ministracao.COLUNAS,
				null, null, null, null, DatabaseHelper.Ministracao.DATA);
		
		while (cursor.moveToNext()) {
			Ministracao ministracao = criarMinistracao(cursor);
			ministracoes.add(ministracao);
		}
		
		cursor.close();
		return ministracoes;
	}
	
	public void inserir(Ministracao ministracao) {
		ContentValues values = new ContentValues();
		
		values.put(DatabaseHelper.Ministracao._ID, ministracao.getId());
		values.put(DatabaseHelper.Ministracao.PALESTRA_ID, ministracao.getPalestraId());
		values.put(DatabaseHelper.Ministracao.DATA, ministracao.getData().getTime());
		
		getDb().insert(DatabaseHelper.Ministracao.TABELA, null, values);
	}
	
	public void remover(int id){}
	
	public Ministracao buscar(int id){return null;}

	private Ministracao criarMinistracao(Cursor cursor) {
		Ministracao ministracao = new Ministracao(
					cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Ministracao._ID)),
					cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Ministracao.PALESTRA_ID)),
					new Date(cursor.getLong(cursor.getColumnIndex(DatabaseHelper.Ministracao.DATA))));
					
		return ministracao;
	}
}
