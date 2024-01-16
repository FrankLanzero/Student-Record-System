import java.util.ArrayList;

public class COR {
    private String enrollmentStatus;
    private int term;
    private int schoolYear;
    private String program;
    private int yearLevel;
    private int unitsEnrolled;
    private ArrayList<Course> courseList = new ArrayList<>();
    private double tuitionFee;
    private double miscellaneousFee;
    private double idValidationFee;
    private ArrayList<String> laboratoryFeeNames = new ArrayList<>();
    private ArrayList<Double> laboratoryFee = new ArrayList<>();
    private ArrayList<String> gedFeeNames = new ArrayList<>();
    private ArrayList<Double> gedFee = new ArrayList<>();
    private double totalAssessment;
    private double downPayment;
    private double midtermDownPayment;
    private double finalDownPayment;
    private double addOn;

    //NEW COR CONSTRUCTOR FOR ENROLLMENTS
    public COR(int yearLevel, int term, String enrollmentStatus, Student student, int unitsEnrolled, ArrayList<Course> newTerm, int academicYear){
        this.yearLevel = yearLevel;
        this.term = term;
        this.enrollmentStatus = enrollmentStatus;
        this.schoolYear = academicYear;
        this.program = student.getProgram();
        this.unitsEnrolled = unitsEnrolled;
        this.courseList.addAll(newTerm);

    }
    //HAVE CODE THAT DETERMINES PRICE and fees ETC
    //AFTER THAT WE GUCCI
    public COR(String[] generalInfo, ArrayList<String> courseList){
        this.schoolYear = Integer.parseInt(generalInfo[1]);
        this.yearLevel = Integer.parseInt(generalInfo[2]);
        this.term = Integer.parseInt(generalInfo[3]);
        this.enrollmentStatus = generalInfo[4];

        String courseCode, courseSection;

        for (String entry : courseList) {
            String[] parts = entry.split(" - ");

            courseCode = parts[0];
            courseSection = parts[1];

            //Splits the courseList and parses through each of the courses there
            for (Course x : Records.COURSE_DATABASE) {
                if(x.getCourseCode().equals(courseCode) && x.getSection().equals(courseSection)){
                    this.courseList.add(x);
                }
            }

        }
    }

    //Method to load the fees required to be paid in the term
    public void loadFees(ArrayList<String> feeList){
        for (String entry : feeList) {
            String[] parts = entry.split(" - ");

            //If ladder that checks if a payment contains any of these keywords
            //Initializes the attributes too
            if(parts[0].contains("Tuition Fee")){
                tuitionFee = Double.parseDouble(parts[1]);
                this.unitsEnrolled = ((int) tuitionFee)/((int) Records.PRICE_PER_UNIT);
            }
            else if(parts[0].contains("Miscellaneous")){
                miscellaneousFee = Double.parseDouble(parts[1]);
            }
            else if(parts[0].contains("Validation")){
                idValidationFee = Double.parseDouble(parts[1]);
            }
            else if(parts[0].contains("ITE Computer Laboratory")){
                laboratoryFeeNames.add(parts[0]);
                laboratoryFee.add(Double.parseDouble(parts[1]));
            }
            else if(parts[0].contains("Science Lab")){
                gedFeeNames.add(parts[0]);
                gedFee.add(Double.parseDouble(parts[1]));
            }
            else if(parts[0].contains("TOTAL")){
                totalAssessment = Double.parseDouble(parts[1]);
            }
            else if(parts[0].contains("Down")){
                downPayment = Double.parseDouble(parts[1]);
            }
            else if(parts[0].contains("Midterm")){
                midtermDownPayment = Double.parseDouble(parts[1]);
            }
            else if(parts[0].contains("Final")){
                finalDownPayment = Double.parseDouble(parts[1]);
            }
            else if(parts[0].contains("Add on")){
                addOn = Double.parseDouble(parts[1]);
            }

        }
    }

    //GET SETTERS
    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public int getTerm() {
        return term;
    }
    public int getSchoolYear() {
        return schoolYear;
    }
    public String getProgram() {
        return program;
    }
    public int getYearLevel() {
        return yearLevel;
    }
    public int getUnitsEnrolled() {
        return unitsEnrolled;
    }
    public ArrayList<Course> getCourseList() {
        return courseList;
    }
    public double getTuitionFee() {
        return tuitionFee;
    }
    public double getMiscellaneousFee() {
        return miscellaneousFee;
    }
    public double getIdValidationFee() {
        return idValidationFee;
    }
    public ArrayList<String> getLaboratoryFeeNames() {
        return laboratoryFeeNames;
    }

    public ArrayList<Double> getLaboratoryFee() {
        return laboratoryFee;
    }
    public ArrayList<String> getGedFeeNames() {
        return gedFeeNames;
    }

    public ArrayList<Double> getGedFee() {
        return gedFee;
    }

    public double getTotalAssessment() {
        return totalAssessment;
    }

    public double getDownPayment() {
        return downPayment;
    }
    public double getMidtermDownPayment() {
        return midtermDownPayment;
    }
    public double getFinalDownPayment() {
        return finalDownPayment;
    }
    public double getAddOn() {
        return addOn;
    }
}
