package ru.nifontbus.notesbook;

import android.content.Context;
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
import android.widget.Toast;

public class DescriptionFragment extends Fragment {

    public static final String ARG_NOTE = "TextCurrentNote";
    private Note currentNote;

    public DescriptionFragment() {
        // Required empty public constructor
    }

    public static DescriptionFragment newInstance(Note note) {
        DescriptionFragment fragment = new DescriptionFragment();
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
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        setHasOptionsMenu(true);
        // находим в контейнере элементы
        TextView tvName = view.findViewById(R.id.nameNote);
        TextView tvText = view.findViewById(R.id.textNote);
        tvName.setText(currentNote.getTitle());
        tvText.setText(currentNote.getDescription());
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_text_menu, menu);
    }

    private void msg(String message) {
        Log.d("my", "msg: " + message);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}