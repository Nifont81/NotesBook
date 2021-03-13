package ru.nifontbus.notesbook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TextFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TextFragment extends Fragment {

    public static final String ARG_NOTE = "text_fragment_id";

    private Note note;

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
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Таким способом можно получить головной элемент из макета
        View view = inflater.inflate(R.layout.fragment_text, container, false);

        // находим в контейнере элементы
        TextView tvName = view.findViewById(R.id.nameNote);
        TextView tvText = view.findViewById(R.id.textNote);

        tvName.setText(note.getName());
        tvText.setText(note.getText());
        return view;

    }
}