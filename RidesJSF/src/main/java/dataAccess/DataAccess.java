package dataAccess;

import java.util.ArrayList;

import java.util.Date;

import java.util.List;

import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import configuration.UtilDate;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import domain.User;
import eredua.JPAUtil;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.UserAlreadyExistsException;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	private EntityManager db;

	/**
	 * This method returns all the cities where rides depart
	 * 
	 * @return collection of cities
	 */
	public List<String> getDepartCities() {
		TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.from FROM Ride r ORDER BY r.from", String.class);
		List<String> cities = query.getResultList();
		return cities;

	}

	/**
	 * This method returns all the arrival destinations, from all rides that depart
	 * from a given city
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getArrivalCities(String from) {
		TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.to FROM Ride r WHERE r.from=?1 ORDER BY r.to",
				String.class);
		query.setParameter(1, from);
		List<String> arrivingCities = query.getResultList();
		return arrivingCities;

	}

	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from        the origin location of a ride
	 * @param to          the destination location of a ride
	 * @param date        the date of the ride
	 * @param nPlaces     available seats
	 * @param driverEmail to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today
	 * @throws RideAlreadyExistException         if the same ride already exists for
	 *                                           the driver
	 */
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String driverEmail)
			throws RideAlreadyExistException, RideMustBeLaterThanTodayException {
		System.out.println(">> DataAccess: createRide=> from= " + from + " to= " + to + " driver=" + driverEmail
				+ " date " + date);
		try {
			if (new Date().compareTo(date) > 0) {
				throw new RideMustBeLaterThanTodayException(
						ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
			}
			db.getTransaction().begin();

			Driver driver = db.find(Driver.class, driverEmail);
			if (driver.doesRideExists(from, to, date)) {
				db.getTransaction().commit();
				throw new RideAlreadyExistException(
						ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
			}
			System.out.println("crea addRide");
			Ride ride = driver.addRide(from, to, date, nPlaces, price);
			// next instruction can be obviated
			db.persist(driver);
			db.getTransaction().commit();

			return ride;
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}

	}

	public User addUser(String email,String name, String password, String rol) throws UserAlreadyExistsException {
		System.out.println(">>DataAccess: addUser=> email=" + email + "rol=" + rol);
		
		if (this.getUserByEmail(email)!=null) {
			throw new UserAlreadyExistsException();
		}
		try {
				User user=null;
				db.getTransaction().begin();
				if("driver".equalsIgnoreCase(rol))
					user= new Driver(email,name,password);
				else
					user=new Traveler(email,name,password);
				db.persist(user);
				db.getTransaction().commit();
				
				return user;
			
		

		} catch (Exception e) {
			if (db.getTransaction().isActive())
				db.getTransaction().rollback();
			return null;
		}
	}

	public User getUserByEmail(String email) {
		try {
			System.out.println("><DataAccess: getUserByEmail=> email=" + email);
			User user = null;
			TypedQuery<User> query = db.createQuery("SELECT u FROM User WHERE u.email=:email", User.class);
			query.setParameter(1, email);
			user=query.getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * This method retrieves the rides from two locations on a given date
	 * 
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date the date of the ride
	 * @return collection of rides
	 */
	public List<Ride> getRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getRides=> from= " + from + " to= " + to + " date " + date);

		List<Ride> res = new ArrayList<Ride>();
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",
				Ride.class);
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, date);
		List<Ride> rides = query.getResultList();
		for (Ride ride : rides) {
			res.add(ride);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date of the month for which days with rides want to be retrieved
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		List<Date> res = new ArrayList<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT r.date FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date BETWEEN ?3 and ?4",
				Date.class);

		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, firstDayMonthDate);
		query.setParameter(4, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			res.add(d);
		}
		return res;
	}

	public void open() {
		db = JPAUtil.getEntityManager();
		try {
			db.getTransaction().begin();
			Driver driver = db.find(Driver.class, "driver3@gmail.com");
			if (driver == null) {
				driver = new Driver("driver3@gmail.com", "Driver Three", "1234");
				db.persist(driver);
				System.out.println(">> Driver inicial creado");
			}
			db.getTransaction().commit();
		} catch (Exception e) {
			if (db.getTransaction().isActive())
				db.getTransaction().rollback();
		}

	}

	public void close() {
		if (db != null && db.isOpen()) {
			db.close();
		}
		System.out.println("DataAccess closed");
	}

}
