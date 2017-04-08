package trainedge.cashtrack;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.renderscript.Int2;

/**
 * Created by Hi ! HARSH on 08-Apr-17.
 */

public class ExpenseDatabaseAdapter {
    //Table name
    private static final String Expense_Table = "expenses";
    // Table id
    public static final String COL_ID = "_id";
    //Table names and columns
    public static final String COL_CATEGORY = "category";
    public static final String COL_TITLE = "title";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_DAY = "day";
    public static final String COL_MONTH = "month";
    public static final String COL_YEAR = "year";
    public static final String COL_HOUR = "hour";
    public static final String COL_MINUTE = "minute ";


    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;


    public ExpenseDatabaseAdapter(Context context) {
        this.context = context;
    }

    public ExpenseDatabaseAdapter open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    /**
     * Closes the database.
     */
    public void close() {
        dbHelper.close();
    }

    //Add Expense Method
    public void Addexpense() {

    }

    // Edit Expense
    public void EditExpense() {

    }

    //Calculate Total Expenditure
    public void CalculateTotalExpenditure() {

    }
    //Get All Expenses
    public void GetAllExpense(){

    }
    //Get Expense By Date
    public void  GetExpenseByDate(){

    }
    //Get Expense By Category
    public void GetExpenseByCategory(){

    }
    //Get Expense In Range
    public void GetExpenseInRange(){

    }
    //Delete Expense
    public void DeleteExpense(){

    }
    //Search Expenses By Id
    public  void searchExpenseById(){

    }


}
