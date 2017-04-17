/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleshipai;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;


public class BattleShipAI {


    public static int best[][] = new int[10][10];
    public static boolean airCraft = true;      // index = 5
    public static boolean battleShip = true;    // index = 4
    public static boolean destroyer = true;     // index = 3
    public static boolean sub = true;           // index = 2
    public static boolean patrol = true;        // index = 1
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        int b[][] = new int[10][10];
        boolean noHits = true;
        int row = -1;
        int col = -1;
        readBoard(b);
        noHits = checkBoard(b);
        
        if(noHits){
            row = randomIndex();
            col = randomIndex();
        }
        else{
            search(b);
            int bestVal = 0;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if(best[i][j] > bestVal){
                        row = i;
                        col = j;
                        bestVal = best[i][j];
                    }
                }
            }
        }
        
        putMove(row,col);
    }
    
    public static void readBoard(int[][] b) throws FileNotFoundException, IOException{
        
        Scanner br = new Scanner(new File("p1.txt"));
        int index;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                index = br.nextInt();
                b[i][j] = index;                
            }
        }
    }
    
    public static boolean checkBoard(int[][] b){
        boolean noHits = true;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                best[i][j] = 0;
                if(b[i][j] > 6){
                    noHits = false;
                }
                if(b[i][j] == 1){
                    patrol = false;
                }
                if(b[i][j] == 2){
                    sub = false;
                }
                if(b[i][j] == 3){
                    destroyer = false;
                }
                if(b[i][j] == 4){
                    battleShip = false;
                }
                if(b[i][j] == 5){
                    airCraft = false;
                }
                //System.out.print(b[i][j]);
            }
            //System.out.println();
        }
        return noHits;
    }
    
    public static boolean putMove(int r, int c) throws IOException{
        BufferedWriter wr = new BufferedWriter(new FileWriter("move.txt"));
        if(r >= 10 || r < 0){
            return false;
        }
        if(c >= 10 || c < 0){
            return false;
        }
        wr.write(r + " " + c);
        wr.close();
        System.out.println(r + " " + c);
        return true;
    }
    
    public static int randomIndex(){
        int random;
        Random rand = new Random();
        random = rand.nextInt(10);
        return random;
    }
    
    public static void search(int[][] b){
        int cur = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(b[i][j] > 6){
                    h(b,i,j);
                }
            }
        }
    }
    
    public static void h(int[][] b, int r, int c){
        boolean left = true;
        boolean right = true;
        boolean up = true;
        boolean down = true;
        int score = 10;
        
        if(r < 1){
            up = false;
        }else if(r > 8){
            down = false;
        }
        if(c < 1){
            left = false;
        }else if(r > 8){
            right = false;
        }
        
        if(up && b[r-1][c] != -1 && b[r-1][c] < 6){
            best[r-1][c] += score;
        }
        if(down && b[r+1][c] != -1 && b[r+1][c] < 6){
            best[r+1][c] += score;
        }
        if(left && b[r][c-1] != -1 && b[r][c-1] < 6){
            best[r][c-1] += score;
        }
        if(right && b[r][c+1] != -1 && b[r][c+1] < 6){
            best[r][c+1] += score;
        }
        
    }
}
