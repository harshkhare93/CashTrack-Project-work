package trainedge.cashtrack;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import io.blackbox_vision.materialcalendarview.view.CalendarView;

public class CalenderActivity extends AppCompatActivity {

    public static final String DAY = "trainedge.cashtrack.day";
    public static final String MONTH = "trainedge.cashtrack.month";
    public static final String YEAR = "trainedge.cashtrack.year";
    private int hour;
    private int minuteSel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        final CalendarView cvExpenses = (CalendarView) findViewById(R.id.cvExpenses);
        cvExpenses.setOnDateClickListener(new CalendarView.OnDateClickListener() {
            @Override
            public void onDateClick(@NonNull final Date date) {
                final Date currentDate = new Date(System.currentTimeMillis());
                if (date.compareTo(currentDate) <= 0) {
                    moveToNext(date);
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(CalenderActivity.this).setTitle("Schedule").setMessage("Do you want to schedule a task or event on this day").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            showAddEventDialog(date);
                        }
                    }).setNeutralButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
                    dialog.show();
                }
            }
        });

    }


    private void showTimePickerDialog(final String eventName, final Date selectedDate) {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                minuteSel = minute;
                AlarmReceiver alarmRec = new AlarmReceiver();
                alarmRec.SetAlarm(CalenderActivity.this, selectedDate, hourOfDay, minute, eventName);
            }
        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void showAddEventDialog(final Date date) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("add event");
        alertDialog.setMessage("enter an event or task");

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String eventName = input.getText().toString();
                        if (eventName.isEmpty()) {
                            input.setError("Cannot be empty");
                        } else {
                            showTimePickerDialog(eventName, date);

                        }
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener()

                {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }


    private void moveToNext(Date selectedDate) {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("passdate", "Date_var_here");
        intent.putExtra(DAY, selectedDate.getDate());
        intent.putExtra(MONTH, selectedDate.getMonth() + 1);
        intent.putExtra(YEAR, selectedDate.getYear() + 1900);
        startActivity(intent);
    }
}
