package com.congresso;

import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import jim.h.common.android.zxinglib.integrator.IntentResult;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.congresso.dao.ParticipacaoDAOImpl;

public class ActivityVerificarPresenca extends Activity implements OnClickListener {

	private AlertDialog confirmacao;
	
	private EditText etInscrito;
	private TextView tvNome;
	
	private Participacao participacao;
	private ParticipacaoDAOImpl dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verificar_presenca);
		
		String id = getIntent().getStringExtra(ActivityListaPalestras.EXTRA_MINISTRACAO_ID);
		
		if (id != null) {
			etInscrito = (EditText) findViewById(R.id.et_inscricao);
			tvNome = (TextView) findViewById(R.id.tv_nome);
			
			
			
			tvNome.setText(id);
		}
			
	}
	
	public void qr(View v) {
		
		IntentIntegrator.initiateScan(this, R.layout.qrcode_reader_layout, 
				R.id.viewfinder_view, R.id.preview_view, true);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		IntentResult result = 
				IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
		
		if (result != null) {
			
			String textoQr = result.getContents();
			
			etInscrito.setText(textoQr);
		}
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

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		
	}
}
