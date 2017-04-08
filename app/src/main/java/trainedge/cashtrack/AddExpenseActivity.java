package trainedge.cashtrack;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

public class AddExpenseActivity extends AppCompatActivity {

    private Spinner spinCategory;
    private EditText etExpenseTitle;
    private EditText etExpAmount;
    private Button btnDatePick;
    private ExpenseDatabaseAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbAdapter = new ExpenseDatabaseAdapter(this).open();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExpense();
                doAwesomeAnimation();

            }
        });

        spinCategory = (Spinner) findViewById(R.id.spinCategory);
        etExpenseTitle = (EditText) findViewById(R.id.etExpenseTitle);
        etExpAmount = (EditText) findViewById(R.id.etExpAmount);
        btnDatePick = (Button) findViewById(R.id.btnDatePick);
        btnDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                btnDatePick.setText("" + dayOfMonth + "/" + month + "/" + year);
            }
        }, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void addExpense() {
        // // TODO: 09-04-2017 validations
    }

    private void doAwesomeAnimation() {
        // TODO: 09-04-2017 my work

    }

}
