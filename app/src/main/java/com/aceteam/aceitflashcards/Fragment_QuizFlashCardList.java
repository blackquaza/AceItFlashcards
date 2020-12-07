package com.aceteam.aceitflashcards;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Fragment_QuizFlashCardList extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_flashcard_list, container, false);
    }

    @SuppressWarnings("deprecation")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.quizflashcardlist_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Fragment_QuizFlashCardList.this)
                        .navigate(R.id.action_fragment_quiz_flashcard_list_to_fragment_Quizlist);
            }
        });


        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                view.findViewById(R.id.quizflashcardlist_back).performClick();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        //Get quiz from previous page
        List<FlashCard> cardList = new ArrayList<>();
        Bundle b = getArguments();
        Quiz q;
        try {
            q = (Quiz) b.getSerializable("Card");
        } catch (NullPointerException e) {
            q = null;
        }
        // The q variable  is used to make the quiz variable effectively final,
        // which is needed in order to be used in the onClick method.
        Quiz qq = q;
        Set<FlashCard> newFlashCards = qq.getFlashCards() ;


        File folder = new File(getContext().getFilesDir(), "flashcards");
        for (File cardFile : folder.listFiles()) {
            cardList.add(FlashCard.importFlash(cardFile));

        }



        LinearLayout layout = view.findViewById(R.id.quizflashcardlist_cardlayout);

        // Fancy stuff here to get the actual height of the screen so I
        // can scale cards to the proper size. Uses newly deprecated code
        // (as in, deprecated in Android 11), so we're not using the new
        // code for backwards compatibility.
        Point s = new Point();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getSize(s);
        int cardHeight = s.y / 4;

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                cardHeight
        );
        p.setMargins(10, 10, 10, 10);

        ConstraintLayout.LayoutParams cp = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        );

        for (FlashCard card : newFlashCards) {
            if (card == null) continue;

            ConstraintLayout cl = new ConstraintLayout(getContext());
            cl.setId(View.generateViewId());

            MaterialCardView cardView = new MaterialCardView(getContext());
            cardView.setId(View.generateViewId());
            cardView.setStrokeColor(Color.BLACK);
            cardView.setStrokeWidth(1);
            cardView.setRadius(10);
            cardView.setLayoutParams(p);

            cardView.addView(cl);
            layout.addView(cardView);

            TextView text = new TextView(getContext());
            text.setId(View.generateViewId());
            text.setText(card.getQuestion());
            text.setPadding(10, 10, 10, 10);

            ChipGroup cg = new ChipGroup(getContext());
            cg.setId(View.generateViewId());

            cl.addView(cg);
            cl.addView(text);

            ConstraintSet cs = new ConstraintSet();
            cs.clone(cl);

            cs.connect(cg.getId(), ConstraintSet.BOTTOM, cl.getId(), ConstraintSet.BOTTOM, 20);
            cs.connect(cg.getId(), ConstraintSet.LEFT, cl.getId(), ConstraintSet.LEFT, 20);
            cs.connect(text.getId(), ConstraintSet.BOTTOM, cl.getId(), ConstraintSet.BOTTOM);
            cs.connect(text.getId(), ConstraintSet.LEFT, cl.getId(), ConstraintSet.LEFT);
            cs.connect(text.getId(), ConstraintSet.TOP, cl.getId(), ConstraintSet.TOP);
            cs.connect(text.getId(), ConstraintSet.RIGHT, cl.getId(), ConstraintSet.RIGHT);
            cs.applyTo(cl);

            for (Tag tag : card.getTags()) {
                Chip c = new Chip(getContext());
                c.setText(tag.getName());
                cg.addView(c);
            }

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle b = new Bundle();
                    b.putSerializable("Card", card);
                    b.putSerializable("Quiz", qq);

                    NavHostFragment.findNavController(Fragment_QuizFlashCardList.this)
                            .navigate(R.id.action_fragment_quiz_flashcard_list_to_fragment_FlashCardVertical, b);
                }
            });
        }

        Space space = new Space(getContext());
        int px = (int) Math.floor(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 75, getResources().getDisplayMetrics()));
        space.setMinimumHeight(px);
        layout.addView(space);

    }
}