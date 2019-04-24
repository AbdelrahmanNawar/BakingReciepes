package bakingreciepes.Components;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alfa.bakingreciepes.*;

import java.util.ArrayList;

import bakingreciepes.Data.Ingredient;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ComponentAdapter extends RecyclerView.Adapter<ComponentAdapter.IngredientViewHolder> {
    private ArrayList<Ingredient> myIngredientList;


    ComponentAdapter() {
    }

    @NonNull
    @Override
    public ComponentAdapter.IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int listLayoutId = R.layout.ingredient_item;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(listLayoutId, parent, false);
        return new ComponentAdapter.IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComponentAdapter.IngredientViewHolder holder, int position) {
        holder.mQuantity.setText((myIngredientList.get(position).getQuantity())+"  "+ myIngredientList.get(position).getMeasure());
        holder.mName.setText(myIngredientList.get(position).getIngredient());
    }


    @Override
    public int getItemCount() {
        return myIngredientList.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder  {
        @Nullable
        @BindView(R.id.quantity)
        TextView mQuantity;

        @Nullable
        @BindView(R.id.name)
        TextView mName;

        IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }



    }

    void loadData(ArrayList<Ingredient> ingredientsList) {
        myIngredientList = ingredientsList;

    }
}
