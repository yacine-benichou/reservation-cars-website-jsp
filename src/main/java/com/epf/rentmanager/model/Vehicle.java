package com.epf.rentmanager.model;

public class Vehicle {

	private int id;
	private String constructor;
	private String model;
	private int nbPlace;

	public Vehicle(int id, String constructor, String model, int nbPlace) {
		super();
		this.id = id;
		this.constructor = constructor;
		this.model = model;
		this.nbPlace = nbPlace;
	}

	public Vehicle(int id, String constructor, int nbPlace) {
		super();
		this.id = id;
		this.constructor = constructor;
		this.nbPlace = nbPlace;
	}
	
	public Vehicle() {
		
	}

	public int getId() {
		return id;
	}

	public String getConstructor() {
		return constructor;
	}

	public void setConstructor(String constructor) {
		this.constructor = constructor;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getNbPlace() {
		return nbPlace;
	}

	public void setNbPlace(int nbPlace) {
		this.nbPlace = nbPlace;
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", constructor=" + constructor + ", model=" + model + ", nbPlace=" + nbPlace + "]";
	}

}
