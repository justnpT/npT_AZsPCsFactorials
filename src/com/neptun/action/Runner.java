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
		
		try {
		System.out.println(decimalSilnia(new BigDecimal(13)));

		SLPlog log = new SLPlog();
		BigDecimal target = decimalSilnia(new BigDecimal(7));
		int[] forInputArray = {2,3,5};
		SLPinputList inputList = new SLPinputList(forInputArray, target);

		ArrayList<SLPresult> finalResults = new ArrayList<SLPresult>();
		ArrayList<char[]> equationMatrix = prepareEquationMatrix(3);
//		ArrayList<char[]> equationMatrix = new ArrayList<char[]>();
//		char[] test = {'v','+','v','-','v','*','v'};
//		equationMatrix.add(test);
		
	for (int t = 0; t < equationMatrix.size(); t++) {
		System.out.println("analizuje rownanie numer "+(t+1)+". Rownan: "+equationMatrix.size());
		
		SLPclock clock = new SLPclock(equationMatrix.get(t), inputList.size());
		SLPequiation slpEquation = new SLPequiation(equationMatrix.get(t), inputList, clock);

		BigDecimal score = slpEquation.nextIteration();
		SLPresult currentResult = new SLPresult(target, score);

		SLPresult nextResult= new SLPresult(target, score);

//		log.logResult(currentResult);
		while (slpEquation.hasNext()) {
			score = slpEquation.nextIteration();
			nextResult = new SLPresult(target, score);
			if (nextResult.compareTo(currentResult)==1) {
				currentResult = nextResult;		
				currentResult.setEquation(slpEquation.getPreviousEquation());
//				log.logResult(currentResult);
//				log.logEquation(currentResult.getEquation());
			}
			
		}
		finalResults.add(currentResult);
		System.out.println("rezultat rownania: "+currentResult.getScore());
		System.out.println("rownanie: "+currentResult.getEquation());
		System.out.println("odleglos od targetu: "+currentResult.getDistance());
		System.out.println("--------------------------------------------------");
	}
	
	System.out.println("koniec poszukiwan");
	
	SLPresult rezultat = finalResults.get(0);
	for (int i = 0; i < finalResults.size(); i++) {
//		System.out.println("rezultat finalowy numer "+i);
//		log.logResult(finalResults.get(i));
//		log.logEquation(finalResults.get(i).getEquation());
		if (finalResults.get(i).compareTo(rezultat)==1) {
			rezultat = finalResults.get(i);
			System.out.println("lepszy rezultat: "+rezultat.getScore());
			System.out.println("jego rownanie: "+rezultat.getEquation());
		}

	}
	System.out.println("==========================");
	System.out.println(" koniec, target to: "+target);
	System.out.println("najlepszy wynik to: "+rezultat.getScore());
	System.out.println("rownianie to: "+rezultat.getEquation());

	}
		catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
	}

	/**
	 * prepare equation with set number of operations
	 * 
	 * @param operationsCount
	 * @return
	 */
	private static ArrayList<char[]> prepareEquationMatrix(int operationsCount) {
		ArrayList<char[]> matrix = new ArrayList<char[]>();
		// potrzebna kombinacja z powt�rzeniami n-elementowa zbioru 3 elementowego, gdzie n = operationsCount, a 3 elementy to *,+,-
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