package trainedge.cashtrack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static trainedge.cashtrack.Constants.HAS_SALARY;
import static trainedge.cashtrack.Constants.MY_PREFS;

public class EditSalaryActivity extends AppCompatActivity {

    private EditText etBudget;
    private SharedPreferences expPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        expPref = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        etBudget = (EditText) findViewById(R.id.etBudget);
        Button btnSave = (Button) findViewById(R.id.btnSave);
        float budget = expPref.getFloat(Constants.BUDGET, 0.0f);
        if (budget > 0) {
            etBudget.setText(String.valueOf(budget));
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToAppPref(etBudget.getText().toString());
            }
        });
    }

    private void saveToAppPref(String budget) {
        if (budget.isEmpty()) {
            etBudget.setError("Fill your Salary here");
            return;
        }
        expPref.edit().putFloat(Constants.BUDGET, Float.parseFloat(budget)).putBoolean(HAS_SALARY,true).apply();
        Toast.makeText(this, "saved your monthly salary", Toast.LENGTH_SHORT).show();
        Intent gotoSalary = new Intent(EditSalaryActivity.this, ListActivity.class);
        startActivity(gotoSalary);
        finish();
    }

}
