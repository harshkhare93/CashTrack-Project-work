package trainedge.cashtrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;

import io.blackbox_vision.materialcalendarview.view.CalendarView;

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
        cvExpenses.setOnDateClickListener(new CalendarView.OnDateClickListener() {
            @Override
            public void onDateClick(@NonNull Date date) {
                Date currentDate = new Date(System.currentTimeMillis());
                if (date.before(currentDate)) {
                    moveToNext(date);
                } else {
                    Toast.makeText(CalenderActivity.this, "Select a past date", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void moveToNext(Date selectedDate) {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("passdate","Date_var_here");
        intent.putExtra(DAY, selectedDate.getDay());
        intent.putExtra(MONTH, selectedDate.getMonth());
        intent.putExtra(YEAR, selectedDate.getYear());
        CalenderActivity.this.startActivity(intent);

    }
}
