package application;

import java.util.Date;

public class Participant {
	private int Matricule_participant;
	private String Nom;
	private String Prenom;
	private Date Date_naissance;
	private Profil Profil;
	private Utilisateur User;
	private int user_code;
	private int profil_code;
	public Participant(int matricule_participant, Date date_naissance, int user_code, int profil_code) {
		super();
		Matricule_participant = matricule_participant;
		Date_naissance = date_naissance;
		this.user_code = user_code;
		this.profil_code = profil_code;
	}
	public int getUser_code() {
		return user_code;
	}
	public int getProfil_code() {
		return profil_code;
	}
	public Utilisateur getUser() {
		return User;
	}
	public int getMatricule_participant() {
		return Matricule_participant;
	}
	public void setMatricule_participant(int matricule_participant) {
		Matricule_participant = matricule_participant;
	}
	public String getNom() {
		return Nom;
	}
	public void setNom(String nom) {
		Nom = nom;
	}
	public String getPrenom() {
		return Prenom;
	}
	public void setPrenom(String prenom) {
		Prenom = prenom;
	}
	public Date getDate_naissance() {
		return Date_naissance;
	}
	public void setDate_naissance(Date date_naissance) {
		Date_naissance = date_naissance;
	}
	public Profil getProfil() {
		return Profil;
	}
	public void setProfil(Profil profil) {
		Profil = profil;
	}
	
}
