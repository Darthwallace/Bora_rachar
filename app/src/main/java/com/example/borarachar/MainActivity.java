package com.example.borarachar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText editTextValor, editTextQuantidadePessoas;
    Button buttonCalcular;
    TextView textViewResultado;

    Button buttonCompartilhar;

    Button buttonFalar;

    private TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextValor = findViewById(R.id.editTextValor);
        editTextQuantidadePessoas = findViewById(R.id.editTextQuantidadePessoas);
        buttonCalcular = findViewById(R.id.buttonCalcular);
        textViewResultado = findViewById(R.id.textViewResultado);
        buttonCompartilhar = findViewById(R.id.buttonCompartilhar);
        buttonFalar = findViewById(R.id.buttonFalar);



        buttonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double valor = Double.parseDouble(editTextValor.getText().toString());
                int quantidadePessoas = Integer.parseInt(editTextQuantidadePessoas.getText().toString());
                double divisao = valor / quantidadePessoas;
                textViewResultado.setText("Cada pessoa deve pagar R$" + divisao);
            }
        });


        buttonCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultado = textViewResultado.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, resultado);
                startActivity(Intent.createChooser(intent, "Compartilhar via"));
            }

        });
            buttonFalar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultado = textViewResultado.getText().toString();
                falarMensagem(resultado);
                }
                private void falarMensagem(String mensagem) {
                    tts.speak(mensagem, TextToSpeech.QUEUE_FLUSH, null);
                }
        });
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(new Locale("pt", "BR"));
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Este idioma não é suportado");
                    }
                } else {
                    Log.e("TTS", "Falha ao inicializar o TextToSpeech");
                }
            }
        });
    }


}
