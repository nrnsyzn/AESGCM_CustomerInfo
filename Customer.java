/**
 * Represents a customer with sensitive information.
 */
class Customer {
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private String totalMemberPoints;

    /**
     * Constructs a Customer object.
     *
     * @param id the customer's ID
     * @param name the customer's name
     * @param phoneNumber the customer's phone number
     * @param email the customer's email address
     * @param password the customer's password
     * @param totalMemberPoints the customer's total member points
     */
    public Customer(String id, String name, String phoneNumber, String email, String password, String totalMemberPoints) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.totalMemberPoints = totalMemberPoints;
    }

    // Getters and setters (if needed)
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getTotalMemberPoints() {
        return totalMemberPoints;
    }
	@Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", totalMemberPoints='" + totalMemberPoints + '\'' +
                '}';
    }}