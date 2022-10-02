package com.example.unicodelookup;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView character, description, info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        character = findViewById(R.id.DetailCharacterTextView);
        description = findViewById(R.id.DetailDescTextView);
        info = findViewById(R.id.DetailInfotextView);

        if (getIntent().hasExtra("UNICODE_OBJECT")) {
            Bundle bundle = getIntent().getExtras();
            Unicode obj = (Unicode) bundle.get("UNICODE_OBJECT");

            character.setText(obj.getCharacter());
            description.setText(obj.getName());
        }
    }
}