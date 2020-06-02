package com.fertilizertool.model;

public class Fertilizers {
	
	
	private int Id;
	private String fertilizerSources;
	private String N;
	private String P;
	private String K;
	private int priceBag;
	private int priceCart;
	private int priceTractor;
	private String type;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getFertilizerSources() {
		return fertilizerSources;
	}
	public void setFertilizerSources(String fertilizerSources) {
		this.fertilizerSources = fertilizerSources;
	}
	public String getN() {
		return N;
	}
	public void setN(String n) {
		N = n;
	}
	public String getP() {
		return P;
	}
	public void setP(String p) {
		P = p;
	}
	public String getK() {
		return K;
	}
	public void setK(String k) {
		K = k;
	}
	public int getPriceBag() {
		return priceBag;
	}
	public void setPriceBag(int priceBag) {
		this.priceBag = priceBag;
	}
	public int getPriceCart() {
		return priceCart;
	}
	public void setPriceCart(int priceCart) {
		this.priceCart = priceCart;
	}
	public int getPriceTractor() {
		return priceTractor;
	}
	public void setPriceTractor(int priceTractor) {
		this.priceTractor = priceTractor;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
}
