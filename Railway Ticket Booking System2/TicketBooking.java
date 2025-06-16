import java.util.*;

public class TicketBooking {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TicketSystem ticketSystem = new TicketSystem();
    
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("    WELCOME TO INDIAN RAILWAYS BOOKING SYSTEM");
        System.out.println("=".repeat(60));
        Logger.info("Railway Booking System started");
        
        while (true) {
            try {
                displayMenu();
                int choice = getValidChoice();
                
                switch (choice) {
                    case 1:
                        handleBookTicket();
                        break;
                    case 2:
                        handleCancelTicket();
                        break;
                    case 3:
                        ticketSystem.printBookedTickets();
                        break;
                    case 4:
                        ticketSystem.printAvailableTickets();
                        break;
                    case 5:
                        ticketSystem.viewRacTickets();
                        break;
                    case 6:
                        ticketSystem.viewWaitingListTickets();
                        break;
                    case 7:
                        handleViewTicketDetails();
                        break;
                    case 8:
                        handleExit();
                        return;
                    default:
                        System.out.println(" Invalid choice! Please try again.");
                }
                
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
                
            } catch (Exception e) {
                Logger.error("Unexpected error: " + e.getMessage());
                System.out.println(" An unexpected error occurred. Please try again.");
            }
        }
    }
    
    private static void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           RAILWAY BOOKING MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Book Ticket");
        System.out.println("2. Cancel Ticket");
        System.out.println("3. View Confirmed Tickets");
        System.out.println("4. View Available Tickets");
        System.out.println("5. View RAC Tickets");
        System.out.println("6. View Waiting List Tickets");
        System.out.println("7. View Ticket Details");
        System.out.println("8. Exit");
        System.out.println("=".repeat(50));
        System.out.print("Enter your choice (1-8): ");
    }
    
    private static int getValidChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            return choice;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // consume invalid input
            return -1;
        }
    }
    
    private static void handleBookTicket() {
        try {
            System.out.println("\n TICKET BOOKING FORM");
            System.out.println("-".repeat(30));
            
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter Age: ");
            int age = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Enter Gender (Male/Female): ");
            String gender = scanner.nextLine();
            
            System.out.print("Enter Berth Preference (L-Lower/U-Upper/M-Middle): ");
            String berthPreference = scanner.nextLine();
            
            System.out.print("Enter Phone Number: ");
            String phoneNumber = scanner.nextLine();
            
            String result = ticketSystem.bookTicket(name, age, gender, berthPreference, phoneNumber);
            System.out.println("\n " + result);
            
        } catch (TicketSystemException e) {
            System.out.println("\n Booking Failed: " + e.getMessage());
        } catch (InputMismatchException e) {
            scanner.nextLine(); // consume invalid input
            System.out.println("\n Invalid input format. Please enter valid details.");
        }
    }
    
    private static void handleCancelTicket() {
        try {
            System.out.print("\nEnter Ticket ID to cancel: ");
            String ticketId = scanner.nextLine();
            
            boolean cancelled = ticketSystem.cancelTicket(ticketId);
            if (cancelled) {
                System.out.println("\n Ticket cancelled successfully!");
            }
            
        } catch (TicketSystemException e) {
            System.out.println("\n Cancellation Failed: " + e.getMessage());
        }
    }
    
    private static void handleViewTicketDetails() {
        try {
            System.out.print("\nEnter Ticket ID: ");
            String ticketId = scanner.nextLine();
            
            Passenger passenger = ticketSystem.getTicketDetails(ticketId);
            System.out.println("\n=== TICKET DETAILS ===");
            System.out.println(passenger);
            
        } catch (TicketSystemException e) {
            System.out.println("\n " + e.getMessage());
        }
    }
    
    private static void handleExit() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("   Thank you for using Railway Booking System!");
        System.out.println("            Have a safe journey! ");
        System.out.println("=".repeat(50));
        Logger.info("Railway Booking System shutdown");
    }
}