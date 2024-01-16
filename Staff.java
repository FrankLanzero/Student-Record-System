import java.util.ArrayList;

public class Staff {
    //Declaration of attributes
    private int staffID;
    private String department;
    private String firstName;
    private String lastName;
    private String middleName;
    private ArrayList<OFES> staffOFES = new ArrayList<>();
    //Constructor from loading StaffInformation.csv
    public Staff(String[] values){
        this.staffID = Integer.parseInt(values[0]);
        this.department = values[1];
        this.firstName = values[2];
        this.middleName = values[3];
        this.lastName = values[4];

    }
    //Each of the OFES Records of a Staff is shown
    public void displayOFESRecords(int academicYear, int term) {
        ArrayList<String> courseCode = new ArrayList<>();
        for(OFES x : staffOFES){
            if(x.getAcademicYear() == academicYear && x.getTerm() == term){
                courseCode.add(x.getCourseCode());
            }
        }

        if(courseCode.size() == 0){
            new GUI("Invalid OFES Input!");
            return;
        }

        ArrayList<String> courseOFESRecords = new ArrayList<>();
        for (String element : courseCode) {
            if (!courseOFESRecords.contains(element)) {
                courseOFESRecords.add(element);
            }
        }

        int len = courseOFESRecords.size();
        int count = 0;
        double[] finalCounts = {0, 0, 0, 0, 0, 0, 0, 0};

        //It will use an algorithm that takes the average of the Staff in each category
        //It is then stored, then displayed.
        while(count < len){
            double[] averagePerCourse = new double[8];
            String currentCourse = courseOFESRecords.get(count);
            double[] tempCumulative = {0,0,0,0,0,0,0,0};
            int numRecords = 0;
            //Loops and takes all averageRating values
            //Then adds it to tempCumulative for later use
            for(OFES x : staffOFES){
                boolean checkYear = x.getAcademicYear() == academicYear;
                boolean checkTerm = x.getTerm() == term;
                boolean checkCourse = x.getCourseCode().equals(currentCourse);
                if(checkYear && checkTerm && checkCourse){
                    for(int i = 0; i < 8; i++){
                        tempCumulative[i] += x.getAverageRating(i);
                    }
                    numRecords++;
                }
            }

            //Takes averagePerCourse by using tempCumulative
            for(int i = 0; i < 8; i++){
                averagePerCourse[i] = tempCumulative[i]/numRecords;
                finalCounts[i] += averagePerCourse[i];
            }
            count++;
        }

        //Gets the final weighted averages
        //Then uses GUI
        double[] finalAverages  = new double[8];
        for(int i = 0; i < 8; i++){
            finalAverages[i] = finalCounts[i]/len;
        }
        new GUI(this, finalAverages, academicYear, term);

    }

    //In general, displays the general information of a staff
    public void displayStaff(){
        System.out.print(firstName);
        if(!middleName.equals("0")){
            System.out.print(" " + middleName);
        }
        System.out.println(" " + lastName);
        System.out.println("DEPARTMENT: " + department);

    }
    //Adds a new OFES Record after answering it
    public void addOFESRecord(String courseCode, int academicYear, int term, double[] rating){
        OFES newRecord = new OFES(courseCode, academicYear, term, rating);
        this.staffOFES.add(newRecord);
    }
    //After reading from file, loads the values to each of the staff
    public void loadOFES(String[] values) {
        OFES loadRecord = new OFES(values);
        this.staffOFES.add(loadRecord);
    }

    //Looks for staff if the staff ID is met
    public static Staff findStaff(int staffID){
        Staff toReturn = null;
        for(Staff x : Records.STAFF_DATABASE){
            if(x.getStaffID() == staffID){
                toReturn = x;
                break;
            }
        }
        return toReturn;
    }
    //Shows all the subjects of that staff
    //Uses GUI
    public static void displayStaffSubjects(int academicYear, int term, int staffID){

        Staff staff = findStaff(staffID);
        new GUI(staff, academicYear, term);
    }

    //GET SETTERS
    public int getStaffID() {
        return staffID;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public ArrayList<OFES> getStaffOFES() {
        return staffOFES;
    }
    public String getDepartment() {
        return department;
    }
    public String getFullName(){
        String middle = " ";
        if(!middleName.equals("0")){
            middle = middleName + " ";
        }
        return firstName + " " + middle + lastName;
    }
}
