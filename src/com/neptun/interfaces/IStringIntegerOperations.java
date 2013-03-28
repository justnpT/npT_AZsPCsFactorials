/**
 * 
 */
package com.neptun.interfaces;

/**
 * @author Michal 'justnpT' Nowierski
 * @param <T>
 *
 */
public interface IStringIntegerOperations<T> {
	
	T add(T sinteger);
	T substract	(T sinteger);
	T multiply (T sinteger);
	T divide (T sinteger);
	Boolean isPositive();
	/**
	 * 12424 is greater than 12322
	 * 
	 * @param sinteger
	 * @return
	 */
	Boolean isGreaterThan(T sinteger);
	void negate();
}
