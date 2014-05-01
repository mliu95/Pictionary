package com.example.pictionary;

import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

/**
 * // -------------------------------------------------------------------------
 * /** The draw activity is used to create a canvas for the player to draw their
 * picture. This activity is called multiple times throughout the game, but it
 * is first called by the start screen, and it instantiates the game with a
 * player amount from the first MainActivity.
 *
 * @author Pictionary Team (Chris Deisher, Edward McEnrue, Michael Liu)
 * @version Apr 16, 2014
 */
public class DrawActivity
    extends Activity
{

    private DrawingView        drawView;
    // private GameController controller;

    // The key value pair to send the recorded drawing to the dialog activity
    //public final static String DRAWING_RECORD = "com.Pictionary.DrawActivity.MESSAGE";


    /**
     * Creates the new activity for DrawActivity and unpacks the intent from
     * MainActivity.
     *
     * @param savedInstanceState
     *            The state of the running application.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        drawView = (DrawingView)this.findViewById(R.id.drawView);
        drawView.setQueue(new DrawQueue<DrawObject>());

        // Get the player amount from the incoming intent key value data.
        // Intent mainIntent = getIntent();

        // Intent i = getIntent();
        // Deneme dene = (Deneme)i.getSerializableExtra("sampleObject");

        // TODO Make sure this isn't called a second time for 2nd round.
        // controller =
        // (GameController)mainIntent.getSerializableExtra(MainActivity.GAME_CONTROLLER);

    }


    // Getting the button to start the StartGuessDialog activity and
    // pass the recorded drawing info below:

    /**
     * Listens for the finishDrawing button to be clicked, and if the
     * drawingView has information entered, then the StartGuessDialog activity
     * will be called with the drawn recording data passed through an intent.
     *
     * @param view
     *            The button view named "finishDrawing"
     */
    public void finishDrawing(View view)
    {
        // Build an intent and the key value pair in response to the button.
        Intent drawingIntent = new Intent(this, StartGuessDialog.class);

        // TODO Get the drawView's recorded drawing data and send it through
        // the intent below, to be relayed through the StartGuessDialog
        // Activity to be used for the GuessActivity activity.

        // drawingIntent.putExtra(DRAWING_RECORD, controller);

        Bundle b = new Bundle();
        b.putParcelable("Drawing", drawView.getQueue());// The recorded drawing
        drawingIntent.putExtras(b);

        startActivity(drawingIntent);
    }

    // TODO Implement an if statement that enforces that the player has
    // drawed something. Use enables or something.
}
