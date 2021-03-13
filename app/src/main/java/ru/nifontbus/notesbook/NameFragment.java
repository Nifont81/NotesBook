package ru.nifontbus.notesbook;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NameFragment extends Fragment {

    private Note currentNote;

    public static final String CURRENT_NOTE = "CurrentNote";
    private boolean isLandscape;

    public NameFragment() {
        // Required empty public constructor
    }

//     @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        String[] noteNames = getResources().getStringArray(R.array.note_names);

        // В этом цикле создаём элемент TextView,
        // заполняем его значениями,
        // и добавляем на экран.
        // Кроме того, создаём обработку касания на элемент
        for (int i = 0; i < noteNames.length; i++) {
            String name = noteNames[i];
            TextView tv = new TextView(getContext());
            tv.setText(name);
            tv.setTextSize(30);
            layoutView.addView(tv);

            final int fi = i;
            tv.setOnClickListener(v -> {
                currentNote = new Note(fi, getResources().getStringArray(R.array.note_names)[fi],
                        getResources().getStringArray(R.array.note_text)[fi]);
                showTextNote(currentNote);
            });

        }
    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }


    // activity создана, можно к ней обращаться. Выполним начальные действия
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Определение ориентации экрана
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        // Если это не первое создание, то восстановим текущую позицию
        if (savedInstanceState != null) {
            // Восстановление текущей позиции.
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        }


        // Если можно нарисовать рядом второй фрагмент, то сделаем это
        if (isLandscape) {
            showLandTextNote(currentNote);
        }
    }


    private void showTextNote(Note currentNote) {
        if (isLandscape) {
            showLandTextNote(currentNote);
        } else {
            showPortTextNote(currentNote);
        }
    }

    // Показать содержимое заметки в ландшафтной ориентации
    private void showLandTextNote(Note currentNote) {
        // Создаём новый фрагмент с текущей позицией для вывода заметки
        TextFragment detail = TextFragment.newInstance(currentNote);

        // Выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.textNote, detail);  // замена фрагмента
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        fragmentTransaction.commit();

        fragmentManager.beginTransaction()
                .replace(R.id.textNote, detail)  // замена фрагмента
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


    private void showPortTextNote(Note currentNote) {
        // Откроем вторую activity
        Intent intent = new Intent();
        intent.setClass(getActivity(), TextActivity.class);

        // и передадим туда параметры
        intent.putExtra(TextFragment.ARG_NOTE, currentNote);
        startActivity(intent);
    }

}