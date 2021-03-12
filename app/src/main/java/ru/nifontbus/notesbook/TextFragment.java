package ru.nifontbus.notesbook;

import android.content.res.TypedArray;
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_INDEX = "text_fragment_id";

    // TODO: Rename and change types of parameters
    private int index;

    public TextFragment() {
        // Required empty public constructor
    }

     public static TextFragment newInstance(int index) {
        TextFragment fragment = new TextFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Таким способом можно получить головной элемент из макета
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        // найти в контейнере элемент для вывода текста
        TextView textView = view.findViewById(R.id.textNote);
        textView.setTextSize(30);
        // Получить из ресурсов массив текстов заметок
        //TypedArray texts = getResources().obtainTypedArray(R.array.note_text);

        String[] noteTexts = getResources().getStringArray(R.array.note_text);
        // Выбрать по индексу подходящий
        textView.setText(noteTexts[index]);
        return view;

    }
}