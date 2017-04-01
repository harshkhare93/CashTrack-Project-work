package trainedge.cashtrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnlogin;
    private TextView forgotpassword;
    private TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        forgotpassword = (TextView) findViewById(R.id.txtforgotPassword);
        signup = (TextView) findViewById(R.id.txtSingnup);
        signup.setOnClickListener(this);
        btnlogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent signup=new Intent(LoginActivity.this,SignupActivity.class);
        startActivity(signup);
        Intent login=new Intent(LoginActivity.this,List.class);
        startActivity(login);
    }
}
