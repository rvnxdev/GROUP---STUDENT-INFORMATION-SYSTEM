public class Student extends Person {
    private String course;
    private int yearLevel;
    private double gpa;
    private String section;

    public Student(String id, String Fullname, String email, String contactNumber, String course, int yearLevel, double gpa, String section) {
        super(id, Fullname, email, contactNumber);
        this.course = course;
        this.yearLevel = yearLevel;
        this.gpa = gpa;
        this.section = section;
    }

    public String getCourse() { return course;}
    public void setCourse(String course) { this.course = course;}

    public int getYearLevel() { return yearLevel;}
    public void setYearLevel(int yearLevel) { this.yearLevel = yearLevel;}

    public double getGpa() { return gpa;}
    public void setGpa(double gpa) { this.gpa = gpa;}

    public String getSection() { return section;}
    public void setSection(String section) { this.section = section;}
}