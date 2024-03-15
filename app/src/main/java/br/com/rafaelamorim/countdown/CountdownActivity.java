package br.com.rafaelamorim.countdown;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import android.os.Bundle;

public class CountdownActivity extends AppCompatActivity {

    private TextView countdownText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        countdownText = findViewById(R.id.countdown_text);

        // Defina o tempo para a contagem regressiva em milissegundos (aqui definido como 10 segundos)
        long countdownTime = 10000;

        // Inicie a tarefa assíncrona para a contagem regressiva
        new CountdownTask().execute(countdownTime);
    }

    // Classe interna AsyncTask para executar a contagem regressiva em segundo plano
    private class CountdownTask extends AsyncTask<Long, Long, Void> {

        // Método principal que executa o trabalho em segundo plano
        @Override
        protected Void doInBackground(Long... params) {
            long countdownTime = params[0];

            while (countdownTime > 0) {
                publishProgress(countdownTime); // Atualize a UI com o tempo restante
                try {
                    Thread.sleep(1000); // Aguarde 1 segundo
                    countdownTime -= 1000; // Subtraia 1 segundo do tempo restante
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        // Método chamado no thread principal para atualizar a UI com o tempo restante
        @Override
        protected void onProgressUpdate(Long... values) {
            long timeRemaining = values[0];
            countdownText.setText("Tempo Restante: " + (timeRemaining / 1000) + " segundos");
        }

        // Método chamado após a conclusão do trabalho em segundo plano
        @Override
        protected void onPostExecute(Void result) {
            countdownText.setText("Contagem Regressiva Concluída");
            Toast.makeText(CountdownActivity.this, "Contagem Regressiva Concluída", Toast.LENGTH_SHORT).show();
        }
    }
}