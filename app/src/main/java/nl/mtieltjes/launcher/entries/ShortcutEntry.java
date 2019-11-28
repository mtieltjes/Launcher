package nl.mtieltjes.launcher.entries;

public class ShortcutEntry implements ListItem {

    private final String label;
    public final String packageName;
    public final String shortcutId;

    public ShortcutEntry(final String label, final String packageName, final String shortcutId) {
        this.label = label;
        this.packageName = packageName;
        this.shortcutId = shortcutId;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
