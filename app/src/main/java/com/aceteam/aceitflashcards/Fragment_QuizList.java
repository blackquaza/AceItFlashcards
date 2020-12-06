package com.aceteam.aceitflashcards;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


public class Fragment_QuizList  extends Fragment{


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_list, container, false);
    }

    @SuppressWarnings("deprecation")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.quizlist_back2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Fragment_QuizList.this)
                        .navigate(R.id.action_fragment_Quizlist_to_fragment_MainMenu);
            }
        });

        view.findViewById(R.id.quizlist_new2 ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Fragment_QuizList.this)
                        .navigate(R.id.action_fragment_Quizlist_to_fragment_CreateQuiz);
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                view.findViewById(R.id.quizlist_back2 ).performClick();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        List<Quiz> cardList = new ArrayList<>();

        File folder = new File(getContext().getFilesDir(), "quizzes");
        for (File cardFile : folder.listFiles()) {
            cardList.add(Quiz.importQuiz(cardFile));
        }

        LinearLayout layout = view.findViewById(R.id.quizlist_cardlayout );

        for (Quiz card : cardList) {
            if (card == null) continue;
            TextView text = new TextView(getContext());
            text.setText(card.getName() );
            text.setPadding(10, 10, 10, 10);

            MaterialCardView cardView = new MaterialCardView(getContext());
            cardView.setStrokeColor(Color.BLACK);
            cardView.setStrokeWidth(1);
            cardView.setRadius(10);

            // Fancy stuff here to get the actual height of the screen so I
            // can scale cards to the proper size. Uses newly deprecated code
            // (as in, deprecated in Android 11), so we're not using the new
            // code for backwards compatibility.
            Point s = new Point();
            ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getSize(s);

            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    s.y / 4
            );
            p.setMargins(10, 10, 10, 10);
            cardView.setLayoutParams(p);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Bundle b = new Bundle();
                    b.putSerializable("Card", card);
                    NavHostFragment.findNavController(Fragment_QuizList.this)
                            .navigate(R.id.action_fragment_Quizlist_to_fragment_quiz_flashcard_list, b);
                }
            });


            cardView.addView(text);
            layout.addView(cardView);

        }
    }
}
