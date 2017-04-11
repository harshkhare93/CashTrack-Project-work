package trainedge.cashtrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalenderActivity extends AppCompatActivity {

    public static final String DAY = "trainedge.cashtrack.day";
    public static final String MONTH = "trainedge.cashtrack.month";
    public static final String YEAR = "trainedge.cashtrack.year";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        final Button btnContinue = (Button) findViewById(R.id.btnContinue);
        final CalendarView cvExpenses = (CalendarView) findViewById(R.id.cvExpenses);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date selectedDate = new Date(cvExpenses.getDate());
                Date currentDate = new Date(System.currentTimeMillis());
                if (selectedDate.before(currentDate)) {
                    moveToNext(selectedDate);
                } else {
                    Toast.makeText(CalenderActivity.this, "Select a past date", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void moveToNext(Date selectedDate) {

        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra(DAY,selectedDate.getDay());
        intent.putExtra(MONTH,selectedDate.getMonth());
        intent.putExtra(YEAR,selectedDate.getYear());
        startActivity(intent);
    }
}
