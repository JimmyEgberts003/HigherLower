package com.egberts.jimmy.higherlower;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_DICE_SIDES = 6;
    private static int currentDiceNumber;
    private int currentScore = 0;
    private int throwAmount = 0;
    private int highscore = 0;

    private TextView currentScoreView;
    private TextView highscoreView;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    private List<String> logEntries;

    private ImageView diceImage;
    private int[] diceImages;

    private Button higherButton;
    private Button lowerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentScoreView = findViewById(R.id.currentScoreView);
        highscoreView = findViewById(R.id.highscoreView);

        listView = findViewById(R.id.listView);

        diceImage = findViewById(R.id.diceImage);

        higherButton = findViewById(R.id.higherButton);
        lowerButton = findViewById(R.id.lowerButton);

        logEntries = new ArrayList<String>();

        diceImages = new int[]{R.drawable.dice_1, R.drawable.dice_2, R.drawable.dice_3, R.drawable.dice_4, R.drawable.dice_5, R.drawable.dice_6};

        currentDiceNumber = throwDice();
        diceImage.setImageResource(diceImages[currentDiceNumber - 1]);

        setScore();

        higherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                throwAmount++;

                int nextDiceNumber = throwDice();
                logEntries.add(0, "[Round: " + throwAmount + "]: Dice thrown: " + nextDiceNumber);
                if (nextDiceNumber > currentDiceNumber) {
                    logEntries.add(1, "You guessed correctly! Score increased by one.");
                    if (currentScore == highscore) {
                        highscore++;
                    }
                    currentScore++;
                } else {
                    logEntries.add(1, "You guessed wrong... Score is reset.");
                    currentScore = 0;
                }
                diceImage.setImageResource(diceImages[nextDiceNumber - 1]);

                setScore();
                updateUI();
                currentDiceNumber = nextDiceNumber;
            }
        });

        lowerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                throwAmount++;

                int nextDiceNumber = throwDice();
                logEntries.add(0, "[Round: " + throwAmount + "]: Dice thrown: " + nextDiceNumber);

                if (nextDiceNumber < currentDiceNumber) {
                    logEntries.add(1, "You guessed correctly! Score increased by one.");
                    if (currentScore == highscore) {
                        highscore++;
                    }
                    currentScore++;
                } else {
                    logEntries.add(1, "You guessed wrong... Score is reset.");
                    currentScore = 0;
                }
                diceImage.setImageResource(diceImages[nextDiceNumber - 1]);

                setScore();
                updateUI();
                currentDiceNumber = nextDiceNumber;
            }
        });
    }

    private int throwDice() {
        Random random = new Random();
        int number = random.nextInt(MAX_DICE_SIDES) + 1;
        if (number == currentDiceNumber) {
            diceImage.setImageResource(diceImages[number - 1]);
            return throwDice();
        } else {
            return number;
        }
    }

    private void updateUI() {
        if (arrayAdapter == null) {
            arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, logEntries);
            listView.setAdapter(arrayAdapter);
        } else {
            arrayAdapter.notifyDataSetChanged();
        }
    }

    private void setScore() {
        currentScoreView.setText(String.valueOf(currentScore));
        highscoreView.setText(String.valueOf(highscore));
    }
}
