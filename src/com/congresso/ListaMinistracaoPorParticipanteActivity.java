package com.congresso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleAdapter;

import com.congresso.dao.ParticipacaoDAOImpl;
import com.congresso.model.Participacao;
import com.congresso.model.Participante;

public class ListaMinistracaoPorParticipanteActivity extends ListActivity {

	private ParticipacaoDAOImpl dao;
	private List<Participacao> participacoes;
	private Participante participante;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		participante = (Participante) 
				getIntent().getSerializableExtra(SelecionarParticipanteActivity.EXTRA_PARTICIPANTE);
		
		if (participante != null) {
			dao = new ParticipacaoDAOImpl(this);
			
			participacoes = dao.listarParticipacoesPorParticipante(participante);
			
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
			
			for (Participacao participacao : participacoes) {
				Map<String, String> item = new HashMap<String, String>();
				
				item.put("data", 
						DateFormat.format("dd/MM/yyyy", participacao.getMinistracao().getData()).toString());
				item.put("nome palestra", 
						participacao.getMinistracao().getPalestra().getNome());
				
				data.add(item);
			}
			
			String[] from = new String[]{"data", "nome palestra"};
			int[] to = new int[]{R.id.tv_data_ministracao, R.id.tv_nome_palestra};
			int resource = R.layout.activity_lista_ministracao_por_participante;
			
			SimpleAdapter adapter = new SimpleAdapter(this, data, resource, from, to);

			setListAdapter(adapter);
			
			setTitle(participante.getNome());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_ministracao_por_participante,
				menu);
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
