package domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TRAVELER")
public class Traveler extends User {
	public Traveler() {
		super();
	}
	public Traveler(String email, String name,String password) {
		super(email,name,password);
	}
}
