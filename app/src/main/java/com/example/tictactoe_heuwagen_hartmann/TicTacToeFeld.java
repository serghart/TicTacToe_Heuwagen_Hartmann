package com.example.tictactoe_heuwagen_hartmann;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintSet;

public class TicTacToeFeld extends View {

    private final int feldFarbe;
    private final int XFarbe;
    private final int OFarbe;
    private final int gewinnLinie;

    private boolean gewinnerLinie = false;

    private final Paint paint = new Paint();

    private final SpielLogik game;

    private int zellGroesse = getWidth()/3;

    public TicTacToeFeld(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        game = new SpielLogik();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TicTacToeFeld, 0, 0);

        try {
            feldFarbe = a.getInteger(R.styleable.TicTacToeFeld_feldFarbe, 0);
            XFarbe = a.getInteger(R.styleable.TicTacToeFeld_XFarbe, 0);
            OFarbe = a.getInteger(R.styleable.TicTacToeFeld_OFarbe, 0);
            gewinnLinie = a.getInteger(R.styleable.TicTacToeFeld_gewinnLinie, 0);

        }finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int breite, int hoehe) {
        super.onMeasure(breite, hoehe);

        int dimensionen = Math.min(getMeasuredWidth(), getMeasuredHeight());
        zellGroesse = dimensionen/3;

        setMeasuredDimension(dimensionen, dimensionen);
    }

    @Override
    protected void onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawSpielFeld(canvas);
        drawMarkers(canvas);

        if (gewinnerLinie){
            paint.setColor(gewinnLinie);
            zeichneSiegerLinie(canvas);
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN){
            int row = (int) Math.ceil(y/zellGroesse);
            int col = (int) Math.ceil(x/zellGroesse);

            if(!gewinnerLinie){
                if (game.updateSpielfeld(row, col)){
                    invalidate();

                    if(game.werIstSieger()){
                        gewinnerLinie = true;
                        invalidate();
                    }

                    // Update Spielerzug

                    if (game.getSpieler() % 2 == 0){
                        game.setSpieler(game.getSpieler()-1);
                    }
                    else{
                        game.setSpieler(game.getSpieler()+1);
                    }
                }
            }

            invalidate();

            return true;
        }

        return false;
    }

    private void drawSpielFeld(Canvas canvas){
        paint.setColor(feldFarbe);
        paint.setStrokeWidth(16);

        for (int c=1; c<3; c++){
            canvas.drawLine(zellGroesse*c, 0, zellGroesse*c, canvas.getWidth(), paint);
        }

        for (int r=1; r<3; r++){
            canvas.drawLine(0, zellGroesse*r, canvas.getWidth(), zellGroesse*r, paint);
        }
    }

    private void drawMarkers(Canvas canvas){
        for (int r=0; r<3; r++){
            for (int c=0; c<3; c++){
                if (game.getSpielFeld() [r][c] != 0){
                    if (game.getSpielFeld() [r][c] == 1){
                        drawX(canvas, r ,c);
                    }
                    else{
                        drawO(canvas, r, c);
                    }
                }
            }
        }
    }

    private void drawX(Canvas canvas, int row, int col){
        paint.setColor(XFarbe);

        canvas.drawLine((float) ((col+1)*zellGroesse - zellGroesse*0.2),
                        (float) (row*zellGroesse + zellGroesse*0.2),
                        (float) (col*zellGroesse + zellGroesse*0.2),
                        (float) ((row+1)*zellGroesse - zellGroesse*0.2),
                        paint);

        canvas.drawLine((float) (col*zellGroesse + zellGroesse*0.2),
                        (float) (row*zellGroesse + zellGroesse*0.2),
                        (float) ((col+1)*zellGroesse - zellGroesse*0.2),
                        (float) ((row+1)*zellGroesse - zellGroesse*0.2),
                        paint);
    }

    private void drawO(Canvas canvas, int row, int col){
        paint.setColor(OFarbe);

        canvas.drawOval((float) (col*zellGroesse + zellGroesse*0.2),
                        (float) (row*zellGroesse + zellGroesse*0.2),
                        (float) ((col*zellGroesse + zellGroesse) - zellGroesse*0.2),
                        (float) ((row*zellGroesse + zellGroesse) - zellGroesse*0.2),
                        paint);
    }

    private void zeichneHorizontaleLinie(Canvas canvas, int row, int col){
        canvas.drawLine(col, row*zellGroesse + (float)zellGroesse/2,
                zellGroesse*3, row*zellGroesse + (float)zellGroesse/2,
                paint);
    }

    private void zeichneVertikaleLinie(Canvas canvas, int row, int col){
        canvas.drawLine(col*zellGroesse + (float)zellGroesse/2, row,
                col*zellGroesse + (float)zellGroesse/2, zellGroesse*3,
                paint);
    }

    private void zeichneDiagonaleLiniePositiv(Canvas canvas){
        canvas.drawLine(0, zellGroesse*3, zellGroesse*3, 0, paint);
    }

    private void zeichneDiagonaleLinieNegativ(Canvas canvas){
        canvas.drawLine(0, 0, zellGroesse*3, zellGroesse*3, paint);
    }

    private void zeichneSiegerLinie(Canvas canvas){
        int row = game.getSiegTyp()[0];
        int col = game.getSiegTyp()[1];

        switch (game.getSiegTyp()[2]){
            case 1:
                zeichneHorizontaleLinie(canvas, row, col);
                break;
            case 2:
                zeichneVertikaleLinie(canvas, row, col);
                break;
            case 3:
                zeichneDiagonaleLinieNegativ(canvas);
                break;
            case 4:
                zeichneDiagonaleLiniePositiv(canvas);
                break;
        }
    }

    public void spielErstellen(Button nochmalSpielen, Button home, TextView spielerBild, String[] name){
        game.setNochmalSpielenButton(nochmalSpielen);
        game.setHomeButton(home);
        game.setSpielerZug(spielerBild);
        game.setSpielerNamen(name);
    }

    public void resetSpiel(){
        game.resetSpiel();
        gewinnerLinie = false;
    }
}
