package com.vintagetechnologies.menschaergeredichnicht.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.os.CancellationSignal;
import android.util.AttributeSet;
import android.view.View;

import com.vintagetechnologies.menschaergeredichnicht.structure.Board;
import com.vintagetechnologies.menschaergeredichnicht.structure.Colorful;
import com.vintagetechnologies.menschaergeredichnicht.structure.EndSpot;
import com.vintagetechnologies.menschaergeredichnicht.structure.Game;
import com.vintagetechnologies.menschaergeredichnicht.structure.GamePiece;
import com.vintagetechnologies.menschaergeredichnicht.structure.Player;
import com.vintagetechnologies.menschaergeredichnicht.structure.RegularSpot;
import com.vintagetechnologies.menschaergeredichnicht.structure.Spot;
import com.vintagetechnologies.menschaergeredichnicht.structure.StartingSpot;

/**
 * Created by johannesholzl on 04.04.17.
 */

public class BoardView extends View {

    private Paint paint;
    private Board board;
    private boolean boardIsDrawn = false;
    double spotRadius;
    double abstand;

    private void init(){
        paint = new Paint();
        board = Board.get();




    }

    public BoardView(Context context) {
        super(context);
        init();
    }

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        if(!boardIsDrawn){
            boardIsDrawn = true;



        }

        System.out.println(canvas);



        spotRadius = getWidth()/40.0;
        abstand = (getWidth()-(22*spotRadius))/22;

        drawBoard(canvas);

        drawGamePieces(canvas);
    }

    private void drawGamePieces(Canvas canvas){
        for(Player p : Game.getInstance().getPlayers()){
            switch (p.getColor()){
                case RED:{
                    paint.setColor(Color.RED);
                    break;}
                case GREEN:{
                    paint.setColor(Color.GREEN);
                    break;}
                case YELLOW:{
                    paint.setColor(Color.YELLOW);
                    break;}
                case BLUE:{
                    paint.setColor(Color.BLUE);
                    break;}
            }
            paint.setStyle(Paint.Style.FILL);
            for(GamePiece gp : p.getPieces()){

                Spot n = gp.getSpot();

                double x = (2*n.getX()+1)*(spotRadius+abstand);
                double y = (2*n.getY()+1)*(spotRadius+abstand);

                canvas.drawCircle((float)x, (float)y, (float)spotRadius/2, paint);
            }


            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            for(GamePiece gp : p.getPieces()){
                Spot n = gp.getSpot();

                double x = (2*n.getX()+1)*(spotRadius+abstand);
                double y = (2*n.getY()+1)*(spotRadius+abstand);

                canvas.drawCircle((float)x, (float)y, (float)spotRadius/2, paint);
            }

        }
    }


    private void drawBoard(Canvas canvas){
        setBackgroundColor(Color.GRAY);

        paint.reset();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        //canvas.drawCircle(getWidth()/4, getHeight()/4, getHeight()/4, paint);


        double prev_x = (2*board.getBoard()[0].getX()+1)*(spotRadius+abstand);
        double prev_y = (2*board.getBoard()[0].getY()+1)*(spotRadius+abstand);


        RegularSpot s = (RegularSpot)board.getBoard()[0];
        RegularSpot n = s.getNextSpot();


        while(s != n){


            n = n.getNextSpot();

            double x = (2*n.getX()+1)*(spotRadius+abstand);
            double y = (2*n.getY()+1)*(spotRadius+abstand);


            canvas.drawLine((float)prev_x, (float)prev_y, (float)x, (float)y, paint);
            prev_x = x;
            prev_y = y;

        }

        for(Spot spot : board.getBoard()){
            double x = (2*spot.getX()+1)*(spotRadius+abstand);
            double y = (2*spot.getY()+1)*(spotRadius+abstand);



            if(spot instanceof RegularSpot) {
                paint.setColor(Color.WHITE);
            }else if(spot instanceof EndSpot || spot instanceof  StartingSpot){
                switch (((Colorful) spot).getColor()){
                    case RED:{
                        paint.setColor(Color.RED);
                        break;}
                    case GREEN:{
                        paint.setColor(Color.GREEN);
                        break;}
                    case YELLOW:{
                        paint.setColor(Color.YELLOW);
                        break;}
                    case BLUE:{
                        paint.setColor(Color.BLUE);
                        break;}
                }
            }
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle((float)x, (float)y, (float)spotRadius, paint);


            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            canvas.drawCircle((float)x, (float)y, (float)spotRadius, paint);
        }
    }
}
