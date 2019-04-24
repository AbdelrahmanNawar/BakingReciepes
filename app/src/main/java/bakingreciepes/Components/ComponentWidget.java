package bakingreciepes.Components;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import com.example.alfa.bakingreciepes.R;

import bakingreciepes.MyWidgetService;
import bakingreciepes.RecipeActivity;
import bakingreciepes.MySharedPreferences;

import static bakingreciepes.RecipeActivity.BAKING_KEY;
import static bakingreciepes.RecipeActivity.BAKING_NAME;


public class ComponentWidget extends AppWidgetProvider {

    private static void updateAppWidget(@NonNull Context context, @NonNull AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = setRemoteViewListView(context);
        String mBakingName = MySharedPreferences.loadSavedPreferences(context, BAKING_NAME);
        views.setTextViewText(R.id.ingredientsName, mBakingName);
        if (!MySharedPreferences.loadSavedPreferences(context, BAKING_KEY).equals("")) {

            Intent configIntent = new Intent(context, RecipeActivity.class);
            configIntent.putExtra("fromWidget", "");
            PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);

            views.setOnClickPendingIntent(R.id.see_more, configPendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views
            );
        }
    }


    @Override
    public void onUpdate(@NonNull Context context, @NonNull AppWidgetManager appWidgetManager, @NonNull int[] appWidgetIds) {
        updateIngredientsWidgets(context, appWidgetManager, appWidgetIds);
    }

    public static void updateIngredientsWidgets(@NonNull Context context, @NonNull AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


    @NonNull
    private static RemoteViews setRemoteViewListView(Context context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

        Intent intent = new Intent(context, MyWidgetService.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        remoteViews.setRemoteAdapter(R.id.appwidget_listView, intent);
        Intent intentLaunched = new Intent(context, RecipeActivity.class);
        intentLaunched.putExtra("fromWidget", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentLaunched, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.see_more, pendingIntent);
        remoteViews.setEmptyView(R.id.item, R.id.no_item);
        return remoteViews;
    }


}