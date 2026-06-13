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
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public boolean isDriver() {
		 System.out.println("isDriver - user: " + user + " - clase: " + (user != null ? user.getClass().getSimpleName() : "null"));
		return user instanceof Driver;
	}

	public boolean isTraveler() {
		return user instanceof Traveler;
	}
	public String goToQueryRides() {
	    return "QueryRides?faces-redirect=true";
	}
	public String egiaztatu() {
		try {
			user = FacadeBean.getBusinessLogic().login(email, pasahitza);
			if (user == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "There is no user with that email", null));
				return "register?faces-redirect=true&error=User";
			}
			if (user instanceof Driver) {
				return "option";
			} else if (user instanceof Traveler) {
				return "option";
			}
			return "register?faces-redirect=true&error=User";
		} catch (InvalidPasswordException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect password", null));
			this.pasahitza = null;
			this.email = null;
			return "register?faces-redirect=true&error=User";
		}

	}
}
