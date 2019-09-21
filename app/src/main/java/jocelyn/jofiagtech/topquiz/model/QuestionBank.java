package jocelyn.jofiagtech.topquiz.model;

import java.util.Collections;
import java.util.List;

public class QuestionBank
{
    private List<Question> mQuestionList;
    private int mNextQuestionIndex;

    public QuestionBank(List<Question> questionList)
    {
        mQuestionList = questionList;

        //Mélanger la liste des questions
        Collections.shuffle(mQuestionList);

        mNextQuestionIndex = 0;
    }

    public Question getQuestion()
    {
        //Réinitialise l'index à 0 lorsqu'on est à la dernière position
        if (mNextQuestionIndex == mQuestionList.size())
            mNextQuestionIndex = 0;

        //On retourne l'index d'une question
        return mQuestionList.get(mNextQuestionIndex++);
    }
}
