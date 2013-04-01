package com.neptun.action;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import com.neptun.classes.SLPclock;
import com.neptun.classes.SLPequiation;
import com.neptun.classes.SLPinputList;
import com.neptun.classes.SLPlog;
import com.neptun.classes.SLPresult;

public class SLPrunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		//	mode = 1 => liczenie silni. np 1 13 3 4 2 3 <- liczenie silni 13 dla 3 do 4 operacji dla inputu od 2 do 3 
			int mode = Integer.parseInt(args[0]);
					
			if (mode==1) {
				int fl = Integer.parseInt(args[1]);
				int oCountL = Integer.parseInt(args[2]);
				int oCountT = Integer.parseInt(args[3]);
				int iArSL = Integer.parseInt(args[4]);
				int iArST = Integer.parseInt(args[5]);

			System.out.println("oblicz SLP dla silni "+fl+" w przedzale operacji: <"+oCountL+","+oCountT+"> w przedzale inputow: <"+iArSL+","+iArST+"> ");
			//operation co	unt
			for (int i = oCountL; i <= oCountT; i++) {
			//	input length count
				for (int j = iArSL; j <= iArST; j++) {
					solveSLP(fl, i, j);	
					System.out.println("koniec liczenia dla silni "+fl+", dla "+i+" operacji, dla dlugosci inputu "+j);
				}
			}
			if (mode==2) {
				System.out.println("oblicz SLP dla silni "+fl+" w przedzale operacji: <"+oCountL+","+oCountT+"> w przedzale inputow: <"+iArSL+","+iArST+"> ");
				
			}
			}
	}

	/**
	 * @param factorial  this is eg. 13!
	 * @param operationCount	this is how many + - + + we will have
	 * @param inputArraySize this is if input array will be of length {1,2} or {1,2,3}
	 */
	private static void solveSLP(int factorial, int operationCount, int inputArraySize) {
		
		SLPlog log = new SLPlog(factorial, operationCount, inputArraySize);
		int[][] inputMatrix = prepareInputMatrix(inputArraySize);
		// dla kazdego inputu o zadanej dlugosci
		try {
		log.beginLogging();
		
		ArrayList<SLPresult> masterResults = new ArrayList<SLPresult>();
		Date firstDate = new Date();

		for (int j = 0; j < inputMatrix.length; j++) {					
		Date date = new Date();
		BigDecimal target = decimalSilnia(new BigDecimal(factorial));
		int[] forInputArray = inputMatrix[j]; // {2,3} , {2,4}, {2,5} itd
		SLPinputList inputList = new SLPinputList(forInputArray, target);

		ArrayList<SLPresult> finalResults = new ArrayList<SLPresult>();
		ArrayList<char[]> equationList = prepareEquationMatrix(operationCount);
		
	SLPresult finalResult = null;
	
		log.logInput(j, forInputArray);
	// dla kazdego rownania o zadanym rozmiarze
	for (int t = 0; t < equationList.size(); t++) {
		if (finalResult==null) {
			finalResult = new SLPresult(target, new BigDecimal(1), forInputArray);
			finalResults.add(finalResult);
		}

//		System.out.println("analizuje rownanie numer "+(t+1)+". Rownan: "+equationList.size());
		
		SLPclock clock = new SLPclock(equationList.get(t), inputList.size());
		SLPequiation slpEquation = new SLPequiation(equationList.get(t), inputList, clock);

		BigDecimal score = slpEquation.nextIteration();
		SLPresult currentResult = new SLPresult(target, score, forInputArray);

		SLPresult nextResult= new SLPresult(target, score, forInputArray);

		while (slpEquation.hasNext()) {
			score = slpEquation.nextIteration();
			nextResult = new SLPresult(target, score, forInputArray);
			if (nextResult.compareTo(currentResult)==1) {
				currentResult = nextResult;		
				currentResult.setEquation(slpEquation.getPreviousEquation());
			}
			
		}
		if (currentResult.compareTo(finalResult)==1) {
			finalResult = currentResult;
			finalResults.add(currentResult);
			log.newFinalResult();
//			System.out.println("nowy rezultat dodany jako finalowy");
			log.logEquation(currentResult.getScore(), currentResult.getEquation(), currentResult.getDistance());
		}
	}
		
	SLPresult rezultat = finalResults.get(finalResults.size()-1);
	for (int i = 0; i < finalResults.size()-1; i++) {
		if (finalResults.get(i).compareTo(rezultat)==0) {
			masterResults.add(finalResults.get(i));

			log.sameResult();
			log.logEquation(rezultat.getScore(), rezultat.getEquation(), rezultat.getDistance());
		}

	}						
		masterResults.add(rezultat);

		Date date2 = new Date();
		log.finishForInput(date, date2, target, rezultat);
		
	}
		
		masterResults = extractBestResults(masterResults);
		for (int i = 0; i < masterResults.size(); i++) {
			log.logMasterResult(masterResults.get(i), i);
		}
		Date lastDate = new Date();
		log.finish(firstDate,lastDate);	
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}	
	

	private static ArrayList<SLPresult> extractBestResults(
			ArrayList<SLPresult> inputResults) {
		ArrayList<SLPresult> outputResults = new ArrayList<SLPresult>();
		
		SLPresult best = inputResults.get(0);
		int bestIndex=0;
		for (int i = 1; i < inputResults.size(); i++) {
			if (best.compareTo(inputResults.get(i))==-1) {
				best = inputResults.get(i);
				bestIndex = i;
			}
		}
		outputResults.add(best);
		for (int i = 0; i < inputResults.size(); i++) {
			if (i!=bestIndex && (best.compareTo(inputResults.get(i))==0)) {
				outputResults.add(inputResults.get(i));
			}
		}				
		return outputResults;
	}

	private static int[][] prepareInputMatrix(int inputArraySize) {
		 // Create the initial vector
		
		int matrixSize = intSilnia(8)/(intSilnia(inputArraySize)*(intSilnia(8-inputArraySize)));
		int[][] matrix = new int[matrixSize][];
		   ICombinatoricsVector<Integer> initialVector = Factory.createVector(
		      new Integer[] {new Integer(2), new Integer(3), new Integer(4), new Integer(5), new Integer(9), new Integer(6), new Integer(7), new Integer(8) } );

		   // Create a simple combination generator to generate 3-combinations of the initial vector
		   Generator<Integer> gen = Factory.createSimpleCombinationGenerator(initialVector, inputArraySize);
		   // Print all possible combinations
		 
			
		   int j = 0;
		   for (ICombinatoricsVector<Integer> combination : gen) {
		      List<Integer> list = combination.getVector();
		      int[] array = new int[inputArraySize];
		      for (int i = 0; i < list.size(); i++) {
				array[i] = list.get(i).intValue();
			
			}
		      matrix[j] = array;
		      j++;
		   }
		return matrix;
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
	
	private static int intSilnia(int intek) {		 
		if (intek == 1) {
			return 1;
		}
		else {
			return intek*intSilnia(intek-1);
		}
	}	
	
}
