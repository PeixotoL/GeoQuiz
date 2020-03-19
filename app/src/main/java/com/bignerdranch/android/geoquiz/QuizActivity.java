package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class QuizActivity extends AppCompatActivity {
    //Making Log MSG
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEATER = "cheater";
    private static final String KEY_CHEATED_QUESTIONS = "cheated_questions";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;

    //    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    private Questions[] mQuestionBank = new Questions[]{
            new Questions(R.string.question_oceans, true),
            new Questions(R.string.question_mideast, false),
            new Questions(R.string.question_africa, false),
            new Questions(R.string.question_america, true),
            new Questions(R.string.question_asia, true)
    };
    private int mCurrentIndex = 0;
    private boolean mIsCheater;
    private int [] mCheatedQuestions = new int[mQuestionBank.length];

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        //Log.id(String, String, Throwable);
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (mIsCheater || mCheatedQuestions[mCurrentIndex] == 1) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Calling the super class implementation before the LOG.d is critical
        // for onCreate. For others is less, but is a good practice overall.
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEATER, false);
            mCheatedQuestions =
                    (int[]) savedInstanceState.getSerializable(KEY_CHEATED_QUESTIONS);
            Log.d(TAG, "inside: " + mCurrentIndex + ", " + mIsCheater);
        }

        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        //Listener to inform when the Button know as mTrueButton has been
        // pressed.
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 checkAnswer(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsCheater){
                    mCheatedQuestions[mCurrentIndex] = 1;
                    mIsCheater = false;
                    updateQuestion();
                } else {
                    mCurrentIndex = (++mCurrentIndex) % mQuestionBank.length;
                    mIsCheater = false;
                    updateQuestion();
                }
            }
        });
      /*  mPrevButton = (ImageButton)findViewById(R.id.prev_img_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex == 0){
                    updateQuestion();
                }else {
                    mCurrentIndex = (--mCurrentIndex) % mQuestionBank.length;
                    updateQuestion();
                }
            }
        });*/
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue =
                        mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this,
                        answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);

            }
        });
        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,
                "onActivityResult_ResquestCode: " + requestCode + ", " + resultCode);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                Log.d(TAG, "data == null");
                return;
            } else {
                mIsCheater = CheatActivity.wasAnswerShown(data);
                Log.d(TAG, "setting: " + mIsCheater);
            }

        }
    }

    //This method is normally called by the system before onPause(), onStop()
    // , onDestroy().
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_CHEATER, mIsCheater);
        savedInstanceState.putSerializable(KEY_CHEATED_QUESTIONS, mCheatedQuestions);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
