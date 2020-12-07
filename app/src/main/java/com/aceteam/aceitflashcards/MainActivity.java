package com.aceteam.aceitflashcards;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;

import android.view.KeyEvent;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initial setup.
        File folder = getApplicationContext().getFilesDir();
        File cardFolder = new File(folder, "flashcards");
        File tagFolder = new File(folder, "tags");
        File quizFolder = new File(folder, "quizzes");

        if (!cardFolder.exists()) {
            cardFolder.mkdirs();
        }
        if (!tagFolder.exists()) {
            tagFolder.mkdirs();
        }
        if (!quizFolder.exists()) {
            quizFolder.mkdirs();
        }

    }

}