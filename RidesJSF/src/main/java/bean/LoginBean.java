package bean;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named("login")
@SessionScoped
public class LoginBean implements Serializable {
	private String email;
	private String pasahitza;

	public LoginBean() {
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasahitza() {
		return pasahitza;
	}

	public void setPasahitza(String pasahitza) {
		this.pasahitza = pasahitza;
	}
	public void egiaztatu() {
		System.out.print("Egoki Logeatu da.");
	}
}
