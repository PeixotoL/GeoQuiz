package com.bignerdranch.android.geoquiz;

public class Questions {
    //mTextResId will hold the resource ID(always an int) of a string resource for the question.
    private int mTextResId;
    private boolean mAnswerTrue;

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        this.mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.mAnswerTrue = answerTrue;
    }

    public Questions(int textResId, boolean answerTrue){
        this.mTextResId = textResId;
        this.mAnswerTrue = answerTrue;
    }
}
