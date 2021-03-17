package ru.nifontbus.notesbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements OnFragmentSendDataListener {

    private Note currentNote;
    public static final String CURRENT_NOTE = "MainCurrentNote";
    private final String TAG = "my";
    private boolean isLandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            currentNote = new Note(0, "Не задано", "");
        }

        Log.d(TAG, "onCreate: MAIN : note = " + currentNote.getName());

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.notes_container, new NameFragment())
                .commit();

        if (isLandscape) {
            Log.d(TAG, "onActivityCreated: NFRAG: isLandscape show note: " + currentNote);
            showLandTextNote(currentNote);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: MAIN Note = " + currentNote.getName());
    }

    @Override
    public void showTextNote(Note note) {
        currentNote = note;
        if (isLandscape) {
            showLandTextNote(note);
        } else {
            showPortTextNote(note);
        }
    }

    // Показать содержимое заметки в ландшафтной ориентации
    public void showLandTextNote(Note note) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.textNote, TextFragment.newInstance(note))  // замена фрагмента
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }

    public void showPortTextNote(Note note) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notes_container, TextFragment.newInstance(note))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }
}