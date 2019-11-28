package nl.mtieltjes.launcher.launching;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.os.Process;

import nl.mtieltjes.launcher.entries.ShortcutEntry;

public class ShortcutLauncher extends Launcher<ShortcutEntry> {

    private final LauncherApps launcherApps;

    ShortcutLauncher(final Context context) {
        super(context, ShortcutEntry.class);
        launcherApps = (LauncherApps) context.getSystemService(Context.LAUNCHER_APPS_SERVICE);
    }

    @Override
    protected void launch(final ShortcutEntry entry) {
        launcherApps.startShortcut(entry.packageName, entry.shortcutId, null,
                null, Process.myUserHandle());
    }
}
