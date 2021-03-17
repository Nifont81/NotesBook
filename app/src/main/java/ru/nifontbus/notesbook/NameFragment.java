package ru.nifontbus.notesbook;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NameFragment extends Fragment {

    private OnFragmentSendDataListener fragmentSendDataListener;

    public NameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentSendDataListener = (OnFragmentSendDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            initList(view);
        }
    }

    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        String[] noteNames = getResources().getStringArray(R.array.note_names);

        for (int i = 0; i < noteNames.length; i++) {
            final String name = noteNames[i];
            TextView tv = new TextView(getContext());
            tv.setText(name);
            tv.setTextSize(30);
            layoutView.addView(tv);

            final int fi = i;
            tv.setOnClickListener(v -> {
                Note note = new Note(fi, name,
                        getResources().getStringArray(R.array.note_text)[fi]);
                fragmentSendDataListener.showTextNote(note);
            });
        }
    }
}