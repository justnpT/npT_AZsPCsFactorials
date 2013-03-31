package com.neptun.classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SLPlog {

	private BufferedWriter out;
	private int factorial;
	private int operationCount;
	private int inputArraySize;
	private String directory;
	private BufferedWriter iterOut;
	
	public SLPlog(int factorial, int operationCount, int inputArraySize) {
		this.factorial = factorial;
		this.operationCount= operationCount;
		this.inputArraySize= inputArraySize;
		this.directory = "scores/score"+factorial+"/score_"+factorial+"_"+operationCount+"_"+inputArraySize;
		try{
			  boolean success = (new File(directory)).mkdirs();
			  if (!success) {
				  System.out.println("directory not created");
			  }	  			
		  FileWriter fstream = new FileWriter(directory+"/aMain.txt");
		  BufferedWriter out = new BufferedWriter(fstream);
		  this.out = out;
		  
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void logEquation(BigDecimal score, String equation, BigDecimal distance) throws IOException {
		this.iterOut.write("rezultat rownania: "+score+"\n rownanie: "+equation+" \n odleglos od targetu: "+distance+"\n======================");
		iterOut.write("\n");
	}

	public void beginLogging() throws IOException {
		this.out.write("starting searching for SLP solution \n factorial:"+factorial+"\n number of operations: "+operationCount+"\n length of input: "+inputArraySize);
		
	}

	public void logInput(int iterOnInput, int[] forInputArray) throws IOException {
		  FileWriter fstream = new FileWriter(directory+"/input"+(iterOnInput+1)+".txt");
		  this.iterOut = new BufferedWriter(fstream);
		  iterOut.write("searching for input: "+getInputElements(forInputArray));
		  iterOut.write("\n");
		  
	}

	private String getInputElements(int[] forInputArray) {
		String result = "{";
		for (int i = 0; i < forInputArray.length-1; i++) {
			result = result + forInputArray[i] +",";
		}
		result = result + forInputArray[forInputArray.length-1];
		result = result + "}";
		return result;
	}

	public void beginLogEquation(int t, int size) throws IOException {
		iterOut.write("analizuje rownanie numer "+(t+1)+". Rownan: "+size);
		iterOut.write("\n");
		
	}

	public void newFinalResult() throws IOException {
		iterOut.write("\nnowy rezultat dodany jako finalowy");
		iterOut.write("\n");
	}

	public void finish(Date firstDate, Date lastDate) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");			
		this.out.write("\n\n==========================\npoczatek:"+dateFormat.format(firstDate)+"\nkoniec:  "+dateFormat.format(lastDate));
		this.out.close();
		}

	public void finishForInput(Date date, Date date2, BigDecimal target, SLPresult rezultat) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");	
		iterOut.write("==========================\npoczatek:"+dateFormat.format(date)+"\nkoniec:  "+dateFormat.format(date2)+"\ntarget to: "+target+"\nnajlepszy wynik to: "+rezultat.getScore()+"\nrownianie to: "+rezultat.getEquation());
		iterOut.close();
	}

	public void sameResult() throws IOException {
		iterOut.write("\n");		
		iterOut.write("napotkano takie samo rozwiazanie");		
		iterOut.write("\n");		
	}

	public void logMasterResult(SLPresult master, int i) throws IOException {
		out.write("rezultat numer "+(i+1)+": "+master.getScore()+"\n rownanie: "+master.getEquation()+" \n odleglos od targetu: "+master.getDistance()+" \n input rownania: "+getInputElements(master.getInputArray())+"\n======================");
		out.write("\n");
	}

}
