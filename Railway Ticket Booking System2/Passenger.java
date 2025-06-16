import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Passenger {
    private String name;
    private int age;
    private String gender;
    private String berthPreference;
    private String allocatedBerth;
    private String ticketId;
    private LocalDateTime bookingTime;
    private String phoneNumber;
    private BookingStatus status;
    
    public enum BookingStatus {
        CONFIRMED, RAC, WAITING_LIST, CANCELLED
    }
    
    public Passenger(String name, int age, String gender, String berthPreference, 
                    String allocatedBerth, String ticketId, String phoneNumber) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.berthPreference = berthPreference;
        this.allocatedBerth = allocatedBerth;
        this.ticketId = ticketId;
        this.phoneNumber = phoneNumber;
        this.bookingTime = LocalDateTime.now();
        this.status = determineStatus(allocatedBerth);
    }
    
    private BookingStatus determineStatus(String berth) {
        if ("RAC".equals(berth)) return BookingStatus.RAC;
        if ("WAITING_LIST".equals(berth)) return BookingStatus.WAITING_LIST;
        return BookingStatus.CONFIRMED;
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getBerthPreference() { return berthPreference; }
    public String getAllocatedBerth() { return allocatedBerth; }
    public String getTicketId() { return ticketId; }
    public LocalDateTime getBookingTime() { return bookingTime; }
    public String getPhoneNumber() { return phoneNumber; }
    public BookingStatus getStatus() { return status; }
    
    public void setAllocatedBerth(String allocatedBerth) { 
        this.allocatedBerth = allocatedBerth; 
        this.status = determineStatus(allocatedBerth);
    }
    public void setStatus(BookingStatus status) { this.status = status; }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("Ticket ID: %s | Name: %s | Age: %d | Gender: %s | " +
                           "Preference: %s | Allocated: %s | Status: %s | Booked: %s | Phone: %s",
                           ticketId, name, age, gender, berthPreference, allocatedBerth, 
                           status, bookingTime.format(formatter), phoneNumber);
    }
}