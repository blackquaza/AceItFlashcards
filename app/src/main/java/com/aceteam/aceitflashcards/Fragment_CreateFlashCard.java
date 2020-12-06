package com.aceteam.aceitflashcards;

import android.app.Activity;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Fragment_CreateFlashCard extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_createflashcard, container, false);
    }

    public void hideKeyboard(View v) {
        ((InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.createflashcard_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Fragment_CreateFlashCard.this)
                        .navigate(R.id.action_fragment_CreateFlashCard_to_fragment_FlashCardList);
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                view.findViewById(R.id.createflashcard_back).performClick();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        EditText qText = getActivity().findViewById(R.id.createflashcard_questioninput);
        EditText aText = getActivity().findViewById(R.id.createflashcard_answerinput);
        EditText hText = getActivity().findViewById(R.id.createflashcard_hintinput);

        List<EditText> editTextList = new ArrayList<EditText>();
        editTextList.add(qText);
        editTextList.add(aText);
        editTextList.add(hText);

        for (EditText editText : editTextList) {
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        hideKeyboard(v);
                    }
                }
            });
        }

        File tagFolder = new File(getContext().getFilesDir(), "tags");

        if (tagFolder.listFiles().length == 0) {
            TextView t = new TextView(getContext());
            t.setText(getResources().getString(R.string.no_tags));
            t.setPadding(20,20,20,20);
            LinearLayout l = view.findViewById(R.id.createflashcard_layout);
            l.addView(t, 7);
        } else {
            for (File f : tagFolder.listFiles()) {
                // TODO: Code this.
            }
        }

        Bundle b = getArguments();
        FlashCard card;
        try {
            card = (FlashCard) b.getSerializable("Card");
        } catch (NullPointerException e) {
            card = null;
        }

        File cardFolder = new File(getContext().getFilesDir(), "flashcards");
        File t = null;

        if (card != null) { // If we're editing a card and not creating one.
            String hash = card.getHash();
            t = new File(cardFolder, hash + ".ser");
            qText.setText(card.getQuestion());
            aText.setText(card.getAnswer());
            hText.setText(card.getHint());
        }
        // The t variable  is used to make the currentFile variable effectively final,
        // which is needed in order to be used in the onClick method.
        File currentFile = t;

        view.findViewById(R.id.createflashcard_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String question = qText.getText().toString().trim();
                String answer = aText.getText().toString().trim();
                String hint = hText.getText().toString().trim();
                Set<String> wrongAnswers = new HashSet<String>();
                Set<Tag> tags = new HashSet<Tag>();

                if (question.isEmpty()) {
                    String e = getResources().getString(R.string.question_needed);
                    Toast.makeText(getContext(), e, Toast.LENGTH_SHORT).show();
                } else if (answer.isEmpty()) {
                    String e = getResources().getString(R.string.answer_needed);
                    Toast.makeText(getContext(), e, Toast.LENGTH_SHORT).show();
                } else {

                    FlashCard card = new FlashCard(question, answer, hint, wrongAnswers, tags);
                    card.exportFlash(cardFolder);

                    if (currentFile != null) {
                        currentFile.delete();
                    }

                    Bundle b = new Bundle();
                    b.putSerializable("Card", card);
                    NavHostFragment.findNavController(Fragment_CreateFlashCard.this)
                            .navigate(R.id.action_fragment_CreateFlashCard_to_fragment_FlashCardVertical, b);

                }
            }
        });
    }
}