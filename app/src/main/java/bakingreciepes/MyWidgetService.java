package bakingreciepes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.alfa.bakingreciepes.R;
import com.google.gson.Gson;

import bakingreciepes.Data.Cook;

import static bakingreciepes.RecipeActivity.BAKING_KEY;

public class MyWidgetService extends RemoteViewsService {
    private Cook mBaking;

    @NonNull
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Gson gson = new Gson();
        String json = MySharedPreferences.loadSavedPreferences(this.getApplicationContext(), BAKING_KEY);
        mBaking = gson.fromJson(json, Cook.class);

        return new BakingRemoteFactory(this.getApplicationContext());
    }

    class BakingRemoteFactory implements RemoteViewsService.RemoteViewsFactory {
        final Context mContext;


        BakingRemoteFactory(Context context) {
            mContext = context;


        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (mBaking != null)
                return mBaking.getIngredients().size();
            else return 0;
        }

        @NonNull
        @Override
        public RemoteViews getViewAt(int position) {


            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_item);
            remoteViews.setTextColor(R.id.quantity, Color.BLACK);
            remoteViews.setTextColor(R.id.name, Color.BLACK);
            String quantityMeasure = mBaking.getIngredients().get(position).getQuantity() + "  " + mBaking.getIngredients().get(position).getMeasure();
            remoteViews.setTextViewText(R.id.quantity, quantityMeasure);
            remoteViews.setTextViewText(R.id.name, mBaking.getIngredients().get(position).getIngredient());
            return remoteViews;
        }

        @Nullable
        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
