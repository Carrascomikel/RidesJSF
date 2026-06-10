package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import domain.Ride;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Named;

@Named("QR")
@SessionScoped
public class QueryRides implements Serializable {

	private String departCity;
	private String arrivalCity;
	private Date date;
	private List<String> departCitys = new ArrayList<String>();
	private List<String> arrivalCitys = new ArrayList<String>();
	
	private List<Ride> rides= new ArrayList<Ride>();

	public QueryRides() {
		this.berrabiaratu();
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
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public void departCityChange(AjaxBehaviorEvent event) {
		arrivalCitys=null;
		arrivalCitys = FacadeBean.getBusinessLogic().getDestinationCities(departCity);
	}
	public void bidaiaBilatu() {
		this.rides=FacadeBean.getBusinessLogic().getRides(departCity, arrivalCity, date);
		
	}

	public List<Ride> getRides() {
		return rides;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}
	public void berrabiaratu() {
		departCitys = FacadeBean.getBusinessLogic().getDepartCities();
	}
}