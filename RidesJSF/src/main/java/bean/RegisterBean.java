package bean;

import exceptions.UserAlreadyExistsException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("register")
@RequestScoped
public class RegisterBean {


    private String izena;
    private String emaila;
    private String pasahitza;
    private String pasahitzaBerretsi;
    private String rola;

    public RegisterBean() {}

    public String getIzena() {
        return izena;
    }
    public void setIzena(String izena) {
        this.izena = izena;
    }

    public String getEmaila() {
        return emaila;
    }
    public void setEmaila(String emaila) {
        this.emaila = emaila;
    }

    public String getPasahitza() {
        return pasahitza;
    }
    public void setPasahitza(String pasahitza) {
        this.pasahitza = pasahitza;
    }

    public String getPasahitzaBerretsi() {
        return pasahitzaBerretsi;
    }
    public void setPasahitzaBerretsi(String pasahitzaBerretsi) {
        this.pasahitzaBerretsi = pasahitzaBerretsi;
    }

    public String getRola() {
        return rola;
    }
    public void setRola(String rola) {
        this.rola = rola;
    }

    public String erregistratu() {

    	
        if (!pasahitza.equals(pasahitzaBerretsi)) {
        	return "register?faces-redirect=true&error=pasahitza";
            
        }
        try {
        	FacadeBean.getBusinessLogic().addUser(emaila, izena,pasahitza, rola);
        	 return "login";
        }catch(UserAlreadyExistsException e) {
        	FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "User with this email already exists", null));
        	this.izena=null;
        	this.emaila=null;
        	this.pasahitza=null;
        	this.pasahitzaBerretsi=null;
        	this.rola=null;
        	return "register?faces-redirect=true&error=User";
        }
    }
}