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

public class ImportarDadosActivity extends Activity {

	private ProgressBar progressBar;
	private TextView tvLink;
	private Button btImportar;
	private EditText etLink;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_importar_dados);
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		tvLink = (TextView) findViewById(R.id.tv_link);
		btImportar = (Button) findViewById(R.id.bt_importar);
		etLink = (EditText) findViewById(R.id.et_link);
		
		progressBar.setVisibility(View.INVISIBLE);
	}
	
	public void loadFromServer(View v) {
//		tvLink.setVisibility(View.INVISIBLE);
//		btImportar.setVisibility(View.INVISIBLE);
//		etLink.setVisibility(View.INVISIBLE);
		progressBar.setVisibility(View.VISIBLE);

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
