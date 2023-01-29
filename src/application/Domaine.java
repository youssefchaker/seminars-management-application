package application;

import java.util.concurrent.atomic.AtomicInteger;

public class Domaine {
	private static final AtomicInteger Code_domaine = new AtomicInteger(0);
	private String Libelle;
	public int getCode() {
		return Code;
	}
	private int Code;
	public String getLibelle() {
		return Libelle;
	}
	public Domaine(int code,String libelle) {
		super();
		Libelle = libelle;
		this.Code=code;
	}
	public void setLibelle(String libelle) {
		Libelle = libelle;
	}
	public static AtomicInteger getCodeDomaine() {
		return Code_domaine;
	}
}
