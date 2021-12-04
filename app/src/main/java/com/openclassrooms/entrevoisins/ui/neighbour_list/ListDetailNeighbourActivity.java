package com.openclassrooms.entrevoisins.ui.neighbour_list;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListDetailNeighbourActivity  extends AppCompatActivity {

    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.imageViewAvatar)
    ImageView mImageViewAvatar;
    @BindView(R.id.textViewName)
    TextView mTextViewName;
    @BindView(R.id.textViewAddress)
    TextView mMTextViewAddress;
    @BindView(R.id.textViewPhone)
    TextView mTextViewPhoneNumber;
    @BindView(R.id.textViewAboutMe)
    TextView mTextViewAboutMe;
    @BindView(R.id.floatingActionBtn)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.toolBarArrow)
    Toolbar mToolbar;

    private NeighbourApiService mApiService = DI.getNeighbourApiService();
    private Neighbour mNeighbour;

    private int imageResStar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail_neighbour);

        ButterKnife.bind(this);

        init();

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabClick();
            }
        });

        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void init() {
        if (getIntent().hasExtra("SelectedNeighbour")) {
            mNeighbour = getIntent().getParcelableExtra("SelectedNeighbour");

            mCollapsingToolbarLayout.setTitle(mNeighbour.getName());
            mTextViewName.setText(mNeighbour.getName());
            mMTextViewAddress.setText(mNeighbour.getAddress());
            mTextViewPhoneNumber.setText(mNeighbour.getPhoneNumber());
            mTextViewAboutMe.setText(mNeighbour.getAboutMe());

            Glide.with(this)
                    .load(mNeighbour.getAvatarUrl())
                    .centerCrop()
                    .into(mImageViewAvatar);
        }
    }

    private void fabClick() {
        if (mApiService.doesExistFavorite(mNeighbour)) {
            mApiService.deleteFavorite(mNeighbour);
            mFloatingActionButton.setImageResource(R.drawable.ic_star_white_24dp);
        } else {
            mApiService.addFavorite(mNeighbour);
            mFloatingActionButton.setImageResource(R.drawable.ic_star_yellow_24dp);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        if (mFloatingActionButton.isSelected()) {
            savedInstanceState.putInt("FavoriteButtonClicked", R.drawable.ic_star_yellow_24dp);
        } else {
            savedInstanceState.putInt("FavoriteButtonUnclicked", R.drawable.ic_star_white_24dp);
        }

    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (mFloatingActionButton.isSelected()) {
            imageResStar = savedInstanceState.getInt("FavoriteButtonClicked");
            mFloatingActionButton.setImageResource(imageResStar);
        } else {
            imageResStar = savedInstanceState.getInt("FavoriteButtonUnclicked");
            mFloatingActionButton.setImageResource(imageResStar);
        }
    }
}

