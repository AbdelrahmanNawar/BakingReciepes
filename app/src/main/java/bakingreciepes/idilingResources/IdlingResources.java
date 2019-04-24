package bakingreciepes.idilingResources;
import androidx.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class IdlingResources implements IdlingResource {

    private volatile ResourceCallback myCallback;

    // Idleness is controlled with this boolean.
    private final AtomicBoolean mIsIdleNow = new AtomicBoolean(true);
    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        myCallback = callback;
    }

    /**
     * Sets the new idle state, if isIdleNow is true, it pings the {@link ResourceCallback}.
     * @param isIdleNow false if there are pending operations, true if idle.
     */
    public void setIdleState(boolean isIdleNow) {
        mIsIdleNow.set(isIdleNow);
        if (isIdleNow && myCallback != null) {
            myCallback.onTransitionToIdle();
        }
    }
}
