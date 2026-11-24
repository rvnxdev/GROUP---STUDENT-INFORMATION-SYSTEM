public class Instructor extends Person {
    private String department;
    private String specialization;
    private String employmentStatus;

    public Instructor(String id, String fullName, String email, String contactNumber,
                      String department, String specialization, String employmentStatus) {
        super(id, fullName, email, contactNumber);
        this.department = department;
        this.specialization = specialization;
        this.employmentStatus = employmentStatus;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getEmploymentStatus() { return employmentStatus; }
    public void setEmploymentStatus(String employmentStatus) { this.employmentStatus = employmentStatus; }

    public String getTeachingLoad() {
        if (employmentStatus.equalsIgnoreCase("Full-time")) return "12-18 units";
        else return "6-9 units";
    }

    @Override
    public void displayInfo() {
        System.out.println("===== INSTRUCTOR PROFILE =====");
        super.displayInfo();
        System.out.println("Department: " + department);
        System.out.println("Specialization: " + specialization);
        System.out.println("Status: " + employmentStatus);
        System.out.println("Teaching Load: " + getTeachingLoad());
        System.out.println("==============================");
    }
}
