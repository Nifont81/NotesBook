package ru.nifontbus.notesbook;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Navigation {

    private final FragmentManager fragmentManager;
    public boolean isLandscape;

    public Navigation(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void addEditFragment(int position) {
        // Открыть транзакцию
        int id;
        if (isLandscape) {
            id = R.id.detail_note;
        } else {
            id = R.id.notes_container;
        }

        fragmentManager.beginTransaction()
                .replace(id, CardFragment.newInstance(position))
                .addToBackStack(null)
                .commit();
    }
}
