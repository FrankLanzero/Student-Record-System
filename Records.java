import java.util.ArrayList;
import java.util.Scanner;

public interface Records {
    //Setting up database
    ArrayList<Student> STUDENT_DATABASE = new ArrayList<>();
    ArrayList<Course> COURSE_DATABASE = new ArrayList<>();
    ArrayList<Staff> STAFF_DATABASE = new ArrayList<>();
    //String arrays for valid values
    String[] CITIZENSHIP = {"Filipino", "American", "Korean"};
    String[] COLLEGES = {"CS", "ME", "ECE"};
    String[] PROGRAMS = {"BSCS", "BSME", "BSECE"};
    //Constant prices
    double PRICE_PER_UNIT = 1960.00;
    double MISC_FEE = 9193.00;
    double ID_VALID = 75.00;
    //Fees for the COR
    //Constant Fees other here as well
    String LABORATORY_FEE_PROMPT = "Laboratory Fee";
    String[] LAB_SUBJECT_WITH_FEES = {"CCS0003L", "CCS0001L", "CCS0007L", "CCS0021L", "CS0003L",
            "CS0070L", "CCS0043L", "CS0017L", "CCS0015L", "CCS0005L",
            "COE0009L", "COE0017L", "COE0015L", "COE0041L", "COE0047L", "COE0053L", "COE0055L",
            "ECE0002L", "ECE0003L", "ECE0005L", "ECE0007L", "ECE0009L",
            "ECE0015L", "ECE0017L", "ECE0019L", "ECE0021L", "ECE0025L",
            "ECE0027L", "ECE0029L", "ECE0031L", "ECE0033L", "ECE0035L",
            "ECE0037L", "ECE0043L", "ECE0047L", "ECE0049L", "ECE0055L",
            "ECE0061L", "ECE0069L",
            "ME0023L", "ME0037L", "ME0041L", "ME0043L", "ME0051L",
            "ME0057L", "ME0061L"};
    double MAJOR_FEE = 3728.00;
    String MPS_PROMPT = "Science Lab Fee";
    String[] MPS_SUBJECT_WITH_FEES = {"GED0081L", "GED0083L"};
    String HSC_PROMPT = "HSC Class Fee";
    String[] HSC_SUBJECT_WITH_FEES = {"GED0001"};
    double MINOR_FEE = 3816.00;
    double ADD_ON = 250.00;
    String ADMIN_PASSKEY = "1006"; //PASSWORD
    //Values for valid FEU Grades
    double[] VALID_FEU_GRADES = {0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0};

    //Initialization of Curriculum values
    //Each row is a term
    String[] CURRICULUM_CS = {"CCS0001", "CCS0001L", "CCS0003", "CCS0003L", "GED0004", "GED0006", "GED0009", "GED0011",
            "CCS0005", "CCS0005L", "CCS0007", "CCS0007L", "GED0001", "GED0015", "GED0027", "GED0029", "NSTP1",
            "CCS0015", "CCS0015L", "CCS0105", "GED0007", "GED0023", "GED0035", "GED0040", "GED0055", "NSTP2",
            "CCS0021", "CCS0021L", "CS0001", "CS0003", "CS0003L", "GED0021", "GED0075", "GED0055", "GED0081", "GED0081L",
            "CCS0101", "CS0007", "CS0010", "CS0017", "CS0017L", "CS0021", "GED0083", "GED0083L",
            "CCS0043", "CCS0043L", "CCS0103", "CS0005", "CS0013", "CS0023", "CS0007", "GED0031",
            "CS0011", "CS0016", "CS0019", "CS0025",
            "CS0027", "CS0031", "CS0035", "GED0019", "GED0059",
            "CCS0047", "CS0029", "CS0033", "GED0061", "GED0073",
            "CS0037", "CS0039", "GED0047", "GED0047", "GED0065"};
    String[] CURRICULUM_ECE = {"COE0001", "COE0003", "COE0005", "GED0001", "GED0004", "GED0006", "GED0007", "NSTP1",
            "COE0007", "COE0009", "COE0009L", "COE0017", "COE0017L", "GED0015", "GED0019", "GED0021", "GED0085",
            "COE0011", "COE0013", "COE0015", "GED0023", "GED0027", "GED0031", "NSTP2",
            "COE0019", "COE0025", "COE0027", "ECE0001", "ECE0002", "ECE0002L", "ECE0003", "ECE0003L", "GED0047", "GED0049",
            "ECE0005", "ECE0005L", "ECE0007", "ECE0007L", "ECE0009", "ECE0009L", "ECE0011", "GED0011", "GED0035",
            "ECE0012", "ECE0013", "ECE0015", "ECE0015L", "ECE0017", "ECE0017L", "ECE0019", "ECE0019L", "GED0043",
            "COE0039", "COE0057", "ECE0021", "ECE0021L", "ECE0023", "ECE0025", "ECE0025L", "ECE0027", "ECE0027L",
            "COE0049", "ECE0028", "ECE0029", "ECE0029L", "ECE0031", "ECE0031L", "ECE0033", "ECE0033L", "GED0061",
            "COE0020", "COE0059", "ECE0035", "ECE0035L", "ECE0037", "ECE0037L", "ECE0041", "ECE0043",
            "COE0061", "ECE0045", "ECE0047", "ECE0047L", "ECE0049", "ECE0049L", "ECE0051", "ECE0053", "ECE0055", "ECE0059",
            "ECE0061", "ECE0061L", "ECE0063", "ECE0065", "ECE0067", "ECE0069", "ECE0069L", "GED0009"
    };
    String[] CURRICULUM_ME = {"COE0001", "COE0003", "COE0005", "GED0001", "GED0004", "GED0006", "GED0007", "NSTP1",
            "COE0007", "COE0009", "COE0009L", "COE0017", "COE0017L", "GED0015", "GED0019", "GED0021", "GED0085",
            "COE0011", "COE0013", "COE0015", "GED0023", "GED0027", "GED0031", "NSTP2",
            "COE0019", "COE0020", "COE0029", "COE0031", "COE0033", "COE0035", "COE0057", "GED0009", "ME0001",
            "COE0025", "COE0037", "COE0041", "COE0041L", "COE0045", "GED0043", "GED0047", "GED0049", "ME0003",
            "COE0039", "COE0049", "ME0005", "ME0007", "ME0007L", "ME0009", "ME0011", "ME0013",
            "COE0047", "COE0047L", "COE0049", "COE0051", "COE0053", "COE0053L", "COE0055", "COE0055L", "ME0015", "ME0017",
            "COE0059", "GED0011", "ME0019", "ME0021", "ME0023", "ME0023L", "ME0035", "ME0036",
            "GED0035", "GED0061", "ME0027", "ME0029", "ME0031", "ME0033", "ME0037", "ME0037L", "ME0045",
            "COE0061", "ME0039", "ME0041", "ME0041L", "ME0043", "ME0043L", "ME0047", "ME0049", "ME0051", "ME0051L", "ME0063",
            "ME0055", "ME0057", "ME0057L", "ME0059", "ME0061", "ME0061L", "ME0065"
    };

    //OFES Question Initializations
    int[] QUESTION_AMOUNT = {10, 4, 3, 10, 9, 10, 6, 3};
    String[] OFES_TITLES = {"A. Mastery, Classroom Presentation, Interaction with Students and Personality",
            "B. Teaching Strategies using Canvas",
            "C. Communication Skills",
            "D. Courseware Evaluation : Video Courseware",
            "E. Courseware Evaluation : Lecture",
            "F. Courseware Evaluation : Assessment and Feedback",
            "G. iCARE / Consultations",
            "H. General Ratings"
    };
    String[] CATEGORY_OFES_A = {
            "Has mastery of the subject matter",
            "Has the ability to integrate values and other related fields into the subject matter",
            "Presents the lesson in a clear and organized manner (virtual consultations/canvas materials)",
            "Uses varied interesting instructional activities/materials and teaching strategies",
            "Gives exams that permit students to show their understanding through formative and summative assessments",
            "Has good public relations and interpersonal skills",
            "Is friendly and helpful to students",
            "Facilitates learning through individualized approach (individualized learning)",
            "Is neat and well groomed (during virtual classes)",
            "Shows vitality and enthusiasm during online activities",
    };
    String[] CATEGORY_OFES_B = {"Use Canvas in the delivery of his/her lesson in class",
            "Use Canvas in creating activities that stimulates student participation",
            "All learning materials are readily available in Canvas",
            "Uses the Canvas` teaching and learning tools",
    };
    String[] CATEGORY_OFES_C = {"Delivers the lesson in modulated voice using English at all times (except in Filipino)",
            "Encourages questions from students", "Is careful and precise in answering questions"};
    String[] CATEGORY_OFES_D = {"The content of the video is understandable and useful for the topic presented.",
            "The content of the video is technically accurate and free from errors.",
            "The length of the video is enough to sustain the student`s attention span.",
            "The speaker in the video is able to engage well with the audience.",
            "The speaker is fluent and able to discuss the topic well.",
            "The video produces a good visual perception in terms of colors and lighting (e.g., high definition, good quality)",
            "The audio possesses good quality sound (e.g. tone, volume, pitch of sound)",
            "The video is free from unnecessary sounds such hums, buzz and other noise from the room.",
            "The video and audio have been edited well.",
            "The timing of the voice over matches perfectly with the video presentation."};

    String[] CATEGORY_OFES_E = {"The lectures/lessons in the modules are aligned with the objectives and learning outcomes of the course.",
            "The learning goals are clearly identified in every lesson",
            "The lectures/lessons follow a logical sequence and the presentation is well-organized.",
            "The level of difficulty of the lectures/lessons is suitable to the students.",
            "The ideas are expressed in clear, coherent, and appropriately-worded language.",
            "The lectures/lessons develop critical thinking by engaging the students to raise questions, reflect, analyze, synthesize, and evaluate.",
            "The learning tasks are reasonable and allows students to accomplish tasks at their own pace.",
            "The modules provide learning interventions to address students` needs.",
            "The modules use current and up-to-date materials and cite references appropriately",
    };
    String[] CATEGORY_OFES_F = {"The course includes a variety of assessments applicable to the lectures/lessons.",
            "The course utilizes assessments that are appropriate for students, and are aligned with the topics.",
            "Assessments are designed to measure the course learning outcomes.",
            "Formative Assessments (FAs) are based on the course materials provided.",
            "Summative Assessments (SAs) are based on the course materials provided.",
            "The formative assessments help me pass my summative assessments.",
            "The course adviser provides sufficient feedback on my assessment results.",
            "The course adviser provides timely feedback on my assessment results.",
            "The course adviser provides explanation when I have questions during guided formative assessments.",
            "The guided formative assessments help me achieve better mastery of the topic/s."
    };

    String[] CATEGORY_OFES_G = {"The faculty member showed expertise in the field he was consulted upon.",
            "Attentive to the concerns of the students particularly on the subject matter presented for Clarification or guidance.",
            "Compassionate in dealing with his/her students.",
            "Suggests and recommends better solutions to students to ensure that tasks are doable.",
            "The faculty was available during the scheduled consultation.",
            "The faculty was responsive and accommodating on student`s request for consultation."
    };
    String[] CATEGORY_OFES_H = {"Considering both the limitations and possibilities of the subject matter and course, how do you rate the overall teaching effectiveness of this instructor? (1-Least Effective - 5-Most Effective; 0-Not applicable)",
            "Focusing now on the course content, how worthwhile was this course in comparison to others you have taken in this department? (1-Least Worthwhile - 5-Most Worthwhile; 0-Not applicable)",
            "In your own personal assessment, were you able to attain and demonstrate the student and course learning outcomes? 1-Not at all - 5-to a Great extent; 0-Not applicable)",
    };
}