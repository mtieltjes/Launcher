package nl.mtieltjes.launcher.launching;

import java.util.ArrayList;
import java.util.List;

public class LaunchRegistry {
    private List<Launcher<?>> launchers;

    public LaunchRegistry() {
        launchers = new ArrayList<>();
    }

    /**
     * @param launcher {@link Launcher} to add to the registry
     */
    public void addLauncher(final Launcher<?> launcher) {
        launchers.add(launcher);
    }

    /**
     * Get a {@link Launcher} that is capable of handling the given item. When using inheritance,
     * this will only return the launcher that matches the item class, not its parents class.
     *
     * @param item to get a matching launcher for
     * @return the first launcher found that matches given item.
     * @throws NoApplicableLauncherException when no launcher has been found for item.
     */
    public Launcher getLauncherForItem(Object item) throws NoApplicableLauncherException {
        for (Launcher launcher: launchers) {
            if (item.getClass().equals(launcher.itemClass)) {
                return launcher;
            }
        }
        throw new NoApplicableLauncherException(item.getClass());
    }

}
