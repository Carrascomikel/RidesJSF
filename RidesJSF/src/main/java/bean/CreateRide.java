package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Named;


@Named("CR")
@SessionScoped
public class CreateRide implements Serializable {
	private String departCity;
	private String arrivalCity;
	private int numberOfSeats;
	private double price;
	private Date date;
	private List<String> departCitys = new ArrayList<String>();
	private List<String> arrivalCitys = new ArrayList<String>();

	public CreateRide() {
		departCitys = this.departures();
		System.out.println(departCitys);
	}

	public List<String> getDepartCitys() {
		return departCitys;
	}

	public void setDepartCitys(List<String> departCitys) {
		this.departCitys = departCitys;
	}
	

	public List<String> getArrivalCitys() {
		return arrivalCitys;
	}

	public void setArrivalCitys(List<String> arrivalCitys) {
		this.arrivalCitys = arrivalCitys;
	}

	public String getDepartCity() {
		return departCity;
	}

	public void setDepartCity(String departCity) {
		this.departCity = departCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<String> departures() {
		return FacadeBean.getBusinessLogic().getDepartCities();
	}
	
	public void departCityChange(AjaxBehaviorEvent event) {
		arrivalCitys=FacadeBean.getBusinessLogic().getDestinationCities(departCity);
	}
}
