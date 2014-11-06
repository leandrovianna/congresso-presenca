package com.congresso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.congresso.httpClient.HttpClientListener;
import com.congresso.httpClient.PostHttpClientTask;
import com.congresso.serverModel.ExportadoraDados;

public class ExportarDadosActivity extends Activity implements HttpClientListener, OnClickListener {

	private ProgressBar progressBar;
	private Button btExportar;
	private TextView tvOutput;

	private final String link = "http://intranet.ifg.edu.br/eventos/admin/post.php";

	private ExportadoraDados exportadora;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exportar_dados);

		exportadora = new ExportadoraDados(this);

		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		btExportar = (Button) findViewById(R.id.bt_exportar);
		tvOutput = (TextView) findViewById(R.id.textOutput);

		btExportar.setOnClickListener(this);

		ativarPrimeiraTela();

	}

	public void exportarDados(View v) throws JSONException{

		ativarTelaCarregamento();

		PostHttpClientTask task = new PostHttpClientTask();
		task.addHttpClientListener(this);

		//gera o JSON
		JSONObject jObj = exportadora.getJsonEvento();

		//adiciona o JSON a task e a executa
		NameValuePair nameValuePair;
		nameValuePair = new BasicNameValuePair("participacoes", jObj.toString());
		task.addNameValuePair(nameValuePair);
		task.execute(link);

		//mostra conteudo do JSON para motivos de teste
		tvOutput.setText(" Json Exportado : " + nameValuePair.toString());

	}

	private void ativarTelaCarregamento() {
		btExportar.setVisibility(View.INVISIBLE);
		progressBar.setVisibility(View.VISIBLE);
		tvOutput.setVisibility(View.INVISIBLE);
	}

	private void ativarPrimeiraTela() {
		btExportar.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.INVISIBLE);
		tvOutput.setVisibility(View.INVISIBLE);
	}

	private void ativarSegundaTela() {
		btExportar.setVisibility(View.INVISIBLE);
		progressBar.setVisibility(View.INVISIBLE);
		tvOutput.setVisibility(View.VISIBLE);
	}

	@Override
	public void updateHttpClientListener(String result) {
		ativarSegundaTela();
		Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exportar_dados, menu);
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

	@Override
	public void onClick(View v) {
		try {
			exportarDados(v);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
