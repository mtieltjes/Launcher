package nl.mtieltjes.launcher;

/**
 * Model containing an app name and package identifier.
 */
public class AppEntry {
    public final String label;
    public final String packageName;

    AppEntry(final String label, final String packageName) {
        this.label = label;
        this.packageName = packageName;
    }
}
