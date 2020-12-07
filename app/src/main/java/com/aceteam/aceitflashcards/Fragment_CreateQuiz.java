package com.aceteam.aceitflashcards;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Fragment_CreateQuiz extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    )
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_createquiz , container, false);
    }

    public void hideKeyboard(View v) {
        ((InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText qText = getActivity().findViewById(R.id.quiz_name);
        List<FlashCard> quizlist = new ArrayList<>();


        view.findViewById(R.id.createquiz_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Fragment_CreateQuiz.this)
                        .navigate(R.id.action_fragment_CreateQuiz_to_fragment_Quizlist );
            }
        });


        view.findViewById(R.id.createquiz_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set<FlashCard> quizset = new HashSet<>(quizlist);
                String name = qText.getText().toString().trim();
                Quiz qzz = new Quiz(name, 5, quizset);
                File quizfolder = new File(getContext().getFilesDir(), "quizzes");

                    qzz.exportQuiz(quizfolder);

                NavHostFragment.findNavController(Fragment_CreateQuiz.this)
                        .navigate(R.id.action_fragment_CreateQuiz_to_fragment_Quizlist);
            }

        });

        qText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                view.findViewById(R.id.createquiz_back).performClick();
            }
        };

        //For edditing a quiz
        Bundle b = getArguments();
        Quiz q;
        try {
            q = (Quiz) b.getSerializable("Card");
        } catch (NullPointerException e) {
            q = null;
        }
        File cardFolder = new File(getContext().getFilesDir(), "quizzes");
        File t = null;

        // The q variable  is used to make the quiz variable effectively final,
        // which is needed in order to be used in the onClick method.
        List<FlashCard> cardList = new ArrayList<>();
        File folder = new File(getContext().getFilesDir(), "flashcards");
        for (File cardFile : folder.listFiles()) {
            FlashCard C = FlashCard.importFlash(cardFile);
            cardList.add(C);
            if (q == null) {

            } else {
                Quiz qq = q;
                String hash = qq.getHash();
                t = new File(cardFolder, hash + ".ser");
                qText.setText(qq.getName());
                Set<FlashCard> newFlashCards = qq.getFlashCards() ;
                if(newFlashCards.contains(C) == true) {
                view.setBackgroundColor(Color.LTGRAY);

                }
        }



//        List<FlashCard> cardList = new ArrayList<>();
//        File folder = new File(getContext().getFilesDir(), "flashcards");
//        for (File cardFile : folder.listFiles()) {
//            FlashCard C = FlashCard.importFlash(cardFile);
//            cardList.add(C);
//            if(q != null){
//                Set<FlashCard> newFlashCards = qq.getFlashCards() ;
//
//
//            }
      }
        LinearLayout layout = view.findViewById(R.id.createquiz_layout);

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

        for (FlashCard card : cardList) {
            if (card == null) continue;
            TextView text = new TextView(getContext());
            text.setText(card.getQuestion());
            text.setPadding(10, 10, 10, 10);

            MaterialCardView cardView = new MaterialCardView(getContext());
            cardView.setStrokeColor(Color.BLACK);
            cardView.setStrokeWidth(1);
            cardView.setRadius(10);
            cardView.setLayoutParams(p);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Add Flashcard to Quiz when selected

                    int color = Color.TRANSPARENT;
                    Drawable background = view.getBackground();
                    if (background instanceof ColorDrawable)
                        color = ((ColorDrawable) background).getColor();
                    if (color == Color.LTGRAY)
                    {
                        view.setBackgroundColor(Color.WHITE);
                        Bundle b = new Bundle();
                        b.putSerializable("Card", card);
                        quizlist.remove(card);
                    }
                    else
                        {
                        Bundle bb = new Bundle();
                        bb.putSerializable("Card", card);
                        view.setBackgroundColor(Color.LTGRAY);
                        quizlist.add(card);
                        }
                }
            });

            cardView.addView(text);
            layout.addView(cardView);

        }

        Space space = new Space(getContext());
        int px = (int) Math.floor(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 75, getResources().getDisplayMetrics()));
        space.setMinimumHeight(px);
        layout.addView(space);

    }
}




