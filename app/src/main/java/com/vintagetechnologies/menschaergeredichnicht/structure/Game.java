package com.vintagetechnologies.menschaergeredichnicht.structure;

import java.util.ArrayList;

/**
 * Created by johannesholzl on 05.04.17.
 */

public class Game {

    private static Game gameInstance;

    private int currentPlayer;
    private Player players[];
    private Board board;

    private boolean initialized = false;

    public Game getInstance(){
        if(gameInstance == null){
            this.gameInstance = new Game();
        }
        return this.gameInstance;
    }

    private Game() {
    }

    public void init(String... names){
        players = new Player[names.length];
        board = Board.get();

        for(int i = 0; i < names.length; i++){
            PlayerColor cColor = PlayerColor.values()[i];
            players[i] = new Player(cColor, names[i]);

            int c = 0;

            for(Spot spot : board.getBoard()){
                if(spot instanceof StartingSpot){
                    StartingSpot sSpot  = (StartingSpot)spot;
                    if(sSpot.getColor() == cColor){
                        players[i].getPieces()[c].setSpot(spot);
                        c++;
                    }
                }
            }


        }
        initialized = true;


    }


    public void nextTurn() {

        Player cp = players[currentPlayer];

        cp.getPieces();




        currentPlayer = (currentPlayer+1)%players.length;

    }

}