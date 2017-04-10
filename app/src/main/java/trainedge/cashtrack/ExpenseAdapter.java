package trainedge.cashtrack;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import trainedge.cashtrack.models.ExpenseModel;

/**
 * Created by xaidi on 11-04-2017.
 */

class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseHolder> {
    private final List<ExpenseModel> expenseList;

    //we also need a constructor for this example
    //alt insert to add constructor


    public ExpenseAdapter(List<ExpenseModel> expenseList) {
        this.expenseList = expenseList;
    }

    @Override
    public ExpenseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = ((LayoutInflater) parent.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.simple_expense_item, parent, false);
        return new ExpenseHolder(row);
    }

    @Override
    public void onBindViewHolder(ExpenseHolder holder, int position) {
        //databinding
        ExpenseModel model = expenseList.get(position);
        holder.tvTitle.setText(model.getTitle());
        holder.tvAmt.setText(String.valueOf(model.getAmt()));
        holder.tvDate.setText(model.getDay() + "/" + model.getMonth() + "/" + model.getYear());
        holder.tvCategory.setText(model.getCategory());
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ExpenseHolder extends RecyclerView.ViewHolder {
        TextView tvAmt;
        TextView tvCategory;
        TextView tvTitle;
        TextView tvDate;

        public ExpenseHolder(View itemView) {
            super(itemView);
            tvAmt = (TextView) itemView.findViewById(R.id.tvAmt);
            tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        }
    }
}
