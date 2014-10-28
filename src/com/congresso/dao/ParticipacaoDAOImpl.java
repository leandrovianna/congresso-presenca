package com.congresso.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.congresso.DatabaseHelper;
import com.congresso.Participacao;
import com.congresso.Participante;

public class ParticipacaoDAOImpl implements ParticipacaoDAO {

	private DatabaseHelper helper;
	private SQLiteDatabase db;
	
	private MinistracaoDAOImpl ministDAO;
	
	public ParticipacaoDAOImpl(Context context) {
		helper = new DatabaseHelper(context);
		ministDAO = new MinistracaoDAOImpl(context);
	}
	
	private SQLiteDatabase getDb() {
		if (db == null)
			db = helper.getWritableDatabase();
		
		return db;
	}

	@Override
	public List<Participacao> listarParticipacao() {
		
		Cursor cursor = getDb().rawQuery("SELECT * FROM participacao, participante " +
				"WHERE participacao.participante_inscricao = participante.inscricao", null);
		
		List<Participacao> participacoes = new ArrayList<Participacao>();
		
		while (cursor.moveToNext()) {
			Participacao p = criarParticipacao(cursor);
			participacoes.add(p);
		}
		
		return participacoes;
	}

	private Participacao criarParticipacao(Cursor cursor) {
		Participacao p = new Participacao();
		
		p.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Participacao._ID)));
		p.setMinistracao(ministDAO.buscarMinistracaoPorId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Participacao.MINISTRACAO_ID))));
		p.setParticipante(new Participante(
					cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Participacao.PARTICIPANTE_INSCRICAO)),
					cursor.getString(cursor.getColumnIndex(DatabaseHelper.Participante.NOME))
				));
		p.setPresenca(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Participacao.PRESENCA)));
		p.setUpdated(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Participacao.UPDATED)));
		
		return p;
	}

	@Override
	public boolean inserirParticipacao(Participacao participacao) {
		ContentValues values = new ContentValues();
		
		values.put("_id", participacao.getId());
		values.put("ministracao_id", participacao.getMinistracao().getId());
		values.put("participante_inscricao", participacao.getParticipante().getInscricao());
		values.put("presenca", participacao.isPresenca());
		values.put("updated", participacao.isUpdated());
		
		long retorno = getDb().insert("participacao", null, values);
		
		return retorno != -1;
	}

	@Override
	public boolean removerParticipacao(Participacao participacao) {
		int retorno = getDb().delete("participacao", "WHERE _id = "+participacao.getId(), null);
		
		return retorno != 0;
	}

	@Override
	public Participacao buscarParticipacaoPorId(int id) {
		Participacao p;
		
		Cursor cursor = getDb().rawQuery("SELECT * FROM participacao, participante " +
				"WHERE participacao.participante_inscricao = participante.inscricao AND " +
				"participacao._id = "+id, null);
		
		p = criarParticipacao(cursor);

		return p;
	}

}
