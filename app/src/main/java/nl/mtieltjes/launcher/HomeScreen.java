package nl.mtieltjes.launcher;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nl.mtieltjes.launcher.entries.AppEntry;
import nl.mtieltjes.launcher.entries.ListItem;
import nl.mtieltjes.launcher.launching.LaunchResolver;

public class HomeScreen extends Activity implements AppsProvider.OnAppsUpdatedListener {

    private ArrayAdapter<ListItem> adapter;
    private ListView listView;
    private QuickScrollBar quickScrollBar;

    private AppsProvider appsProvider;
    private LaunchResolver launchResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        launchResolver = new LaunchResolver(this);

        appsProvider = new AppsProvider(getPackageManager(), new ShortcutProvider(this),
                new ArrayList<String>() {{ add(getPackageName()); }});
        appsProvider.reloadApps();

        // Create UI elements
        listView = findViewById(R.id.listView);
        listView.setSelector(R.drawable.list_item_selector);

        adapter = new ArrayAdapter<ListItem>(this, R.layout.list_item, new ArrayList<>()) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final TextView view = (TextView) (convertView != null ? convertView :
                        getLayoutInflater().inflate(android.R.layout.simple_list_item_1, null));

                final ListItem item = getItem(position);
                if (item != null) {
                    view.setText(item.getLabel());
                }

                return view;
            }
        };

        listView.setOnItemClickListener((parent, view, position, id) -> launchResolver.launch(adapter.getItem(position)));
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            launchResolver.launchSettings(adapter.getItem(position));
            return false;
        });
        listView.setAdapter(adapter);

        quickScrollBar = findViewById(R.id.quickScrollBar);
        quickScrollBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                listView.setSelection(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        appsProvider.setListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        appsProvider.reloadApps();
    }

    @Override
    protected void onDestroy() {
        appsProvider.removeListener();
        super.onDestroy();
    }

    /**
     * @param app Model containing a package identifier to launch.
     */
    private void launchApp(final AppEntry app) {
        if (app == null) {
            return;
        }

        try {
            final Intent intent = getPackageManager().getLaunchIntentForPackage(app.packageName);
            if (intent != null) {
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param app model containing a package identifier to launch in Settings
     */
    private void launchAppSettings(final AppEntry app) {
        if (app == null) {
            return;
        }

        try {
            // Attempt to launch the app with the package name
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + app.packageName));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAppsUpdated(List<ListItem> apps) {
        adapter.clear();
        adapter.addAll(apps);
        quickScrollBar.setMax(apps.size() - 1);
    }
}
