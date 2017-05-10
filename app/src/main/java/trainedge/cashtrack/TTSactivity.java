package trainedge.cashtrack;

import android.content.SharedPreferences;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class TTSactivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private SharedPreferences pref;
    private TextToSpeech engine;
    private String msg;
    private TextView tvReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttsactivity);
        tvReminder = (TextView) findViewById(R.id.tvReminder);
        pref = getSharedPreferences(Constants.SETTING_PREF, MODE_PRIVATE);
        boolean isTTSenabled = pref.getBoolean(Constants.KEY_TTS, true);
        if (getIntent().hasExtra("trainedge.cashtrack.EXTRA_MSG")) {
            msg = getIntent().getStringExtra("trainedge.cashtrack.EXTRA_MSG");
            tvReminder.setText("Reminder " + msg);
            if (isTTSenabled) {
                engine = new TextToSpeech(this, this);
                launchTTS(msg);
            }
        }

    }

    private void launchTTS(String msg) {

        engine = new TextToSpeech(getApplicationContext(), this);
        int speak = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            speak = engine.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            engine.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
        }
        if (speak == TextToSpeech.SUCCESS) {
            finish();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            engine.setLanguage(Locale.ENGLISH);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                engine.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                engine.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

}
