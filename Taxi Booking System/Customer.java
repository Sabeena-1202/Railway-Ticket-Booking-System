

public class Customer implements Cloneable{
	
    private int customerId;
    private int taxiId;
    private char pickUpLocation;
    private char dropLocation;
    private int pickUpTime;
    private int dropTime;
    private int fare;
    
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getTaxiId() {
		return taxiId;
	}
	public void setTaxiId(int taxiId) {
		this.taxiId = taxiId;
	}
	public char getPickUpLocation() {
		return pickUpLocation;
	}
	public void setPickUpLocation(char pickUpLocation) {
		this.pickUpLocation = pickUpLocation;
	}
	public char getDropLocation() {
		return dropLocation;
	}
	public void setDropLocation(char dropLocation) {
		this.dropLocation = dropLocation;
	}
	public int getPickUpTime() {
		return pickUpTime;
	}
	public void setPickUpTime(int pickUpTime) {
		this.pickUpTime = pickUpTime;
	}
	public int getDropTime() {
		return dropTime;
	}
	public void setDropTime(int dropTime) {
		this.dropTime = dropTime;
	}
	public int getFare() {
		return fare;
	}
	public void setFare(int fare) {
		this.fare = fare;
	}
    
	public String toString() {
		return "[CUSTOMER DETAILS]: " + "Customer ID = " + customerId + "TaxiID = " + taxiId + "PickUp Location = " + pickUpLocation + "Drop Location = " + dropLocation + "PickUp Time = " + pickUpTime + "Drop Time = " + dropTime + "Fare  = " + fare; 
	}
    
}
