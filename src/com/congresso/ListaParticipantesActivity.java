package com.congresso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleAdapter;

import com.congresso.dao.ParticipacaoDAOImpl;
import com.congresso.model.Ministracao;
import com.congresso.model.Participacao;

public class ListaParticipantesActivity extends ListActivity {

	private ParticipacaoDAOImpl dao;
	private Ministracao m;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dao = new ParticipacaoDAOImpl(this);
		m = (Ministracao) getIntent().getSerializableExtra(VerificarPresencaActivity.EXTRA_MINISTRACAO);
		
		if (m != null) {
			List<Participacao> participacoes = dao.listarParticipacoesPorMinistracao(m);
			
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
			
			for (Participacao participacao : participacoes) {
				Map<String, String> item = new HashMap<String, String>();
				
				item.put("inscricao", ""+participacao.getParticipante().getInscricao());
				item.put("nome", participacao.getParticipante().getNome());
				
				data.add(item);
			}
			
			String[] from = new String[]{"inscricao", "nome"};
			int[] to = new int[]{R.id.tv_inscricao_numero, R.id.tv_nome_participante};
			int resource = R.layout.activity_lista_participantes;
			
			SimpleAdapter adapter = new SimpleAdapter(this, data, resource, from, to);

			setListAdapter(adapter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_participantes, menu);
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
