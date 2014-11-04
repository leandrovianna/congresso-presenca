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
import android.widget.ImageButton;
import android.widget.TextView;

import com.congresso.dao.MinistracaoDAOImpl;
import com.congresso.dao.ParticipacaoDAOImpl;
import com.congresso.model.Ministracao;
import com.congresso.model.Participacao;

public class VerificarPresencaActivity extends Activity implements OnClickListener {

	private AlertDialog dialogConfirmacao;

	private EditText etInscricao;
	private TextView tvNome, tvPalestra;
	private ImageButton btValidar;

	private Participacao participacao;
	private Ministracao ministracao;
	private ParticipacaoDAOImpl dao;
	private MinistracaoDAOImpl daoM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verificar_presenca);

		String id = getIntent().getStringExtra(ListaPalestrasActivity.EXTRA_MINISTRACAO_ID);

		if (id != null) {
			dialogConfirmacao = constroiDialogoConfirmacao();

			dao = new ParticipacaoDAOImpl(this);
			daoM = new MinistracaoDAOImpl(this);
			participacao = new Participacao();
			ministracao = daoM.buscarMinistracaoPorId(Integer.parseInt(id));

			etInscricao = (EditText) findViewById(R.id.et_inscricao);
			tvNome = (TextView) findViewById(R.id.tv_nome);
			btValidar = (ImageButton) findViewById(R.id.bt_validar);
			tvPalestra = (TextView) findViewById(R.id.tvPalestra);

			tvPalestra.setText(ministracao.getPalestra().getNome());
			btValidar.setEnabled(false);
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
			
			try {
				int numeroInscricao = Integer.parseInt(textoQr);
				etInscricao.setText(numeroInscricao+"");
				buscarInscrito(null);
			} catch (NumberFormatException e) {
				etInscricao.setText("");
			}
		}
	}

	public void buscarInscrito (View v) {
		
		if (etInscricao.getText().length() != 0) {
			
			int inscricao = Integer.parseInt(etInscricao.getText().toString());

			participacao = dao.buscarParticipacaoPorInscricaoMinistracao(
					inscricao, ministracao);
	
			if (participacao != null) {
				
				tvNome.setText(participacao.getParticipante().getNome());
				btValidar.setEnabled(true);
				
			} else {
				
				tvNome.setText("Inscrito não encontrado.");
				btValidar.setEnabled(false);
			}
		}
	}

	public void validarPresenca (View v) {
		//chamando dialogo de confirmação da presença
		dialogConfirmacao.show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		
		if (dialog == dialogConfirmacao && which == DialogInterface.BUTTON_POSITIVE) {
			participacao.setPresenca(true);
			boolean sucesso = dao.updateParticipacao(participacao);
			
			if (sucesso) {
				mostrarDialogoMensagem("A presença foi gravada com sucesso.");
				
				//reiniciando os valores na interface
				tvNome.setText("");
				btValidar.setEnabled(false);
				etInscricao.setText("");
			}
			else
				mostrarDialogoMensagem("Oops! Ocorreu um erro na gravação da presença.");
			
			
		}

	}

}
