import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for ElectronicGradeBook class.
 */
class ElectronicGradeBookTest {

    private ElectronicGradeBook gradeBook;

    @BeforeEach
    void setUp() {
        gradeBook = new ElectronicGradeBook(false);
    }

    @Test
    void testCalculateCurrentAverage_EmptyRecords() {
        assertEquals(0.0, gradeBook.calculateCurrentAverage());
    }

    @Test
    void testCalculateCurrentAverage_WithRecords() {
        gradeBook.addRecord(new AcademicRecord(
                "Math", Grade.EXCELLENT, AssessmentType.EXAM, 1));
        gradeBook.addRecord(new AcademicRecord(
                "Physics", Grade.GOOD, AssessmentType.EXAM, 1));
        gradeBook.addRecord(new AcademicRecord(
                "Thesis", Grade.EXCELLENT, AssessmentType.QUALIFICATION_WORK, 4));

        assertEquals(4.5, gradeBook.calculateCurrentAverage());
    }

    @Test
    void testCalculateCurrentAverage_OnlyQualificationWork() {
        gradeBook.addRecord(new AcademicRecord(
                "Thesis", Grade.EXCELLENT, AssessmentType.QUALIFICATION_WORK, 4));

        assertEquals(0.0, gradeBook.calculateCurrentAverage());
    }

    @Test
    void testIsPossibleForBudgetTransfer_NotPossible_SatisfactoryGrade() {
        gradeBook.setCurrentSemester(3);

        gradeBook.addRecord(new AcademicRecord(
                "Math", Grade.SATISFACTORY, AssessmentType.EXAM, 3));

        assertFalse(gradeBook.isPossibleForBudgetTransfer());
    }

    @Test
    void testIsPossibleForBudgetTransfer_NotPossible_AlreadyBudget() {
        ElectronicGradeBook budgetGradeBook = new ElectronicGradeBook(true);
        budgetGradeBook.setCurrentSemester(3);

        assertFalse(budgetGradeBook.isPossibleForBudgetTransfer());
    }

    @Test
    void testIsPossibleForBudgetTransfer_NotPossible_NotEnoughSemesters() {
        gradeBook.setCurrentSemester(1);

        gradeBook.addRecord(new AcademicRecord(
                "Math", Grade.EXCELLENT, AssessmentType.EXAM, 1));

        assertFalse(gradeBook.isPossibleForBudgetTransfer());
    }

    @Test
    void testIsPossibleForBudgetTransfer_Possible() {
        gradeBook.setCurrentSemester(3);

        gradeBook.addRecord(new AcademicRecord(
                "Math", Grade.EXCELLENT, AssessmentType.EXAM, 2));
        gradeBook.addRecord(new AcademicRecord(
                "Physics", Grade.GOOD, AssessmentType.EXAM, 3));
        gradeBook.addRecord(new AcademicRecord(
                "Programming", Grade.SATISFACTORY, AssessmentType.DIFFERENTIATED_CREDIT, 3)); 
        assertTrue(gradeBook.isPossibleForBudgetTransfer());
    }

    @Test
    void testIsPossibleForHonorsDiploma_NotPossible_NoQualificationWork() {
        gradeBook.addRecord(new AcademicRecord(
                "Math", Grade.EXCELLENT, AssessmentType.EXAM, 1));
        gradeBook.addRecord(new AcademicRecord(
                "Physics", Grade.EXCELLENT, AssessmentType.EXAM, 1));

        assertFalse(gradeBook.isPossibleForHonorsDiploma());
    }

    @Test
    void testIsPossibleForHonorsDiploma_NotPossible_QualificationWorkNotExcellent() {
        gradeBook.addRecord(new AcademicRecord(
                "Math", Grade.EXCELLENT, AssessmentType.EXAM, 1));
        gradeBook.addRecord(new AcademicRecord(
                "Thesis", Grade.GOOD, AssessmentType.QUALIFICATION_WORK, 4));

        assertFalse(gradeBook.isPossibleForHonorsDiploma());
    }

    @Test
    void testIsPossibleForHonorsDiploma_NotPossible_HasSatisfactoryGrade() {
        gradeBook.addRecord(new AcademicRecord(
                "Math", Grade.EXCELLENT, AssessmentType.EXAM, 1));
        gradeBook.addRecord(new AcademicRecord(
                "Physics", Grade.SATISFACTORY, AssessmentType.EXAM, 1));
        gradeBook.addRecord(new AcademicRecord(
                "Thesis", Grade.EXCELLENT, AssessmentType.QUALIFICATION_WORK, 4));

        assertFalse(gradeBook.isPossibleForHonorsDiploma());
    }

    @Test
    void testIsPossibleForHonorsDiploma_NotPossible_LowExcellentPercentage() {
        gradeBook.addRecord(new AcademicRecord(
                "Math", Grade.EXCELLENT, AssessmentType.EXAM, 1));
        gradeBook.addRecord(new AcademicRecord(
                "Physics", Grade.EXCELLENT, AssessmentType.EXAM, 1));
        gradeBook.addRecord(new AcademicRecord(
                "Programming", Grade.GOOD, AssessmentType.DIFFERENTIATED_CREDIT, 2));
        gradeBook.addRecord(new AcademicRecord(
                "Algorithms", Grade.GOOD, AssessmentType.EXAM, 2));
        gradeBook.addRecord(new AcademicRecord(
                "Thesis", Grade.EXCELLENT, AssessmentType.QUALIFICATION_WORK, 4));

        assertFalse(gradeBook.isPossibleForHonorsDiploma());
    }

    @Test
    void testIsPossibleForHonorsDiploma_Possible() {
        gradeBook.addRecord(new AcademicRecord(
                "Math", Grade.EXCELLENT, AssessmentType.EXAM, 1));
        gradeBook.addRecord(new AcademicRecord(
                "Physics", Grade.EXCELLENT, AssessmentType.EXAM, 1));
        gradeBook.addRecord(new AcademicRecord(
                "Programming", Grade.EXCELLENT, AssessmentType.DIFFERENTIATED_CREDIT, 2));
        gradeBook.addRecord(new AcademicRecord(
                "Algorithms", Grade.GOOD, AssessmentType.EXAM, 2)); 
        gradeBook.addRecord(new AcademicRecord(
                "Thesis", Grade.EXCELLENT, AssessmentType.QUALIFICATION_WORK, 4));

        assertTrue(gradeBook.isPossibleForHonorsDiploma());
    }

    @Test
    void testIsPossibleForIncreasedScholarship_NotBudget() {
        gradeBook.setCurrentSemester(2);
        gradeBook.addRecord(new AcademicRecord(
                "Math", Grade.EXCELLENT, AssessmentType.EXAM, 2));

        assertFalse(gradeBook.isPossibleForIncreasedScholarship());
    }

    @Test
    void testIsPossibleForIncreasedScholarship_NotAllExcellent() {
        ElectronicGradeBook budgetGradeBook = new ElectronicGradeBook(true);
        budgetGradeBook.setCurrentSemester(2);

        budgetGradeBook.addRecord(new AcademicRecord(
                "Math", Grade.EXCELLENT, AssessmentType.EXAM, 2));
        budgetGradeBook.addRecord(new AcademicRecord(
                "Physics", Grade.GOOD, AssessmentType.EXAM, 2));

        assertFalse(budgetGradeBook.isPossibleForIncreasedScholarship());
    }

    @Test
    void testIsPossibleForIncreasedScholarship_NoRecords() {
        ElectronicGradeBook budgetGradeBook = new ElectronicGradeBook(true);
        budgetGradeBook.setCurrentSemester(2);

        assertFalse(budgetGradeBook.isPossibleForIncreasedScholarship());
    }

    @Test
    void testIsPossibleForIncreasedScholarship_Possible() {
        ElectronicGradeBook budgetGradeBook = new ElectronicGradeBook(true);
        budgetGradeBook.setCurrentSemester(2);

        budgetGradeBook.addRecord(new AcademicRecord(
                "Math", Grade.EXCELLENT, AssessmentType.EXAM, 2));
        budgetGradeBook.addRecord(new AcademicRecord(
                "Physics", Grade.EXCELLENT, AssessmentType.DIFFERENTIATED_CREDIT, 2));

        assertTrue(budgetGradeBook.isPossibleForIncreasedScholarship());
    }

    @Test
    void testTransferToBudget_Successful() {
        gradeBook.setCurrentSemester(3);
        gradeBook.addRecord(new AcademicRecord(
                "Math", Grade.EXCELLENT, AssessmentType.EXAM, 2));

        assertTrue(gradeBook.transferToBudget());
        assertTrue(gradeBook.isBudgetForm());
    }

    @Test
    void testTransferToBudget_Unsuccessful() {
        gradeBook.setCurrentSemester(3);
        gradeBook.addRecord(new AcademicRecord(
                "Math", Grade.SATISFACTORY, AssessmentType.EXAM, 3));

        assertFalse(gradeBook.transferToBudget());
        assertFalse(gradeBook.isBudgetForm());
    }

    @Test
    void testAcademicRecordToString() {
        AcademicRecord record = new AcademicRecord(
                "Mathematics", Grade.EXCELLENT, AssessmentType.EXAM, 2);

        String result = record.toString();
        assertTrue(result.contains("Mathematics"));
        assertTrue(result.contains("EXCELLENT"));
        assertTrue(result.contains("EXAM"));
        assertTrue(result.contains("2"));
    }

    @Test
    void testGetRecords_ReturnsCopy() {
        AcademicRecord record = new AcademicRecord(
                "Math", Grade.EXCELLENT, AssessmentType.EXAM, 1);
        gradeBook.addRecord(record);

        var records = gradeBook.getRecords();
        assertEquals(1, records.size());

        records.clear(); // doesn't affect on the original List
        assertEquals(1, gradeBook.getRecords().size());
    }

    @Test
    void testGradeNumericValues() {
        assertEquals(5, Grade.EXCELLENT.getNumericValue());
        assertEquals(4, Grade.GOOD.getNumericValue());
        assertEquals(3, Grade.SATISFACTORY.getNumericValue());
    }

    @Test
    void testAcademicRecordAccessors() {
        AcademicRecord record = new AcademicRecord(
                "Computer Science", Grade.GOOD, AssessmentType.DIFFERENTIATED_CREDIT, 3);

        assertEquals("Computer Science", record.subject());
        assertEquals(Grade.GOOD, record.grade());
        assertEquals(AssessmentType.DIFFERENTIATED_CREDIT, record.type());
        assertEquals(3, record.semester());
    }
}
