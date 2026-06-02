package bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named("register")
@RequestScoped
public class RegisterBean {


    private String izena;
    private String emaila;
    private String pasahitza;
    private String pasahitzaBerretsi;
    private String rola;



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
        	System.out.print("Pasahitza berdinak izan behar dute.");
        	return "register?faces-redirect=true&error=pasahitza";
            
        }

        // TODO: Gordetzeko logika gehitu (datu-basea, etc.)
        System.out.println("Erregistro berria:");
        System.out.println("  Izena:  " + izena);
        System.out.println("  Emaila: " + emaila);
        System.out.println("  Rola:   " + rola);



        return "login?faces-redirect=true";
    }
}