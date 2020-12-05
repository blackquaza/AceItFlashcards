package com.aceteam.aceitflashcards;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import java.util.HashSet;
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

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.createflashcard_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Fragment_CreateFlashCard.this)
                        .navigate(R.id.action_fragment_CreateFlashCard_to_fragment_FlashCardList);
            }
        });

        view.findViewById(R.id.createflashcard_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText qText = getActivity().findViewById(R.id.createflashcard_questioninput);
                String question = qText.getText().toString().trim();
                EditText aText = getActivity().findViewById(R.id.createflashcard_answerinput);
                String answer = aText.getText().toString().trim();

                String hint = "";
                Set<String> wrongAnswers = new HashSet<String>();
                Set<Tag> tags = new HashSet<Tag>();

                FlashCard card = new FlashCard(question, answer, hint, wrongAnswers, tags);
                card.exportFlash();

                Bundle b = new Bundle();
                b.putSerializable("Card", card);
                NavHostFragment.findNavController(Fragment_CreateFlashCard.this)
                        .navigate(R.id.action_fragment_CreateFlashCard_to_fragment_FlashCardVertical, b);
            }
        });
    }
}