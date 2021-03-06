package com.example.pictionary;


import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import com.example.pictionary.R;
import android.os.Bundle;
import android.app.Activity;

/**
 * // -------------------------------------------------------------------------
 * /** The ScoreUpdateActivity is where the player is told their current score
 * for the round, and it is also where the player is to press the button in
 * order to begin their drawing turn.
 *
 * @author Pictionary Team (Chris Deisher, Edward McEnrue, Michael Liu)
 * @version Apr 16, 2014
 */
public class ScoreUpdateActivity
    extends Activity
{
    /**
     * Creates the new activity for ScoreUpdateActivity and unpacks the
     * intent from GuessActivity.
     *
     * @param savedInstanceState
     *            The state of the running application.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_update);

        TextView scoreDisplay = (TextView)findViewById(R.id.scoreDisplay);
        TextView winnerDisplay = (TextView)findViewById(R.id.winnerDisplay);
        Button newMatchButton = (Button)findViewById(R.id.startNewMatch);

        GameController gameState = ((GameController)getApplicationContext());

        String state = "" + gameState.getScoreList();

        scoreDisplay.setText(""+ state);

        int winner = gameState.isWon();
        if(winner == -1)
        {
            winnerDisplay.setText("Who will win?");
        }
        else
        {
            winnerDisplay.setText("player " + winner + " won!");
            newMatchButton.setEnabled(false);
        }
    }

    // ----------------------------------------------------------
    /**
     * Overrides the back button to direct to nothing
     */
    @Override
    public void onBackPressed()
    {
        //Left blank intentionally
    }

    /**
     * Listens for the beginNewRound button to be clicked, wherein this method
     * will start the DrawActivity again. This will start the beginning of
     * a new round of the game, and the guesser becomes drawer.
     *
     * @param view
     *            The button view named "beginNewRound"
     */
    public void beginNewRound(View view)
    {
        // Build an intent and the key value pair in response to the button.
        Intent newRoundIntent = new Intent(this, DrawActivity.class);
        startActivity(newRoundIntent);
    }
}
