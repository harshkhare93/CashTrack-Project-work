package trainedge.cashtrack;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.provider.ContactsContract.CommonDataKinds.Email.CONTENT_URI;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText edtName;
    private EditText edtEmail;
    private EditText edtPass;
    private EditText edtConPass;
    private EditText edtPhoneno;

    private EditText edtOccupation;
    private Button signup;


    private UserDatabaseAdapter dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        SharedPreferences settings = getSharedPreferences(Constants.MY_PREFS, 0);
        settings.edit().putLong(Constants.UID, 0).apply();


        edtName = (EditText) findViewById(R.id.edtname);
        edtEmail = (EditText) findViewById(R.id.edtemail);
        edtPass = (EditText) findViewById(R.id.edtpass);
        edtConPass = (EditText) findViewById(R.id.edtPassConfirm);
        edtPhoneno = (EditText) findViewById(R.id.edtphoneno);

        edtOccupation = (EditText) findViewById(R.id.etoccupation);
        dbHelper = new UserDatabaseAdapter(this);
        dbHelper.open();
        signup = (Button) findViewById(R.id.btnsignup);
        signup.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        boolean error = false;
        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        String confirmPass = edtConPass.getText().toString().trim();
        String phone = edtPhoneno.getText().toString().trim();
        String occupation = edtOccupation.getText().toString().trim();

        if (name.isEmpty() || name.length() < 3) {
            edtName.setError("Valid Username is required");
            error = true;
        }
        if (email.isEmpty() || email.length() < 3) {
            edtEmail.setError("Valid email is required");
            error = true;
        }
        if (pass.isEmpty() || (pass.length() < 6 && pass.length() > 25)) {
            edtPass.setError("Valid Password is required");
            error = true;
        }
        if (confirmPass.isEmpty() || confirmPass.length() < 3) {
            edtConPass.setError("Valid password is required");
            error = true;
        }
        if (phone.isEmpty() || phone.length() < 3) {
            edtPhoneno.setError("Valid phone number is required");
            error = true;
        }
        if (occupation.isEmpty() || occupation.length() < 3) {
            edtOccupation.setError("Valid occupation is required");
            error = true;
        }

        if (!confirmPass.equals(pass)) {
            edtConPass.setError("password should be same");
            error = true;
        }
        //final
        if (!error) {
            savetoDB(name, pass, email, phone, occupation);
        }
    }

    private void savetoDB(String name, String pass, String email, String phone, String occupation) {

        //Create the new username.
        long id = dbHelper.createUser(name, pass, email, phone, occupation);
        if (id > 0) {
            saveLoggedInUId(id, email, pass, phone);
            Toast.makeText(this, "you are registered ! login to continue", Toast.LENGTH_SHORT).show();
            BackToLogin();
        } else {
            Snackbar.make(signup, "Failed to create new username", Snackbar.LENGTH_SHORT).show();
        }
        ClearForm();

    }


    private void saveLoggedInUId(long id, String email, String password, String phone) {
        SharedPreferences settings = getSharedPreferences(Constants.MY_PREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(Constants.UID, id);
        editor.putString(Constants.EMAIL, email);
        editor.putString(Constants.PASSWORD, password);
        editor.putString(Constants.NUMBER, phone);
        editor.putBoolean(Constants.STATE, false);
        editor.apply();
    }

    /**
     * Clears the registration fields.
     */
    private void ClearForm() {

        edtPass.setText("");

        edtOccupation.setText("");
        edtEmail.setText("");
        edtName.setText("");
        edtConPass.setText("");
        edtPhoneno.setText("");
    }

    /**
     * Takes user back to login.
     */
    private void BackToLogin() {
        finish();
    }

}
