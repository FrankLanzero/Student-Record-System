import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

public class Main implements Records {
    //Initialized Values, to be changed later
    //These represent the current year and terms, stored in the AcademicYearInfo.csv
    static int academicYearStart = 0;
    static int academicYearEnd = 0;
    static int academicYearRange = 0;
    static int currentTerm = 1;
    static Map<String, String> courseLibrary = new HashMap<>();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws InputMismatchException, FileNotFoundException {
        loadRecords(); //Load values
        mainMenu(); //Main menu

    }

    public static void exit() {
        saveRecords(); //Save values
        input.close();
    }

    public static void mainMenu() throws InputMismatchException {
        String option = "";
        //MAIN MENU
        while (!option.equals("0")) {
            System.out.println("MAIN MENU");
            System.out.println("==================================");
            System.out.println("[0] - Exit");
            //Exits and saves
            System.out.println("[1] - Student");
            //Leads to student login
            System.out.println("[2] - Admin");
            //Leads to admin login
            System.out.println("==================================");
            System.out.print("Enter: ");
            option = input.next();
            switch (option) {
                case ("0") -> exit();
                case ("1") -> studentLogin();
                case ("2") -> adminMenu();
                default -> System.out.println("Enter a valid option.");
            }
        }


    }

    //Method that lets Admin add a student
    public static void addStudent() throws InputMismatchException {
        System.out.println("Welcome to Add Student Menu");
        while (true) {
            try {
                int studentNumber;
                //Loops until a valid student is entered
                while (true) {
                    System.out.print("Enter Student Number (" + academicYearStart + "xxxxx): ");
                    studentNumber = input.nextInt();

                    if (studentNumber / 100000 != academicYearStart) {
                        System.out.println("Invalid Student Number!");
                    } else {
                        break;
                    }
                }

                boolean found = false;
                for (Student x : STUDENT_DATABASE) {
                    if (x.getStudentNumber() == studentNumber) {
                        System.out.println("Student already in database!");
                        found = true;
                        break;
                    }
                }
                if (found) {
                    continue;
                }

                input.nextLine();

                //Password loop that loops until the Admin enters a valid password
                String password = "";
                boolean passwordValid = false;
                while (!passwordValid) {
                    System.out.print("Enter Password for Student: ");
                    password = input.nextLine();
                    if (isPasswordValid(password)) {
                        passwordValid = true;
                    } else {
                        System.out.println("Password must be length of 8.");
                        System.out.println("Password must have an uppercase and lowercase character.");
                        System.out.println("Password must contain a digit");
                        System.out.println("Password must contain a special character");
                    }
                }

                //Allows the user to enter a name
                //Provided they are valid
                boolean nameValid = false;
                String firstName = "", lastName = "", middleName = "";
                while (!nameValid) {
                    System.out.print("Enter First Name: ");
                    firstName = input.nextLine();
                    System.out.print("Enter Last Name: ");
                    lastName = input.nextLine();
                    System.out.print("Enter Middle Name (Input ~ if None): ");
                    middleName = input.nextLine();
                    if (isNameValid(firstName, lastName, middleName)) {
                        nameValid = true;
                        if (middleName.equals("0")) {
                            middleName = "";
                        }

                        firstName = firstName.toUpperCase();
                        lastName = lastName.toUpperCase();
                        middleName = middleName.toUpperCase();
                    } else {
                        System.out.println("Please enter a proper name!");
                    }
                }

                //Loops until the Admin enters a citizenship that is valid
                //And is found inside Records
                String citizenship = "";
                boolean valid = false;
                while (!valid) {
                    System.out.print("Enter Citizenship (e.g. Filipino): ");
                    citizenship = input.nextLine();
                    for (String x : CITIZENSHIP) {
                        if (x.equals(citizenship)) {
                            valid = true;
                            break;
                        }
                    }
                    if (!valid) {
                        System.out.println("Please enter a valid citizenship!");
                    }
                }

                //Once again, loops until the right college is entered
                String college = "", program = "";
                boolean validCollege = false;
                while (!validCollege) {
                    System.out.print("Enter College (e.g. ECE): ");
                    college = input.nextLine();
                    int count = 0;
                    for (String x : COLLEGES) {
                        if (x.equals(college)) {
                            validCollege = true;
                            program = PROGRAMS[count];
                            break;
                        }
                        count++;
                    }
                    if (!validCollege) {
                        System.out.println("Please enter a valid college!");
                    }
                }

                //Will take curriculum code based on what program the Admin entered
                String curriculumCode = retrieveCurriculumCode(program);

                //Creates a new student and adds to the database
                Student newStudent = new Student(studentNumber, password, firstName, lastName,
                        middleName, citizenship, program, college, curriculumCode, 1);
                STUDENT_DATABASE.add(newStudent);
                System.out.println("Student " + studentNumber + " successfully added.");
                break;
            }
            //Catcher for invalid inputs
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                input.next();
            }
        }

    }

    //Method to get corresponding curriculum code
    private static String retrieveCurriculumCode(String program) {
        return switch (program) {
            case "BSCS" -> "CS0000";
            case "BSME" -> "ME0000";
            case "BSECE" -> "ECE0000";
            default -> "";
        };
    }
    //Boolean method to check the names if it is valid
    public static boolean isNameValid(String firstName, String lastName, String middleName) {
        boolean checkFirstName = true;
        boolean checkLastName = true;
        boolean checkMiddleName = true;

        //NAMES CANNOT HAVE DIGITS
        for (char c : firstName.toCharArray()) {
            if (Character.isDigit(c)) {
                checkFirstName = false;
                break;
            }
        }
        for (char c : lastName.toCharArray()) {
            if (Character.isDigit(c)) {
                checkLastName = false;
                break;
            }
        }
        for (char c : middleName.toCharArray()) {
            if (Character.isDigit(c)) {
                checkMiddleName = false;
                break;
            }
        }

        return checkFirstName && checkMiddleName && checkLastName;
    }

    //Boolean method to check password
    public static boolean isPasswordValid(String password) {
        int minLength = 8;  // Minimum password length
        boolean containsUppercase = false;  // Should contain at least one uppercase letter
        boolean containsLowercase = false;  // Should contain at least one lowercase letter
        boolean containsDigit = false;      // Should contain at least one digit
        boolean containsSpecialChar = false;  // Should contain at least one special character

        String specialChars = "!@#$%^&*()_-+=<>?";

        // Check the password against the criteria
        if (password.length() < minLength) {
            return false;
        }

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                containsUppercase = true;
            }
            else if (Character.isLowerCase(c)) {
                containsLowercase = true;
            }
            else if (Character.isDigit(c)) {
                containsDigit = true;
            }
            else if (specialChars.contains(String.valueOf(c))) {
                containsSpecialChar = true;
            }
        }

        // Check if all criteria are met
        return containsUppercase && containsLowercase && containsDigit && containsSpecialChar;
    }

    //Method that contains all the save features
    public static void saveRecords(){
        Save saveRecords = new Save();
        saveRecords.saveStudentInformation();
        saveRecords.saveStudentFinanceAndFees();
        saveRecords.saveCourses();
        saveRecords.saveStaff();
        saveRecords.saveGrades();
        saveRecords.saveAcademicValues();
    }
    //Method that contains all the load features
    public static void loadRecords() throws FileNotFoundException {
        Load loadInfo = new Load();
        loadInfo.loadAcademicValues();
        loadInfo.loadCourses();
        loadInfo.loadStudentDetails();
        loadInfo.loadStaffDetails();
        loadInfo.loadStudentCourses();
        loadInfo.loadStudentProgram();
        loadInfo.loadStudentGrades();
        loadInfo.loadStudentFinanceRecords();
        loadInfo.loadOFESRecords();

    }
    //ADMIN
    public static void adminMenu() {
        String passkeyInput;
        //Admin Log In Prompt first
        input.nextLine();
        System.out.print("ENTER ADMIN PASSKEY: ");
        passkeyInput = input.nextLine();
        if (!passkeyInput.equals(Records.ADMIN_PASSKEY)) {
            new GUI("INVALID PASSWORD!");
            System.out.println("RETURNING TO MENU!");
            return;
        }

        String option;
        int adminMenuKey = 0;

        //ADMIN MENU
        do {
            System.out.println("ADMIN MENU");
            System.out.println("==================================");
            System.out.println("[0] - Log Out");
            //Exits and saves all the records
            System.out.println("[1] - New Student");
            //Add new student
            System.out.println("[2] - Move to Next Term");
            //Moves the term to the next
            System.out.println("[3] - View Records");
            //Check out the records
            System.out.println("[4] - View Courses");
            //View specific courses
            System.out.println("[5] - Add Balance");
            //Add money to a student
            System.out.println("[6] - Enter Grades");
            //Allows user to enter grades
            System.out.println("[7] - View Curriculum");
            //Allows user to enter grades
            System.out.println("==================================");
            System.out.print("Enter: ");
            option = input.next();
            switch (option) {
                case ("0") -> adminMenuKey++;
                case ("1") -> addStudent();
                case ("2") -> updateTerm();
                case ("3") -> viewRecordsMenu();
                case ("4") -> viewCourses();
                case ("5") -> addBalance(chooseStudent());
                case ("6") -> gradeStudent(chooseStudent());
                case ("7") -> viewCurriculum(chooseCurriculumCode());
                default -> System.out.println("Enter a valid option.");
            }
        } while (adminMenuKey == 0);

    }
    private static void viewCurriculum(String curriculumCode) {
        if(curriculumCode == null){
            return;
        }
        String[] array = switch (curriculumCode) {
            case "CS0000" -> Records.CURRICULUM_CS;
            case "ECE0000" -> Records.CURRICULUM_ECE;
            case "ME0000" -> Records.CURRICULUM_ME;
            default -> null;
        };

        assert array != null;
        new GUI(curriculumCode, array);
    }
    private static String chooseCurriculumCode() {
        String option;

        System.out.println("Choose a Curriculum Code to View");
        System.out.println("==================================");
        for(int i = 0; i < Records.PROGRAMS.length; i++){
            System.out.println("[" + (i + 1) + "] - " + Records.PROGRAMS[i]);
        }
        System.out.println("==================================");
        System.out.print("Enter: ");
        option = input.next();
        switch (option) {
            case ("1") -> option = "CS0000";
            case ("2") -> option = "ME0000";
            case ("3") -> option = "ECE0000";
            default -> {
                new GUI("Enter a valid option.");
                return null;
            }
        }
        return option;
    }
    //Student login first
    public static void studentLogin() throws InputMismatchException {
        int id;
        //Student will first log in
        while (true) {
            try {
                System.out.print("ENTER STUDENT ID: ");
                id = input.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!");
            }
        }

        //Searches for a student based on the input student ID
        //If it does not exist, then it will leave the login
        Student student = null;
        String studentPassword = "";
        boolean studentExist = false;

        for (Student x : STUDENT_DATABASE) {
            if (x.getStudentNumber() == id) {
                student = x;
                studentPassword = x.getPassword();
                studentExist = true;
                break;
            }
        }
        if (!studentExist) {
            new GUI("STUDENT IS NOT IN RECORD");
            return;
        }

        //Enters password
        //Searches for respective password
        String password;
        input.nextLine();
        System.out.print("ENTER PASSWORD: ");
        password = input.nextLine();
        if (password.equals(studentPassword)) {
            studentMenu(student);
        }
        else {
            new GUI("INVALID PASSWORD!");
            System.out.println("RETURNING TO MENU!");
        }
    }

    //STUDENT
    public static void studentMenu(Student student) {
        char opt;
        int studentMenuKey = 0;

        //STUDENT MENU
        do {

            System.out.println("STUDENT MENU: " + student.getStudentNumber());
            System.out.println("==================================");
            System.out.println("[0] - Log Out");
            //Returns to Main Menu
            System.out.println("[1] - Enroll");
            //Enrolls subjects
            System.out.println("[2] - View COR");
            //Views own COR
            System.out.println("[3] - View Course Card");
            //View own Course Card
            System.out.println("[4] - Pay");
            //Pay fees
            System.out.println("[5] - View Profile");
            //View Student Profile
            System.out.println("[6] - View Finance Record");
            //View financial payments
            System.out.println("[7] - Answer OFES");
            //Answer Online Faculty Evaluation System
            System.out.println("[8] - View Curriculum");
            System.out.println("==================================");
            System.out.print("Enter: ");
            opt = input.next().charAt(0);
            switch (opt) {
                case ('0') -> studentMenuKey++;
                case ('1') -> enroll(student);
                case ('2') -> viewCOR(student);
                case ('3') -> viewCourseCard(student);
                case ('4') -> payFee(student);
                case ('5') -> viewStudentProfile(student);
                case ('6') -> viewStudentFinanceRecord(student);
                case ('7') -> answerOFES(student);
                case ('8') -> viewCurriculum(student.getCurriculumCode());
                default -> System.out.println("Enter a valid option.");
            }
        } while (studentMenuKey == 0);

    }

    //Picks a student from the database
    //Will return a student if it exists
    public static Student chooseStudent() {
        int id;
        System.out.println("ENTER STUDENT ID: ");
        while (true) {
            try {
                id = input.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.print("Please enter a valid ID: ");
                input.nextLine();
            }
        }

        Student student = null;
        boolean studentExist = false;

        for (Student x : STUDENT_DATABASE) {
            if (x.getStudentNumber() == id) {
                student = x;
                studentExist = true;
                break;
            }
        }
        if (!studentExist) {
            return null;
        }
        return student;
    }

    //Same with student, asks for a Staff
    //Then returns a Staff object
    public static Staff chooseStaff() {
        int staffId;
        System.out.print("ENTER STAFF ID: ");
        while (true) {
            try {
                staffId = input.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.print("Please enter a valid ID: ");
                input.nextLine();
            }
        }

        Staff staff = null;
        boolean staffExist = false;

        for (Staff x : STAFF_DATABASE) {
            if (x.getStaffID() == staffId) {
                staff = x;
                staffExist = true;
                break;
            }
        }
        if (!staffExist) {
            return null;
        }
        return staff;
    }

    //VIEW
    public static void viewRecordsMenu() {
        String option;
        int viewRecordsMenuKey = 0;

        //VIEW RECORDS MENU
        do {
            System.out.println("RECORDS MENU");
            System.out.println("==================================");
            System.out.println("[0] - Return to Main Menu");
            //Return
            System.out.println("[1] - Show Student COR");
            //View a student's COR
            System.out.println("[2] - Show Staff Records");
            //View a staff's information
            System.out.println("[3] - View Student Profile");
            //View a student's profile
            System.out.println("[4] - View Student Course Card");
            //View a student's Course Card
            System.out.println("[5] - View Student Payments");
            //View a student's payment record
            System.out.println("[6] - View Student Balance");
            //View a student's balance
            System.out.println("[7] - View Staff OFES");
            //View a staff's OFES records
            System.out.println("==================================");
            System.out.print("Enter: ");
            option = input.next();
            switch (option) {
                case ("0") -> viewRecordsMenuKey++;
                case ("1") -> viewCOR(chooseStudent());
                case ("2") -> viewStaffRecords(chooseStaff());
                case ("3") -> viewStudentProfile(chooseStudent());
                case ("4") -> viewCourseCard(chooseStudent());
                case ("5") -> viewStudentFinanceRecord(chooseStudent());
                case ("6") -> viewStudentBalance(chooseStudent());
                case ("7") -> viewStaffOFES(chooseStaff());
                default -> System.out.println("Enter a valid option.");
            }
        } while (viewRecordsMenuKey == 0);
    }

    //Displays staff information
    private static void viewStaffRecords(Staff staff) {
        if (staff == null) {
            new GUI("Staff does not exist!");
            return;
        }

        int staffId = staff.getStaffID();
        Staff.displayStaffSubjects(academicYearRange, currentTerm, staffId);
    }

    //Display staff OFES records
    private static void viewStaffOFES(Staff staff) {
        if (staff == null) {
            new GUI("Staff does not exist!");
            return;
        }
        int term;
        int academicYear;

        //Asks for a specific year and term first
        while (true) {
            try {
                System.out.print("Enter Academic Year (e.g. 20232024): ");
                academicYear = input.nextInt();
                System.out.print("Enter Term: ");
                term = input.nextInt();
                break;
            }
            catch (InputMismatchException e) {
                System.out.print("Please enter year and term properly.");
                input.nextLine();
            }
        }

        staff.displayOFESRecords(academicYear, term);
    }

    //Views a student's COR and displays it
    public static void viewCOR(Student student) {
        int year, term;

        if (student == null) {
            new GUI("Student does not exist!");
            return;
        }

        while (true) {
            try {
                System.out.print("Enter Year: ");
                year = input.nextInt();
                System.out.print("Enter Term: ");
                term = input.nextInt();
                break;
            }
            catch (InputMismatchException e) {
                new GUI("Please enter year and term properly.");
                input.nextLine();
            }
        }
        student.displayCOR(year, term);

    }
    //View a student's remaining balance
    public static void viewStudentBalance(Student student) {
        if (student == null) {
            new GUI("Student does not exist.!");
            return;
        }
        System.out.print("Remaining Balance: ");
        System.out.println(student.getBalance());
    }
    //View a student's payment list
    public static void viewStudentFinanceRecord(Student student) {
        int year, term;

        if (student == null) {
            new GUI("Student does not exist.!");
            return;
        }
        while(true){
            try{
                System.out.print("Enter Year: ");
                year = input.nextInt();
                System.out.print("Enter Term: ");
                term = input.nextInt();
                break;
            }
            catch(InputMismatchException e){
                new GUI("Enter a valid input!");
                input.nextLine();
            }
        }
        student.viewBalanceInquiry(year, term);
    }
    //View a student's courseCard or Grade
    public static void viewCourseCard(Student student) {
        if (student == null) {
            new GUI("Student does not exist.!");
            return;
        }
        if (Double.isNaN(student.calculateGWA())) {
            new GUI("Student has no grades yet!");
            return;
        }
        student.displayGrades();
    }
    //View a student's profile
    public static void viewStudentProfile(Student student) {
        if (student == null) {
            new GUI("Student does not exist!");
            return;
        }
        student.displayProfile();
    }
    //View a specific course and its iterations
    public static void viewCourses() {
        String courseId;

        input.nextLine();
        System.out.print("Enter Course ID (ex. GED0001): ");
        courseId = input.nextLine();

        ArrayList<Course> courseList = new ArrayList<>();
        boolean found = false;
        for (Course x : COURSE_DATABASE) {
            if (x.getCourseCode().equals(courseId)) {
                courseList.add(x);
                found = true;
            }
        }
        if (!found) {
            new GUI("Course not in database!");
            return;
        }
        new GUI(courseId, courseList);
    }

    //ENROLL METHOD
    public static void enroll(Student student) {
        int year = academicYearEnd - student.getStudentNumber() / 100000;
        if (student.findCOR(year, currentTerm)) {
            new GUI("ALREADY ENROLLED");
            return;
        }
        ArrayList<Course> newTerm = new ArrayList<>();

        int unitsEnrolled = 0;
        int recentYear = student.getYearLevel();
        int recentTerm = student.getMostRecentTerm(recentYear) + 1;
        System.out.println("Year: " + recentYear + " Term: " + recentTerm);

        //When student has reached a minimum number of units
        //They can stop enrolling or keep on going
        char pickContinue;
        while (true) {
            if (unitsEnrolled >= 12 && unitsEnrolled <= 22) {
                System.out.print("Keep enrolling [Y] - [N]: ");
                pickContinue = input.next().charAt(0);
                if (pickContinue == 'Y' || pickContinue == 'y') {
                    System.out.println("Keep enrolling!");
                } else if (pickContinue == 'N' || pickContinue == 'n') {
                    break;
                } else {
                    System.out.println("Please enter again!");
                    continue;
                }
            }

            //Iterates through course database to display
            int count = 1;
            System.out.println("AVAILABLE SUBJECTS");
            for (Course x : COURSE_DATABASE) {
                //Checks if subject has been passed by student
                boolean hasPassed = "PASSED".equals(student.checkCourseStatus(x.getCourseCode()));
                //Returns true if subject has a laboratory
                //If false, it's only lecture
                boolean isLab = x.getCourseTitle().contains("LAB");
                //Returns true if the course is already in chosen list
                boolean alreadyPicked = findCourseCode(newTerm, x.getCourseCode());
                //Returns true if subject is offered for that year and term.
                boolean currentlyOffered = x.getAcademicYear() / 10000 == academicYearStart && x.getTerm() == currentTerm;
                //Returns true if there are still slots
                boolean slotsAvailable = x.getSlotsAvailable() > 0;
                //Returns true if the subject is part of that student's curriculum
                boolean inCurriculum = isInCurriculum(student.getCurriculumCode(), x.getCourseCode());
                //Returns true if the prerequisites have been satisfied
                boolean takenPrerequisite = student.passPrerequisites(x);
                //Returns true if the timeslot is overlapping
                boolean availableTime = !checkTime(x, newTerm);

                //IF ALL ARE TRUE, STUDENT CAN ENROLL
                if ((!hasPassed && !isLab) && !alreadyPicked && slotsAvailable && currentlyOffered && inCurriculum && takenPrerequisite && availableTime) {
                    System.out.println("[" + count + "] - " + x.getCourseCode() + " - " + x.getCourseTitle() + " - " + x.getSection());
                    count++;
                }
            }

            //No subjects offered
            if (count == 1) {
                new GUI("NO SUBJECTS OFFERED!");
                return;
            }

            int pick;
            while (true) {
                try {
                    System.out.print("Enroll a subject: ");
                    pick = input.nextInt();

                    //If pick exceeds count, repeat
                    if (pick <= 0 || pick >= count) {
                        new GUI("Please enter within range!");
                        continue;
                    }
                    break;
                }
                catch (InputMismatchException e) {
                    new GUI("Please enter an integer!");
                    input.nextLine();
                }
            }

            //Once they entered it, it will search for that pick and add it to a temporary ArrayList
            //The temp list is called newTerm
            for (Course x : COURSE_DATABASE) {
                boolean hasPassed = "PASSED".equals(student.checkCourseStatus(x.getCourseCode()));
                boolean isLec = x.getCourseTitle().contains("LAB");
                boolean alreadyPicked = findCourseCode(newTerm, x.getCourseCode());
                boolean currentlyOffered = x.getAcademicYear() / 10000 == academicYearStart && x.getTerm() == currentTerm;
                boolean slotsAvailable = x.getSlotsAvailable() > 0;
                boolean inCurriculum = isInCurriculum(student.getCurriculumCode(), x.getCourseCode());
                boolean takenPrerequisite = student.passPrerequisites(x);
                boolean availableTime = !checkTime(x, newTerm);
                if ((!hasPassed && !isLec) && !alreadyPicked && currentlyOffered && slotsAvailable && inCurriculum && takenPrerequisite && availableTime) {
                    pick--;
                }

                if (pick == 0) {
                    //If statement to add both lec and lab
                    if (x.getCourseTitle().contains("LEC")) { //LEC AND LAB
                        Course y = findRespectiveLab(x);
                        if (checkTime(x, newTerm) || (checkTime(y, newTerm))) {
                            System.out.println("Timeslot unavailable");
                            break;
                        }
                        newTerm.add(x);
                        newTerm.add(y);
                        unitsEnrolled += x.getUnits();
                        unitsEnrolled += y.getUnits();
                    }
                    else {
                        //Standalone subject
                        newTerm.add(x);
                        unitsEnrolled += x.getUnits();
                    }
                    break;
                }
            }
        }

        System.out.println("\nSUBJECTS ENROLLED");
        for (Course x : newTerm) {
            System.out.println(x.getCourseTitle());
        }
        //Asks for a confirmation from student
        boolean confirm = true;
        while (confirm) {
            System.out.print("Confirm Enrollment [Y/N]: ");
            char confirmEnrollment = input.next().charAt(0);
            switch (confirmEnrollment){
                case 'Y', 'y' -> {
                    System.out.println("Understood!");
                    confirm = false;
                }
                case 'N', 'n' -> {
                    System.out.println("Returning to menu!");
                    return;
                }
                default -> System.out.println("Please enter again!");
            }

        }

        student.addCOR(recentYear, recentTerm, unitsEnrolled, newTerm, academicYearRange);
    }

    //Checks if a subject is in a curriculum
    public static boolean isInCurriculum(String curriculumCode, String courseCode) {
        boolean toReturn = false;
        switch (curriculumCode) {
            case "CS0000" -> {
                for (String x : CURRICULUM_CS) {
                    if (x.equals(courseCode)) {
                        toReturn = true;
                        break;
                    }
                }
            }
            case "ME0000" -> {
                for (String x : CURRICULUM_ME) {
                    if (x.equals(courseCode)) {
                        toReturn = true;
                        break;
                    }
                }
            }
            case "ECE0000" -> {
                for (String x : CURRICULUM_ECE) {
                    if (x.equals(courseCode)) {
                        toReturn = true;
                        break;
                    }
                }
            }
        }
        return toReturn;
    }

    //Checks if a subject overlaps with a specific timeslot
    public static boolean checkTime(Course course, ArrayList<Course> subjects) {
        String currentDay;
        int count = 0;
        boolean timeReturn;
        ArrayList<Course> subjectsToCheck = new ArrayList<>();

        //If there's one day, it will go into the findTimeslot alone
        if (course.getNumDays() == 1) {
            currentDay = course.getClassDays().get(0);
            timeReturn = findTimeslot(course, subjects, currentDay, subjectsToCheck);
            return timeReturn;
        }

        //If there's two days, it will go twice
        if (course.getNumDays() == 2) {

            boolean[] twoDay = new boolean[2];
            while (count < 2) {

                currentDay = course.getClassDays().get(count);

                twoDay[count] = findTimeslot(course, subjects, currentDay, subjectsToCheck);

                subjectsToCheck.clear();
                count++;
            }
            timeReturn = twoDay[0] || twoDay[1];

            return timeReturn;
        }

        //If there's three, it will go thrice and check thrice
        boolean[] threeDay = new boolean[3];
        while (count < 3) {

            currentDay = course.getClassDays().get(count);

            threeDay[count] = findTimeslot(course, subjects, currentDay, subjectsToCheck);

            subjectsToCheck.clear();
            count++;
        }
        timeReturn = threeDay[0] || threeDay[1] || threeDay[2];

        return timeReturn;
    }
    //This is a method used in conjunction to check the overlapping times
    public static boolean findTimeslot(Course course, ArrayList<Course> subjects, String currentDay, ArrayList<Course> subjectsToCheck) {
        LocalTime start, end;
        boolean timeReturn = false;

        for (Course x : subjects) {
            if (x.getClassDays().contains(currentDay)) {
                subjectsToCheck.add(x);
            }
        }

        for (Course x : subjectsToCheck) {
            start = x.getStartTime();
            end = x.getEndTime();
            if (course.getStartTime().isAfter(start) && course.getStartTime().isBefore(end)) {
                timeReturn = true;
            } else if (course.getEndTime().isAfter(start) && course.getEndTime().isBefore(end)) {
                timeReturn = true;
            } else if (course.getStartTime().equals(start) || course.getEndTime().equals(end)) {
                timeReturn = true;
            }
        }
        return timeReturn;
    }
    //Finds a course's respective lab subject.
    public static Course findRespectiveLab(Course lec) {
        Course newCourse = null;
        for (Course x : COURSE_DATABASE) {
            if (x.getCourseCode().equals(lec.getCourseCode() + "L") && x.getSection().equals(lec.getSection())) {
                newCourse = x;
                break;
            }
        }
        return newCourse;
    }
    //Finds the courseCode in the newTerm collection of Course objects
    public static boolean findCourseCode(ArrayList<Course> newTerm, String courseCode) {
        for (Course x : newTerm) {
            if (x.getCourseCode().equals(courseCode) || x.getCourseCode().equals(courseCode + "L")) {
                return true;
            }
        }
        return false;
    }
    //Gets units for a specific course, given the course code and not the Course
    public static int getUnits(String course) {
        int toReturn = 0;
        for (Course x : COURSE_DATABASE) {
            if (x.getCourseCode().equals(course)) {
                toReturn = x.getUnits();
                break;
            }
        }
        return toReturn;
    }

    //Main method for answering OFES
    public static void answerOFES(Student student) {

        int year = academicYearEnd - student.getStudentNumber() / 100000;
        //Stops if student is not yet enrolled
        if (!student.checkIfEnrolled(year, currentTerm)) {
            new GUI("Cannot answer OFES YET!");
            return;
        }
        //Stops if student has already answered
        if (student.checkOFESStatus(year, currentTerm)) {
            new GUI("OFES already rated!");
            return;
        }

        student.gradeOFES(academicYearRange, year, currentTerm);
    }
    //Student will pay fee
    public static void payFee(Student student) {
        int year = student.getYearLevel();
        int term = student.getMostRecentTerm(year);

        //Will stop if the student hasn't enrolled yet
        if (student.checkBalanceInquiry(year, term)) {
            new GUI("Please Enroll First!");
            return;
        }
        double remaining = student.getRemainingBalance(year, term);
        //Will check if student is paid
        if (remaining == 0 && term == currentTerm) {
            new GUI("Fully Paid!");
            return;
        }

        //Stops if the student has remaining unpaid
        if (currentTerm != term) {
            double temp = student.getRemainingBalance(year, term);
            if (temp != 0) {
                new GUI("Previous term unpaid!");
            }
            if (temp == 0) {
                new GUI("Paid for this term!");
                System.out.println("Please enroll for the next!");
                return;
            }
        }

        //Retrieves current time
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = dateFormat.format(currentDate);
        System.out.println("Current Date: " + formattedDate);

        double studentBalance = student.getBalance();
        double payment;
        //Payment loop
        while (true) {
            try {
                System.out.println("Balance: " + studentBalance);
                System.out.print("ENTER PAYMENT (Payment Remaining - " + remaining + "): ");
                payment = input.nextDouble();
                //Payment must not exceed student balance
                if(payment < 0){
                    new GUI("Cannot pay negatively!");
                    return;
                }
                else if (payment > studentBalance) {
                    new GUI("Invalid Amount. Returning to Menu.");
                    return;
                }
                //Payment can exceed remaining, but will chop off payment
                if (payment > remaining) {
                    System.out.println("Amount exceeds total assessment.");
                    System.out.println("Will be chopping off money.");
                    payment = remaining;
                }
                break;
            }
            catch (InputMismatchException e) {
                new GUI("Please enter a proper number!");
                input.nextLine();
            }
        }
        System.out.println("Payment of " + payment + " successful!");
        student.addPayment(year, term, payment, formattedDate);
    }
    //Admin can add balance to a student's balance
    public static void addBalance(Student student) {
        if (student == null) {
            new GUI("Student does not exist.!");
            return;
        }
        double amount;
        while (true) {
            try {
                System.out.println("Student Balance: " + student.getBalance());
                System.out.print("ENTER BALANCE TO ADD: ");
                amount = input.nextDouble();
                //Value must be positive
                if (amount < 0) {
                    new GUI("Please enter a positive number!");
                    continue;
                }
                break;
            }
            catch (InputMismatchException e) {
                new GUI("Please enter a proper amount!");
                input.nextLine();
            }
        }
        System.out.println("Successfully added " + amount + " to Student " + student.getStudentNumber());
        student.addBalance(amount);
    }

    //Method that grades a student
    public static void gradeStudent(Student student) {
        if (student == null) {
            new GUI("Student does not exist.");
            return;
        }
        int year = academicYearEnd - student.getStudentNumber() / 100000;
        CourseCard ungradedCourse = student.getCourseCard(year, currentTerm);

        //Returns if already graded
        if (ungradedCourse.isGraded()) {
            new GUI("Student already graded for this term!");
            return;
        }
        ArrayList<Double> midtermGrades = new ArrayList<>();
        ArrayList<Double> finalGrades = new ArrayList<>();
        double gradeInput;

        ArrayList<String> coursesToGrade = ungradedCourse.getCourses();
        ArrayList<String> labCourses = new ArrayList<>();

        //Lab courses have the same grade as lecture counterparts
        for(String x : coursesToGrade){
            if(x.charAt(x.length() - 1) == 'L'){
                labCourses.add(x);
            }
        }

        //Separates lec and lab
        for(String x : labCourses){
            coursesToGrade.remove(x);
        }

        int len = ungradedCourse.getCourses().size();

        int half = 0;
        int count;

        //Algorithm that grades midterms of all subjects
        //and also grades finals of all subjects
        while (half < 2) {
            if(half == 0){
                System.out.println("MIDTERM GRADING");
            }
            else if(half == 1){
                System.out.println("FINALS GRADING");
            }
            count = 0;
            while (count < len) {
                try {
                    String currentToGrade = coursesToGrade.get(count);
                    System.out.print(currentToGrade + " : ");
                    gradeInput = input.nextDouble();
                    if (feuGradeCheck(gradeInput)) {
                        if (half == 0) {
                            midtermGrades.add(gradeInput);
                        } else if (half == 1) {
                            finalGrades.add(gradeInput);
                        }

                        //This is for lab courses to be graded alongside as well
                        for(String x : labCourses){
                            if(x.equals(currentToGrade + "L") && half == 0){
                                midtermGrades.add(gradeInput);
                            }
                            if(x.equals(currentToGrade + "L") && half == 1){
                                finalGrades.add(gradeInput);
                            }
                        }
                        count++;
                    }
                    else {
                        new GUI("Enter A Valid Grade!");
                    }
                }
                catch (InputMismatchException e) {
                    new GUI("Enter a proper value!");
                    input.nextLine();
                }
            }
            half++;
        }

        student.gradeCourseCard(year, currentTerm, midtermGrades, finalGrades);
    }

    //Checks if the input grade is in the valid FEU grades
    public static boolean feuGradeCheck(double grade) {
        for (double x : Records.VALID_FEU_GRADES) {
            if (x == grade) {
                return true;
            }
        }
        return false;
    }

    //Edit the slot of a particular course
    public static void changeSlots(ArrayList<Course> courses) {
        int len = courses.size();
        int count = 0;
        while (count < len) {
            for (Course x : COURSE_DATABASE) {
                if (courses.get(count).equals(x)) {
                    courses.get(count).editSlot();
                }
            }
            count++;
        }
    }

    //Update a term to move to the next
    public static void updateTerm() {
        System.out.println("Movement to next term successful!");
        boolean studentsGraded = studentsGraded();
        //Check if all current students have been graded
        boolean ofesRated = ofesRated();
        //Check if all current students have answered OFES
        boolean studentsPaid = studentsPaid();
        //Check if all current students are paid

        if (!(studentsGraded && ofesRated && studentsPaid)) {
            new GUI("Moving terms will lead to many changes!\nReturning to menu!");
            return;
        }

        if (currentTerm == 3) {
            currentTerm = 1;
            academicYearStart++;
            academicYearEnd++;
            academicYearRange += 10001;
            for(Student x : STUDENT_DATABASE){
                x.updateYearLevel();
            }
        } else {
            currentTerm++;
        }
        new GUI("Movement to next term successful!");
    }

    //Checks if all students are graded
    //If at least one isn't, returns false
    public static boolean studentsGraded() {
        for (Student student : STUDENT_DATABASE) {
            for (CourseCard courseCard : student.getStudentCourseCard()) {
                if (courseCard.getSchoolYear() == academicYearRange && courseCard.getTerm() == currentTerm) {
                    if (!courseCard.isGraded()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    //Checks if all students are paid
    //If at least one isn't, returns false
    public static boolean studentsPaid() {
        for (Student student : STUDENT_DATABASE) {
            for (BalanceInquiry balanceInquiry : student.getBalanceInquiry()) {
                if (balanceInquiry.getSchoolYear() == academicYearRange && balanceInquiry.getTerm() == currentTerm) {
                    if (!balanceInquiry.getFullyPaid()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    //Checks if all students have answered OFES
    //If at least one isn't, returns false
    public static boolean ofesRated() {
        for (Student student : STUDENT_DATABASE) {
            for (CourseCard courseCard : student.getStudentCourseCard()) {
                if (courseCard.getSchoolYear() == academicYearRange && courseCard.getTerm() == currentTerm) {
                    for (String x : courseCard.getRatedStatus()) {
                        if (x.equals("UNRATED")) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
