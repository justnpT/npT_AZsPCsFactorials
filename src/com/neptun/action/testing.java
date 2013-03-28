package com.neptun.action;

import java.util.ArrayList;
import java.util.LinkedList;

public class testing {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LinkedList<char[]> items = new LinkedList<char[]>();
        char[] item = new char[13];
        char[] input = {'+', '-', '*'};
        rep(items, input, item, 0);

        ArrayList<char[]> matrix = new ArrayList<char[]>();
        
        int size = (items.get(0).length*2)+1;
        char[] equation = new char[size];
        for (char[] rep : items) {
        	
        	equation[0] = 'v';
        	for (int i = 0; i < rep.length; i++) {
				equation[1+2*i] = rep[i];
				equation[2*(1+i)] = 'v';
			}
            System.out.println(rep);
            System.out.println(equation);
        }
	}
	private static void rep(LinkedList<char[]> reps, char[] input, char[] item, int count){
        if (count < item.length){
            for (int i = 0; i < input.length; i++) {
                item[count] = input[i];
                rep(reps, input, item, count+1);
            }
        }else{
            reps.add(item.clone());
        }
    }

}
