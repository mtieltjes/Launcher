package nl.mtieltjes.launcher.entries;

/**
 * Model containing an app name and package identifier.
 */
public class AppEntry implements ListItem {
    private final String label;
    public final String packageName;

    public AppEntry(final String label, final String packageName) {
        this.label = label;
        this.packageName = packageName;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
