package jocelyn.jofiagtech.topquiz.controlleur;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import jocelyn.jofiagtech.topquiz.R;
import jocelyn.jofiagtech.topquiz.model.Question;
import jocelyn.jofiagtech.topquiz.model.QuestionBank;


public class GameActivity extends AppCompatActivity implements View.OnClickListener
{
    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    private TextView mQuestionTextView;
    private Button mAnswerButton1;
    private Button mAnswerButton2;
    private Button mAnswerButton3;
    private Button mAnswerButton4;

    private int mScore;
    private int mNumberOfQuestions;

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionBank = this.generateQuestions();

        //Référence des éléments graphiques
        mQuestionTextView = findViewById(R.id.activity_game_question);
        mAnswerButton1 = findViewById(R.id.activity_game_answer1_btn);
        mAnswerButton2 = findViewById(R.id.activity_game_answer2_btn);
        mAnswerButton3 = findViewById(R.id.activity_game_answer3_btn);
        mAnswerButton4 = findViewById(R.id.activity_game_answer4_btn);

        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);

        //Attribution d'un identifiant à chque bouton afin de trouver celui cliqué
        mAnswerButton1.setTag(0);
        mAnswerButton2.setTag(1);
        mAnswerButton3.setTag(2);
        mAnswerButton4.setTag(3);

        //Activation du Listener (qui est la claase elle-même) sur les bouton
        mAnswerButton1.setOnClickListener(this);
        mAnswerButton2.setOnClickListener(this);
        mAnswerButton3.setOnClickListener(this);
        mAnswerButton4.setOnClickListener(this);

        mScore = 0;
        mNumberOfQuestions = 4;
    }

    @Override
    public void onClick(View v) //v : c'est le bouton sur lequel l'utilisateur a cliquer
    {
        //On enrégistre le bouton sur lequel l'utilisateur a cliqué
        //on utilise un cast car la méthode getTag() renvoit un objet par défaut
        int answerChoosedIndex = (int)v.getTag();

        //Vérification de la réponse choisie par l'utilisateur
        if(answerChoosedIndex == mCurrentQuestion.getAnswerIndex())
        {
            //On affiche (show()) correct (text) dans l'activité GameActivity (context)
            //pendant une courte durrée (Toast.LENGHT_SHORT)
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            mScore++;
        }
        else
        {
            //Si non, on affiche Wrong
            Toast.makeText(this, "Wrong !", Toast.LENGTH_SHORT).show();
        }

        // Affichage du score et arrêt du jeu après que l'utilisateur ai 4 questions posées.
        if (--mNumberOfQuestions == 0)
        {
            //Affichage du score et fin du jeu.
            endGame();
        }
        else
        {
            //Si non, on passe à la question suivante.
            mCurrentQuestion = mQuestionBank.getQuestion();
            displayQuestion(mCurrentQuestion);
        }

    }

    private void endGame()
    {
        //Affichage du score dans une boite de dialogue
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Well done!")
                .setMessage("Your score is " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // Sauvegarde et envoit du score à l'activité précédente (MainActivity)
                        Intent intent = new Intent(); //Il est nécéssaire d'envoyer le score à MainActivity sous forme d'intent
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore); //On envoit le score dans notre intent
                        setResult(RESULT_OK, intent); //RESULT_OK indique qu'il n'y a pas eu de problème
                        //Fin de l'activité et retour à celle précédente
                        finish();
                    }
                })
                .create()
                .show();
    }

    public QuestionBank generateQuestions()
    {
        Question question1 = new Question("Who is the creator of Android?",
                Arrays.asList("Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"),
                0);

        Question question2 = new Question("When did the first man land on the moon?",
                Arrays.asList("1958",
                        "1962",
                        "1967",
                        "1969"),
                3);

        Question question3 = new Question("What is the house number of The Simpsons?",
                Arrays.asList("42",
                        "101",
                        "666",
                        "742"),
                3);

        return new QuestionBank(Arrays.asList(question1, question2, question3));
    }

    public void displayQuestion(final Question question)
    {
        mQuestionTextView.setText(question.getQuestion());
        mAnswerButton1.setText(question.getChoiceList().get(0));
        mAnswerButton2.setText(question.getChoiceList().get(1));
        mAnswerButton3.setText(question.getChoiceList().get(2));
        mAnswerButton4.setText(question.getChoiceList().get(3));
    }
}
