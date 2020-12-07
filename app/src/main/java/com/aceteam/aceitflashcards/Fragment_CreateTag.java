package com.aceteam.aceitflashcards;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.chip.Chip;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Fragment_CreateTag extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_createtag, container, false);
    }

    public void hideKeyboard(View v) {
        ((InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.createtag_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Fragment_CreateTag.this)
                        .navigate(R.id.action_fragment_CreateTag_to_fragment_TagList);
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                view.findViewById(R.id.createtag_back).performClick();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        EditText tagText = getActivity().findViewById(R.id.createtag_nameinput);

        tagText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        Bundle b = getArguments();
        Tag tag;
        try {
            tag = (Tag) b.getSerializable("Tag");
        } catch (NullPointerException e) {
            tag = null;
        }
        Tag oldTag = tag;

        File tagFolder = new File(getContext().getFilesDir(), "tags");
        File t = null;
        Set<FlashCard> t2 = new HashSet<>();

        if (tag != null) { // If we're editing a card and not creating one.
            t = new File(tagFolder, tag.getName() + ".ser");
            tagText.setText(tag.getName());
            t2 = tag.getFlashCards();
        }
        // The t variable  is used to make the currentFile variable effectively final,
        // which is needed in order to be used in the onClick method.
        File currentFile = t;
        Set<FlashCard> cardSet = t2;

        view.findViewById(R.id.createtag_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = tagText.getText().toString().trim();

                if (name.isEmpty()) {
                    String e = getResources().getString(R.string.question_needed);
                    Toast.makeText(getContext(), e, Toast.LENGTH_SHORT).show();
                } else {

                    if (currentFile != null) {
                        currentFile.delete();
                    }

                    Tag tag = new Tag(name);
                    File cardFolder = new File(getContext().getFilesDir(), "flashcards");
                    for (File cardFile : cardFolder.listFiles()) {
                        FlashCard card = FlashCard.importFlash(cardFile);
                        for (Tag cardTag : card.getTags()) {
                            if (cardTag.getName().equalsIgnoreCase(oldTag.getName())) {
                                card.removeTag(card.findTagbyName(oldTag.getName()));
                                card.addTag(tag);
                                tag.addFlashCard(card);
                                card.exportFlash(cardFolder);
                            }
                        }
                    }

                    File tagFolder = new File(getContext().getFilesDir(), "tags");
                    tag.exportTag(tagFolder);

                    NavHostFragment.findNavController(Fragment_CreateTag.this)
                            .navigate(R.id.action_fragment_CreateTag_to_fragment_TagList);

                }
            }
        });
    }
}