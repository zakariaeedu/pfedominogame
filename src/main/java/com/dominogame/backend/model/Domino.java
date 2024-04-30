package com.dominogame.backend.model;


import java.util.ArrayList;

public class Domino {

    private int left;
    private int right;
    private static int[] pts = {0,1,2, 3,4, 5,6};
    public Domino(int l, int r){
        left = l;
        right = r;
    }
    public int getLeft(){return left;}
    public int getRight(){return right;}
    public int getWeight(){return left+right;}
    public void reverse(){
        int tmp = left;
        left = right;
        right = tmp;
    }
    public String toString(){
        return "["+pts[left]+"|"+pts[right]+ "]";
    }


}

