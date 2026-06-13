package bean;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("O")
@SessionScoped
public class Option implements Serializable {	
	public Option() {
		
	}
	public String goToCreateRide() {
	    return "createRide";
	}

	public String goToQueryRides() {
	    return "queryRides";
	}
}
