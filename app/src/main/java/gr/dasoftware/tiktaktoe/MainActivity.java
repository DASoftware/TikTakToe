package gr.dasoftware.tiktaktoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private LinearLayout playAgainLayout;
    private TextView winnerMessage;
    // 0 = yellow, 1 = red.
    int activePlayer = 0;

    boolean isActive = true;

    // 2 means unplayed positions.
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winingPostions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
        playAgainLayout.setTranslationY(-2000f);

        winnerMessage = (TextView) findViewById(R.id.winnerMessage);
    }

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && isActive) {
            counter.setTranslationY(-2000f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            gameState[tappedCounter] = activePlayer;
            counter.animate().translationYBy(2000f).rotation(360f).setDuration(300);
            // Someone has one.
            for (int[] winingPostion : winingPostions) {
                if (gameState[winingPostion[0]] == gameState[winingPostion[1]]
                        && gameState[winingPostion[1]] == gameState[winingPostion[2]]
                        && gameState[winingPostion[0]] != 2) {


                    if (gameState[winingPostion[0]] == 0)
                        winnerMessage.setText("Νίκησε ο κόκκινος!!");
                    else
                        winnerMessage.setText("Νίκησε ο κίτρινος!!");

                    isActive = false;
                    playAgainLayout.setVisibility(View.VISIBLE);
                    playAgainLayout.animate().translationYBy(2000f).rotation(360f).setDuration(300);
                } else {
                    boolean gameDraw = true;

                    for (int counterState : gameState) {
                        if (counterState == 2) {
                            gameDraw = false;
                        }
                    }

                    // Game ended in draw.
                    if (gameDraw) {
                        winnerMessage.setText("Έχουμε ισοπαλία!!");

                        isActive = false;
                        playAgainLayout.setVisibility(View.VISIBLE);
                        playAgainLayout.animate().translationYBy(2000f).rotation(360f).setDuration(300);
                    }
                }
            }
        }
    }

    public void playAgainClicked(View view) {
        activePlayer = 0;
        isActive = true;

        for(int i = 0; i<gameState.length; i++) {
            gameState[i] = 2;
        }

        playAgainLayout.setVisibility(View.GONE);
        playAgainLayout.setTranslationY(-2000f);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }
}
