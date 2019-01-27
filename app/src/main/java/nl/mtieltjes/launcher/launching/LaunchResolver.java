package nl.mtieltjes.launcher.launching;

import android.content.Context;

public class LaunchResolver {
    private LaunchRegistry registry;

    public LaunchResolver(Context context) {
        registry = new LaunchRegistry();
        registry.addLauncher(new PackageLauncher(context));
        registry.addLauncher(new URLLauncher(context));
    }

    public void launch(final Object object) {
        try {
            final Launcher launcher = registry.getLauncherForItem(object);
            launcher.launchItem(object);
        } catch (NoApplicableLauncherException e) {
            // do nothing for now
        }
    }

    public void launchSettings(final Object object) {

    }
}
