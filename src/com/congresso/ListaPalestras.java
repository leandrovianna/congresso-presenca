package com.congresso;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ListaPalestras extends ListActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ArrayList<HashMap<String, String>> itens = getMinistracoesDoDia();

		String[] from = new String[]{"nome"};
		int[] to = new int[]{android.R.id.text1};
		int resource = android.R.layout.simple_selectable_list_item;

		SimpleAdapter adapter = new SimpleAdapter(this, itens, resource, from, to);

		setListAdapter(adapter);

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Toast.makeText(this, "Selecionou opção " + id , Toast.LENGTH_SHORT).show();

		//Intent intent = new Intent(getApplicationContext(), ActivityPresenca.class);
		//intent.putExtra("id_palestra", id);
		//startActivity(intent);
	}

	public static String milisParaData(String dataMilis,String formatoData) {
		return DateFormat.format(formatoData, Long.parseLong(dataMilis)).toString();
	}

	private static ArrayList<HashMap<String, String>> getMinistracoesDoDia(){

		ArrayList<HashMap<String, String>> itens = new ArrayList<HashMap<String, String>>();

		//Dados de teste, futuramente acessará o db para as informações
		String[] nomes = new String[]{
				"Minicurso Arduino",
				"Minicurso Desenvolvimento de Jogos",
				"Minicurso Introdução Ruby",
				"Palestra A",
				"Palestra B",
				"Palestra C",
				"Palestra D",
				"Palestra E",
				"Palestra F"
		};
		String hoje  = milisParaData(""+(System.currentTimeMillis()-86400000), "dd/MM/yyyy");
		String[] datas = new String[]{
				hoje,
				hoje,
				hoje,
				hoje,
				hoje,
				hoje,
				hoje,
				"10/10/2014",
				"10/10/2014"
		};

		for (int i = 0; i < nomes.length; i++) {
			HashMap<String, String> ministracoes = new HashMap<String, String>();
			if(datas[i]==hoje){
				ministracoes.put("nome", nomes[i]);
				itens.add(ministracoes);
			}
		}

		return itens;

	}
}
