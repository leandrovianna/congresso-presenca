package com.congresso;

import com.congresso.dao.ParticipacaoDAOImpl;

import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityVerificarPresenca extends Activity {

	private AlertDialog confirmacao;
	
	private EditText etInscrito;
	private TextView tvNome;
	
	private Participacao participacao;
	private ParticipacaoDAOImpl dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verificar_presenca);
		
		String id = getIntent().getStringExtra(ListaPalestras.EXTRA_MINISTRACAO_ID);
		
		if (id != null) {
			etInscrito = (EditText) findViewById(R.id.et_inscricao);
			tvNome = (TextView) findViewById(R.id.tv_nome);
			
			tvNome.setText(id);
		}
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_verificar_presenca, menu);
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
	
	public void qr () {
		
		IntentIntegrator.initiateScan(this, R.layout.qrcode_reader_layout, 
				R.id.viewfinder_view, R.id.preview_view, true);
	}
	
	public void buscarInscrito () {
	}
	
	public void checkPresenca () {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setTitle("Confirmação");
		builder.setMessage("O aluno está presente?");
		
		builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				// habilitar para confirmar a presenca				
			}
		});
		
		builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				// retornar, mas com os campos limpos	
			}
		});
		
		confirmacao = builder.create();
		confirmacao.show();
	}
}
