package com.neptun.classes;

import java.math.BigDecimal;

public class SLPresult {
	
	private BigDecimal score;
	private BigDecimal target;
	private String equation;
	private BigDecimal distanceFromTarget;
	
	public SLPresult(BigDecimal target, BigDecimal score) {
		this.score = score;
		this.target = target;
		this.distanceFromTarget = getAbsoluteValueOf(this.target.subtract(getAbsoluteValueOf(this.score)));
	}

	public int compareTo(SLPresult externalResult) {		
		BigDecimal externalDistanceFromTarget = externalResult.getDistance();
		int isThisWorse = this.distanceFromTarget.compareTo(externalDistanceFromTarget);
		if (isThisWorse==1) {
			//System.out.println("new result is not better");
			return -1;
		}
		else if (isThisWorse==0){
			return 0;
		}
		else {
			return 1;
		}		
	}
	
	public BigDecimal getScore() {
		return this.score;
	}
	
	public void setEquation(String equation) {
		this.equation = equation;
	}
	
	public String getEquation() {
		return this.equation;
	}
	
	/**
	 * returns absolute value of BigDecimal
	 * 
	 * @param minimum
	 * @return
	 */
	private BigDecimal getAbsoluteValueOf(BigDecimal bdecimal) {
		if (bdecimal.compareTo(new BigDecimal(0))==-1) {
			return bdecimal.negate();
		}
		else {
			return bdecimal;
		}
	}

	public BigDecimal getDistance() {
		return this.distanceFromTarget;
	}
}
