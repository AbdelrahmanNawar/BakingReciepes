package bakingreciepes;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.example.alfa.bakingreciepes.*;
import com.google.gson.Gson;


import java.util.ArrayList;

import bakingreciepes.Components.ComponentWidget;
import bakingreciepes.Data.Ingredient;
import bakingreciepes.Data.Cook;
import bakingreciepes.Data.Step;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeActivity extends AppCompatActivity {
    private boolean isTablet;
    private Cook mBaking;
    private ArrayList<Ingredient> mIngredientList;
    private ArrayList<Step> mStepList;
    @Nullable
    @BindView(R.id.pin)
    Button pin;
    public static final String BAKING_KEY = "baking-key";
    public static final String INGREDIENTS_KEY = "component-key";
    public static final String STEPS_KEY = "step-key";
    public static final String BAKING_NAME = "baking-name";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        if (getIntent() != null && getIntent().hasExtra("fromWidget")) {
            Gson gson = new Gson();
            String json = MySharedPreferences.loadSavedPreferences(this, BAKING_KEY);
            mBaking = gson.fromJson(json, Cook.class);
            mIngredientList = mBaking.getIngredients();
            mStepList = mBaking.getSteps();
        } else if (getIntent() != null && !getIntent().hasExtra("fromWidget")) {

            mBaking = getIntent().getParcelableExtra(BAKING_KEY);
            mIngredientList = getIntent().getParcelableArrayListExtra(INGREDIENTS_KEY);
            mStepList = getIntent().getParcelableArrayListExtra(STEPS_KEY);
        }
        Fragment fragmentIngredients = getSupportFragmentManager().findFragmentById(R.id.master_list_ingredients);
        Bundle bundle2 = new Bundle();
        bundle2.putParcelableArrayList(INGREDIENTS_KEY, mIngredientList);
        fragmentIngredients.setArguments(bundle2);

        Fragment fragmentSteps = getSupportFragmentManager().findFragmentById(R.id.master_list_steps);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STEPS_KEY, mStepList);
        if (findViewById(R.id.layoutForTablet) != null) {
            isTablet = true;

            if (savedInstanceState == null || getSupportFragmentManager().getBackStackEntryCount() == 0) {
                bundle = new Bundle();

                DetailFragment detailFragment = new DetailFragment();
                bundle.putBoolean("isTablet", isTablet);
                detailFragment.setSteps(mStepList, 0);
                detailFragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.master_list_detail, detailFragment)
                        .commit();
            }
        }
        bundle = new Bundle();

        bundle.putBoolean("isTablet", isTablet);
        bundle.putParcelableArrayList(STEPS_KEY, mStepList);

        fragmentSteps.setArguments(bundle);
        if (getSupportFragmentManager() != null) {
            getSupportActionBar().setTitle(mBaking.getName());
        }
    }

    @OnClick(R.id.pin)
    public void pin(View v) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, ComponentWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_listView);

        Gson gson = new Gson();
        String json = gson.toJson(mBaking);
        MySharedPreferences.StringPreferenceSaving(this, BAKING_KEY, json);
        MySharedPreferences.StringPreferenceSaving(this, BAKING_NAME, mBaking.getName());
        ComponentWidget.updateIngredientsWidgets(this, appWidgetManager, appWidgetIds);

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}

