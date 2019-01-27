package nl.mtieltjes.launcher.launching;

public class NoApplicableLauncherException extends Exception {

    public NoApplicableLauncherException(final Class objectClass) {
        super(String.format("No applicable launcher found for object of class %s", objectClass.getSimpleName()));
    }
}
