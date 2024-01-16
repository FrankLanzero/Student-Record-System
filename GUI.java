import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class GUI extends JFrame {

    //STUDENT COR WINDOW
    public GUI(Student student, COR cor) {
        JPanel panel = new JPanel();
        JLabel enrollment, termSchoolYear, studentNum, studentInfo;

        //TITLE OF THE FRAME
        JLabel titleLabel = new JLabel("CERTIFICATE OF REGISTRATION");
        panel.add(titleLabel);

        //BORDER AND LAYOUT
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(0, 1));

        //LABELS
        enrollment = new JLabel("ENROLLMENT STATUS: Enrolled");
        enrollment.setForeground(Color.GREEN);
        panel.add(enrollment);
        termSchoolYear = new JLabel("Term : " + cor.getTerm() + "          School Year : " + cor.getSchoolYear());
        panel.add(termSchoolYear);
        studentNum = new JLabel("Student Number : " + student.getStudentNumber() + "      College : " + student.getCollege() + "      Program : " + student.getProgram());
        panel.add(studentNum);
        studentInfo = new JLabel("Name : " + student.getFullName() + "    Year : " + cor.getYearLevel() + "    Term : " + cor.getTerm());
        panel.add(studentInfo);

        String[] columns = {"Course Code", "Course Title", "Section", "Units", "Days", "Time", "Room"};

        //LABEL OF THE COURSES
        DefaultTableModel modelCourse = new DefaultTableModel(columns, 0);
        String[] rowInfo = new String[7];
        for (Course course : cor.getCourseList()) {
            rowInfo[0] = course.getCourseCode();
            rowInfo[1] = course.getCourseTitle();
            rowInfo[2] = course.getSection();
            rowInfo[3] = String.valueOf(course.getUnits());
            rowInfo[4] = course.getClassDays().get(0);
            for (int i = 1; i < course.getClassDays().size(); i++) {
                rowInfo[4] += "/" + course.getClassDays().get(i);
            }
            rowInfo[5] = course.getStartTime() + "-" + course.getEndTime();
            rowInfo[6] = course.getRoom();
            modelCourse.addRow(rowInfo);
        }
        JTable tableCourse = new JTable(modelCourse);
        panel.add(tableCourse);
        JScrollPane scrollPaneCourse = new JScrollPane(tableCourse);
        panel.add(scrollPaneCourse);

        String[] columnsFees = {"Payment", "Price"};
        DefaultTableModel modelFees = new DefaultTableModel(columnsFees, 0);
        String[] rowFees = new String[2];

        rowFees[0] = "TUITION FEE";
        rowFees[1] = String.valueOf(cor.getTuitionFee());
        modelFees.addRow(rowFees);
        rowFees[0] = "MISCELLANEOUS FEE";
        rowFees[1] = String.valueOf(cor.getMiscellaneousFee());
        modelFees.addRow(rowFees);
        rowFees[0] = "ID VALIDATION FEE";
        rowFees[1] = String.valueOf(cor.getIdValidationFee());
        modelFees.addRow(rowFees);
        for (int i = 0; i < cor.getLaboratoryFee().size(); i++) {
            rowFees[0] = cor.getLaboratoryFeeNames().get(i);
            rowFees[1] = String.valueOf(cor.getLaboratoryFee().get(i));
            modelFees.addRow(rowFees);
        }
        for (int i = 0; i < cor.getGedFeeNames().size(); i++) {
            rowFees[0] = cor.getGedFeeNames().get(i);
            rowFees[1] = String.valueOf(cor.getGedFee().get(i));
            modelFees.addRow(rowFees);
        }

        rowFees[0] = "TOTAL ASSESSMENT";
        rowFees[1] = String.valueOf(cor.getTotalAssessment());
        modelFees.addRow(rowFees);
        rowFees[0] = "DOWN PAYMENT";
        rowFees[1] = String.valueOf(cor.getDownPayment());
        modelFees.addRow(rowFees);
        rowFees[0] = "MIDTERM PAYMENT";
        rowFees[1] = String.valueOf(cor.getMidtermDownPayment());
        modelFees.addRow(rowFees);
        rowFees[0] = "FINAL PAYMENT";
        rowFees[1] = String.valueOf(cor.getFinalDownPayment());
        modelFees.addRow(rowFees);
        rowFees[0] = "ADD ON";
        rowFees[1] = String.valueOf(cor.getAddOn());
        modelFees.addRow(rowFees);


        JTable tableFees = new JTable(modelFees);

        JScrollPane scrollPaneFees = new JScrollPane(tableFees);
        panel.add(scrollPaneFees);

        JButton exitButton = new JButton("Exit");
        panel.add(exitButton);

        this.add(panel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("STUDENT RECORD SYSTEM");
        this.pack();
        this.setVisible(true);

        exitButton.addActionListener(e -> dispose());
    }
    //STUDENT COURSE CARD WINDOW
    public GUI(Student student, ArrayList<CourseCard> courseCard) {
        // Create a panel to contain the components of the GUI.
        JPanel panel = new JPanel();

        // Add a label to display the title of the main menu.
        JLabel titleLabel = new JLabel("COURSE CARD");
        panel.add(titleLabel);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(0, 1));

        panel.add(new JLabel("Student Number : " + student.getStudentNumber() + "      College : " + student.getCollege() + "      Program : " + student.getProgram()));
        panel.add(new JLabel("Name : " + student.getLastName()));
        String[] columns = {"Course Code", "Midterm Grade", "Final Grade", "Status"};
        DefaultTableModel modelCourse = new DefaultTableModel(columns, 0);
        String[] rowInfo = new String[4];
        for (CourseCard cc : courseCard) {
            for (int i = 0; i < cc.getCourses().size(); i++) {
                rowInfo[0] = cc.getCourses().get(i);
                rowInfo[1] = String.valueOf(cc.getMidtermGrades().get(i));
                rowInfo[2] = String.valueOf(cc.getFinalGrades().get(i));
                rowInfo[3] = cc.getGradedStatus().get(i);
                modelCourse.addRow(rowInfo);
            }
            JTable tableCourse = new JTable(modelCourse);
            JScrollPane scrollPaneCourse = new JScrollPane(tableCourse);
            panel.add(scrollPaneCourse);
        }

        panel.add(new JLabel("GWA: " + student.calculateGWA()));


        JButton exitButton = new JButton("Exit");
        panel.add(exitButton);

        this.add(panel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("STUDENT RECORD SYSTEM");
        this.pack();
        this.setVisible(true);

        exitButton.addActionListener(e -> dispose());
    }
    //STUDENT PAYMENTS WINDOW
    public GUI(BalanceInquiry balanceInquiry, Student student) {
        // Create a panel to contain the components of the GUI.
        JPanel panel = new JPanel();

        // Add a label to display the title of the main menu.
        JLabel titleLabel = new JLabel("ASSESSMENT");
        panel.add(titleLabel);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(0, 1));

        panel.add(new JLabel("YEAR: " + balanceInquiry.getSchoolYear()));
        panel.add(new JLabel("TERM: " + balanceInquiry.getTerm()));
        panel.add(new JLabel("Student Number : " + balanceInquiry.getStudentNumber() + "      College : " + student.getCollege() + "      Program : " + student.getProgram()));
        panel.add(new JLabel("Full Name : " + student.getFullName()));
        panel.add(new JLabel("Course Program: " + student.getProgram()));

        panel.add(new JLabel());
        panel.add(new JLabel("Beginning Balance: " + 8500.00));
        panel.add(new JLabel("Total Assessment: " + balanceInquiry.getTotalAssessment()));

        panel.add(new JLabel());
        panel.add(new JLabel("PAYMENT LIST:"));
        for (int i = 0; i < balanceInquiry.getPaymentDate().size(); i++) {
            String date = balanceInquiry.getPaymentDate().get(i);
            double payment = balanceInquiry.getPaymentList().get(i);
            panel.add(new JLabel("   " + date + "     (" + payment + ")"));
        }

        panel.add(new JLabel("Remaining Balance / (Overpayment)      " + balanceInquiry.getRemainingBalance()));

        if (balanceInquiry.getFullyPaid()) {
            panel.add(new JLabel("Student is FULLY PAID!"));
        }
        JButton exitButton = new JButton("Exit");
        panel.add(exitButton);

        this.add(panel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("STUDENT RECORD SYSTEM");
        this.pack();
        this.setVisible(true);

        exitButton.addActionListener(e -> dispose());
    }
    //STUDENT INFORMATION WINDOW
    public GUI(Student student) {
        // Create a panel to contain the components of the GUI.
        JPanel panel = new JPanel();

        // Add a label to display the title of the main menu.
        JLabel titleLabel = new JLabel("STUDENT INFORMATION");
        panel.add(titleLabel);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(0, 1));

        panel.add(new JLabel("Student Number : " + student.getStudentNumber() + "      College : " + student.getCollege() + "      Program : " + student.getProgram()));
        panel.add(new JLabel("Name : " + student.getFullName()));
        panel.add(new JLabel("Citizenship : " + student.getCitizenship()));
        panel.add(new JLabel("Year Level : " + student.getYearLevel()));
        panel.add(new JLabel("Balance : " + student.getBalance()));

        JButton exitButton = new JButton("Exit");
        panel.add(exitButton);

        this.add(panel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("STUDENT RECORD SYSTEM");
        this.pack();
        this.setVisible(true);

        exitButton.addActionListener(e -> dispose());
    }
    //STAFF OFES WINDOW
    public GUI(Staff staff, double[] finalAverages, int academicYear, int term) {
        // Create a panel to contain the components of the GUI.
        JPanel panel = new JPanel();

        // Add a label to display the title of the main menu.
        JLabel titleLabel = new JLabel("STAFF INFORMATION");
        panel.add(titleLabel);

        //Border and Layout
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(0, 1));

        //Labels
        panel.add(new JLabel("Staff Name : " + staff.getFullName()));
        panel.add(new JLabel("Department : " + staff.getDepartment()));
        panel.add(new JLabel("Year : " + academicYear));
        panel.add(new JLabel("Term : " + term));

        panel.add(new JLabel());
        panel.add(new JLabel("OFES RECORDS"));
        for (int i = 0; i < 8; i++) {
            panel.add(new JLabel(Records.OFES_TITLES[i] + " - " + finalAverages[i]));
        }
        //Exit
        JButton exitButton = new JButton("Exit");
        panel.add(exitButton);

        this.add(panel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("STUDENT RECORD SYSTEM");
        this.pack();
        this.setVisible(true);

        exitButton.addActionListener(e -> dispose());
    }

    //STAFF SUBJECT WINDOW
    public GUI(Staff staff, int academicYear, int term) {
        // Create a panel to contain the components of the GUI.
        JPanel panel = new JPanel();

        // Add a label to display the title of the main menu.
        JLabel titleLabel = new JLabel("STAFF SUBJECTS");
        panel.add(titleLabel);

        //Border and Layout
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(0, 1));

        //Labels
        panel.add(new JLabel("Staff Name : " + staff.getFullName()));
        panel.add(new JLabel("Department : " + staff.getDepartment()));
        panel.add(new JLabel("Year : " + academicYear));
        panel.add(new JLabel("Term : " + term));

        panel.add(new JLabel());
        panel.add(new JLabel("STAFF SUBJECTS"));
        for (Course x : Records.COURSE_DATABASE) {
            boolean checkYear = x.getAcademicYear() == academicYear;
            boolean checkTerm = x.getTerm() == term;
            boolean checkId = x.getStaffId() == staff.getStaffID();
            if (checkYear && checkTerm && checkId) {
                panel.add(new JLabel(x.getCourseCode() + " - " + x.getCourseTitle() + " - " + x.getSection()));
            }
        }

        //Exit
        JButton exitButton = new JButton("Exit");
        panel.add(exitButton);

        this.add(panel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("STUDENT RECORD SYSTEM");
        this.pack();
        this.setVisible(true);

        exitButton.addActionListener(e -> dispose());
    }

    //COURSE WINDOW
    public GUI(String courseId, ArrayList<Course> courseList) {
        // Create a panel to contain the components of the GUI.
        JPanel panel = new JPanel();

        // Add a label to display the title of the main menu.
        JLabel titleLabel = new JLabel("COURSE: " + courseId);
        panel.add(titleLabel);

        //Border and Layout
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(0, 1));

        //Labels
        panel.add(new JLabel());
        for (Course x : courseList) {
            panel.add(new JLabel(x.getSection() + " - " + Staff.findStaff(x.getStaffId()).getFullName() + " - " + x.getStartTime() + "-" + x.getEndTime()));
        }

        //Exit
        JButton exitButton = new JButton("Exit");
        panel.add(exitButton);

        this.add(panel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("STUDENT RECORD SYSTEM");
        this.pack();
        this.setVisible(true);

        exitButton.addActionListener(e -> dispose());

    }

    public GUI(String message) {
        JOptionPane.showMessageDialog(this, message,
                "Invalid Record!", JOptionPane.ERROR_MESSAGE);
    }

    public GUI(String curriculumCode, String[] courseArray) {

        JPanel panel = new JPanel();

        //TITLE OF THE FRAME
        JLabel titleLabel = new JLabel("CURRICULUM CODE: " + curriculumCode);
        panel.add(titleLabel);

        //BORDER AND LAYOUT
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(0, 1));

        //LABELS
        panel.add(new JLabel("CURRICULUM SUBJECTS"));


        String[] columns = {"Course Code", "Course Title"};

        //LABEL OF THE COURSES
        DefaultTableModel modelCourse = new DefaultTableModel(columns, 0);
        String[] rowInfo = new String[2];
        for (String course : courseArray) {
            rowInfo[0] = course;
            rowInfo[1] = Main.courseLibrary.get(course);
            modelCourse.addRow(rowInfo);
        }
        JTable tableCourse = new JTable(modelCourse);
        panel.add(tableCourse);
        JScrollPane scrollPaneCourse = new JScrollPane(tableCourse);
        panel.add(scrollPaneCourse);

        JButton exitButton = new JButton("Exit");
        panel.add(exitButton);

        this.add(panel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("STUDENT RECORD SYSTEM");
        this.pack();
        this.setVisible(true);

        exitButton.addActionListener(e -> dispose());
    }
}
