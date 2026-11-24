public class Student extends Person {
    private String course;
    private int yearLevel;
    private double gpa;
    private String section;

    public Student(String id, String fullName, String email, String contactNumber,
                   String course, int yearLevel, double gpa, String section) {
        super(id, fullName, email, contactNumber);
        this.course = course;
        this.yearLevel = yearLevel;
        this.gpa = gpa;
        this.section = section;
    }

    // Getters and Setters
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public int getYearLevel() { return yearLevel; }
    public void setYearLevel(int yearLevel) { this.yearLevel = yearLevel; }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getAcademicStatus() {
        if (gpa >= 3.5) return "Dean's Lister";
        else if (gpa >= 3.0) return "Good Standing";
        else if (gpa >= 2.0) return "Probation";
        else return "At Risk";
    }

    @Override
    public void displayInfo() {
        System.out.println("===== STUDENT PROFILE =====");
        super.displayInfo(); 
        System.out.println("Course: " + course);
        System.out.println("Year Level: " + yearLevel);
        System.out.println("Section: " + section);
        System.out.println("GPA: " + gpa);
        System.out.println("Status: " + getAcademicStatus());
        System.out.println("===========================");
    }
}
