package trainedge.cashtrack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static trainedge.cashtrack.Constants.EMAIL;
import static trainedge.cashtrack.Constants.NUMBER;

public class ForgotActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences settings;
    private Button btnConfirm;
    private EditText etEmail;
    private EditText etContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etContact = (EditText) findViewById(R.id.etContact);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnConfirm.setOnClickListener(this);
        settings = getSharedPreferences(Constants.MY_PREFS, MODE_PRIVATE);

    }


    @Override
    public void onClick(View v) {
        if (settings.contains(EMAIL) && settings.contains(NUMBER)) {
            String email = etEmail.getText().toString();
            String contact = etContact.getText().toString();
            String savedEmail = settings.getString(EMAIL, "");
            String savedContact = settings.getString(NUMBER, "");

            if (email.equals(savedEmail) && contact.equals(savedContact)) {
                String savedPassword = settings.getString(Constants.PASSWORD, "");
                composeEmail(new String[]{savedEmail}, "Password recovery mail", savedPassword);

            }
        } else {
            Snackbar.make(etEmail, "You have not registered yet", Snackbar.LENGTH_INDEFINITE).show();
        }

    }

    public void composeEmail(String[] addresses, String subject, String msg) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT,  msg);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
