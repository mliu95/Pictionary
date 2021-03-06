package com.example.pictionary;

import android.app.Application;
import java.util.ArrayList;

/**
 * // -------------------------------------------------------------------------
 * /** Controls all information that must stay consistent throughout a game. It
 * implements the Serializable interface in order to be shared between each
 * activity.
 *
 * @author (cdd5) Chris Deisher
 * @author Edward McEnrue
 * @author Michael Liu
 * @version Apr 16, 2014
 */
public class GameController
    extends Application
{

    private ArrayList<Integer> scoreList;
    private int                currentPlayer;
    private int                winscore;


    /**
     * Sets the current player, mostly for testing purposes
     *
     * @param cur
     *            is the current player you want
     */
    public void setCurrentPlayer(int cur)
    {
        currentPlayer = cur;
    }


    /**
     * initializes our Game Controller
     */
    public GameController()
    {
        winscore = 1000;
        currentPlayer = 1;
    }


    /**
     * Takes the score from the draw controller and adds it to the correct
     * players
     *
     * @param drawscore
     *            is the calculated score for the round
     */
    public void receiveDrawScore(int drawscore)
    {
        addToScore(currentPlayer, drawscore);
        if (currentPlayer != 1)
        {
            addToScore(currentPlayer - 1, drawscore);
        }
        else
        {
            addToScore(getNumPlayers(), drawscore);
        }
    }


    /**
     * Creates our score list, with 0 as everyone's starting score
     *
     * @param numplayers
     *            the total number of players
     */
    public void createScoreList(int numplayers)
    {

        scoreList = new ArrayList<Integer>(5);

        for (int i = 0; i < numplayers; i++)
        {
            scoreList.add(0);
        }
    }


    /**
     * gets number of players
     *
     * @return numplayers
     */
    public int getNumPlayers()
    {
        return scoreList.size();
    }


    /**
     * returns an ArrayList of everyones scores
     *
     * @param playernum
     *            is the player's number you want the score of;
     * @return score
     */
    public int getScore(int playernum)
    {
        return scoreList.get(playernum - 1);
    }


    /**
     * Move the game to the next player
     *
     * @return the number representation of the current player
     */
    public int nextPlayer()
    {
        currentPlayer += 1;
        if (currentPlayer > scoreList.size())
        {
            currentPlayer = 1;
        }
        return currentPlayer;
    }


    /**
     * Returns the list of scores for use in our round-by-round update, similar
     * to toString() in function
     *
     * @return the scores
     */
    public String getScoreList()
    {
        String endresult = "ScoreList:\n";

        for (int i = 1; i <= scoreList.size(); i++)
        {
            endresult +=
                "Player " + i + ": " + scoreList.get(i - 1) + " points";

            if (i == currentPlayer)
            {
                endresult += " (The guesser)\n";
            }
            else if (i == currentPlayer - 1
                || (currentPlayer == 1 && i == scoreList.size()))
            {
                endresult += " (The artist)\n";
            }
            else
            {
                endresult += "\n";
            }
        }

        return endresult;
    }


    /**
     * gets the current player
     *
     * @return the players numbers
     */
    public int getCurrentPlayer()
    {
        // return the current player number
        return currentPlayer;
    }


    /**
     * tells you if the round/game is won yet
     *
     * @return -1 if no winner, player number of winner
     */
    public int isWon()
    {

        int currentwinner = -1;
        for (int i = 0; i < scoreList.size(); i++)
        {
            if (scoreList.get(i) > winscore)
            {
                if (currentwinner == -1)
                {
                    currentwinner = i;
                }
                else
                {
                    if (scoreList.get(i) > scoreList.get(currentwinner))
                    {
                        currentwinner = i;
                    }
                }
            }
        }
        if (currentwinner != -1)
        {
            currentwinner++;
        }
        // add one to go from index to players number
        return currentwinner;
    }


    /**
     * sets the score
     *
     * @param playernum
     *            the players number
     * @param score
     *            the score to add
     */
    public void addToScore(int playernum, int score)
    {
        scoreList.set(playernum - 1, scoreList.get(playernum - 1) + score);
    }

}
