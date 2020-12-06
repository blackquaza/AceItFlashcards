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
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Fragment_TagList extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tag_list, container, false);
    }

    @SuppressWarnings("deprecation")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.taglist_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Fragment_TagList.this)
                        .navigate(R.id.action_fragment_TagList_to_fragment_MainMenu);
            }
        });

        view.findViewById(R.id.taglist_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Fragment_TagList.this)
                        .navigate(R.id.action_fragment_TagList_to_fragment_CreateTag);
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                view.findViewById(R.id.taglist_back).performClick();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        List<Tag> tagList = new ArrayList<>();

        File folder = new File(getContext().getFilesDir(), "tags");
        for (File tagFile : folder.listFiles()) {
            tagList.add(Tag.importTag(tagFile));
        }

        LinearLayout layout = view.findViewById(R.id.taglist_cardlayout);

        // Fancy stuff here to get the actual height of the screen so I
        // can scale cards to the proper size. Uses newly deprecated code
        // (as in, deprecated in Android 11), so we're not using the new
        // code for backwards compatibility.
        Point s = new Point();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getSize(s);
        int cardHeight = s.y / 10;

        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                cardHeight
        );
        p.setMargins(10, 10, 10, 10);

        for (Tag tag : tagList) {
            if (tag == null) continue;
            TextView text = new TextView(getContext());
            text.setText(tag.getName());
            text.setPadding(10, 10, 10, 10);

            MaterialCardView cardView = new MaterialCardView(getContext());
            cardView.setStrokeColor(Color.BLACK);
            cardView.setStrokeWidth(1);
            cardView.setRadius(10);
            cardView.setLayoutParams(p);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle b = new Bundle();
                    b.putSerializable("Tag", tag);

                    // TODO: Code an edit screen?
                    //NavHostFragment.findNavController(Fragment_TagList.this)
                    //        .navigate(R.id.action_fragment_FlashCardList_to_fragment_FlashCardVertical, b);
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