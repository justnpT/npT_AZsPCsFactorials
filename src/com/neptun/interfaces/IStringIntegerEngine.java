package com.neptun.interfaces;

import java.util.ArrayList;

/**
 * @author Michal
 *
 * @param <T>
 * @param <E>
 */
public interface IStringIntegerEngine {
	
	void fillResultIntArrayWithZeros();
	void prepareStringArrays();
	void makeSureArraysHaveTheSameLength(ArrayList<Integer> firstList, ArrayList<Integer> secondList);
	ArrayList<Integer> returnLongerArray(ArrayList<Integer> firstList, ArrayList<Integer> secondList);
	void changeCharArrayIntoIntArray(char[] charArray, ArrayList<Integer> arrayList);
	
	
}
