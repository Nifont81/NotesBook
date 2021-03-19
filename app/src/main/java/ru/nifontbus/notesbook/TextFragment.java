package ru.nifontbus.notesbook;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TextFragment extends Fragment {

    public static final String ARG_NOTE = "TextCurrentNote";
    private Note currentNote;

    public TextFragment() {
        // Required empty public constructor
    }

    public static TextFragment newInstance(Note note) {
        TextFragment fragment = new TextFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);

        if (getArguments() != null) {
            currentNote = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Таким способом можно получить головной элемент из макета
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        setHasOptionsMenu(true);
        // находим в контейнере элементы
        TextView tvName = view.findViewById(R.id.nameNote);
        TextView tvText = view.findViewById(R.id.textNote);
        tvName.setText(currentNote.getName());
        tvText.setText(currentNote.getText());
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_text_menu, menu);
    }
}