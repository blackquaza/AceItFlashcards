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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Fragment_FlashCardList extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flashcard_list, container, false);
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

        List<FlashCard> cardList = new ArrayList<>();

        File folder = new File(getContext().getFilesDir(), "flashcards");
        for (File cardFile : folder.listFiles()) {
            cardList.add(FlashCard.importFlash(cardFile.getName()));
        }

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.flashcardlist_cardlayout);

        for (FlashCard card : cardList) {
            if (card == null) continue;
            TextView text = new TextView(getContext());
            text.setText(card.getQuestion());
            text.setPadding(10, 10, 10, 10);
            layout.addView(text);
        }
    }
}