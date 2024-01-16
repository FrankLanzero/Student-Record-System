import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Student extends StudentAbstract implements Records{
    private String curriculumCode; //Custom made curriculum codes for each program
    private int yearLevel; //Student Current Year Level
    private String registrationStatus; //Enrolled or Registered
    private double balance; //Account balance
    private ArrayList<COR> studentCOR = new ArrayList<>(); //Contains all the CORs for that student's school career
    private ArrayList<CourseCard> studentCourseCard = new ArrayList<>(); //Contains all the Course Cards (Grades)
    private ArrayList<BalanceInquiry> studentBalanceInquiry = new ArrayList<>(); //Balance Inquiries per Term

    //Constructor student for Add Student in Main Menu
    public Student(int studentNumber, String password, String firstName, String lastName,
                   String middleName, String citizenship, String program, String college,
                   String curriculumCode, int yearLevel){

        this.setStudentNumber(studentNumber);
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setMiddleName(middleName);
        this.setCitizenship(citizenship);
        this.yearLevel = yearLevel;
        this.setProgram(program);
        this.setCollege(college);
        this.curriculumCode = curriculumCode;
        this.registrationStatus = "Registered";

        this.balance = 0;
    }
    //All student values from the StudentInformation.csv File
    public Student(String[] values){
        this.setStudentNumber(Integer.parseInt(values[0]));
        this.setPassword(values[1]);
        this.setFirstName(values[2]);
        this.setMiddleName(values[3]);
        this.setLastName(values[4]);
        this.setCitizenship(values[5]);
        this.balance = Double.parseDouble(values[6]);

        this.yearLevel = Main.academicYearEnd - this.getStudentNumber()/100000;
    }

    //Loads all student courses from StudentCourses.csv and makes a new COR for the student
    public void loadCourses(ArrayList<String> values){
        String[] generalInfo = new String[5];
        ArrayList<String> courseList = new ArrayList<>();

        int count = 0;
        for(String x : values){
            if(count < 5){
                generalInfo[count] = x;
                count++;
            }
            else{
                courseList.add(x);
            }
        }

        //generalInfo is for the General Information of the COR
        //courseList is all Courses of the COR
        COR cor = new COR(generalInfo, courseList);
        studentCOR.add(cor);
    }
    //Loads additional student information from StudentProgram.csv File
    public void loadStudentProgram(String[] values){
        this.setProgram(values[1]);
        this.setCollege(values[2]);
        this.curriculumCode = values[3];
        this.registrationStatus = values[4];
    }
    //Loads grades of students from StudentGrades.csv File
    public void loadStudentGrades(String[] values, ArrayList<String> grades){
        CourseCard newCourseCard = new CourseCard(values, grades);
        studentCourseCard.add(newCourseCard);
    }
    //Displays the grades of students, uses a GUI.
    public void displayGrades(){
        new GUI(this, this.studentCourseCard);
    }
    //Checks if a students' specific subject was passed
    //Returns true if passed, false if not
    public String checkCourseStatus(String courseCode){
        boolean checkIfPass = false;
        for (CourseCard x : studentCourseCard) {
            if(x.passOrFail(courseCode)){
                checkIfPass = true;
                break;
            }
        }
        if(checkIfPass){
            return "PASSED";
        }
        return "FAILED";
    }
    //Views a student's balance inquiry
    //Also uses a GUI for viewing it
    public void viewBalanceInquiry(int yearLevel, int term){
        boolean found = false;
        for (BalanceInquiry x : studentBalanceInquiry) {
            if(x.getYearLevel() == yearLevel && x.getTerm() == term){
                x.updatePaymentLeft();
                x.displayPayments(this);
                found = true;
                break;
            }
        }
        if(!found){
            new GUI("Balance Record Not Found!");
        }
    }
    //Method that adds Balance Inquiry
    //Adds a balance inquiry after a student enrolls in a subject
    public void addBalanceInquiry(int schoolYear, int yearLevel, int term, double total){
        BalanceInquiry newBalance = new BalanceInquiry(schoolYear, yearLevel,
                term, this, total
        );
        studentBalanceInquiry.add(newBalance);
    }
    //Gets the remaining balance of a student for that particular term
    //Traverses the balance inquiries until finding the one with the particular term
    public double getRemainingBalance(int year, int term){
        double toReturn = 0;
        for (BalanceInquiry x : studentBalanceInquiry) {
            if(x.getYearLevel() == year && x.getTerm() == term){
                toReturn = x.getRemainingBalance();
                break;
            }
        }
        return toReturn;
    }
    //Calculates a student's total GWA from all Course Cards
    public double calculateGWA(){
        int units;
        double cumulativeUnits = 0;
        double cumulativeGWA = 0;
        //Iterates through all course cards
        //Uses weighted mean to calculate the GWA
        for(CourseCard x : studentCourseCard){
            for(int i = 0; i < x.getCourses().size(); i++){
                if(x.getGradedStatus().get(i).equals("PASSED")){
                    units = Main.getUnits(x.getCourses().get(i));
                    cumulativeUnits += units;
                    cumulativeGWA += units * x.getFinalGrades().get(i);
                }
            }
        }
        return cumulativeGWA/cumulativeUnits;
    }

    //This is called from the Load Class
    public void loadFees(ArrayList<String> values){
        String[] generalInfo = new String[4];
        ArrayList<String> feeList = new ArrayList<>();

        int count = 0;
        for(String x : values){
            if(count < 4){
                generalInfo[count] = x;
                count++;
            }
            else{
                feeList.add(x);
            }
        }
        int parameterYear = Integer.parseInt(generalInfo[2]);
        int parameterTerm = Integer.parseInt(generalInfo[3]);

        //Loads all values from the values array into each COR
        for (COR x : studentCOR){
            if(parameterTerm == x.getTerm() && parameterYear == x.getYearLevel()){
                x.loadFees(feeList);
                break;
            }
        }

    }

    //Called from the Load Class
    //Loads all financial records of the student
    public void loadFinance(ArrayList<String> values){
        String[] generalInfo = new String[4];
        ArrayList<String> financeList = new ArrayList<>();

        int count = 0;
        for(String x : values){
            if(count < 4){
                generalInfo[count] = x;
                count++;
            }
            else{
                financeList.add(x);
            }
        }
        int parameterYear = Integer.parseInt(generalInfo[2]);
        int parameterTerm = Integer.parseInt(generalInfo[3]);

        double totalFee = 0;
        for (COR x : studentCOR){
            if(parameterTerm == x.getTerm() && parameterYear == x.getYearLevel()){
                totalFee = x.getTotalAssessment();
                break;
            }
        }
        //Creates a new Balance Inquiry object once the values have been loaded
        BalanceInquiry newBal = new BalanceInquiry(totalFee, generalInfo, financeList);
        studentBalanceInquiry.add(newBal);
    }
    //Traverses all COR of a student to display and find it
    //Displays it using GUI when a matching one is found
    public void displayCOR(int yearLevel, int term){
        boolean found = false;
        System.out.println();
        for (COR x : this.studentCOR) {
            if(x.getYearLevel() == yearLevel && x.getTerm() == term){
                new GUI(this, x);
                found = true;
                break;
            }
        }
        if(!found){
            new GUI("The specific COR is not in the Student's Records!");
        }
    }
    //Searches for a matching COR to see if it already exists
    public boolean findCOR(int yearLevel, int term){
        for (COR x : studentCOR) {
            if(x.getYearLevel() == yearLevel && x.getTerm() == term){
                return true;
            }
        }
        return false;
    }
    //After enrolling, the values for the COR are defined here
    public void addCOR(int yearLevel, int term, int unitsEnrolled, ArrayList<Course> newTerm, int academicYear){
        //All the constant fees are set here
        double totalFees;
        ArrayList<String> subjectFees = new ArrayList<>();
        double tuition = unitsEnrolled * Records.PRICE_PER_UNIT;
        subjectFees.add("Tuition Fee (" + unitsEnrolled + ") - " + tuition);
        double misc = Records.MISC_FEE;
        subjectFees.add("Miscellaneous Fee - " + misc);
        double idValidation = Records.ID_VALID;
        subjectFees.add("ID Validation Fee - " + idValidation);
        totalFees = tuition + misc + idValidation;

        //All the department specific fees are defined here through a loop
        for(Course x : newTerm){
            String z = x.getCourseCode();
            for(String y : LAB_SUBJECT_WITH_FEES){
                if(z.equals(y)){
                    subjectFees.add(LABORATORY_FEE_PROMPT + " (" + z + ") - " + MAJOR_FEE);
                    totalFees += MAJOR_FEE;
                }
            }
            for(String y : MPS_SUBJECT_WITH_FEES){
                if(z.equals(y)){
                    subjectFees.add(MPS_PROMPT + " (" + z + ") - " + MINOR_FEE);
                    totalFees += MINOR_FEE;
                }
            }
            for(String y : HSC_SUBJECT_WITH_FEES){
                if(z.equals(y)){
                    subjectFees.add(HSC_PROMPT + " (" + z + ") - " + MINOR_FEE);
                    totalFees += MINOR_FEE;
                }
            }
        }

        //All the term fees and percentage here
        subjectFees.add("TOTAL ASSESSMENT - " + totalFees);
        subjectFees.add("Down Payment (Upon Enrollment) 40% - " + totalFees * 0.40);
        subjectFees.add("Midterm 30% - " + totalFees * 0.30);
        subjectFees.add("Final 30% - " + totalFees * 0.30);
        subjectFees.add("Add on: Installment Fee - " + ADD_ON);

        //To confirm enrollment, a student must pay 8,500 pesos
        //This is just to confirm a student's payment if they want to finalize enrollment
        Scanner input = new Scanner(System.in);
        boolean confirm = false;
        while(!confirm){
            //If the balance is less than 8500, the enrollment will not go through
            if(this.getBalance() < 8500){
                System.out.println("Student balance is insufficient!");
                System.out.println("Cancelling enrollment!");
                return;
            }
            input.nextLine();
            //Else, it will ask the user if they want to confirm it now
            System.out.print("Would you like to pay a down payment of 8,500 to fully confirm [Y/N]: ");
            String accept = input.nextLine();
            switch (accept) {
                case "Y", "y" -> {
                    this.addBalanceInquiry(academicYear, yearLevel, term, totalFees);
                    Date currentDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    String formattedDate = dateFormat.format(currentDate);
                    this.addPayment(yearLevel, term, 8500.0, formattedDate);
                    this.registrationStatus = "Enrolled";
                    confirm = true;
                }
                case "N", "n" -> {
                    return;
                }
                default -> System.out.println("Enter again.");
            }
        }

        //Once done, all subjects will be added first to the Course Card
        addCourseCard(academicYear, yearLevel, term, newTerm);

        //All data in the enrollment area will be used to create a new COR
        COR newCOR = new COR(yearLevel, term, "ENROLLED",
                this, unitsEnrolled, newTerm, academicYear
        );
        newCOR.loadFees(subjectFees);

        //Finalizes the addition of a new COR for a term
        this.studentCOR.add(newCOR);
        Main.changeSlots(newTerm);
    }
    //Method to separate the addition of a new Course Card to the student
    //Values were taken from the addCOR method
    private void addCourseCard(int academicYear, int yearLevel, int term, ArrayList<Course> newTerm) {
        CourseCard newCourseCard = new CourseCard(academicYear, yearLevel, term, newTerm);
        this.studentCourseCard.add(newCourseCard);
    }
    //Finds the most recent term enrolled by a student based off their CORs
    public int getMostRecentTerm(int year){
        int toReturn = 0;
        for(COR y : studentCOR){
            if(y.getYearLevel() == year){
                if(y.getTerm() == 1){
                    toReturn = 1;
                }
                else if(y.getTerm() == 2){
                    toReturn = 2;
                }
                else{
                    toReturn = 3;
                }
            }
        }
        return toReturn;
    }
    //Checks if student is enrolled in a specific term and year
    public boolean checkIfEnrolled(int year, int term){
        for(COR y : studentCOR){
            if(y.getYearLevel() == year){
                if (y.getTerm() == term && y.getEnrollmentStatus().equals("ENROLLED")){
                    return true;
                }
            }
        }
        return false;
    }
    //Adds a payment to the specified year and term balance inquiry
    //Deducts from balance as well
    public void addPayment(int year, int term, double payment,
                           String date){
        for(BalanceInquiry x : studentBalanceInquiry){
            if(x.getYearLevel() == year && x.getTerm() == term){
                x.addPayment(payment, date);
            }
        }
        this.balance -= payment;
    }
    //Increase student balance from Admin
    public void addBalance(double amount){
        this.balance += amount;
    }
    //Opens a new window to show student information using GUI
    public void displayProfile(){
        new GUI(this);
    }
    //Returns a student's specific course card from a specific year and term
    public CourseCard getCourseCard(int year, int term){
        CourseCard newCourseCard = null;
        boolean found = false;
        //Iterates through course cards to find matching year and term
        for (CourseCard x : studentCourseCard) {
            if(x.getYearLevel() == year && x.getTerm() == term){
                newCourseCard = x;
                found = true;
                break;
            }
        }
        if(!found){
            //If the course card isn't found, it returns null
            return null;
        }
        return newCourseCard;
    }
    //Grades student course card after entering inputs from method in Main
    public void gradeCourseCard(int year, int term, ArrayList<Double> midtermGrades, ArrayList<Double> finalGrades) {
        for (CourseCard x : studentCourseCard) {
            if(x.getYearLevel() == year && x.getTerm() == term){
                //Fills up the matching course card of grades
                x.gradeCourseCard(midtermGrades, finalGrades);
                break;
            }
        }
    }
    //Checks if all of OFES is rated. Returns false if not
    public boolean checkOFESStatus(int year, int term){
        CourseCard checker = new CourseCard();
        for(CourseCard x : studentCourseCard){
            if(x.getYearLevel() == year && x.getTerm() == term){
                checker = x;
                break;
            }
        }


        //Unrated means not yet OFES is not yet answered
        //If there is at least one, return false
        for(String x : checker.getRatedStatus()){
            if(x.equals("UNRATED")){
                return false;
            }
        }
        return true;
    }
    //Method that allows students to grade their professors through OFES
    public void gradeOFES(int academicYear, int year, int term){
        CourseCard currentCard = new CourseCard();
        //Traverses Course Card collection to find the specified matching year and term
        for (CourseCard x : studentCourseCard){
            if(x.getTerm() == term && x.getYearLevel() == year){
                currentCard = x;
                break;
            }
        }

        Scanner input = new Scanner(System.in);

        Course currentCourse;
        //Number of courses to grade
        int len = currentCard.getCourses().size();
        int count = 0;
        //8 is the number of categories in OFES
        double[] gradedOFES = new double[8];
        //Iterates based on the number of courses to grade
        while(count < len){
            //Gets the current iteration of course code and section
            String code = currentCard.getCourses().get(count);
            String section = currentCard.getSection().get(count);
            currentCourse = Course.findCourse(code, section);
            currentCourse.displayCourse();

            //Initializes a two-dimensional array for each category
            String[][] categories = {CATEGORY_OFES_A, CATEGORY_OFES_B, CATEGORY_OFES_C, CATEGORY_OFES_D,
                    CATEGORY_OFES_E, CATEGORY_OFES_F, CATEGORY_OFES_G, CATEGORY_OFES_H};
            int i = 0;
            //This is a deeply nested loop
            //The outer loop iterated through all categories
            while(i < 8){
                //Declares values
                System.out.println(Records.OFES_TITLES[i]);
                //Variable to add the inputs of the user
                int tempOFES = 0;
                int grade = 0;

                //Now iterates through each category's questions
                for (String x : categories[i]) {
                    boolean validInput = false;
                    //Will keep on asking for a valid input if they input something incorrect
                    while (!validInput) {
                        System.out.print(x + ": ");
                        try {
                            grade = input.nextInt();
                            if (grade >= 1 && grade <= 5) {
                                validInput = true;
                            }
                            else {
                                System.out.println("Please enter a number between 1 and 5.");
                            }
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Please enter an integer!");
                            input.nextLine();
                        }
                    }
                    tempOFES += grade;
                }
                //Will divide the total OFES for a specific category
                //Divided by the number of questions
                gradedOFES[i] = (double) tempOFES / Records.QUESTION_AMOUNT[i];
                i++;

            }
            //After finishing all categories, will then add the OFES record
            //The addition will happen based on which staff the rating happened
            for(Staff x : STAFF_DATABASE){
                int temp = currentCourse.getStaffId();
                if(x.getStaffID() == temp){
                    x.addOFESRecord(code, academicYear, term, gradedOFES);
                    break;
                }
            }
            count++;
        }

        //Will now set the status of OFES rating in the student course card as RATED
        for (CourseCard x : studentCourseCard){
            if(x.getTerm() == term && x.getYearLevel() == year){
                x.rateAll();
                break;
            }
        }
    }

    //Checks if a student has passed a prerequisite for a specific course
    public boolean passPrerequisites(Course course){
        //Number of prerequisites for a course
        int toSatisfy  = course.getPrerequisites().size();
        //Counter to check if number of prerequisite is satisfied
        int count = 0;
        //Iterates if all subjects needed as a prerequisite is passed
        for(String x : course.getPrerequisites()){
            if(this.checkCourseStatus(x).equals("PASSED")){
                count++;
            }
        }
        //If passed a total number of prerequisites to the actual prerequisites, return true
        return toSatisfy == count;
    }
    //Checks if a student has a balance inquiry for a specific term
    //Uses iteration to find a matching balance inquiry
    public boolean checkBalanceInquiry(int year, int term) {
        boolean toReturn = false;
        for(BalanceInquiry x : studentBalanceInquiry){
            if(x.getYearLevel() == year && x.getTerm() == term){
                toReturn = true;
                break;
            }
        }
        return !toReturn;
    }
    //Updates the year level of all students after whole school year passes
    public void updateYearLevel(){
        this.yearLevel++;
    }

    //GETTERS
    public String getCurriculumCode() {
        return curriculumCode;
    }
    public int getYearLevel() {
        return yearLevel;
    }
    public String getRegistrationStatus() {
        return registrationStatus;
    }
    public ArrayList<COR> getStudentCOR() {
        return studentCOR;
    }
    public ArrayList<CourseCard> getStudentCourseCard() {
        return studentCourseCard;
    }
    public double getBalance() {
        return balance;
    }
    public ArrayList<BalanceInquiry> getBalanceInquiry() {
        return studentBalanceInquiry;
    }

}