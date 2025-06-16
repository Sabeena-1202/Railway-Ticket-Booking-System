import java.util.regex.Pattern;

public class ValidationUtils {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[6-9]\\d{9}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]{2,50}$");
    
    public static void validatePassengerDetails(String name, int age, String gender, 
                                              String berthPreference, String phoneNumber) 
                                              throws TicketSystemException {
        if (!isValidName(name)) {
            throw new TicketSystemException("Invalid name format. Name should contain only letters and spaces (2-50 characters)");
        }
        
        if (!isValidAge(age)) {
            throw new TicketSystemException("Invalid age. Age should be between 1 and 120");
        }
        
        if (!isValidGender(gender)) {
            throw new TicketSystemException("Invalid gender. Please enter 'Male' or 'Female'");
        }
        
        if (!isValidBerthPreference(berthPreference)) {
            throw new TicketSystemException("Invalid berth preference. Please enter 'L', 'U', or 'M'");
        }
        
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new TicketSystemException("Invalid phone number. Please enter a valid 10-digit Indian mobile number");
        }
    }
    
    private static boolean isValidName(String name) {
        return name != null && NAME_PATTERN.matcher(name.trim()).matches();
    }
    
    private static boolean isValidAge(int age) {
        return age >= 1 && age <= 120;
    }
    
    private static boolean isValidGender(String gender) {
        return gender != null && (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female"));
    }
    
    private static boolean isValidBerthPreference(String preference) {
        return preference != null && (preference.equalsIgnoreCase("L") || 
                                    preference.equalsIgnoreCase("U") || 
                                    preference.equalsIgnoreCase("M"));
    }
    
    private static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && PHONE_PATTERN.matcher(phoneNumber.trim()).matches();
    }
}
