package nl.mtieltjes.launcher.launching;

import android.content.Context;

public abstract class Launcher<T> {

    public final Class<?> itemClass;
    protected Context context;

    protected Launcher(Context context, Class<?> itemClass) {
        this.context = context;
        this.itemClass = itemClass;
    }

    @SuppressWarnings("unchecked")
    public void launchItem(Object object) {
        launch((T) object);
    }

    protected abstract void launch(T object);
}
