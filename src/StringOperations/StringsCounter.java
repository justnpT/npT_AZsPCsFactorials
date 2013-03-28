package StringOperations;

import java.util.ArrayList;

public class StringsCounter {
	// prepare arrayLists of integers from strings
	
private static	ArrayList<Integer> firstIntArray = new ArrayList<Integer>();
private static	ArrayList<Integer> secondIntArray = new ArrayList<Integer>();
private static	ArrayList<Integer> resultIntArray = new ArrayList<Integer>();


	public static String add(String firstString, String secondString) throws Exception {
		String resultString = null;		
		prepareIntegerArrays(firstString,secondString); // prepare results array of int		
		int IAlenth = arraysLengthAssurance();		
		prepareResultIntArray(IAlenth);		  			// fill result array with enough zeros
		
		// run addition algorythm
		int rest = 0;
		boolean isRest = false;
		for (int i = IAlenth; i > 0; i--) {
			int result = 0;
			boolean lastStep = false;
			if (isRest) {
				result = firstIntArray.get(i-1)+secondIntArray.get(i-1) + rest;                  				
				rest = 0;
			}
			else {				
				result = firstIntArray.get(i-1)+secondIntArray.get(i-1);                  				
			}
			if (result > 9) {
				if (i == 1) {
					lastStep = true;
				}
				result = result - 10;
				rest++;
				isRest = true;
				resultIntArray.add(i-1,result);
			}
			else {
				resultIntArray.add(i-1,result);				
			}
			if (lastStep) {				
				resultIntArray.add(i-1,1);
			}
		}		
	resultString = resultIntArray.toString(); 					//returns sth like: [1, 2, 5, 7]
	resultString = resultString.replaceAll("\\[|,+ |\\]", "");  //replaces [ and ", " and ] with nothing
	return resultString;		
	}

	public static String substract(String firstString, String secondString) throws Exception {
		String resultString = null;		
		prepareIntegerArrays(firstString,secondString); // prepare results array of int		
		int IAlenth = arraysLengthAssurance();		
		prepareResultIntArray(IAlenth);		  			// fill result array with enough zeros
		
		// run substraction algorythm
		int rest = 0;
		boolean isRest = false;
		for (int i = IAlenth; i > 0; i--) {
			int result = 0;
			boolean lastStep = false;
			if (isRest) {
				result = firstIntArray.get(i-1)-secondIntArray.get(i-1) - rest;                  				
				rest = 0;
			}
			else {				
				result = firstIntArray.get(i-1)-secondIntArray.get(i-1);                  				
			}
			if (result < 0) {
				if (i == 1) {
					lastStep = true;
				}
				result = -result;
				rest++;
				isRest = true;
				resultIntArray.add(i-1,result);
			}
			else {
				resultIntArray.add(i-1,result);				
			}
			if (lastStep) {				
				resultIntArray.add(i-1,1);
			}
		}		
	resultString = resultIntArray.toString(); 					//returns sth like: [1, 2, 5, 7]
	resultString = resultString.replaceAll("\\[|,+ |\\]", "");  //replaces [ and ", " and ] with nothing
	return resultString;				
	}
	private static void prepareResultIntArray(int iAlenth) {		
		for (int i = 0; i < iAlenth; i++) {
			resultIntArray.add(0);
		}		
	}

	private static int arraysLengthAssurance() throws Exception {
		int fIAlenth = firstIntArray.size();
		int sIAlenth = secondIntArray.size();	
		if (!(fIAlenth==sIAlenth)) {
			throw new Exception("intArrays Not Equally Long");
		}
		return sIAlenth;
	}


	private static void prepareIntegerArrays(String firstString,String secondString) {
		char[] firstStringArray = firstString.toCharArray();
		char[] secondStringArray = secondString.toCharArray();
		// check which is longer and remember the number
		int fSAlength = firstStringArray.length;
		int sSAlength = secondStringArray .length;
		
		boolean isFirstLonger = false;
		int firstLonger =0;
		int secondLonger = 0;
		if (fSAlength>=sSAlength) {
			firstLonger = fSAlength - sSAlength;
			isFirstLonger = true;			
		}
		else {
			secondLonger = sSAlength = fSAlength;
		}				
		// fill arrays with numbers. If numbers are 1525 and 24, then arrays will be [1,5,2,5] and [0,0,2,4]		
		for (int i = 0; i < firstStringArray.length; i++) {
			if (!isFirstLonger) {
				for (int j = 0; j < secondLonger; j++) {
					firstIntArray.add(0);
				}
			}
			String string = Character.toString(firstStringArray[i]);
			firstIntArray.add((Integer.parseInt(string)) );
		}
		for (int i = 0; i < secondStringArray.length; i++) {
			if (isFirstLonger) {
				for (int j = 0; j < firstLonger; j++) {
					secondIntArray.add(0);
					isFirstLonger=false;
				}
			}
			String string = Character.toString(secondStringArray[i]);
			secondIntArray.add((Integer.parseInt(string)) );			
		}
		
	}	
}
