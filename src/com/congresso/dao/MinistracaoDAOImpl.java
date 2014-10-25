package com.congresso.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.congresso.DatabaseHelper;
import com.congresso.Ministracao;
import com.congresso.Palestra;

public class MinistracaoDAOImpl implements MinistracaoDAO {

	private SimpleDateFormat dateFormat;
	
	private DatabaseHelper helper;
	private SQLiteDatabase db;
	
	@SuppressLint("SimpleDateFormat")
	public MinistracaoDAOImpl(Context context) {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		helper = new DatabaseHelper(context);
	}
	
	private SQLiteDatabase getDb() {
		if (db == null)
			db = helper.getWritableDatabase();
		
		return db;
	}

	@Override
	public List<Ministracao> listarMinistracaoDeHoje() {

		//formatando a data de hoje para yyyy-MM-dd
		String dataHoje = dateFormat.format(new Date()); 
		
		Cursor cursor = getDb().rawQuery("SELECT ministracao._id, ministracao.palestra_id, palestra.nome FROM ministracao, palestra " + 
				"WHERE palestra._id = ministracao.palestra_id AND ministracao.data = ?", new String[]{dataHoje});
		
		List<Ministracao> ministracoes = new ArrayList<Ministracao>();
		
		while (cursor.moveToNext()) {
			Ministracao m = new Ministracao(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Ministracao._ID)), 
					new Palestra(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Palestra.NOME)), 
							cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Ministracao.PALESTRA_ID))), 
								new Date());
			
			ministracoes.add(m);
		}
		
		return ministracoes;
	}

	@Override
	public void inserirMinistracao(Ministracao m) {

	}

	@Override
	public boolean removerMinistracao(Ministracao m) {
		return false;
	}

	@Override
	public Ministracao buscarMinistracaoPorId(int id) {
		return null;
	}

}
