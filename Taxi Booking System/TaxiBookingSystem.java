

import java.util.Scanner;


public class TaxiBookingSystem {
   public static void main(String[] args) throws Exception {
	   Scanner scanner = new Scanner(System.in);
	   
	   boolean loop = true;
	 
	   
	   while(loop) {
		   System.out.println("Choose any one\n1.Book Taxi\n2.Display Details\n3.Exit");
		   int choice = scanner.nextInt();
		   
		   switch(choice) {
		   
		      case 1:
		    	  System.out.println("Enter Pickup Location [A-F]");
                  char pickupLocation = scanner.next().charAt(0);
                  System.out.println("Enter Drop Location [A-F]");
                  char dropLocation = scanner.next().charAt(0);
                  System.out.println("Enter Pickup Time[ in hrs]");
                  int pickupTime = scanner.nextInt();
                  if(validateInput(pickupLocation, dropLocation, pickupTime))
                  System.out.println(TaxiBooking.bookTaxi(pickupLocation, dropLocation, pickupTime));
                  else {
					throw new Exception("Invalid input");
				}
                  break;
		      case 2:
		    	  TaxiBooking.display();
		    	  break;
		    	  
		      case 3:
		    	  loop = false;
		    	  System.out.println("\tThank You!!!");
                  scanner.close();
		   }
		   
	   
	          	   
	   }
   }

private static boolean validateInput(char pickupLocation, char dropLocation, int pickupTime) {
	if(!validateLocation(pickupLocation, dropLocation)) {
		return false;
	}
	if(!validateTime(pickupTime)) {
		return false;
	}
	return true;
}

private static boolean validateTime(int pickupTime) {
	if(pickupTime >=1 && pickupTime <= 24) {
		return true;
	}
	return false;
}

private static boolean validateLocation(char pickupLocation, char dropLocation) {
	if(pickupLocation >= 'A' && pickupLocation <= 'F' && dropLocation >= 'A' && dropLocation <=  'F') {
		return true;
	}
	return false;
	
}
}
