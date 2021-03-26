package ru.nifontbus.notesbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Date;
import java.util.List;

import ru.nifontbus.notesbook.observe.Publisher;

public class MainActivity extends AppCompatActivity implements fragmentSendDataListener {

    private CardData currentNote;
    public static final String CURRENT_NOTE = "MainCurrentNote";
    private boolean isLandscape;
    private Publisher publisher = new Publisher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);

//        getNavigation().addEditFragment(TitleFragment.newInstance(), false);

        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            currentNote = new CardData(-1, "...", "", false,
                    new Date(), R.drawable.draw1);
        }

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.notes_container, TitleFragment.newInstance())
                .commit();

        if (isLandscape && currentNote != null) {
            showLandDetailNote(currentNote);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showDetailNote(CardData note) {
        currentNote = note;
        if (isLandscape) {
            showLandDetailNote(note);
        } else {
            showPortDetailNote(note);
        }
    }

    // Показать содержимое заметки в ландшафтной ориентации
    public void showLandDetailNote(CardData note) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detail_note, DescriptionFragment.newInstance(note))  // замена фрагмента
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public void showPortDetailNote(CardData note) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notes_container, DescriptionFragment.newInstance(note))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }

    public void addEditFragment(CardData cardData) {
        // Открыть транзакцию
        int id;
        if (isLandscape) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_note, CardEditFragment.newInstance(cardData))
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notes_container, CardEditFragment.newInstance(cardData))
                    .addToBackStack(null)
                    .commit();
        }
    }

    /**
     * Обработка бокового навигационного меню
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
                FragmentManager fm = getSupportFragmentManager();
                List<Fragment> fragments = fm.getFragments();
                for (Fragment fragment : fragments) {
                    msg("frament :" + fragment.getClass());
                }
                return true;
            case R.id.action_about:
                msg("О программе...");
                return true;
        }
        return false;
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

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
            if (navigateFragment(id)) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    private void msg(String message) {
        Log.d("my", "msg: " + message);
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}