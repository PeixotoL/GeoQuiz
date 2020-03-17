package com.bignerdranch.android.geoquiz;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class QuizActivity extends AppCompatActivity {
    //Making Log MSG
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
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

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }
    private void checkAnswer(boolean userPressedTrue){
        //Log.id(String, String, Throwable);
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if(userPressedTrue == answerIsTrue){
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Calling the super class implementation before the LOG.d is critical for onCreate. For others is less, but is a good practice overall.
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
       /* int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);*/

        mTrueButton = (Button) findViewById(R.id.true_button);
        //Listener to inform when the Button know as mTrueButton has been pressed.
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(QuizActivity.this, R.string.incorrect_toast,
                        Toast.LENGTH_SHORT).show();*/
                checkAnswer(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(QuizActivity.this, R.string.correct_toast,
                        Toast.LENGTH_SHORT).show();*/
                checkAnswer(false);
            }
        });
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (++mCurrentIndex) % mQuestionBank.length;
                /*int question = mQuestionBank[mCurrentIndex].getTextResId();
                mQuestionTextView.setText(question);*/
                updateQuestion();
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
        updateQuestion();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
