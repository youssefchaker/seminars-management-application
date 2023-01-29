package application;

import java.util.concurrent.atomic.AtomicInteger;

import javafx.scene.control.Button;

public class Formation {
	private static final AtomicInteger Code_formation = new AtomicInteger(0);
	private int code_form;
	private int Code;
	public int getCode_form() {
		return code_form;
	}
	public int getCode() {
		return Code;
	}
	public Formation(int code, String intitulé, int nombre_jours,int code_form, int nombre_participants) {
		super();
		this.Code=code;
		this.code_form = code_form;
		Intitulé = intitulé;
		Nombre_jours = nombre_jours;
		Nombre_participants = nombre_participants;
	}
	private String Intitulé;
	private Domaine Domaine;
	private String Domaine_name;
	private String Formateur_name;
	private int Nombre_jours;
	private int Annee;
	private int Mois;
	private Formateur Formateur;
	private int Nombre_participants;
	private Button Button;
	public Button getButton() {
		return Button;
	}
	public void setButton(Button button) {
		this.Button = button;
	}
	public String getIntitulé() {
		return Intitulé;
	}
	public String getDomaine_name() {
		return Domaine_name;
	}
	public void setDomaine_name(String domaine_name) {
		Domaine_name = domaine_name;
	}
	public String getFormateur_name() {
		return Formateur_name;
	}
	public void setFormateur_name(String formateur_name) {
		Formateur_name = formateur_name;
	}
	public void setIntitulé(String intitulé) {
		Intitulé = intitulé;
	}
	public Domaine getDomaine() {
		return Domaine;
	}

	public Formation(String intitulé, String domaine_name, int nombre_jours, int annee, int mois, String formateur_name , int nombre_participants,Button btn) {
		super();
		Intitulé = intitulé;
		Domaine_name = domaine_name;
		Formateur_name = formateur_name;
		Nombre_jours = nombre_jours;
		Annee = annee;
		Mois = mois;
		Nombre_participants = nombre_participants;
		Button=btn;
	}
	public void setDomaine(Domaine domaine) {
		Domaine = domaine;
	}
	public int getNombre_jours() {
		return Nombre_jours;
	}
	public void setNombre_jours(int nombre_jours) {
		Nombre_jours = nombre_jours;
	}
	public int getAnnee() {
		return Annee;
	}
	public void setAnnee(int annee) {
		Annee = annee;
	}
	public int getMois() {
		return Mois;
	}
	public void setMois(int mois) {
		Mois = mois;
	}
	public Formateur getFormateur() {
		return Formateur;
	}
	public void setFormateur(Formateur formateur) {
		Formateur = formateur;
	}
	public int getNombre_participants() {
		return Nombre_participants;
	}
	public void setNombre_participants(int nombre_participants) {
		Nombre_participants = nombre_participants;
	}
	public static AtomicInteger getCodeFormation() {
		return Code_formation;
	}
	
}

