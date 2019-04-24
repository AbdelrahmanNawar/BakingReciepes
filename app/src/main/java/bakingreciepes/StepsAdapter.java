package bakingreciepes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alfa.bakingreciepes.*;

import java.util.ArrayList;

import bakingreciepes.Data.Step;
import butterknife.BindView;
import butterknife.ButterKnife;


public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {
    private ArrayList<Step> mStepList;
    final private ListItemClickListener mClickHandler;
    private static final int WITH_VIDEO = 1;
    private static final int WITHOUT_VIDEO = 0;

    public interface ListItemClickListener {
        void onClick(int position);

    }

    public StepsAdapter( ListItemClickListener clickHandler) {
        mClickHandler = clickHandler;
    }


    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int listLayoutId = R.layout.step_item;


        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(listLayoutId, parent, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        if (getItemViewType(position) == WITH_VIDEO)

            holder.playIV.setVisibility(View.VISIBLE);
        else if (getItemViewType(position) == WITHOUT_VIDEO)
            holder.playIV.setVisibility(View.GONE);
        holder.mTextView.setText(mStepList.get(position).getShortDescription());

    }


    @Override
    public int getItemCount() {
        return mStepList.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Nullable
        @BindView(R.id.shortDesc)
        TextView mTextView;
        @Nullable
        @BindView(R.id.play)
        ImageView playIV;
        @Nullable
        @BindView(R.id.thumbnail)
        ImageView thumbnailIV;

        StepsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if(mClickHandler!=null)
            mClickHandler.onClick(position);
        }

    }

    public void loadData(ArrayList<Step> stepList) {
        this.mStepList = stepList;

    }


    @Override

    public int getItemViewType(int position) {
        if (!mStepList.get(position).getVideoURL().equals("")) {
            return WITH_VIDEO;
        } else if ((mStepList.get(position).getVideoURL().equals(""))) {
            return WITHOUT_VIDEO;
        }
        return -1;
    }
}
