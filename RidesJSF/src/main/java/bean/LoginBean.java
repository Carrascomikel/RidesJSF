package bean;

import java.io.Serializable;

import domain.Driver;
import domain.Traveler;
import domain.User;
import exceptions.InvalidPasswordException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("login")
@SessionScoped
public class LoginBean implements Serializable {
	private String email;
	private String pasahitza;
	private User user;

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
	
	public String egiaztatu() {
		try {
		user=FacadeBean.getBusinessLogic().login(email, pasahitza);
		if (user==null)
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "There is no user with that email", null));
		if(user instanceof Driver)
			System.out.print("Barrura Driver");
		else if (user instanceof Traveler)
			System.out.print("Barrura Travler");
		}catch(InvalidPasswordException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect password", null));
			this.pasahitza=null;
			this.email=null;
		}
			
	}
}
