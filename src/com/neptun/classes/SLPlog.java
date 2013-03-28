package com.neptun.classes;

public class SLPlog {

	public void logResult(SLPresult currentResult) {
		System.out.println("logging result: "+currentResult.getScore());
	}

	public void logEquation(String previousEquation) {
		System.out.println("logging equation: "+previousEquation);		
	}

}
