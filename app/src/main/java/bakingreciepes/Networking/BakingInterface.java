package bakingreciepes.Networking;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import bakingreciepes.Data.Cook;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface BakingInterface {
    @NonNull
    @GET("/topher/2017/May/59121517_baking/baking.json")
    Observable<ArrayList<Cook>> getBaking();

}
