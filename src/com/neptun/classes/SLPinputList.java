package com.neptun.classes;

import java.math.BigDecimal;
import java.util.ArrayList;

public class SLPinputList extends ArrayList<BigDecimal>{

	private BigDecimal target;
	private ArrayList<ArrayList<BigDecimal>> inputMatrix = new ArrayList<ArrayList<BigDecimal>>();
	
	public SLPinputList(int[] forInputArray, BigDecimal target) {
		this.target = target;
		//prepare array list for all params from forInputArray
		for (int i = 0; i < forInputArray.length; i++) {
			inputMatrix.add(prepareTable(forInputArray[i]));
		}
		this.clear();
		mergeInputMatrix();
		
	}

	/**
	 * merges all inputs from the matrix into one with non-repeating elemnts
	 * 
	 */
	private void mergeInputMatrix() {
		for (int i = 0; i < inputMatrix.size(); i++) {
			for (int j = 0; j < inputMatrix.get(i).size(); j++) {
				BigDecimal element = inputMatrix.get(i).get(j);
				if (!this.contains(element)) {
					this.add(element);					
				}
			}
		}
	}

	/**
	 * if param is 2, it will return [2,4,16,256...] till product is lower than numberToResolve
	 * 
	 * @param bigDecimal
	 * @return
	 */
	private ArrayList<BigDecimal> prepareTable(int param) {
		BigDecimal bigDecimal = new BigDecimal(param);
		ArrayList<BigDecimal> resultArray = new ArrayList<BigDecimal>();
		BigDecimal previousNumber = bigDecimal;
		BigDecimal currentNumber = previousNumber.multiply(previousNumber);
		resultArray.add(new BigDecimal(1));
		resultArray.add(previousNumber);
		while (currentNumber.compareTo(this.target)==-1) {
			resultArray.add(currentNumber);
			previousNumber = currentNumber;
			currentNumber = previousNumber.multiply(previousNumber);		
		}
		resultArray.add(currentNumber);
		return resultArray;
	}
}
