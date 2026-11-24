public class Instructor extends Person {

    private String department;
    private String specialization;
    private String employmentStatus;

    // Constructor
    public Instructor(String id, String fullName, String email,
                     String contactNumber, String department,
                     String specialization, String employmentStatus) {

        super(id, fullName, email, contactNumber);

        this.department = department;
        this.specialization = specialization;
        this.employmentStatus = employmentStatus;
    }

    // Getters
    public String getDepartment() {
        return department;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    // Setters
    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    // Teaching Load
    public String getTeachingLoad() {
        if (employmentStatus.equalsIgnoreCase("Full-time")) {
            return "Full teaching load";
        } else {
            return "Part-time teaching load";
        }
    }

    // Display Information
    @Override
    public void displayInfo() {
        super.displayInfo();

        System.out.println("Department: " + department);
        System.out.println("Specialization: " + specialization);
        System.out.println("Employment Status: " + employmentStatus);
        System.out.println("Teaching Load: " + getTeachingLoad());
    }
}