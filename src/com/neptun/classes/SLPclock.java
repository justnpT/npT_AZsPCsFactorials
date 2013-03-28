package com.neptun.classes;

import java.util.ArrayList;

public class SLPclock extends ArrayList<Integer>{

	private int listSize;
	private int selfSize;
	private boolean hasNext;
	private Integer int0 = (Integer) new java.lang.Integer(0);
	private Integer intek;
	
	public SLPclock(char[] cs, int listSize) {
		// create SLPclock and fill it with necessary amount of zeros
		this.listSize = listSize;
		hasNext = true;
		for (int i = 0; i < (cs.length+1)/2; i++) {
			this.add(0);
		}
		this.selfSize = this.size();
	}
	
	public boolean hasNext() { return hasNext;}

	public void setNext() throws Exception{	
		setNext(selfSize-1);
	}

	/**
	 * set next on the specified place in the clock
	 * 
	 * @param i
	 * @throws Exception 
	 */
	private void setNext(int i) throws Exception {		
		if (hasNext) {			
			if (this.get(i)!=listSize-1) {
				intek = this.get(i);
				this.remove(i);
				this.add(i,intek+1);
			}
			else {
				this.remove(i);
				this.add(i,int0);
				setNext(i-1);
			}
			// imitates situation when clock has numbers such as: 17 . . . . . 17;
			// it is only possible when the clock is done.
			for (int j = 0; j < selfSize; j++) {
				hasNext = false;
				if (this.get(j)!=listSize-1) {
					hasNext = true;
					break;
				}	
			}
		}
		else {
			throw new Exception("clock is done, no next element");
		}
	}

}
