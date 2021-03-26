package ru.nifontbus.notesbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Calendar;

import ru.nifontbus.notesbook.observe.Observer;
import ru.nifontbus.notesbook.observe.Publisher;

public class TitleFragment extends Fragment {

    private static ru.nifontbus.notesbook.fragmentSendDataListener fragmentSendDataListener;
    private static final int MY_DEFAULT_DURATION = 1000;
    private CardsSource data;
    private CardAdapter adapter;
    private RecyclerView recyclerView;
    private Publisher publisher;

    // признак, что при повторном открытии фрагмента
    // (возврате из фрагмента, добавляющего запись)
    // надо прыгнуть на первую запись
    private boolean moveToFirstPosition;

    public TitleFragment() {
        // Required empty public constructor
    }

    public static TitleFragment newInstance() {
        TitleFragment titleFragment = new TitleFragment();
        return titleFragment;
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

        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_title, container, false);
        initView(view);
        setHasOptionsMenu(true);

        data = new CardsSourceFirebaseImpl().init(cardsData -> adapter.notifyDataSetChanged());
        adapter.setDataSource(data);

        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);
        initRecyclerView();
    }

    private void initRecyclerView() {
        // Если элементы одного размера, то так быстрее работает:
        recyclerView.setHasFixedSize(true);

        // Установили тоже в ресурсах
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CardAdapter(this);
        recyclerView.setAdapter(adapter);

        // Добавим разделитель карточек
        DividerItemDecoration itemDecoration =
                new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        // Установим анимацию. А чтобы было хорошо заметно, сделаем анимацию долгой
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(MY_DEFAULT_DURATION);
        animator.setRemoveDuration(MY_DEFAULT_DURATION);
        recyclerView.setItemAnimator(animator);

        if (moveToFirstPosition && data.size() > 0) {
            recyclerView.scrollToPosition(0);
            moveToFirstPosition = false;
        }

        adapter.SetOnItemClickListener(fragmentSendDataListener);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);
        MenuItem search = menu.findItem(R.id.toolbar_menu_search); // поиск пункта меню поиска
        SearchView searchText = (SearchView) search.getActionView(); // строка поиска

        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // реагирует на конец ввода поиска
            @Override
            public boolean onQueryTextSubmit(String query) {
                findAction(query);
                return true;
            }

            // реагирует на нажатие каждой клавиши
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    /**
     * Обработка верхнего меню
     *
     * @param item
     * @return
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_menu_add:

                int position = data.size();
                CardData cardData = new CardData(position, "", "",
                        false, Calendar.getInstance().getTime(), R.drawable.draw2);
                ((MainActivity) getActivity()).addEditFragment(cardData);

                publisher.subscribe(cardDataNew -> {
                    data.addCardData(cardDataNew);
                    adapter.notifyItemInserted(position);
                    // это сигнал, чтобы вызванный метод onCreateView
                    // перепрыгнул на начало списка
                    moveToFirstPosition = true;
                });
                break;

            case R.id.toolbar_menu_search:
                msg("Поиск");
                break;
            case R.id.toolbar_menu_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Поиск элемента
     *
     * @param query - строка поиска
     */
    private void findAction(String query) {
        msg("Поиск " + query);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu,
                                    @NonNull View v,
                                    @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final int position = adapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.action_update:
                CardData cardData = data.getCardData(position);
                ((MainActivity) getActivity()).addEditFragment(cardData);
                publisher.subscribe(cardDataUpdate -> {
                    data.updateCardData(cardDataUpdate);
                    adapter.notifyItemChanged(position);
                });

                return true;
            case R.id.action_delete:
                data.deleteCardData(position);
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void msg(String message) {
        Log.d("my", "msg: " + message);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}