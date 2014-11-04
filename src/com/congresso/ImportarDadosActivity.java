package com.congresso;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.congresso.httpClient.GetHttpClientTask;
import com.congresso.httpClient.HttpClientListener;
import com.congresso.serverModel.Evento;
import com.google.gson.Gson;

public class ImportarDadosActivity extends Activity implements HttpClientListener {

	private ProgressBar progressBar;
	private TextView tvLink;
	private Button btImportar;
	private EditText etLink;

	private GetHttpClientTask getHttpTask;
	private final String link = "http://intranet.ifg.edu.br/eventos/admin/congresso.json";
	
	private Gson gson;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_importar_dados);
		
		getHttpTask = new GetHttpClientTask();
		getHttpTask.addHttpClientListener(this);
		
		gson = new Gson();
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		tvLink = (TextView) findViewById(R.id.tv_link);
		btImportar = (Button) findViewById(R.id.bt_importar);
		etLink = (EditText) findViewById(R.id.et_link);
		
		ativarTelaNormal();
	}
	
	public void loadFromServer(View v) {
		ativarTelaCarregamento();
		
		getHttpTask.execute(link);
	}

	@Override
	public void updateHttpClientListener(String result) {
		ativarTelaNormal();

		Evento evento = gson.fromJson(result, Evento.class);

	}
	
	private void ativarTelaCarregamento() {
		tvLink.setVisibility(View.INVISIBLE);
		btImportar.setVisibility(View.INVISIBLE);
		etLink.setVisibility(View.INVISIBLE);
		progressBar.setVisibility(View.VISIBLE);
	}
	
	private void ativarTelaNormal() {
		tvLink.setVisibility(View.VISIBLE);
		btImportar.setVisibility(View.VISIBLE);
		etLink.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.INVISIBLE);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.importar_dados, menu);
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
