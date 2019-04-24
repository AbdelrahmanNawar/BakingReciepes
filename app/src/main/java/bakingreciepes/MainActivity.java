package bakingreciepes;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.test.espresso.IdlingResource;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.alfa.bakingreciepes.R;

import java.util.ArrayList;

import bakingreciepes.Data.Cook;
import bakingreciepes.Networking.BakingInterface;
import bakingreciepes.Networking.RetrofitClient;
import bakingreciepes.idilingResources.IdlingResources;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static bakingreciepes.RecipeActivity.BAKING_KEY;
import static bakingreciepes.RecipeActivity.INGREDIENTS_KEY;
import static bakingreciepes.RecipeActivity.STEPS_KEY;


public class MainActivity extends AppCompatActivity implements BakingAdapter.ListItemClickListener {
    @Nullable
    @BindView(R.id.bakingRv)
    protected
    RecyclerView mRecyclerView;
    @Nullable
    @BindView(R.id.reload)
    protected
    ImageView reload;
    @Nullable
    @BindView(R.id.retry)
    protected
    LinearLayout retry;
    private BakingInterface mInterface;
    private BakingAdapter bakingAdapter;
    @Nullable
    private ArrayList<Cook> mBakingList;
    private final String STATE_KEY = "check-key";


    @Nullable
    private IdlingResources mIdlingResource;


    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        return mIdlingResource;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIdlingResource = new IdlingResources();
        mIdlingResource.setIdleState(false);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bakingAdapter = new BakingAdapter( this);
        SetGridLayout gridLayout = new SetGridLayout(this);
        mRecyclerView.setLayoutManager(gridLayout);

        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_KEY)) {
            mBakingList = savedInstanceState.getParcelableArrayList(STATE_KEY);
            bakingAdapter.loadData(mBakingList);
            mRecyclerView.setAdapter(bakingAdapter);


        } else {


            loadBakingData();
        }


    }

    @OnClick(R.id.reload)
    public void tryAgain() {
        retry.setVisibility(View.GONE);

        loadBakingData();
    }

    private void loadBakingData() {
        mInterface = RetrofitClient.getClient().create(BakingInterface.class);
        mInterface.getBaking().subscribeOn(Schedulers.io())
                .retry(1)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ArrayList<Cook>>() {

            public void onError(Throwable e) {
                System.out.println("failure");
                retry.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }

            @Override
            public void onComplete() {

            }


            @Override
            public void onSubscribe(Disposable d) {

            }

            public void onNext(ArrayList<Cook> list) {
                mRecyclerView.setVisibility(View.VISIBLE);
                reload.setVisibility(View.GONE);
                mBakingList = list;
                bakingAdapter.loadData(list);
                mRecyclerView.setAdapter(bakingAdapter);

                mIdlingResource.setIdleState(true);

            }


        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_KEY, mBakingList);
    }

    @Override
    public void onClick(int position) {
        Cook baking = mBakingList.get(position);
        Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
        intent.putExtra(BAKING_KEY, baking);
        intent.putParcelableArrayListExtra(INGREDIENTS_KEY, baking.getIngredients());
        intent.putParcelableArrayListExtra(STEPS_KEY, baking.getSteps());

        startActivity(intent);
    }

}
