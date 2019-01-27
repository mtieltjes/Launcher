package nl.mtieltjes.launcher;

import java.util.Comparator;

import nl.mtieltjes.launcher.entries.ListItem;

public class ListItemComparator implements Comparator<ListItem> {
    @Override
    public int compare(ListItem o1, ListItem o2) {
        return o1.getLabel().compareTo(o2.getLabel());
    }
}
