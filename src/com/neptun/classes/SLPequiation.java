package com.neptun.classes;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class SLPequiation {
	
	private SLPclock clock;
	private ArrayList<ArrayList<BigDecimal>> variableMatrix = new ArrayList<ArrayList<BigDecimal>>();
	
	private int variablesCount;
	private int operationCount;
	private char[] equation;
	private String previuousEquation;

	public SLPequiation(char[] cs, SLPinputList inputList, SLPclock clock) {
		this.equation = cs;
		this.clock = clock;
		this.variablesCount = (cs.length+1)/2;
		this.operationCount = (cs.length-1)/2;
		for (int i = 0; i < variablesCount; i++) {
			this.variableMatrix.add(inputList);
		}
		
	}

	public boolean hasNext() {
		return this.clock.hasNext();
	}

	public BigDecimal nextIteration() throws Exception {
		this.previuousEquation = getCurrentEquation();
		BigDecimal firstFactor = null;
		BigDecimal secondFactor = null;
		BigDecimal result = variableMatrix.get(0).get(clock.get(0));
		for (int i = 0; i < operationCount; i++) {
			firstFactor = result;
			secondFactor = variableMatrix.get(i+1).get(clock.get(i+1));
			if (equation[1+i*2]=='*') {
				result = firstFactor.multiply(secondFactor);
			}
			if (equation[1+i*2]==('+')) {
				result = firstFactor.add(secondFactor);			
			}
			if (equation[1+i*2]==('-')) {
				result = firstFactor.subtract(secondFactor);						
			}
		}
		this.clock.setNext();
		return result;
	}

	public String getCurrentEquation() {
		String result = "";
		result = result + variableMatrix.get(0).get(clock.get(0));
		for (int i = 0; i < operationCount; i++) {		
			result = result + equation[1+i*2];			
			result = result + variableMatrix.get(i+1).get(clock.get(i+1));
		}
		return result;
	}
	public String getPreviousEquation() {
		return this.previuousEquation;		
	}
}
