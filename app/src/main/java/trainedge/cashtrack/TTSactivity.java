package trainedge.cashtrack;

import android.content.SharedPreferences;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Locale;

public class TTSactivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private SharedPreferences pref;
    private TextToSpeech engine;
    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttsactivity);
        pref = getSharedPreferences(Constants.SETTING_PREF, MODE_PRIVATE);
        boolean isTTSenabled = pref.getBoolean(Constants.KEY_TTS, true);
        if (getIntent().hasExtra("trainedge.cashtrack.EXTRA_MSG")) {
            msg = getIntent().getStringExtra("trainedge.cashtrack.EXTRA_MSG");
            if (isTTSenabled) {
                engine = new TextToSpeech(this, this);
            }
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
            finish();
        }
    }

}
