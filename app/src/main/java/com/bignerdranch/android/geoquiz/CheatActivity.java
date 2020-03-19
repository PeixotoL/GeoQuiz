package com.bignerdranch.android.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch" +
            ".android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch" +
            ".android" +
            ".geoquiz.answer_show";
    private static final String KEY_ANSWER_SHOWN = "answer_shown";

    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private boolean isAnswerShown = false;


    public static Intent newIntent(Context packageContext,
                                   boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Cheat - onCreate(Bundle) Called");
        setContentView(R.layout.activity_cheat);

        if(savedInstanceState != null){
            isAnswerShown = savedInstanceState.getBoolean(KEY_ANSWER_SHOWN,false);
            setAnswerShownResult(isAnswerShown);
        } else {
            mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,
                    false);
            mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

            mShowAnswer = (Button) findViewById(R.id.show_answer_button);
            mShowAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAnswerIsTrue) {
                        mAnswerTextView.setText(R.string.true_button);
                    } else {
                        mAnswerTextView.setText(R.string.false_button);
                    }
                    isAnswerShown = true;
                    setAnswerShownResult(true);
                }
            });
        }
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Log.d(TAG, "setAnswerShownResult");
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState");
        savedInstanceState.putBoolean(KEY_ANSWER_SHOWN,isAnswerShown);
    }
}
