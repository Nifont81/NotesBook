package ru.nifontbus.notesbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class TitleFragment extends Fragment {

    private static ru.nifontbus.notesbook.fragmentSendDataListener fragmentSendDataListener;

    public TitleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentSendDataListener = (ru.nifontbus.notesbook.fragmentSendDataListener) context;
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

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_title,
                container, false);

        // Если элементы одного размера, то так быстрее работает:
        recyclerView.setHasFixedSize(true);

        // Установили в ресурсах
//        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
//        recyclerView.setLayoutManager(layoutManager);

        CardsSource data = new CardsSourceImpl(getResources()).init();
        final ViewHolderCardAdapter adapter = new ViewHolderCardAdapter(data);
        recyclerView.setAdapter(adapter);

        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration =
                new DividerItemDecoration(getContext(),  LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);


        adapter.SetOnItemClickListener(fragmentSendDataListener);

        return recyclerView;
    }
/*

    */
/**
     * Обработка длительного нажатия на список
     * (в следующем задании)
     *//*

    @SuppressLint("NonConstantResourceId")
    private void addPopUpMenu(View view) {
        view.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), view);
            popupMenu.inflate(R.menu.button_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_item_add:
                        msg("Добавить");

                        return true;
                    case R.id.menu_item_del:
                        msg("Удалить");

                        return true;
                    default:
                        return false;
                }
            });
            popupMenu.show();
            return true;
        });
    }
*/

    private void msg(String message) {
        Log.d("my", "msg: " + message);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}