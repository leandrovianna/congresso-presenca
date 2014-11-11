package com.congresso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.congresso.dao.MinistracaoDAOImpl;
import com.congresso.model.Ministracao;

public class ListaPalestrasActivity extends ListActivity {

	public static final String EXTRA_MINISTRACAO = "ministracao_object";
	private MinistracaoDAOImpl mDAO;
	private List<Ministracao> ministracoesHoje;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mDAO = new MinistracaoDAOImpl(this);
		ministracoesHoje = mDAO.listarMinistracoes();

		ArrayList<HashMap<String, String>> itens = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < ministracoesHoje.size(); i++) {
			HashMap<String, String> ministracoes = new HashMap<String, String>();
			ministracoes.put("nome", ministracoesHoje.get(i).getPalestra().getNome());
			itens.add(ministracoes);
		}

		String[] from = new String[]{"nome"};
		int[] to = new int[]{android.R.id.text1};
		int resource = android.R.layout.simple_selectable_list_item;

		SimpleAdapter adapter = new SimpleAdapter(this, itens, resource, from, to);

		setListAdapter(adapter);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_palestras, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.action_part_list:
			//por teste abrindo lista de participacoes
			startActivity(new Intent(this, ParticipacaoListActivity.class));
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent intent = new Intent(getApplicationContext(), VerificarPresencaActivity.class);
		intent.putExtra(EXTRA_MINISTRACAO, ministracoesHoje.get(position));
		startActivity(intent);
	}

}