import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

public class Save {
    //Save Class that saves each record to their respective files
    //Each method has its own different way of saving to the file
    public void saveStudentInformation() {
        //Student Information csv File
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/StudentInformation.csv"))) {
            for(Student x : Records.STUDENT_DATABASE){
                int studentNumber = x.getStudentNumber();
                String password = x.getPassword();
                String firstName = x.getFirstName();
                String middleName = x.getMiddleName();
                String lastName = x.getLastName();
                String citizenship = x.getCitizenship();
                double balance = x.getBalance();

                String studentLine = studentNumber + ", " + password + ", " + firstName + ", " +
                        middleName + ", " + lastName + ", " + citizenship + ", " + balance + "\n";
                writer.write(studentLine);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //Student Courses csv File
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/StudentCourses.csv"))) {
            for(Student student : Records.STUDENT_DATABASE){
                int studentNumber = student.getStudentNumber();
                for(COR cor : student.getStudentCOR()){
                    int academicYear = cor.getSchoolYear();
                    int year = cor.getYearLevel();
                    int term = cor.getTerm();
                    String status = cor.getEnrollmentStatus();

                    StringBuilder line = new StringBuilder(studentNumber + ", " + academicYear + ", "
                            + year + ", " + term + ", " + status);
                    for(Course course : cor.getCourseList()){
                        line.append(", ").append(course.getCourseCode()).append(" - ").append(course.getSection());
                    }
                    line.append("\n");

                    writer.write(line.toString());
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //Student Program csv File

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/StudentProgram.csv"))) {
            for(Student student : Records.STUDENT_DATABASE){
                int studentNumber = student.getStudentNumber();
                String program = student.getProgram();
                String college = student.getCollege();
                String curriculum = student.getCurriculumCode();
                String status = student.getRegistrationStatus();
                String line = studentNumber + ", " + program + ", "
                        + college + ", " + curriculum + ", " + status + "\n";
                writer.write(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void saveStudentFinanceAndFees(){
        //Student Finance csv File
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/StudentFinance.csv"))) {
            for(Student student : Records.STUDENT_DATABASE){
                int studentNumber = student.getStudentNumber();
                for(BalanceInquiry balanceInquiry : student.getBalanceInquiry()) {
                    int academicYear = balanceInquiry.getSchoolYear();
                    int year = balanceInquiry.getYearLevel();
                    int term = balanceInquiry.getTerm();
                    StringBuilder studentLine = new StringBuilder(studentNumber + ", " + academicYear + ", " + year + ", " +
                            term);
                    for (int i = 0; i < balanceInquiry.getPaymentList().size(); i++) {
                        studentLine.append(", ").append(balanceInquiry.getPaymentList().get(i)).append(" - ").append(balanceInquiry.getPaymentDate().get(i));
                    }
                    studentLine.append("\n");
                    writer.write(studentLine.toString());
                }
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //Student Fees csv File
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/StudentFees.csv"))) {
            for(Student student : Records.STUDENT_DATABASE){
                int studentNumber = student.getStudentNumber();
                for(COR cor : student.getStudentCOR()) {
                    int academicYear = cor.getSchoolYear();
                    int year = cor.getYearLevel();
                    int term = cor.getTerm();
                    int units = cor.getUnitsEnrolled();
                    StringBuilder studentLine = new StringBuilder(studentNumber + ", " + academicYear + ", " + year + ", " +
                            term +
                            ", Tuition Fee (" + units + ") - " + cor.getTuitionFee() +
                            ", Miscellaneous Fee - " + cor.getMiscellaneousFee() +
                            ", ID Validation Fee - " + cor.getIdValidationFee());
                    for (int i = 0; i < cor.getLaboratoryFeeNames().size(); i++) {
                        studentLine.append(", ").append(cor.getLaboratoryFeeNames().get(i)).append(" - ").append(cor.getLaboratoryFee().get(i));
                    }
                    for (int i = 0; i < cor.getGedFeeNames().size(); i++) {
                        studentLine.append(", ").append(cor.getGedFeeNames().get(i)).append(" - ").append(cor.getGedFee().get(i));
                    }
                    studentLine.append(", TOTAL ASSESSMENT - ").append(cor.getTotalAssessment()).append(", ").append("Down Payment (Upon Enrollment) 40% - ").append(cor.getDownPayment()).append(", ").append("Midterm 30% - ").append(cor.getMidtermDownPayment()).append(", ").append("Final 30% - ").append(cor.getFinalDownPayment()).append(", ").append("Add on: Installment Fee - ").append(cor.getAddOn()).append("\n");
                    writer.write(studentLine.toString());
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveCourses(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/CourseDatabase.txt"))) {
            for(Course course : Records.COURSE_DATABASE){
                String courseCode = course.getCourseCode();
                String courseTitle = course.getCourseTitle();
                int academicYear = course.getAcademicYear();
                int term = course.getTerm();
                int units = course.getUnits();
                String section = course.getSection();

                String line = courseCode + " ~ " + courseTitle + " ~ "
                        + academicYear + " - " + term + " ~ " + section + " ~ " + units + " ~ ";
                String dayLine = course.getClassDays().get(0);
                if(course.getClassDays().size() == 2){
                    dayLine = dayLine + "/" + course.getClassDays().get(1);
                }
                if(course.getClassDays().size() == 3){
                    dayLine = dayLine + "/" + course.getClassDays().get(1);
                    dayLine = dayLine + "/" + course.getClassDays().get(2);
                }

                LocalTime startTime = course.getStartTime();
                LocalTime endTime = course.getEndTime();
                String room = course.getRoom();
                int staffId = course.getStaffId();
                int slots = course.getSlotsAvailable();
                int students = course.getStudents();

                line = line + dayLine + " ~ " + startTime + ":00 - " + endTime + ":00 ~ "
                + room + " ~ " + staffId + " ~ " + slots + " ~ " + students + "\n";

                writer.write(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void saveStaff(){
        //Staff csv File
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/StaffInformation.csv"))) {
            for(Staff staff : Records.STAFF_DATABASE){
                int staffId = staff.getStaffID();
                String department = staff.getDepartment();
                String firstName = staff.getFirstName();
                String middleName = staff.getMiddleName();
                String lastName = staff.getLastName();

                String studentLine = staffId + ", " + department + ", " + firstName + ", " +
                        middleName + ", " + lastName + "\n";
                writer.write(studentLine);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //OSES Records csv File
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/OFESRecords.csv"))) {
            for(Staff staff : Records.STAFF_DATABASE){
                StringBuilder ofesLine;
                int staffId = staff.getStaffID();
                for(OFES ofes : staff.getStaffOFES()){
                    String courseCode = ofes.getCourseCode();
                    int academicYear = ofes.getAcademicYear();
                    int term = ofes.getTerm();
                    double[] average = ofes.getOFESRating();
                    ofesLine = new StringBuilder(staffId + ", " + courseCode + ", " + academicYear + ", " +
                            term);
                    for(int i = 0; i < 8; i++){
                        ofesLine.append(", ").append(average[i]);
                    }
                    ofesLine.append("\n");
                    writer.write(ofesLine.toString());
                }


            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void saveGrades(){
        //Student Grades csv File
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/StudentGrades.csv"))) {
            for(Student student : Records.STUDENT_DATABASE){
                int studentNumber = student.getStudentNumber();
                for(CourseCard courseCard : student.getStudentCourseCard()){
                    int academicYear = courseCard.getSchoolYear();
                    int year = courseCard.getYearLevel();
                    int term = courseCard.getTerm();
                    boolean graded = courseCard.isGraded();

                    StringBuilder line = new StringBuilder(studentNumber + ", " + academicYear + ", "
                            + year + ", " + term + ", " + graded);

                    for(int i = 0; i < courseCard.getCourses().size(); i++) {
                        line.append(", ").append(courseCard.getCourses().get(i)).append(" - ").append(courseCard.getMidtermGrades().get(i)).append(" - ").append(courseCard.getFinalGrades().get(i)).append(" - ").append(courseCard.getGradedStatus().get(i)).append(" - ").append(courseCard.getRatedStatus().get(i)).append(" - ").append(courseCard.getSection().get(i));
                    }
                    line.append("\n");

                    writer.write(line.toString());
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void saveAcademicValues(){
        //Academic Year Info csv File
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/AcademicYearInfo.csv"))) {
            String line = Main.academicYearStart + ", " + Main.academicYearEnd + ", "
                    + Main.academicYearRange + ", " + Main.currentTerm;
            writer.write(line);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
