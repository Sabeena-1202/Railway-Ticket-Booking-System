import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class TicketSystem {
    // Configuration constants
    private static final int MAX_BERTHS = 3;
    private static final int MAX_RAC_TICKETS = 2;
    private static final int MAX_WAITING_LIST = 3;
    
    // Thread-safe collections with lock
    private final ReentrantLock lock = new ReentrantLock();
    private final List<String> availableBerths;
    private final Queue<Passenger> racQueue;
    private final Queue<Passenger> waitingListQueue;
    private final List<Passenger> confirmedPassengers;
    private final Map<String, Passenger> ticketMap; // For O(1) lookup
    
    private int ticketCounter = 1;
    
    public TicketSystem() {
        this.availableBerths = new ArrayList<>(Arrays.asList("L", "U", "M"));
        this.racQueue = new LinkedList<>();
        this.waitingListQueue = new LinkedList<>();
        this.confirmedPassengers = new ArrayList<>();
        this.ticketMap = new HashMap<>();
        
        Logger.info("Railway Ticket System initialized successfully");
    }
    
    public synchronized String bookTicket(String name, int age, String gender, 
                                        String berthPreference, String phoneNumber) 
                                        throws TicketSystemException {
        lock.lock();
        try {
            // Validate input
            ValidationUtils.validatePassengerDetails(name, age, gender, berthPreference, phoneNumber);
            
            String ticketId = generateTicketId();
            String normalizedGender = gender.trim();
            String normalizedPreference = berthPreference.trim().toUpperCase();
            
            Passenger passenger;
            
            if (!availableBerths.isEmpty()) {
                // Confirmed booking
                String allocatedBerth = allocateBerth(age, normalizedGender, normalizedPreference);
                passenger = new Passenger(name.trim(), age, normalizedGender, 
                                        normalizedPreference, allocatedBerth, ticketId, phoneNumber);
                
                confirmedPassengers.add(passenger);
                ticketMap.put(ticketId, passenger);
                availableBerths.remove(allocatedBerth);
                
                Logger.info("Ticket confirmed - ID: " + ticketId + ", Name: " + name);
                return "CONFIRMED: " + passenger.toString();
                
            } else if (racQueue.size() < MAX_RAC_TICKETS) {
                // RAC booking
                passenger = new Passenger(name.trim(), age, normalizedGender, 
                                        normalizedPreference, "RAC", ticketId, phoneNumber);
                
                racQueue.offer(passenger);
                ticketMap.put(ticketId, passenger);
                
                Logger.info("Ticket in RAC - ID: " + ticketId + ", Name: " + name);
                return "RAC: " + passenger.toString();
                
            } else if (waitingListQueue.size() < MAX_WAITING_LIST) {
                // Waiting list
                passenger = new Passenger(name.trim(), age, normalizedGender, 
                                        normalizedPreference, "WAITING_LIST", ticketId, phoneNumber);
                
                waitingListQueue.offer(passenger);
                ticketMap.put(ticketId, passenger);
                
                Logger.info("Ticket in Waiting List - ID: " + ticketId + ", Name: " + name);
                return "WAITING_LIST: " + passenger.toString();
                
            } else {
                Logger.warn("Booking failed - No tickets available for: " + name);
                throw new TicketSystemException("Sorry! No tickets available. All berths, RAC, and waiting list are full.");
            }
            
        } finally {
            lock.unlock();
        }
    }
    
    public synchronized boolean cancelTicket(String ticketId) throws TicketSystemException {
        lock.lock();
        try {
            if (ticketId == null || ticketId.trim().isEmpty()) {
                throw new TicketSystemException("Invalid ticket ID");
            }
            
            Passenger passenger = ticketMap.get(ticketId.trim());
            if (passenger == null) {
                throw new TicketSystemException("No ticket found with ID: " + ticketId);
            }
            
            if (passenger.getStatus() == Passenger.BookingStatus.CANCELLED) {
                throw new TicketSystemException("Ticket is already cancelled: " + ticketId);
            }
            
            // Remove from respective collections
            switch (passenger.getStatus()) {
                case CONFIRMED:
                    confirmedPassengers.remove(passenger);
                    availableBerths.add(passenger.getAllocatedBerth());
                    promoteFromRAC();
                    break;
                    
                case RAC:
                    racQueue.remove(passenger);
                    promoteFromWaitingList();
                    break;
                    
                case WAITING_LIST:
                    waitingListQueue.remove(passenger);
                    break;
            }
            
            passenger.setStatus(Passenger.BookingStatus.CANCELLED);
            ticketMap.remove(ticketId);
            
            Logger.info("Ticket cancelled successfully - ID: " + ticketId + ", Name: " + passenger.getName());
            return true;
            
        } finally {
            lock.unlock();
        }
    }
    
    private void promoteFromRAC() {
        if (!racQueue.isEmpty()) {
            Passenger racPassenger = racQueue.poll();
            String allocatedBerth = allocateBerth(racPassenger.getAge(), 
                                                racPassenger.getGender(), 
                                                racPassenger.getBerthPreference());
            
            racPassenger.setAllocatedBerth(allocatedBerth);
            confirmedPassengers.add(racPassenger);
            availableBerths.remove(allocatedBerth);
            
            Logger.info("RAC ticket promoted to confirmed - ID: " + racPassenger.getTicketId());
            promoteFromWaitingList();
        }
    }
    
    private void promoteFromWaitingList() {
        if (!waitingListQueue.isEmpty() && racQueue.size() < MAX_RAC_TICKETS) {
            Passenger waitingPassenger = waitingListQueue.poll();
            waitingPassenger.setAllocatedBerth("RAC");
            racQueue.offer(waitingPassenger);
            
            Logger.info("Waiting list ticket promoted to RAC - ID: " + waitingPassenger.getTicketId());
        }
    }
    
    private String allocateBerth(int age, String gender, String preference) {
        // Senior citizens (>60) and females get lower berth preference
        if ((age > 60 || gender.equalsIgnoreCase("Female")) && availableBerths.contains("L")) {
            return "L";
        }
        
        // Try to allocate preferred berth
        if (availableBerths.contains(preference)) {
            return preference;
        }
        
        // Allocate any available berth
        return availableBerths.get(0);
    }
    
    private String generateTicketId() {
        return String.format("PNR%06d", ticketCounter++);
    }
    
    public void printBookedTickets() {
        lock.lock();
        try {
            if (confirmedPassengers.isEmpty()) {
                System.out.println("\n=== No Confirmed Tickets ===");
            } else {
                System.out.println("\n=== CONFIRMED TICKETS ===");
                System.out.println("Total Confirmed: " + confirmedPassengers.size());
                confirmedPassengers.forEach(System.out::println);
            }
        } finally {
            lock.unlock();
        }
    }
    
    public void printAvailableTickets() {
        lock.lock();
        try {
            System.out.println("\n=== AVAILABILITY STATUS ===");
            System.out.println("Available Berths: " + availableBerths.size() + "/" + MAX_BERTHS + " " + availableBerths);
            System.out.println("Available RAC: " + (MAX_RAC_TICKETS - racQueue.size()) + "/" + MAX_RAC_TICKETS);
            System.out.println("Available Waiting List: " + (MAX_WAITING_LIST - waitingListQueue.size()) + "/" + MAX_WAITING_LIST);
            System.out.println("Last updated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } finally {
            lock.unlock();
        }
    }
    
    public void viewRacTickets() {
        lock.lock();
        try {
            if (racQueue.isEmpty()) {
                System.out.println("\n=== No RAC Tickets ===");
            } else {
                System.out.println("\n=== RAC TICKETS ===");
                System.out.println("Total RAC: " + racQueue.size());
                racQueue.forEach(System.out::println);
            }
        } finally {
            lock.unlock();
        }
    }
    
    public void viewWaitingListTickets() {
        lock.lock();
        try {
            if (waitingListQueue.isEmpty()) {
                System.out.println("\n=== No Waiting List Tickets ===");
            } else {
                System.out.println("\n=== WAITING LIST TICKETS ===");
                System.out.println("Total Waiting: " + waitingListQueue.size());
                waitingListQueue.forEach(System.out::println);
            }
        } finally {
            lock.unlock();
        }
    }
    
    public Passenger getTicketDetails(String ticketId) throws TicketSystemException {
        lock.lock();
        try {
            if (ticketId == null || ticketId.trim().isEmpty()) {
                throw new TicketSystemException("Invalid ticket ID");
            }
            
            Passenger passenger = ticketMap.get(ticketId.trim());
            if (passenger == null) {
                throw new TicketSystemException("No ticket found with ID: " + ticketId);
            }
            
            return passenger;
        } finally {
            lock.unlock();
        }
    }
}
