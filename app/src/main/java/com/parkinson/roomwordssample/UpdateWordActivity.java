package com.parkinson.roomwordssample;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.parkinson.roomwordssample.model.Word;
import com.parkinson.roomwordssample.viewModel.WordViewModel;

public class UpdateWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "unique.key.for.REPLY";
    private EditText mEditWordView;
    private WordViewModel mWordViewModel;
    private int wordId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_word);

        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        mEditWordView = findViewById(R.id.edit_word);
        Button button = findViewById(R.id.button_save);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("word") && intent.hasExtra("id")) {
            String wordText = intent.getStringExtra("word");
            wordId = intent.getIntExtra("id", -1);
            mEditWordView.setText(wordText);
        } else {
            Toast.makeText(this, "No word data received", Toast.LENGTH_SHORT).show();
            finish();
        }

        button.setOnClickListener(
                view -> {
                    String updatedText = mEditWordView.getText().toString().trim();
                    if (!TextUtils.isEmpty(updatedText)) {
                        Word updatedWord = new Word(updatedText);
                        updatedWord.setId(wordId);
                        mWordViewModel.update(updatedWord);
                        Toast.makeText(this, "Word updated!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        mEditWordView.setError("Text required");
                    }
                }
        );
    }
}