package jocelyn.jofiagtech.topquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                //Lancement de GameActivity lorsque le bouton est cliqué
                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(gameActivityIntent);
            }
        });
    }
}
