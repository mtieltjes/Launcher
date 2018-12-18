package nl.mtieltjes.launcher;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;

public class AppsProvider {

    private final List<String> packagesToExclude;
    private final PackageManager packageManager;
    private final List<AppEntry> apps;
    private OnAppsUpdatedListener appsUpdatedListener;

    /**
     * Create a provider that will get the (launchable) installed apps and notifies them to a listener.
     *
     * @param packageManager    {@link PackageManager} to get the installed apps from
     * @param packagesToExclude List of packages that should be excluded from the reported apps list
     */
    AppsProvider(final PackageManager packageManager, final List<String> packagesToExclude) {
        this.packageManager = packageManager;
        this.packagesToExclude = packagesToExclude;
        apps = new ArrayList<>();
    }

    /**
     * Get the list of packages, filter out all apps that cannot be launched and sort alphabetically.
     * After the list has been updated, notify the listener of the change.
     */
    public void reloadApps() {
        // Query the package manager for all apps
        List<ResolveInfo> packages = packageManager.queryIntentActivities(
                new Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER), 0);


        // Sort the applications by alphabetical order and add them to the list
        packages.sort(new ResolveInfo.DisplayNameComparator(packageManager));

        // Recalculate the apps list
        apps.clear();
        for (final ResolveInfo resolver : packages) {
            // Exclude self
            final String packageName = resolver.activityInfo.packageName;
            if (packagesToExclude.contains(packageName))
                continue;

            final String appName = resolver.loadLabel(packageManager).toString();
            apps.add(new AppEntry(appName, packageName));
        }

        // Let the listener know of the changes
        notifyListener();
    }

    /**
     * Subscribe a listener to changes on the apps list.
     *
     * @param listener to notify when the list changes
     */
    public void setListener(final OnAppsUpdatedListener listener) {
        this.appsUpdatedListener = listener;

        notifyListener();
    }

    /**
     * Unsubscribe the listener to changes on the apps list.
     */
    public void removeListener() {
        this.appsUpdatedListener = null;
    }

    /**
     * Update the listener with the current apps list
     */
    private void notifyListener() {
        appsUpdatedListener.onAppsUpdated(apps);
    }

    /**
     * Listener that is used for callbacks about changes in the appslist
     */
    public interface OnAppsUpdatedListener {

        /**
         * Called when the installed apps list has been updated.
         *
         * @param apps List of apps that can be launched
         */
        void onAppsUpdated(final List<AppEntry> apps);
    }
}
