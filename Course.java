import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class Course {
    private int academicYear;
    private int term;
    private String courseCode;
    private String courseTitle;
    private String section;
    private int units;
    private ArrayList<String> classDays;
    private int numDays;
    private LocalTime startTime;
    private LocalTime endTime;
    private String room;
    private int staffId;
    private int slotsAvailable;
    private int students;
    ArrayList<String> prerequisites = new ArrayList<>();

    //Constructor after loading values from CourseDatabase.txt
    public Course(String[] courseDetails){
        //Initializes the attributes using values from courseDetails array

        this.courseCode = courseDetails[0];
        this.courseTitle = courseDetails[1];

        String[] courseInfo = courseDetails[2].split(" - ");
        this.academicYear = Integer.parseInt(courseInfo[0]);
        this.term = Integer.parseInt(courseInfo[1]);

        this.section = courseDetails[3];
        this.units = Integer.parseInt(courseDetails[4]);
        this.classDays = new ArrayList<>();

        String[] dayParts = courseDetails[5].split("/");
        this.classDays.add(dayParts[0]);
        numDays = 1;
        if(dayParts.length == 2){
            this.classDays.add(dayParts[1]);
            numDays++;
        }

        String[] timeParts = courseDetails[6].split(" - ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime startTime = LocalTime.parse(timeParts[0], formatter);
        LocalTime endTime = LocalTime.parse(timeParts[1], formatter);
        this.startTime = startTime;
        this.endTime = endTime;

        this.room = courseDetails[7];
        this.staffId = Integer.parseInt(courseDetails[8]);
        this.slotsAvailable = Integer.parseInt(courseDetails[9]);
        this.students = Integer.parseInt(courseDetails[10]);
    }
    //Load prerequisites from each subject
    public void loadPrerequisites(String[] values){
        prerequisites.addAll(Arrays.asList(values).subList(2, values.length));
    }
    //Displays the course information along with the staffs
    public void displayCourse(){
        System.out.println("Course Code: " + this.courseCode);
        System.out.println("Section: " + this.section);
        Staff staff = Staff.findStaff(this.staffId);
        staff.displayStaff();
    }
    //Finds the course in the course database and returns the Course object.
    public static Course findCourse(String courseCode, String section){
        Course toReturn = null;
        for(Course x : Records.COURSE_DATABASE){
            if(courseCode.equals(x.getCourseCode()) && section.equals(x.getSection())){
                toReturn = x;
                break;
            }
        }
        return toReturn;
    }
    //Updates slots available when a new student enrolls
    public void editSlot(){
        this.slotsAvailable--;
        this.students++;
    }

    //GET SETTERS
    public String getCourseCode() {
        return courseCode;
    }
    public ArrayList<String> getPrerequisites() {
        return prerequisites;
    }
    public String getCourseTitle() {
        return courseTitle;
    }
    public String getSection() {
        return section;
    }
    public int getUnits() {
        return units;
    }
    public String getRoom() {
        return room;
    }
    public int getStaffId() {
        return staffId;
    }
    public int getSlotsAvailable() {
        return slotsAvailable;
    }
    public int getStudents() {
        return students;
    }
    public LocalTime getStartTime(){
        return startTime;
    }
    public LocalTime getEndTime(){
        return endTime;
    }
    public ArrayList<String> getClassDays(){
        return classDays;
    }
    public int getNumDays() {
        return numDays;
    }
    public int getAcademicYear() {
        return academicYear;
    }
    public int getTerm() {
        return term;
    }
}
