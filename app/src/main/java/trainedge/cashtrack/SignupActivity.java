package trainedge.cashtrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner;
    private EditText phoneno;
    private EditText email;
    private EditText name;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = (EditText) findViewById(R.id.edtname);
        email = (EditText) findViewById(R.id.edtemail);
        phoneno = (EditText) findViewById(R.id.edtphoneno);

        signup = (Button) findViewById(R.id.btnsignup);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent signup=new Intent(SignupActivity.this,LoginActivity.class);
        Toast.makeText(this, "Account Created CashTrack is here for you", Toast.LENGTH_LONG).show();
        startActivity(signup);
    }
}
