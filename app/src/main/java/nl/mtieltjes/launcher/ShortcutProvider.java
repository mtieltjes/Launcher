package nl.mtieltjes.launcher;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import nl.mtieltjes.launcher.entries.ShortcutEntry;

public class ShortcutProvider {
    private final LauncherApps launcherApps;
    private final Context context;

    ShortcutProvider(final Context context) {
        this.context = context;
        launcherApps = (LauncherApps) context.getSystemService(Context.LAUNCHER_APPS_SERVICE);
    }

    public List<ShortcutEntry> getPinnedShortcutForPackage(final String packageName) {
        final LauncherApps.ShortcutQuery query = new LauncherApps.ShortcutQuery();
        query.setQueryFlags(LauncherApps.ShortcutQuery.FLAG_MATCH_PINNED);
        query.setPackage(packageName);
        try {
            final List<ShortcutInfo> shortcuts = launcherApps.getShortcuts(query, Process.myUserHandle());
            if (shortcuts != null && !shortcuts.isEmpty()) {
                Log.d("Merijn", "Found! " + shortcuts.toString());
            }
            if (shortcuts == null) {
                return Collections.emptyList();
            }
            return shortcuts.stream().map(item -> new ShortcutEntry(
                    item.getShortLabel() != null ? item.getShortLabel().toString() : item.getPackage(),
                    item.getPackage(),
                    item.getId())).collect(Collectors.toList());
        } catch (SecurityException e) {
            Toast.makeText(context, "No permission", Toast.LENGTH_SHORT).show();
            return Collections.emptyList();
        }
    }
}
