package ru.nifontbus.notesbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements OnFragmentSendDataListener {

    private Note currentNote;
    public static final String CURRENT_NOTE = "MainCurrentNote";
    private boolean isLandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);

        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            currentNote = new Note(0, "Не задано", "");
        }

        msg("onCreate: MAIN : note = " + currentNote.getName());

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.notes_container, new NameFragment())
                .commit();

        if (isLandscape) {
            msg("onActivityCreated: NFRAG: isLandscape show note: " + currentNote);
            showLandTextNote(currentNote);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
        msg("onSaveInstanceState: MAIN Note = " + currentNote.getName());
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

    /**
     * Обработка бокового навигационного меню
     * @param id
     * @return
     */
    @SuppressLint("NonConstantResourceId")
    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.action_settings:
                msg("Настройки");
                return true;
            case R.id.action_main:
                msg("Основное");
                return true;
            case R.id.action_favorite:
                msg("Избранное");
                return true;
            case R.id.action_about:
                msg("О программе...");
                return true;
        }
        return false;
    }

    /**
     * Обработка верхнего меню
     * @param item
     * @return
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_menu_add:
                msg("Добавить элемент");
                break;
            case R.id.toolbar_menu_search:
                msg("Поиск");
                break;
            case R.id.toolbar_menu_delete:
                msg("Удалить элемент");
                break;
            case R.id.toolbar_menu_view:
                msg("Изменить вид");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Поиск элемента
     * @param query - строка поиска
     */
    private void findAction(String query){
        msg("Поиск " + query);
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    // регистрация drawer
    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Обработка навигационного меню
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (navigateFragment(id)){
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

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

        return true;
    }

    private void msg(String message) {
        Log.d("my", "msg: " + message);
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}