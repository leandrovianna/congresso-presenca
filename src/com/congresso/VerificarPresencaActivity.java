package com.congresso;

import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import jim.h.common.android.zxinglib.integrator.IntentResult;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.congresso.dao.ParticipacaoDAOImpl;
import com.congresso.model.Ministracao;
import com.congresso.model.Participacao;

public class VerificarPresencaActivity extends Activity implements OnClickListener {

	private AlertDialog dialogConfirmacao;

	private EditText etInscricao;
	private TextView tvNome, tvPalestra;
	private ImageButton btValidar;
	private LinearLayout fundoBusca;
	

	private Participacao participacao;
	private Ministracao ministracao;
	private ParticipacaoDAOImpl dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verificar_presenca);
		
		ministracao = (Ministracao) getIntent().getSerializableExtra(ListaPalestrasActivity.EXTRA_MINISTRACAO);

		if (ministracao != null) {
			dialogConfirmacao = constroiDialogoConfirmacao();

			dao = new ParticipacaoDAOImpl(this);
			participacao = new Participacao();

			etInscricao = (EditText) findViewById(R.id.et_inscricao);
			tvNome = (TextView) findViewById(R.id.tv_nome);
			btValidar = (ImageButton) findViewById(R.id.btValidar);
			tvPalestra = (TextView) findViewById(R.id.tvPalestra);
			fundoBusca = (LinearLayout) findViewById(R.id.fundoBusca);
		
			
						
			tvPalestra.setText(ministracao.getPalestra().getNome());
			btValidar.setEnabled(false);
			limparBusca();
		}
	}

	
	
	// MÉTODO QUE BUSCA QR-CODE EM COMPONENTE
	public void qr(View v) {
		IntentIntegrator.initiateScan(this, 
									  R.layout.qrcode_reader_layout, 
									  R.id.viewfinder_view, 
									  R.id.preview_view, 
									  true);
	}

	
	// RETORNO DO COMPONENTE DE LEITURA DO QR-CODE
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

		if (result != null) {

			String textoQr = result.getContents();
			
			try {
				int numeroInscricao = Integer.parseInt(textoQr);
				etInscricao.setText(numeroInscricao+"");
				buscarInscrito(null);
			} catch (NumberFormatException e) {
				etInscricao.setText("");
			}
		}
	}

	
	
	
	// MÉTODO PARA BUSCAR UMA INSCRICAO
	public void buscarInscrito (View v) {
		
		if (etInscricao.getText().length() != 0) {
			
			int inscricao = Integer.parseInt(etInscricao.getText().toString());

			participacao = dao.buscarParticipacaoPorInscricaoMinistracao(inscricao, ministracao);
	
			if (participacao != null) {
				
				tvNome.setText(participacao.getParticipante().getNome());				
				btValidar.setEnabled(true);
				fundoBusca.setBackgroundColor(Color.rgb(168, 207, 96));
																			
			} else {
				
				tvNome.setText("Inscrição não encontrada.");
				btValidar.setEnabled(false);
				fundoBusca.setBackgroundColor(Color.rgb(248, 172, 146));
				
			}
		}else{
			Toast.makeText(this, "Digite uma inscrição válida", Toast.LENGTH_LONG).show();
			limparBusca();
		}
	}


	
	// MÉTODO PARA VALIDAR A PRESENÇA 
	public void validarPresenca (View v) {
		//chamando dialogo de confirmaÃ§Ã£o da presenÃ§a
		dialogConfirmacao.show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		
		if (dialog == dialogConfirmacao && which == DialogInterface.BUTTON_POSITIVE) {
			participacao.setPresenca(true);
			boolean sucesso = dao.updateParticipacao(participacao);
			
			if (sucesso) {
				Toast.makeText(this,"Presença registrada com sucesso.", Toast.LENGTH_LONG).show();				
				//reiniciando os valores na interface
				limparBusca();
			}
			else
				mostrarDialogoMensagem("Oops! Ocorreu um erro na gravação da presença.");					
		}

	}

	
	
	private void mostrarDialogoMensagem(String message) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setTitle("Aviso");
		builder.setMessage(message);
		builder.setNeutralButton("OK", null);
		
		builder.show();
	}

	private AlertDialog constroiDialogoConfirmacao() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Confirmação");
		builder.setMessage("O aluno está presente?");

		builder.setPositiveButton("Confirmar", this);

		builder.setNegativeButton("Cancelar", this);

		return builder.create();		
	}
	
	
	private void limparBusca(){
		tvNome.setText("");
		btValidar.setEnabled(false);
		etInscricao.setText("");
		fundoBusca.setBackgroundColor(Color.rgb(210, 211, 213));
	}
	
	

}
