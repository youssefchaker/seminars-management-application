package application;

import java.util.concurrent.atomic.AtomicInteger;

public class Profil {
	private static final AtomicInteger Code_profil = new AtomicInteger(0);
	private String Libelle;
	public String getLibelle() {
		return Libelle;
	}
	public void setLibelle(String libelle) {
		this.Libelle = libelle;
	}
	public static AtomicInteger getCodeProfil() {
		return Code_profil;
	}
}
