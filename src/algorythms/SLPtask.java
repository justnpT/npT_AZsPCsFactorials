package algorythms;

import java.math.BigDecimal;
import java.util.ArrayList;

public class SLPtask {
	
private BigDecimal numberToResolve;
private int numberOfIngridients;
private ArrayList<String>	pathList;
private ArrayList<BigDecimal> factorList1;  // first factor list  { 16 ...
private ArrayList<BigDecimal> factorList2;	// second factor list { 17 ...
private ArrayList<String>	calculationList;  // calculation list { multiplying ...
												//the above means  16*17 reading from the end of the path
private ArrayList<BigDecimal> modifiedFactorsList;
private ArrayList<BigDecimal> tableOfP1;
private ArrayList<BigDecimal> tableOfP2;
private ArrayList<BigDecimal> tableOfP3;
private ArrayList<BigDecimal> tableOfP4;
private boolean ifProductShouldBeGreaterP1;
private ArrayList<BigDecimal> supportTable;
private boolean ifProductShouldBeGreaterP2;
private boolean ifProductShouldBeGreaterP3;
private boolean ifProductShouldBeGreaterP4;

public SLPtask(BigDecimal numberToResolve) {
	pathList = new ArrayList<String>();
	factorList1 = new ArrayList<BigDecimal>();
	factorList2 = new ArrayList<BigDecimal>();
	calculationList = new ArrayList<String>();
	modifiedFactorsList = new ArrayList<BigDecimal>();
	this.numberToResolve = numberToResolve;
	supportTable = this.prepareSupportTable(9);
	tableOfP1 = this.prepareTable(new BigDecimal("2"));
	tableOfP2 = this.prepareTable(new BigDecimal("3"));
	tableOfP3 = this.prepareTable(new BigDecimal("5"));
	tableOfP4 = this.prepareTable(new BigDecimal("6"));

	printTableElements(tableOfP1);
	printTableElements(tableOfP2);
	printTableElements(tableOfP3);
	printTableElements(tableOfP4);
	tableOfP1 = removeDistantElement(tableOfP1);
	//this operation affect modifiedFactorList, so it shouldnt be at this point modified for other tables
//	tableOfP2 = removeDistantElement(tableOfP2);
//	tableOfP3 = removeDistantElement(tableOfP3);
//	tableOfP4 = removeDistantElement(tableOfP4);
	}

private void printTableElements(ArrayList<BigDecimal> table) {
	System.out.println("new table elements will be printed");
for (int i = 0; i < table.size(); i++) {
	System.out.println("table element number "+i+" = "+table.get(i));
}	
}

/**
 * This method will compare two last elements of priortyTable to numberToResolve and delete the unwanted factor
 * If priority Table is {...,16, 256), and we're looking for 19, then 256 will be deleted)
 * If we are looking for 250, then 256 will stay, and boolean value will be set to false
 * 
 * @param priorityTable
 * @return 
 */
private ArrayList<BigDecimal> removeDistantElement(ArrayList<BigDecimal> priorityTable) {
	BigDecimal lastBD = priorityTable.get(priorityTable.size()-1);
	BigDecimal previousBD = priorityTable.get(priorityTable.size()-2);	
	BigDecimal diff1 = lastBD.subtract(numberToResolve);
	BigDecimal diff2 = numberToResolve.subtract(previousBD);
	System.out.println("");
	System.out.println("removeDistantElement method");
	System.out.println("inner method");
	System.out.println("comparing two numbers: "+diff1+" and "+diff2);
	System.out.println("");
	if (diff1.compareTo(diff2)==1) {
	// 	256 - 19 > 19 - 16
		pathList.add(numberToResolve.toString() + " - " + previousBD.toString());
		BigDecimal factor = priorityTable.get(priorityTable.size()-3);
		factorList1.add(factor);
		factorList2.add(factor);
		modifiedFactorsList.add(factor);
		modifiedFactorsList.add(factor);
		priorityTable.remove(lastBD);
		priorityTable.remove(previousBD);
		calculationList.add("multiplying");		
	}
	else {
		// 256 - 250 < 250 - 16
		pathList.add( lastBD.toString()+ " - " + numberToResolve.toString());
		factorList1.add(previousBD);
		factorList2.add(previousBD);
		modifiedFactorsList.add(previousBD);
		modifiedFactorsList.add(previousBD);
		priorityTable.remove(lastBD);
		calculationList.add("multiplying");
	}
	return priorityTable;

	
}

/**
 * prepares support table, for iterations = 4 it will be {1,2,3,4}
 * support table is later used for SLP operations on results
 * 
 * @param iterations
 * @return
 */
private ArrayList<BigDecimal> prepareSupportTable(int iterations) {
	ArrayList<BigDecimal> resultArray = new ArrayList<BigDecimal>();
	for (int j = 0; j < iterations; j++) {
		resultArray.add(new BigDecimal(j+1));
	}
	return resultArray;
}

/**
 * if param is 2, it will return [2,4,16,256...] till product is lower than numberToResolve
 * 
 * @param bigDecimal
 * @return
 */
private ArrayList<BigDecimal> prepareTable(BigDecimal bigDecimal) {
	ArrayList<BigDecimal> resultArray = new ArrayList<BigDecimal>();
	BigDecimal previousNumber = bigDecimal;
	BigDecimal currentNumber = previousNumber.multiply(previousNumber);
	resultArray.add(new BigDecimal(1));
	resultArray.add(previousNumber);
	while (currentNumber.compareTo(numberToResolve)==-1) {
		resultArray.add(currentNumber);
		previousNumber = currentNumber;
		currentNumber = previousNumber.multiply(previousNumber);		
	}
	resultArray.add(currentNumber);
	return resultArray;
}

public SLPsolution solve(int i) {
	this.numberOfIngridients = numberOfIngridients;
	BigDecimal dividor = divideFactorial();
	BigDecimal difference = this.findMinimalDifference();
	
	return null;
}

private BigDecimal divideFactorial() {
 BigDecimal divisor =tableOfP1.get(tableOfP1.size()-1).multiply(tableOfP1.get(tableOfP1.size()-1));
 return	numberToResolve.divideToIntegralValue(divisor);
}

private BigDecimal findMinimalDifference() {
	BigDecimal diff = getMinimalTopElementsDistance(modifiedFactorsList);
	BigDecimal firstDiff = diff;
	BigDecimal acceptableDiff = new BigDecimal("10000"); // acceptable difference should be established base on priority table 
	int firstFactorIndicator = 0;  //wskazuje ktory index z tableOfP1 ma byc dodany do pierwszego factora. jesli 0 to nie doda nic
	int secondFactorIndicator = 0; //wskazuje ktory index z tableOfP1 ma byc dodany do drugiego factora. jesli 0 to doda 1 element
	ArrayList<BigDecimal> diffsList = new ArrayList<BigDecimal>();
	diffsList.add(diff);
	while (!decideIfDifferenceIsSmallEnough(diff,acceptableDiff)) {
		// modify factors - (instead of 16, 16, do 17,16 etc)
		modifyFactors(firstFactorIndicator, secondFactorIndicator);
		diff=getMinimalTopElementsDistance(modifiedFactorsList);
		diffsList.add(diff);
		if (secondFactorIndicator == tableOfP1.size()-1) {
			// jesli faktor2 byl modyfikowany o kazdy element z tabeli, wyzeruj factor2 i zmien factor1
			secondFactorIndicator = -1; // it will be 0 anyway, cause its incremented in the end of while
			firstFactorIndicator++;
		}
		if (firstFactorIndicator == tableOfP1.size()) {
			//jesli factor1 jest juz maksymalny, to koniec
			
			break;
		}
		secondFactorIndicator++;
	}
	diff = getMinimalElement(diffsList);	
	System.out.println("first diff: "+firstDiff);
	System.out.println("found diff: "+diff);
	return diff;
}

/**
 * returns minimal element in diffsList
 * 
 * @param diffsList
 * @return
 */
private BigDecimal getMinimalElement(ArrayList<BigDecimal> diffsList) {
	// -7, -5, 7 5
	BigDecimal minimum = diffsList.get(0);
	for (int i = 1; i < diffsList.size()-1; i++) {
		if (getAbsoluteValueOf(diffsList.get(i)).compareTo(getAbsoluteValueOf(numberToResolve))==-1) {
			minimum = diffsList.get(i);
		}
	}
	return minimum;
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

private void modifyFactors(int firstFactorIndicator, int secondFactorIndicator) {
	//TODO: (3 marca 2013) zrob tak by faktoy nie modyfikowaly sie o dodanie 1dynki, tylko o dodanie elementu z tableOfP1
	BigDecimal factor1 = factorList1.get(0);
	BigDecimal factor2 = factorList2.get(0);
	factorList1.remove(0);
	factorList2.remove(0);
	calculationList.remove(0);
	// change to i%2 to i%10
	int factorsIndex = tableOfP1.indexOf(factor1);;
	if (firstFactorIndicator==1 && (secondFactorIndicator==0)) {
		factor1 = factor1.add(tableOfP1.get(firstFactorIndicator-1));
	}
	if (firstFactorIndicator>1 && (secondFactorIndicator==0)) {
		factor1 = factor1.add(tableOfP1.get(firstFactorIndicator-1)).subtract(tableOfP1.get(firstFactorIndicator-2));		
	}
	if (secondFactorIndicator==0 && (firstFactorIndicator==0)) {
		factor2=factor2.add(tableOfP1.get(secondFactorIndicator));		
	}
	if (secondFactorIndicator==0 && (firstFactorIndicator!=0)) {
		factor2=factor2.add(tableOfP1.get(secondFactorIndicator)).subtract(tableOfP1.get(tableOfP1.size()-1));		
	}
	if (secondFactorIndicator!=0) {
		factor2=factor2.add(tableOfP1.get(secondFactorIndicator)).subtract(tableOfP1.get(secondFactorIndicator-1));						
	}	
		modifiedFactorsList.remove(0);
		modifiedFactorsList.remove(0);
		modifiedFactorsList.add(factor1);
		modifiedFactorsList.add(factor2);
		factorList1.add(factor1);
		factorList2.add(factor2);
		calculationList.add("multiplying");
			
	}

private boolean decideIfDifferenceIsSmallEnough(BigDecimal diff, BigDecimal acceptableDiff) {
	if (diff.compareTo(acceptableDiff)==1 | diff.compareTo(acceptableDiff.negate())==-1) {
		return false;
	}
	else {
		return true;
	}
}

/**
 * this method will return difference between product of multiplied modified factors and numberToResolve
 * so if modified factors are 16 and 17, and wanted result is 258, this will return 272 - 258 (which is 16*17 - 258) 
 * 
 * @param modifiedFactorsList
 * @return
 */
private BigDecimal getMinimalTopElementsDistance(
		ArrayList<BigDecimal> modifiedFactorsList) {
	BigDecimal firstBD = modifiedFactorsList.get(modifiedFactorsList.size()-1);
	BigDecimal secondBD = modifiedFactorsList.get(modifiedFactorsList.size()-2);
	BigDecimal newProduct = firstBD.multiply(secondBD);
	BigDecimal diff;
	if (ifProductShouldBeGreaterP1) {
		diff = numberToResolve.subtract(newProduct);
	}
	else {
		diff = newProduct.subtract(numberToResolve);		
	}
	return diff;
}

}
