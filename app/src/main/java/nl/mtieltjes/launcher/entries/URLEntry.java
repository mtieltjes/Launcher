package nl.mtieltjes.launcher.entries;

public class URLEntry implements ListItem {

    private final String label;
    private final String url;

    public URLEntry(final String label, final String url) {
        this.label = label;
        this.url = url;
    }

    public String getURL() {
        return url;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
