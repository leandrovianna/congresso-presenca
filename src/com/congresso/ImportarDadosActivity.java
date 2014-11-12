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
import android.widget.Toast;

import com.congresso.httpClient.GetHttpClientTask;
import com.congresso.httpClient.HttpClientListener;
import com.congresso.httpClient.InternetCheck;
import com.congresso.serverModel.ImportadoraDados;
import com.congresso.serverModel.ImportadoraDadosTask;

public class ImportarDadosActivity extends Activity implements HttpClientListener {

	private ProgressBar progressBar;
	private TextView tvLink;
	private TextView tvAguarde;
	private Button btImportar;
	private EditText etLink;

	private GetHttpClientTask getHttpTask;
	private final String link = "http://intranet.ifg.edu.br/eventos/admin/dadosjson.php";
	
	private ImportadoraDados importadora;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_importar_dados);
		
		getHttpTask = new GetHttpClientTask();
		getHttpTask.addHttpClientListener(this);
		
		importadora = new ImportadoraDados(this);
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		tvLink      = (TextView) findViewById(R.id.tv_link);
		tvAguarde   = (TextView) findViewById(R.id.tv_aguarde);
		btImportar  = (Button) findViewById(R.id.bt_importar);
		etLink      = (EditText) findViewById(R.id.et_link);
		
		etLink.setText(link);
		
		ativarTelaNormal();
	}
	
	public void loadFromServer(View v) {
		
		if(InternetCheck.isConnected(this)){
			ativarTelaCarregamento();
			tvAguarde.setText("Importando dados de " + etLink.getText().toString());
			getHttpTask.execute(etLink.getText().toString());
		}else{
			Toast.makeText(this, getString(R.string.internet_erro), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void updateHttpClientListener(String result) {
		
		if (!result.equals(null)){
			Toast.makeText(this, "Os dados foram importados com sucesso!", Toast.LENGTH_SHORT).show();
			tvAguarde.setText("Gravando no banco de dados local... essa operação pode levar alguns minutos");
			
			ImportadoraDadosTask taskUpdate = new ImportadoraDadosTask(this);
			taskUpdate.execute(result);
			
			//boolean retorno = importadora.gravarDados(result);

		}else{
			Toast.makeText(this, "Oops! Os dados não foram importados.", Toast.LENGTH_SHORT).show();
		}
				
	}
	
	
	public void retornoGravacaoDados(Boolean retorno){
		
		if (retorno)
			tvAguarde.setText("Dados importados e gravados com sucesso!");
		else
			tvAguarde.setText("Oops! Os dados não foram gravados");			
		
		ativarTelaNormal();
		tvAguarde.setVisibility(View.VISIBLE);
		
	}
	
	
	
	
	private void ativarTelaCarregamento() {
		tvLink.setVisibility(View.INVISIBLE);
		btImportar.setVisibility(View.INVISIBLE);
		etLink.setVisibility(View.INVISIBLE);
		progressBar.setVisibility(View.VISIBLE);
		tvAguarde.setVisibility(View.VISIBLE);
	}
	
	private void ativarTelaNormal() {
		tvLink.setVisibility(View.VISIBLE);
		btImportar.setVisibility(View.VISIBLE);
		etLink.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.INVISIBLE);
		tvAguarde.setVisibility(View.INVISIBLE);
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
