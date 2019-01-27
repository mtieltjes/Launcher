package nl.mtieltjes.launcher.launching;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import nl.mtieltjes.launcher.entries.URLEntry;

public class URLLauncher extends Launcher<URLEntry> {
    protected URLLauncher(Context context) {
        super(context, URLEntry.class);
    }

    @Override
    protected void launch(URLEntry urlEntry) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlEntry.getURL()));
        context.startActivity(browserIntent);
    }
}
