package com.neptun.action;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;

import com.neptun.classes.SLPclock;
import com.neptun.classes.SLPequiation;
import com.neptun.classes.SLPinputList;
import com.neptun.classes.SLPlog;
import com.neptun.classes.SLPresult;

import algorythms.SLPsolution;
import algorythms.SLPtask;

public class Runner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// i will not show
	}

	/**
	 * prepare equation with set number of operations
	 * 
	 * @param operationsCount
	 * @return
	 */
	private static ArrayList<char[]> prepareEquationMatrix(int operationsCount) {
		ArrayList<char[]> matrix = new ArrayList<char[]>();
		// potrzebna kombinacja z powtórzeniami n-elementowa zbioru 3 elementowego, gdzie n = operationsCount, a 3 elementy to *,+,-
		// combinations with repetition 
		LinkedList<char[]> items = new LinkedList<char[]>();
        char[] item = new char[operationsCount];
        char[] input = {'+', '-', '*'};
        rep(items, input, item, 0);

        int size = (items.get(0).length*2)+1;
        char[] equation = new char[size];
        for (char[] rep : items) {
        	
        	equation[0] = 'v';
        	for (int i = 0; i < rep.length; i++) {
				equation[1+2*i] = rep[i];
				equation[2*(1+i)] = 'v';
			}
        	matrix.add(equation.clone());
//            System.out.println(rep);
//            System.out.println(equation);
        }	
		
		return matrix;
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

	private static BigDecimal decimalSilnia(BigDecimal decimal) {
		if (decimal.compareTo(new BigDecimal("1"))==0) {
			return new BigDecimal("1");
		}
		else {
			return decimal.multiply(decimalSilnia(decimal.subtract(new BigDecimal("1"))));			
		}
	}
	
	private static Double doubleSilnia(Double _double) {		 
	if (_double == 1) {
		return (double) 1;
	}
	else {
		return _double*doubleSilnia(_double-1);
	}
	}	
	
}
