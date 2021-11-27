package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListDetailNeighbourActivity  extends AppCompatActivity {

    private ImageView mImageView_CollapsingToolBar;
    @BindView(R.id.textViewName)
    TextView mTextView_name;
    @BindView(R.id.textViewAddress)
    TextView mTextView_address;
    @BindView(R.id.textViewPhone)
    TextView mTextView_phoneNumber;
    @BindView(R.id.textViewAboutMe)
    TextView mTextView_aboutMe;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail_neighbour);

        ButterKnife.bind(this);

        if (getIntent().hasExtra("Selected Neighbour")) {
            Neighbour neighbour = getIntent().getParcelableExtra("Selected Neighbour");
            mTextView_name.setText(neighbour.getName());
            mTextView_address.setText(neighbour.getAddress());
            mTextView_phoneNumber.setText(neighbour.getPhoneNumber());
            mTextView_aboutMe.setText(neighbour.getAboutMe());
        }

        // Toolbar Back button
        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
