package com.aceteam.aceitflashcards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Fragment_CreateQuiz extends Fragment {

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
                NavHostFragment.findNavController(Fragment_CreateQuiz.this)
                        .navigate(R.id.action_fragment_CreateQuiz_to_fragment_Quizlist );
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                view.findViewById(R.id.createflashcard_back).performClick();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        view.findViewById(R.id.createflashcard_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText qText = getActivity().findViewById(R.id.createflashcard_questioninput);
                String question = qText.getText().toString().trim();
                EditText aText = getActivity().findViewById(R.id.createflashcard_answerinput);
                String answer = aText.getText().toString().trim();
                EditText hText = getActivity().findViewById(R.id.createflashcard_hintinput);
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
                    File cardFolder = new File(getContext().getFilesDir(), "flashcards");
                    card.exportFlash(cardFolder);

                    Bundle b = new Bundle();
                    b.putSerializable("Card", card);
                    NavHostFragment.findNavController(Fragment_CreateQuiz.this)
                            .navigate(R.id.action_fragment_CreateFlashCard_to_fragment_FlashCardVertical, b);

                }
            }
        });
    }
}