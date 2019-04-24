package bakingreciepes.Components;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alfa.bakingreciepes.*;

import java.util.ArrayList;

import bakingreciepes.Data.Ingredient;
import butterknife.BindView;
import butterknife.ButterKnife;

import static bakingreciepes.RecipeActivity.INGREDIENTS_KEY;

public class ComponentFragment extends Fragment {

    @BindView(R.id.ingredientsRv)
    RecyclerView ingredientRecyclerView;
    private ArrayList<Ingredient> myIngredient;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.ingredient_fragment, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            myIngredient = getArguments().getParcelableArrayList(INGREDIENTS_KEY);
            setIngredients(myIngredient);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        ingredientRecyclerView.addItemDecoration(itemDecorator);
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }


    private void setIngredients(ArrayList<Ingredient> ingredients) {
        myIngredient = ingredients;
        ComponentAdapter componentAdapter = new ComponentAdapter();
        componentAdapter.loadData(myIngredient);
        ingredientRecyclerView.setAdapter(componentAdapter);


    }


}

