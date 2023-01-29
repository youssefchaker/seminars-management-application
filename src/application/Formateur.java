package application;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Formateur {
	private static final AtomicInteger Code_formateur = new AtomicInteger(0);
	private int Code;
	private String Nom;
	private String Prenom;
	private Domaine Domaine;
	private String N_telephone;
	private String Organisme;
	private Date Date_naissance;
	private Utilisateur User;
	private Profil Profil;
	private int Etat;
	private String Login;
	private String Libelle;
	
	public String getLibelle() {
		return Libelle;
	}

	public void setLibelle(String libelle) {
		Libelle = libelle;
	}

	public String getLogin() {
		return Login;
	}

	public void setLogin(String login) {
		Login = login;
	}

	//extra
	private int user_code;
	private String Domaine_name;
	private int Code_domaine;
	private int profil_code;
	
	//constructor
	public Formateur(int code, String nom, String prenom,String login,String lib, String n_telephone) {
		super();
		Code = code;
		Nom = nom;
		Prenom = prenom;
		Login=login;
		Libelle=lib;
		N_telephone = n_telephone;
	}

	public int getCode() {
		return Code;
	}

	public void setCode(int code) {
		Code = code;
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

	public Domaine getDomaine() {
		return Domaine;
	}

	public void setDomaine(Domaine domaine) {
		Domaine = domaine;
	}

	public String getN_telephone() {
		return N_telephone;
	}

	public void setN_telephone(String n_telephone) {
		N_telephone = n_telephone;
	}

	public String getOrganisme() {
		return Organisme;
	}

	public void setOrganisme(String organisme) {
		Organisme = organisme;
	}

	public Date getDate_naissance() {
		return Date_naissance;
	}

	public void setDate_naissance(Date date_naissance) {
		Date_naissance = date_naissance;
	}

	public Utilisateur getUser() {
		return User;
	}

	public void setUser(Utilisateur user) {
		User = user;
	}

	public Profil getProfil() {
		return Profil;
	}

	public void setProfil(Profil profil) {
		Profil = profil;
	}

	public int getEtat() {
		return Etat;
	}

	public void setEtat(int etat) {
		Etat = etat;
	}

	public int getUser_code() {
		return user_code;
	}

	public void setUser_code(int user_code) {
		this.user_code = user_code;
	}

	public String getDomaine_name() {
		return Domaine_name;
	}

	public void setDomaine_name(String domaine_name) {
		Domaine_name = domaine_name;
	}

	public int getCode_domaine() {
		return Code_domaine;
	}

	public void setCode_domaine(int code_domaine) {
		Code_domaine = code_domaine;
	}

	public int getProfil_code() {
		return profil_code;
	}

	public void setProfil_code(int profil_code) {
		this.profil_code = profil_code;
	}

	public static AtomicInteger getCodeFormateur() {
		return Code_formateur;
	}
		
}
