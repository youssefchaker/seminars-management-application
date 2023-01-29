package application;

import java.util.concurrent.atomic.AtomicInteger;

public class Utilisateur {
	private static final AtomicInteger Code_utilisateur = new AtomicInteger(0);
	private String Login;
	private String Password;
	private enum Role {admin,user}
	public String getLogin() {
		return Login;
	}
	public void setLogin(String login) {
		Login = login;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public static AtomicInteger getCodeUtilisateur() {
		return Code_utilisateur;
	};
}
