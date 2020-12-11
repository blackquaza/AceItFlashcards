package com.aceteam.aceitflashcards;

import android.app.Activity;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
        AutoCompleteTextView tText = view.findViewById(R.id.createflashcard_taginput);

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
        ChipGroup group = view.findViewById(R.id.createflashcard_taginc);
        HashMap<String, Tag> tagMap = new HashMap<>();

        if (tagFolder.listFiles().length == 0) {
            TextView t = new TextView(getContext());
            t.setText(getResources().getString(R.string.no_tags));
            t.setPadding(20,20,20,20);
            LinearLayout l = view.findViewById(R.id.createflashcard_layout);
            l.addView(t, 7);
            l.removeView(tText);
        } else {
            for (File f : tagFolder.listFiles()) {
                Tag tag = Tag.importTag(f);
                tagMap.put(tag.getName(), tag);
            }
        }

        ArrayList<String> tagList = new ArrayList<String>(tagMap.keySet());
        ArrayAdapter<String> autofill = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_list_item_1, tagList);
        tText.setAdapter(autofill);

        tText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    for (String test : tagList) {
                        String s = v.getText().toString().trim();
                        if (s.equalsIgnoreCase(test)) {
                            Chip c = new Chip(getContext());
                            c.setText(s);
                            c.setCloseIconResource(android.R.drawable.ic_delete);
                            c.setCloseIconVisible(true);

                            c.setOnCloseIconClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    tagList.add(test);
                                    ((ViewManager)c.getParent()).removeView(c);
                                }
                            });

                            group.addView(c);
                            tagList.remove(test);
                            break;
                        }
                    }
                }
                tText.setText("");
                return false;
            }
        });

        tText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                    tText.setText("");
                }
            }
        });

        Bundle b = getArguments();
        FlashCard card;
        try {
            card = (FlashCard) b.getSerializable("Card");
        } catch (NullPointerException e) {
            card = null;
        }
        FlashCard oldCard = card;

        File cardFolder = new File(getContext().getFilesDir(), "flashcards");
        File t = null;

        if (card != null) { // If we're editing a card and not creating one.
            String hash = card.getHash();
            t = new File(cardFolder, hash + ".ser");
            qText.setText(card.getQuestion());
            aText.setText(card.getAnswer());
            hText.setText(card.getHint());
            for (Tag tag : card.getTags()) {
                Chip c = new Chip(getContext());
                c.setText(tag.getName());
                c.setCloseIconResource(android.R.drawable.ic_delete);
                c.setCloseIconVisible(true);

                c.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tagList.add(tag.getName());
                        ((ViewManager)c.getParent()).removeView(c);
                    }
                });

                group.addView(c);
                tagList.remove(tag.getName());
            }
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

                for (int i = 0; i < group.getChildCount(); i++) {
                    Chip c = (Chip) group.getChildAt(i);
                    Tag tag = tagMap.get(c.getText());
                    if (tag != null) {
                        tags.add(tag);
                    }
                }

                if (question.isEmpty()) {
                    String e = getResources().getString(R.string.question_needed);
                    Toast.makeText(getContext(), e, Toast.LENGTH_SHORT).show();
                } else if (answer.isEmpty()) {
                    String e = getResources().getString(R.string.answer_needed);
                    Toast.makeText(getContext(), e, Toast.LENGTH_SHORT).show();
                } else {

                    if (currentFile != null) {
                        currentFile.delete();
                    }

                    FlashCard card = new FlashCard(question, answer, hint, wrongAnswers, tags);

                    File tagFolder = new File(getContext().getFilesDir(), "tags");
                    for (Tag tag : card.getTags()) {
                        tag.addFlashCard(card);
                    }
                    if (oldCard != null) {
                        File quizFolder = new File(getContext().getFilesDir(), "quizzes");
                        for (File quizFile : quizFolder.listFiles()) {
                            Quiz quiz = Quiz.importQuiz(quizFile);
                            Set<FlashCard> toAdd = new HashSet<FlashCard>();
                            Set<FlashCard> toRemove = new HashSet<FlashCard>();
                            for (FlashCard qcard : quiz.getFlashCards()) {
                                if (qcard.getHash().equalsIgnoreCase(oldCard.getHash())) {
                                    toRemove.add(qcard);
                                    toAdd.add(card);
                                }
                            }
                            for (FlashCard qcard : toRemove) {
                                quiz.removeFlashCard(qcard);
                            }
                            for (FlashCard qcard : toAdd) {
                                quiz.addFlashCard(qcard);
                            }
                            quiz.exportQuiz(quizFolder);
                        }
                    }
                    card.exportFlash(cardFolder);

                    Bundle b = new Bundle();
                    b.putSerializable("Card", card);
                    NavHostFragment.findNavController(Fragment_CreateFlashCard.this)
                            .navigate(R.id.action_fragment_CreateFlashCard_to_fragment_FlashCardVertical, b);

                }
            }
        });
    }
}