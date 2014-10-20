package com.congresso;

import java.util.Calendar;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {

	private DatabaseHelper helper;
	private SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		helper =  new DatabaseHelper(this);
		db = helper.getWritableDatabase();
		
		inserirDadosTeste();
	}

	private void inserirDadosTeste() {
		ContentValues values = new ContentValues();
		ContentValues ministracaoValues = new ContentValues();
		ContentValues participacoesValues = new ContentValues();
		
		long dateTest = Calendar.getInstance().getTime().getTime();
		
		//INSERINDO PALESTRAS E SUAS MINITRAÇÕES
		
		values.put(DatabaseHelper.Palestra._ID, 0);
		values.put(DatabaseHelper.Palestra.NOME, "Desenvolvimento de Jogos");
		db.insert(DatabaseHelper.Palestra.TABELA, null, values);
		
		ministracaoValues.put(DatabaseHelper.Ministracao._ID, 0);
		ministracaoValues.put(DatabaseHelper.Ministracao.DATA, dateTest);
		ministracaoValues.put(DatabaseHelper.Ministracao.PALESTRA_ID, 0);
		db.insert(DatabaseHelper.Ministracao.TABELA, null, ministracaoValues);
		
		ministracaoValues.put(DatabaseHelper.Ministracao._ID, 1);
		ministracaoValues.put(DatabaseHelper.Ministracao.DATA, dateTest);
		ministracaoValues.put(DatabaseHelper.Ministracao.PALESTRA_ID, 0);
		db.insert(DatabaseHelper.Ministracao.TABELA, null, ministracaoValues);
		
		values.put(DatabaseHelper.Palestra._ID, 1);
		values.put(DatabaseHelper.Palestra.NOME, "Minicurso Arduino");
		db.insert(DatabaseHelper.Palestra.TABELA, null, values);
		
		ministracaoValues.put(DatabaseHelper.Ministracao._ID, 2);
		ministracaoValues.put(DatabaseHelper.Ministracao.DATA, dateTest);
		ministracaoValues.put(DatabaseHelper.Ministracao.PALESTRA_ID, 1);
		db.insert(DatabaseHelper.Ministracao.TABELA, null, ministracaoValues);
		
		values.put(DatabaseHelper.Palestra._ID, 2);
		values.put(DatabaseHelper.Palestra.NOME, "Introdução Ruby");
		db.insert(DatabaseHelper.Palestra.TABELA, null, values);
		
		ministracaoValues.put(DatabaseHelper.Ministracao._ID, 3);
		ministracaoValues.put(DatabaseHelper.Ministracao.DATA, dateTest);
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

	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
