package nl.mtieltjes.launcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherApps;
import android.os.Bundle;
import android.widget.Toast;

public class ShortcutConfirmation extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            final String action = intent.getAction();
            if (LauncherApps.ACTION_CONFIRM_PIN_SHORTCUT.equals(action)) {
                final LauncherApps launcherApps = (LauncherApps) getSystemService(Context.LAUNCHER_APPS_SERVICE);
                if (launcherApps != null) {
                    LauncherApps.PinItemRequest mRequest = launcherApps.getPinItemRequest(intent);
                    mRequest.accept();
                }
                Toast.makeText(this, "Shortcut added", Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }
}
