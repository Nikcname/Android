package user.mobile.zeyneddin.zeyneddin.main.sqlite.recycleView;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import user.mobile.zeyneddin.zeyneddin.R;
import user.mobile.zeyneddin.zeyneddin.main.sqlite.SqliteActivity;

public class RecycleView extends RecyclerView.Adapter<RecycleView.ViewHolder> {

    private List<String> mDatasetProductName;
    private List<String> mDatasetProductAmount;
    private List<String> mDatasetProductPrice;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ConstraintLayout mView;

        public ViewHolder(ConstraintLayout v){
            super(v);
            mView = v;
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public RecycleView(List<String> myDatasetName,
                       List<String> myDatasetAmount,
                       List<String> myDatasrtPrice) {
        mDatasetProductName = myDatasetName;
        mDatasetProductAmount = myDatasetAmount;
        mDatasetProductPrice = myDatasrtPrice;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecycleView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(
                parent.getContext()).inflate(R.layout.element_recycle_database,
                parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView productName = holder.mView.findViewById(R.id.rvEleementOne);
        TextView productAmount = holder.mView.findViewById(R.id.rvEleementTwo);
        TextView productPrice = holder.mView.findViewById(R.id.rvEleementTree);

        productName.setText(mDatasetProductName.get(position));
        productAmount.setText(mDatasetProductAmount.get(position));
        productPrice.setText(mDatasetProductPrice.get(position));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mCallback.onClick(
//                        mDatasetProductName.get(position),
//                        mDatasetProductAmount.get(position),
//                        mDatasetProductPrice.get(position)
//                        );
                SqliteActivity.bus.post(new CallbackFromRecycleView(
                        mDatasetProductName.get(position),
                        mDatasetProductAmount.get(position),
                        mDatasetProductPrice.get(position)
                ));
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDatasetProductName.size();
    }
}
