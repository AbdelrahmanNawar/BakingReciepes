package bakingreciepes;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;

public class MySharedPreferences {
	
	public static void StringPreferenceSaving(Context context, String key, String value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.apply();
	}

	
	@Nullable
	public static String loadSavedPreferences(Context context, String key) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);

		return sharedPreferences.getString(key, "");
	}


}
