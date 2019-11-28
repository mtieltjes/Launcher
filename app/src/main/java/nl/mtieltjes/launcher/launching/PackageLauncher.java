package nl.mtieltjes.launcher.launching;

import android.content.Context;
import android.content.Intent;

import nl.mtieltjes.launcher.entries.AppEntry;

public class PackageLauncher extends Launcher<AppEntry> {

    PackageLauncher(final Context context) {
        super(context, AppEntry.class);
    }

    @Override
    public void launch(final AppEntry entry) {
        try {
            final Intent intent = context.getPackageManager().getLaunchIntentForPackage(entry.packageName);
            if (intent != null) {
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
