package bakingreciepes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alfa.bakingreciepes.*;

import java.util.ArrayList;

import bakingreciepes.Data.Cook;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BakingAdapter extends RecyclerView.Adapter<BakingAdapter.BakingViewHolder> {
    private ArrayList<Cook> bakingList;
    final private ListItemClickListener mClickHandler;


    public interface ListItemClickListener {
        void onClick(int position);
    }

    BakingAdapter( ListItemClickListener clickHandler) {
        mClickHandler = clickHandler;
    }


    @NonNull
    @Override
    public BakingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int listLayoutId = R.layout.card_view_item;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(listLayoutId, parent, false);
        return new BakingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BakingViewHolder holder, int position) {
        holder.mTextView.setText(bakingList.get(position).getName());

    }


    @Override
    public int getItemCount() {
        if(bakingList !=null)
        return bakingList.size();
        else return 0;
    }

    public class BakingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Nullable
        @BindView(R.id.bakingName)
        TextView mTextView;

        BakingViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mClickHandler.onClick(position);
        }

    }

    public void loadData(ArrayList<Cook> bakingList) {
        this.bakingList = bakingList;

    }
}
