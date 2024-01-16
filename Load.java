import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Load {
    //Load Class to separate the loading of all the information from the Main class

    //Uses File class to load a specific file
    public void loadCourses() throws FileNotFoundException {
        String line;

        //Scanner is then used to scan the lines
        Scanner scan = new Scanner(getReference("src/CourseDatabase.txt"));
        while(scan.hasNextLine()) {
            line = scan.nextLine();
            String[] values = line.split(" ~ ");
            //Splits all values in csv and puts it in the values array

            Course loadCourse = new Course(values);
            Records.COURSE_DATABASE.add(loadCourse);
        }

        scan = new Scanner(getReference("src/CoursePrerequisites.txt"));
        while(scan.hasNextLine()) {
            line = scan.nextLine();
            String[] values = line.split(" ~ ");

            //Splits it, if it is two, don't bother with the code below
            //A value of 2 for length means that a subject does not have a prerequisite
            Main.courseLibrary.put(values[0], values[1]);
            if(values.length == 2){
                continue;
            }

            for(Course x : Records.COURSE_DATABASE){
                if(values[0].equals(x.getCourseCode())){
                    x.loadPrerequisites(values);
                }
            }

        }
    }
    public void loadStudentDetails() throws FileNotFoundException{
        String line;

        Scanner scan = new Scanner(getReference("src/StudentInformation.csv"));
        while(scan.hasNextLine()) {
            line = scan.nextLine();
            String[] values = line.split(", ");
            //Splits all values in csv and puts it in the values array

            Student loadStudent = new Student(values);
            Records.STUDENT_DATABASE.add(loadStudent);
        }
    }
    public void loadStaffDetails() throws FileNotFoundException{
        String line;

        Scanner scan = new Scanner(getReference("src/StaffInformation.csv"));
        while(scan.hasNextLine()) {
            line = scan.nextLine();
            String[] values = line.split(", ");
            //Splits all values in csv and puts it in the values array

            Staff loadStaff = new Staff(values);
            Records.STAFF_DATABASE.add(loadStaff);
        }
    }
    public void loadStudentCourses() throws FileNotFoundException{
        String line;

        Scanner scan = new Scanner(getReference("src/StudentCourses.csv"));
        while(scan.hasNextLine()) {
            line = scan.nextLine();
            String[] values = line.split(", ");
            //Splits all values in csv and puts it in the values array

            ArrayList<String> val = new ArrayList<>();
            Collections.addAll(val, values);

            //If a student number matches one in the database
            //It will load the respective courses
            int studentNumber = Integer.parseInt(values[0]);
            for(Student x : Records.STUDENT_DATABASE){
                if(x.getStudentNumber() == studentNumber){
                    x.loadCourses(val);
                    break;
                }
            }
        }

    }
    public void loadStudentProgram() throws FileNotFoundException{
        String line;

        Scanner scan = new Scanner(getReference("src/StudentProgram.csv"));
        while(scan.hasNextLine()) {
            line = scan.nextLine();
            String[] values = line.split(", ");
            //Splits all values in csv and puts it in the values array

            //If a student number matches one in the database
            //It will load the respective program
            int studentNumber = Integer.parseInt(values[0]);
            for(Student x : Records.STUDENT_DATABASE){
                if(x.getStudentNumber() == studentNumber){
                    x.loadStudentProgram(values);
                    break;
                }
            }
        }

    }
    public void loadStudentGrades() throws FileNotFoundException{
        String line;

        Scanner scan = new Scanner(getReference("src/StudentGrades.csv"));
        while(scan.hasNextLine()) {
            line = scan.nextLine();
            String[] values = line.split(", ");
            //Splits all values in csv and puts it in the values array

            ArrayList<String> grades = new ArrayList<>();
            String[] studentTermInfo = new String[5];
            int count = 0;
            for(String x : values){
                if(count < 5){
                    studentTermInfo[count] = x;
                }
                else{
                    grades.add(x);
                }
                count++;
            }

            //Loads the grades from the files
            int studentNumber = Integer.parseInt(studentTermInfo[0]);
            for(Student x : Records.STUDENT_DATABASE){
                if(x.getStudentNumber() == studentNumber){
                    x.loadStudentGrades(studentTermInfo, grades);
                    break;
                }
            }
        }

    }

    public void loadStudentFinanceRecords() throws FileNotFoundException{
        String line;
        Scanner scan = new Scanner(getReference("src/StudentFees.csv"));
        while(scan.hasNextLine()) {
            line = scan.nextLine();
            String[] values = line.split(", ");
            //Splits all values in csv and puts it in the values array

            ArrayList<String> val = new ArrayList<>();
            Collections.addAll(val, values);

            //If a student number matches one in the database
            //It will load the respective fees
            int studentNumber = Integer.parseInt(values[0]);
            for(Student x : Records.STUDENT_DATABASE){
                if(x.getStudentNumber() == studentNumber){
                    x.loadFees(val);
                    break;
                }
            }
        }

        scan = new Scanner(getReference("src/StudentFinance.csv"));
        while(scan.hasNextLine()) {
            line = scan.nextLine();
            String[] values = line.split(", ");
            //Splits all values in csv and puts it in the values array

            ArrayList<String> val = new ArrayList<>();
            Collections.addAll(val, values);

            //If a student number matches one in the database
            //It will load the respective financial records
            int studentNumber = Integer.parseInt(values[0]);
            for(Student x : Records.STUDENT_DATABASE){
                if(x.getStudentNumber() == studentNumber){
                    x.loadFinance(val);
                    break;
                }
            }
        }


    }
    public void loadOFESRecords() throws FileNotFoundException{
        String line;

        Scanner scan = new Scanner(getReference("src/OFESRecords.csv"));
        while(scan.hasNextLine()) {
            line = scan.nextLine();
            String[] values = line.split(", ");
            //Splits all values in csv and puts it in the values array

            //Stores each value into a specific staff
            for(Staff x : Records.STAFF_DATABASE){
                if(x.getStaffID() == Integer.parseInt(values[0])){
                    x.loadOFES(values);
                }
            }
        }
    }

    public void loadAcademicValues() throws FileNotFoundException{
        String line;

        Scanner scan = new Scanner(getReference("src/AcademicYearInfo.csv"));
        while(scan.hasNextLine()) {
            line = scan.nextLine();
            String[] values = line.split(", ");
            //Splits all values in csv and puts it in the values array

            //Initializes the values in Main
            Main.academicYearStart = Integer.parseInt(values[0]);
            Main.academicYearEnd = Integer.parseInt(values[1]);
            Main.academicYearRange = Integer.parseInt(values[2]);
            Main.currentTerm = Integer.parseInt(values[3]);
        }

    }
    //Method that returns a File object based on a relative path of a file
    public File getReference(String relativePath){
        File file;
        file = new File(relativePath);
        return file;
    }
}
