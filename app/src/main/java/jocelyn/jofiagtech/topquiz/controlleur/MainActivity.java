package jocelyn.jofiagtech.topquiz.controlleur;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import jocelyn.jofiagtech.topquiz.R;
import jocelyn.jofiagtech.topquiz.model.User;

public class MainActivity extends AppCompatActivity
{
    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private User mUser;
    public static final int GAME_ACTIVITY_REQUEST_CODE = 2;
    private SharedPreferences mPreferences;     //Les préferences de l'utiliateur (gérées par l'API SharedPreferences)

    // Récupération du résultat envoyé par la GameActivity
    // onActivityResult() est appelée lorsqu'une activité renvoit un résultat
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode)
        {
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
            //0 est la valeur qui sera affectée au score si on arrive pas à récupérer le scor envoyé par la GameActivity.

            //Sauvegarde du score dans les préférences
            mPreferences.edit().putInt("score", score).apply();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUser = new User();

        //Initialisation de mPreferences en mode privé pour éviter que les autres appli du téléphone y est accès
        // car cette variable est enrégistrée dans la mémoire du téléphone
        mPreferences = getPreferences(MODE_PRIVATE);

        //Référence des éléments graphiques
        mGreetingText = findViewById(R.id.activity_main_greeting_txt);
        mNameInput = findViewById(R.id.activity_main_name_input);
        mPlayButton = findViewById(R.id.activity_main_play_btn);

        //Désactivation du bouton de l'interface
        mPlayButton.setEnabled(false);

        //notification lorsque l'utilisateur commence à remplir le champ
        mNameInput.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

             //Chaque fois que l'utilisateur saisit un lettre onTextChanged est appelée afin de savoir
            //s'il a commencé à saisir son prénom
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                //On active le bouton dès que la taille de la chaine de caractères entré est
                //différent de 0.
                mPlayButton.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        //Détection du clic sur le bouton par l'utilisateur.
        mPlayButton.setOnClickListener(new View.OnClickListener()
        {
            //La méthode onClick() est appelée chaque fois que l'utilisateur clique sur le bouton
            //C'est dans cette fonction qu'il faut lancer le jeu.
            @Override
            public void onClick(View v)
            {
                //Récupération puis enegistrement du prénom entré par l'utilisateur (son prénom)
                String firstname = mNameInput.getText().toString();
                mUser.setFirstName(firstname);

                //Sauvegarde du prénom dans les préférences
                mPreferences.edit().putString("firstname", mUser.getFirstName()).apply();

                //Lancement de GameActivity lorsque le bouton est cliqué
                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(gameActivityIntent);
                //Lancer l'activité et obtenir une donnée
                // le deuxième paramètre est l'identifiant de l'activité à lancer
                startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);
            }
        });
    }
}
