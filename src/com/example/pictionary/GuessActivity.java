package com.example.pictionary;


import java.util.TimerTask;
import java.util.Timer;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;
import com.example.pictionary.R;
import android.os.Bundle;
import android.app.Activity;

/**
 * // -------------------------------------------------------------------------
 * /** The GuessActivity is where the next player assumes control and the
 * recorded drawing begins to be displayed. The activity allows the player
 * to enter text and make guesses. If the guess is correct, then the player
 * is awarded a certain amount of points, otherwise, they will continue to make
 * guesses until the time runs out, in which case, no points are awarded.
 *
 * @author Pictionary Team (Chris Deisher, Edward McEnrue, Michael Liu)
 * @version Apr 16, 2014
 */
public class GuessActivity
    extends Activity
{

    private String drawingName;
    private DrawQueue<DrawObject> queue;
    private RedrawingView redrawingView;
    private DrawController controller;
    private Timer timer;
    /**
     * Creates the new activity for GuessActivity and unpacks the
     * intent from StartGuessDialog.
     *
     * @param savedInstanceState
     *            The state of the running application.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);


        Intent drawingIntent = getIntent();
        drawingName = drawingIntent.getStringExtra("drawing_name");
        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            queue = b.getParcelable("Drawing");
        }

        // The redrawingView is given the queue.
        redrawingView = (RedrawingView) this.findViewById(R.id.redrawingView);
        System.out.println(redrawingView);
        controller = new DrawController(queue);
        controller.setWord(drawingName);

        // The Ui thread handles the speed at which the drawing is redrawn.
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (controller.hasNext()) {
                            DrawObject step = controller.pop();
                            redrawingView.step(step);
                        }
                    }
                });
            }
        }, 0, 100);
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
     * Listens for the finishGuessing button to be clicked, wherein this
     * method will check if the guess in the guessBox view is the correct
     * name as the drawing's name. If it is, then the score will update with
     * a win and the score screen will activate.
     *
     * @param view
     *            The button view named "finishGuessing"
     */
    public void finishGuessing(View view)
    {
        EditText nameGuessBox = (EditText)findViewById(R.id.guessBox);

        // The next screen only shows if a correct guess happened.
        if(nameGuessBox.getText().toString().equals(drawingName))
        {
            GameController gameState =
                ((GameController)getApplicationContext());
            gameState.receiveDrawScore(controller.getScore());
            Intent guessIntent = new Intent(this, ScoreUpdateActivity.class);
            startActivity(guessIntent);
        }
    }

    /**
     * Listens for the giveUp button to be clicked, wherein this
     * method will assume the guesser has failed, and assign the guesser
     * and the drawer 0 points.
     *
     * @param view
     *            The button view named "giveUp"
     */
    public void giveUp(View view)
    {

        Intent guessIntent = new Intent(this, ScoreUpdateActivity.class);
        startActivity(guessIntent);
    }
}
