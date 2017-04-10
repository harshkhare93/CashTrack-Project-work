package trainedge.cashtrack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class AddExpenseActivity extends AppCompatActivity {

    private Spinner spinCategory;
    private EditText etExpenseTitle;
    private EditText etExpAmount;
    private Button btnDatePick;
    private ExpenseDatabaseAdapter dbAdapter;
    private boolean error;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbAdapter = new ExpenseDatabaseAdapter(this).open();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExpense();


            }
        });

        spinCategory = (Spinner) findViewById(R.id.spinCategory);
        etExpenseTitle = (EditText) findViewById(R.id.etExpenseTitle);
        etExpAmount = (EditText) findViewById(R.id.etExpAmount);
        btnDatePick = (Button) findViewById(R.id.btnDatePick);
        btnDatePick.setText(Calendar.getInstance().get(Calendar.YEAR) + "/"
                + Calendar.getInstance().get(Calendar.MONTH) + "/"
                + Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
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
        error = false;
        String expensetitle = etExpenseTitle.getText().toString();
        String expamount = etExpAmount.getText().toString();
        if (expensetitle.isEmpty() || expensetitle.length() < 3) {
            etExpenseTitle.setError("Required valid Expense Title");
            error = true;
        }
        String amount = etExpAmount.getText().toString();
        if (amount.isEmpty() || expamount.length() < 0) {
            etExpAmount.setError("Required valid amount ");
            error = true;
        }
        ExpenseDatabaseAdapter dbAdapter = new ExpenseDatabaseAdapter(this).open();
        String category = getResources().getStringArray(R.array.categories)[spinCategory.getSelectedItemPosition()];
        long rowId = dbAdapter.addExpense(category, expensetitle, Double.parseDouble(expamount));
        if (rowId!=-1) {
            doAwesomeAnimation();
        }else{
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
        }

    }

    private void doAwesomeAnimation() {
        fab.animate().rotationY(360).setDuration(500).setInterpolator(new AccelerateInterpolator()).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Toast.makeText(AddExpenseActivity.this, "saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
