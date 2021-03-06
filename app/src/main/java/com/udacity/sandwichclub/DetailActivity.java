package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //    Detail activity variable

    private ImageView ingredientsIv;
    private TextView descriptionTv;
    private TextView placeOfOriginTv;
    private TextView alsoKnownAsTv;
    private TextView ingredientsTv;
    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

     //getting content by find view by Id
        ingredientsIv = findViewById(R.id.image_iv);
        descriptionTv = findViewById(R.id.description_tv);
        placeOfOriginTv = findViewById(R.id.origin_tv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();


    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


    private void populateUI() {
        Picasso.get()
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

        if (sandwich.getDescription().isEmpty()) {
            descriptionTv.setText(R.string.noResult);
        } else {
            descriptionTv.setText(sandwich.getDescription());
        }

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            placeOfOriginTv.setText(R.string.noResult);
        } else {
            placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnownAsTv.setText(R.string.noResult);
        } else {
            alsoKnownAsTv.setText(TextUtils.join("\n", sandwich.getAlsoKnownAs()));
        }

        if (sandwich.getIngredients().isEmpty()) {
            ingredientsTv.setText(R.string.noResult);
        } else {
            ingredientsTv.setText(TextUtils.join("\n", sandwich.getIngredients()));
        }

    }

}
