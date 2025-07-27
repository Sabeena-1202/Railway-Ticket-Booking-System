

import java.util.ArrayList;

public class TaxiBooking {
   private static ArrayList<Taxi> taxiList = new ArrayList<Taxi>();
   private static final int TAXI_LIMIT = 4;
   private static int idGenerator = 1;
   private static ArrayList<Customer> TaxiBookingHistory = new ArrayList<Customer>();
   
   public static String bookTaxi(char pickUpLocation, char dropLocation, int pickUpTime) throws CloneNotSupportedException {
	   
	   if(taxiList.size()<TAXI_LIMIT) {
		   taxiList.add(new Taxi());
	   }
	   
	   int minDistance = Integer.MAX_VALUE;
	   Taxi taxiReady = null;
	   
	   for(Taxi t: taxiList) {
		   if(t.getDropTime() < pickUpTime) {
			   int distance = Math.abs(t.getCurrentLocation()- pickUpLocation);
			   if(distance < minDistance) {
				   minDistance = distance;
				   taxiReady = t;
			   }else if(distance == minDistance) {
				   if(taxiReady != null && t.getEarnings() < taxiReady.getEarnings()) {
					   taxiReady = t;
				   }
			   }
		   }
	   }
	   
	   if(taxiReady!= null) {
		   Customer customer = new Customer();
		   customer.setCustomerId(idGenerator++);
		   customer.setTaxiId(taxiList.indexOf(taxiReady)+1);
		   customer.setPickUpLocation(pickUpLocation);
		   customer.setDropLocation(dropLocation);
		   customer.setPickUpTime(pickUpTime);
		   customer.setDropTime(pickUpTime + Math.abs(dropLocation - pickUpLocation));
		   customer.setFare(Math.abs(dropLocation - pickUpLocation) * ((15-5)*10)+100);
		   
		   taxiReady.setCurrentLocation(dropLocation);
		   taxiReady.setDropTime(customer.getDropTime());
		   taxiReady.setEarnings(taxiReady.getEarnings() + customer.getFare());
		   taxiReady.addCustomer((Customer)customer.clone());
		   TaxiBookingHistory.add((Customer) customer.clone());
	   }
	   
	   return taxiReady!=null ? "Taxi Number: " + (taxiList.indexOf(taxiReady)+1) + " is booked Successfully!" : "No available Taxies";
   }
   
   public static void display() {
	   System.out.println("=".repeat(150));
	   
	   for(Taxi taxi : taxiList) {
		   System.out.println("Taxi - " + taxiList.indexOf(taxi)+1 + " | " + taxi);
		   
		   for(Customer customer : taxi.getCustomerHistory()) {
			   System.out.println(" "+ customer);
		   }
		   System.out.println("-".repeat(150));
	   }
   }
}
