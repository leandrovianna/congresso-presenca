package com.congresso.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.congresso.DatabaseHelper;
import com.congresso.Ministracao;
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
		if (cursor.moveToNext()) {

			Participacao p = new Participacao();

			p.setId(cursor.getInt(cursor.getColumnIndex("_id")));
			p.setMinistracao(ministDAO.buscarMinistracaoPorId(cursor.getInt(cursor.getColumnIndex("ministracao_id"))));
			p.setParticipante(new Participante(
					cursor.getInt(cursor.getColumnIndex("participante_inscricao")),
					cursor.getString(cursor.getColumnIndex("nome"))
					));
			p.setPresenca(cursor.getInt(cursor.getColumnIndex("presenca")));
			p.setUpdated(cursor.getInt(cursor.getColumnIndex("updated")));

			return p;

		}

		return null;
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

	@Override
	public Participacao buscarParticipacaoPorInscricaoMinistracao(
			int inscricao, Ministracao ministracao) {

		Cursor cursor = getDb().rawQuery("SELECT * FROM participacao, participante " +
				"WHERE participante.inscricao = participacao.participante_inscricao AND " +
				"participacao.ministracao_id = "+ministracao.getId()+" AND " +
				"participacao.participante_inscricao = "+inscricao
				, null);

		Participacao p = criarParticipacao(cursor);

		return p;
	}

	@Override
	public boolean updateParticipacao(Participacao participacao) {

		getDb().execSQL("UPDATE participacao " +
				"SET ministracao_id='"+participacao.getMinistracao().getId()+"', " +
				"participante_inscricao='"+participacao.getParticipante().getInscricao()+"', " +
				"updated='false', " +
				"presenca='"+participacao.isPresenca()+"' " +
				"WHERE _id='"+participacao.getId()+"';");
		
		return true;
	}

}
