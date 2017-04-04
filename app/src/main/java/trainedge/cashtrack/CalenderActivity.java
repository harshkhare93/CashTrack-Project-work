package trainedge.cashtrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import java.util.Calendar;
import java.util.Date;

public class CalenderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        final Button btnContinue = (Button) findViewById(R.id.btnContinue);
        final CalendarView cvExpenses = (CalendarView) findViewById(R.id.cvExpenses);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long selectedDate = cvExpenses.getDate();
                long currentDate = System.currentTimeMillis();
                if (selectedDate > currentDate) {
                    Snackbar.make(btnContinue, "Select a previous date", BaseTransientBottomBar.LENGTH_LONG).show();
                } else {
                    Intent historyIntent = new Intent(CalenderActivity.this, HistoryActivity.class);
                    historyIntent.putExtra("trainedge.cashtrack.KEY_DATE", selectedDate);
                }
            }
        });

    }
}
