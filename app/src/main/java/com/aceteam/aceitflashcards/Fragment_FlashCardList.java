package com.aceteam.aceitflashcards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;

public class Fragment_FlashCardList extends Fragment {

    List<FlashCard> cardList = new ArrayList<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flashcard_list, container, false);

        // TODO: Import all the flashcards and put them in cardList.
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.flashcardlist_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Fragment_FlashCardList.this)
                        .navigate(R.id.action_fragment_FlashCardList_to_fragment_MainMenu);
            }
        });

        view.findViewById(R.id.flashcardlist_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Fragment_FlashCardList.this)
                        .navigate(R.id.action_fragment_FlashCardList_to_fragment_CreateFlashCard);
            }
        });

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.flashcardlist_cardlayout);

        for (FlashCard card : cardList) {
            // TODO: Fill the container with flashcards.
            TextView text = new TextView(getContext());
            text.setText(card.getQuestion());
            text.setPadding(10, 10, 10, 10);
            layout.addView(text);
        }
    }
}