package com.congresso;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListaPalestras extends Activity {

	private ListView listView;
	private String[] itens;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_palestras);

		listView = (ListView)findViewById(R.id.lista);
		//designar elementos da lista
		itens = new String[]{
				" Evento A ",
				" Evento B ",
				" Evento C ",
				" Evento D ",
				" Evento E ",
				" Evento F ",
				" Evento G ",
				" Evento H ",
				" Evento I ",
				" Evento J "
		};
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1,itens);

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				String valorItem = (String) listView.getItemAtPosition(position);
				Toast.makeText(getApplicationContext(),"Evento selecionado: "+ valorItem + " id: " + id ,
						Toast.LENGTH_LONG).show();
				
				//Intent intent = new Intent(getApplicationContext(), ActivityPresenca.class);
				//intent.putExtra("id_palestra", id);
				//startActivity(intent);
				
			}
		}); 
	}
}
