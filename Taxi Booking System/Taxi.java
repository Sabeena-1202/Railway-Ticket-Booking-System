

import java.util.ArrayList;

public class Taxi {
   private char currentLocation = 'A';
   private int dropTime;
   private int earnings;
   private ArrayList<Customer> customerHistory = new ArrayList<Customer>();
   
   
public char getCurrentLocation() {
	return currentLocation;
}
public void setCurrentLocation(char currentLocation) {
	this.currentLocation = currentLocation;
}
public int getDropTime() {
	return dropTime;
}
public void setDropTime(int dropTime) {
	this.dropTime = dropTime;
}
public int getEarnings() {
	return earnings;
}
public void setEarnings(int earnings) {
	this.earnings = earnings;
}
public void addCustomer(Customer custumer) {
	customerHistory.add(custumer);
}
public ArrayList<Customer> getCustomerHistory(){
	return customerHistory;
}

public String toString() {
	return "Location: " + currentLocation + " | Free Time: " + dropTime + " | Earnings: " + earnings;
}
   
}
