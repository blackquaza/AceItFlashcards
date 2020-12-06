package com.aceteam.aceitflashcards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class Fragment_FlashCard extends Fragment {

    boolean showQ = true;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flashcard, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.flashcard_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Fragment_FlashCard.this)
                        .navigate(R.id.action_fragment_FlashCardVertical_to_fragment_FlashCardList);
            }
        });
        view.findViewById(R.id.flashcard_hint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Hint: ",Toast.LENGTH_SHORT).show();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                view.findViewById(R.id.flashcard_back).performClick();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        Bundle b = getArguments();
        FlashCard t;
        try {
            t = (FlashCard) b.getSerializable("Card");
        } catch (NullPointerException e) {
            t = null;
        }
        // The t variable  is used to make the card variable effectively final,
        // which is needed in order to be used in the onClick method.
        FlashCard card = t;

        TextView box = view.findViewById(R.id.flashcard_textbox);
        box.setText(card.getQuestion());

        view.findViewById(R.id.flashcard_textbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showQ) {
                    box.setText(card.getAnswer());
                    showQ = false;
                } else {
                    box.setText(card.getQuestion());
                    showQ = true;
                }
            }
        });
    }
}