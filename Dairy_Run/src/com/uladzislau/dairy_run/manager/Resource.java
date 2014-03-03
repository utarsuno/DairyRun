package com.uladzislau.dairy_run.manager;

public interface Resource {

	/**
	 * Creates the desired resource. If the resource has already been created the program will log an error.
	 */
	public abstract void initialize();
	
	/**
	 * @return the name of this resource.
	 */
	public abstract String getName();

	/**
	 * @return information or link regarding the creator of this resource.
	 */
	public abstract String getSource();

	/**
	 * This is used for debugging purposes.
	 * 
	 * @return information useful for debugging.
	 */
	public abstract String currentStatus();

	/**
	 * Frees the memory allocated for this resource.
	 */
	public abstract void dispose();

}