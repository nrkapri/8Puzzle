package com.example.root.myapplication;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Integer[] initarr  = new Integer[8];
    int free_x=0;
    int free_y=0;

    Button[][] buttons = new Button[3][3];

    int board[][] = new int [3][3];

    long moves=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resetBoard();
        Button resetButton =findViewById(R.id.resetBoard);

    }

    private void resetBoard() {
        generateInitialRandomArray(initarr);
        populateInitBoard(initarr, board);
        mapBoardButtonstoViewXml(buttons);
        setBoardArrayToButtons(buttons, board);
        moves=0;
    }

    private void setBoardArrayToButtons(Button[][] buttons, int[][] board) {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(board[i][j]!=0)
                {
                    buttons[i][j].setText(" "+new Integer(board[i][j]).toString()+" ");
                    buttons[i][j].setTag(R.id.TAG_KEY_1,i);
                    buttons[i][j].setTag(R.id.TAG_KEY_2,j);
                    buttons[i][j].setTag(R.id.TAG_KEY_3,board[i][j]);
                    buttons[i][j].setTextSize(100);
                    //buttons[i][j].setBackgroundColor(Color.GRAY);
                    buttons[i][j].setVisibility(View.VISIBLE);
                }
                else
                {
                    buttons[i][j].setText(new String("   "));
                    buttons[i][j].setTag(R.id.TAG_KEY_1,i);
                    buttons[i][j].setTag(R.id.TAG_KEY_2,j);
                    buttons[i][j].setTag(R.id.TAG_KEY_3,board[i][j]);
                    buttons[i][j].setTextSize(100);
                    buttons[i][j].setVisibility(View.INVISIBLE);

                    free_x=i;
                    free_y=j;
                }
            }
        }
    }

    private void mapBoardButtonstoViewXml(Button[][] buttons) {
        buttons[0][0]= (Button) findViewById(R.id.button1);
        buttons[0][1]= (Button) findViewById(R.id.button2);
        buttons[0][2]= (Button) findViewById(R.id.button3);

        buttons[1][0]= (Button) findViewById(R.id.button4);
        buttons[1][1]= (Button) findViewById(R.id.button5);
        buttons[1][2]= (Button) findViewById(R.id.button6);

        buttons[2][0]= (Button) findViewById(R.id.button7);
        buttons[2][1]= (Button) findViewById(R.id.button8);
        buttons[2][2]= (Button) findViewById(R.id.button9);
    }

    public void clickNumber(View view)
    {
        Button clickedButton =(Button) view;

        int x=  (int)clickedButton.getTag(R.id.TAG_KEY_1);
        int y=  (int )clickedButton.getTag(R.id.TAG_KEY_2);
        int val= (int ) clickedButton.getTag(R.id.TAG_KEY_3);

        //left
        if(  free_x==x && free_y==y-1)
        {
            swapButtonValues(clickedButton, x, y, val);
        }
        //right
        else if(  free_x==x && free_y==y+1)
        {
            swapButtonValues(clickedButton, x, y, val);
        }
        //up
        else if(  free_x==x-1 && free_y==y)
        {
            swapButtonValues(clickedButton, x, y, val);
        }
        //down
        else if (free_x==x+1 && free_y==y)
        {
            swapButtonValues(clickedButton, x, y, val);
        }
        else
        {
            Toast myToast = Toast.makeText(this, "Invalid move",
                    Toast.LENGTH_SHORT);
            myToast.show();
        }

        ckeckIfSolved();
    }

    private void ckeckIfSolved()
    {
        boolean solved=false;

        if (board[0][0]==1 &&
                board[0][1]==2 &&
                board[0][2]==3 &&
                board[1][0]==4 &&
                board[1][1]==5 &&
                board[1][2]==6 &&
                board[2][0]==7 &&
                board[2][1]==8 )
        {
            solved=true;
        }

        if(solved) {
            Toast myToast = Toast.makeText(this, "Congratulations!! The Son of a bitch did it.You took "+moves+" moves.",
                    Toast.LENGTH_LONG);
            myToast.show();
        }
    }

    private void swapButtonValues(Button clickedButton, int x, int y, int val) {
        Button freeButton = buttons[free_x][free_y];

        freeButton.setText(" "+new Integer(val).toString()+" ");
        freeButton.setTag(R.id.TAG_KEY_3,val);
        freeButton.setVisibility(View.VISIBLE);

        clickedButton.setText(new String("   "));
        clickedButton.setTag(R.id.TAG_KEY_3,0);
        clickedButton.setVisibility(View.INVISIBLE);

        board[free_x][free_y]=val;
        board[x][y]=0;

        free_x=x;
        free_y=y;

        moves++;

        Button movecnt = findViewById(R.id.movesCount);
        movecnt.setText("Moves:"+moves);
    }

    public void resetBoardClick(View view)
    {
        resetBoard();

        Button  movecnt =   findViewById(R.id.movesCount);
        movecnt.setText("Moves:"+0);

        new Toast(this).makeText(this,"Your board is reset.Go!",Toast.LENGTH_SHORT).show();
    }

    private int populateInitBoard(Integer[] initarr, int[][] board) {
        int emptyspace=  new Random().nextInt(9);
        int k=0;
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(k==emptyspace)
                {
                    System.out.println("skipping empty space");
                    emptyspace=-1;
                    board[i][j]=0;
                }
                else {
                    board[i][j]= initarr[k];
                    System.out.println("board:"+i+","+j+":"+board[i][j]+",k:"+k+",empty:"+emptyspace);
                    k++;
                }
                if(k>=8) break;

            }
        }
        return emptyspace;
    }

    private void generateInitialRandomArray(Integer[] initarr) {
        System.out.println("init array");

        List<Integer> arr= new ArrayList<Integer>(8);

        for(int i =0;i<8;i++)
        {
            arr.add(i+1);
        }

        Collections.shuffle(arr, new Random());

        for(int i=0;i<8;i++)
        {
            initarr[i]=arr.get(i);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
     //   getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       // int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
         //   return true;
        //}

        return  super.onOptionsItemSelected(item);
    }
}
