package trainedge.cashtrack;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import trainedge.cashtrack.models.ExpenseModel;

import static trainedge.cashtrack.ExpenseDatabaseAdapter.COL_AMOUNT;
import static trainedge.cashtrack.ExpenseDatabaseAdapter.COL_CATEGORY;
import static trainedge.cashtrack.ExpenseDatabaseAdapter.COL_DAY;
import static trainedge.cashtrack.ExpenseDatabaseAdapter.COL_MONTH;
import static trainedge.cashtrack.ExpenseDatabaseAdapter.COL_TITLE;
import static trainedge.cashtrack.ExpenseDatabaseAdapter.COL_YEAR;

public class GraphActivity extends AppCompatActivity {

    private SharedPreferences expPref;
    private TextView tvAmtRemaining;
    private TextView tvTotalSalary;
    private BarChart barChart;
    private List<BarEntry> entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        expPref = getSharedPreferences("exp_pref", MODE_PRIVATE);
        tvAmtRemaining = (TextView) findViewById(R.id.tvRemainingAmount);

        barChart = (BarChart) findViewById(R.id.chart);

        entries = new ArrayList<BarEntry>();
        cursorToList();
    }

    private void cursorToList() {

        ExpenseDatabaseAdapter dbAdapter = new ExpenseDatabaseAdapter(this).open();
        Cursor cursor = dbAdapter.getAllExpense();
        float budget = expPref.getFloat("budget", 0.0f);
        dbAdapter.close();
        cursor = new ExpenseDatabaseAdapter(this).open().getAllExpense();

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                double total = 0;
                while (cursor.moveToNext()) {
                    double amt = cursor.getDouble(cursor.getColumnIndex(COL_AMOUNT));
                    String title = cursor.getString(cursor.getColumnIndex(COL_TITLE));
                    String category = cursor.getString(cursor.getColumnIndex(COL_CATEGORY));
                    int day = cursor.getInt(cursor.getColumnIndex(COL_DAY));
                    int month = cursor.getInt(cursor.getColumnIndex(COL_MONTH));
                    int year = cursor.getInt(cursor.getColumnIndex(COL_YEAR));
                    entries.add(new BarEntry((float) amt, month-1));
                }
                BarDataSet dataSet = new BarDataSet(entries, "Monthly expense");
                barChart.setData(new BarData(new String[]{"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"}, dataSet));
                barChart.invalidate();
            }
        } else {
            TextView tLabel = new TextView(this);

        }
    }



}
