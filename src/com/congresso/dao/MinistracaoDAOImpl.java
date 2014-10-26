package com.congresso.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.congresso.DatabaseHelper;
import com.congresso.Ministracao;
import com.congresso.Palestra;

public class MinistracaoDAOImpl implements MinistracaoDAO {

	private SimpleDateFormat dateFormat;
	private DatabaseHelper helper;
	private SQLiteDatabase db;

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

		//precisamos da data de hoje para fazer a comparacao na busca sql
		//formatando a data de hoje para yyyy-MM-dd
		String dataHoje = dateFormat.format(new Date());

		Log.d("MinistracaoDAOImpl", "Data de hoje para busca no banco: "+dataHoje);

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
	public boolean inserirMinistracao(Ministracao m) {

		ContentValues values = new ContentValues();

		values.put(DatabaseHelper.Ministracao._ID, m.getId());
		values.put(DatabaseHelper.Ministracao.PALESTRA_ID, m.getPalestra().getId());
		values.put(DatabaseHelper.Ministracao.DATA, dateFormat.format(m.getData()));

		long retorno = getDb().insert(DatabaseHelper.Ministracao.TABELA, null, values);

		return (retorno != 1);

	}

	@Override
	public boolean removerMinistracao(Ministracao m) {

		String id = String.valueOf(m.getId());

		int retorno = getDb().delete(DatabaseHelper.Ministracao.TABELA, "WHERE _id = ?", 
				new String[]{id});

		return retorno != 0;
	}

	@Override
	public Ministracao buscarMinistracaoPorId(int id) {

		Ministracao ministracao = null;
		String[] whereClause = new String[]{String.valueOf(id)};

		Cursor cursor = getDb().query(DatabaseHelper.Ministracao.TABELA, null, 
				"WHERE _id = ?", whereClause, null, null, null);

		cursor.moveToFirst();

		try {
			ministracao = new Ministracao(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Ministracao._ID)), 
					new Palestra(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Palestra.NOME)), 
							cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Ministracao.PALESTRA_ID))), 
							dateFormat.parse(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Ministracao.DATA))));
		} catch (ParseException e) {
		}
		
		return ministracao;
	}

}
