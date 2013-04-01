package com.neptun.action;

import java.util.LinkedList;

public class testing {

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		System.out.println("siemanko");
//	}
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
