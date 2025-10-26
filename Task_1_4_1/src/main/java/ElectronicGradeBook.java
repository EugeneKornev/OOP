import java.util.ArrayList;
import java.util.List;

/**
 * Represents an electronic grade book for FIT students.
 * Tracks student's academic performance and provides various academic calculations.
 */
public class ElectronicGradeBook {
    private final List<AcademicRecord> records;
    private boolean isBudgetForm;
    private int currentSemester;

    /**
     * Constructs an electronic grade book.
     * @param isBudgetForm initial form of education (true for budget, false for paid)
     */
    public ElectronicGradeBook(boolean isBudgetForm) {
        this.records = new ArrayList<>();
        this.isBudgetForm = isBudgetForm;
        this.currentSemester = 1;
    }


    /**
     * Adds an academic record to the grade book.
     * @param record the academic record to add
     */
    public void addRecord(AcademicRecord record) {
        records.add(record);
    }

    /**
     * Sets the current semester.
     * @param semester current semester number
     */
    public void setCurrentSemester(int semester) {
        this.currentSemester = semester;
    }

    /**
     * Calculates the current average grade across all semesters.
     * @return current average grade as double
     */
    public double calculateCurrentAverage() {
        if (records.isEmpty()) {
            return 0.0;
        }

        double sum = records.stream()
                .filter(record -> record.type() != AssessmentType.QUALIFICATION_WORK)
                .mapToInt(record -> record.grade().getNumericValue())
                .sum();

        long count = records.stream()
                .filter(record -> record.type() != AssessmentType.QUALIFICATION_WORK)
                .count();

        return count > 0 ? sum / count : 0.0;
    }

    /**
     * Checks if student is possible to transfer from paid to budget form of education.
     * @return true if Possible for transfer, false otherwise
     */
    public boolean isPossibleForBudgetTransfer() {
        if (isBudgetForm) {
            return false;
        }

        if (currentSemester < 2) {
            return false; // Need at least two semesters
        }

        int lastSemester = currentSemester;
        int previousSemester = currentSemester - 1;

        boolean hasSatisfactoryInLastTwoSessions = records.stream()
                .filter(record -> record.type() == AssessmentType.EXAM)
                .filter(record -> record.semester() == lastSemester
                        || record.semester() == previousSemester)
                .anyMatch(record -> record.grade() == Grade.SATISFACTORY);

        return !hasSatisfactoryInLastTwoSessions;
    }

    /**
     * Checks if student is Possible for honors diploma (red diploma).
     * @return true if Possible for honors diploma, false otherwise
     */
    public boolean isPossibleForHonorsDiploma() {
        if (records.isEmpty()) {
            return false;
        }

        boolean hasExcellentQualificationWork = records.stream()
                .filter(record -> record.type() == AssessmentType.QUALIFICATION_WORK)
                .anyMatch(record -> record.grade() == Grade.EXCELLENT);

        if (!hasExcellentQualificationWork) {
            return false;
        }

        List<AcademicRecord> finalAssessments = records.stream()
                .filter(record -> record.type() == AssessmentType.EXAM ||
                        record.type() == AssessmentType.DIFFERENTIATED_CREDIT)
                .toList();

        if (finalAssessments.isEmpty()) {
            return false;
        }

        boolean hasSatisfactory = finalAssessments.stream()
                .anyMatch(record -> record.grade() == Grade.SATISFACTORY);

        if (hasSatisfactory) {
            return false;
        }

        long excellentCount = finalAssessments.stream()
                .filter(record -> record.grade() == Grade.EXCELLENT)
                .count();

        double excellentPercentage = (double) excellentCount / finalAssessments.size();

        return excellentPercentage >= 0.75;
    }

    /**
     * Checks if student is Possible for increased scholarship in current semester.
     * @return true if Possible for increased scholarship, false otherwise
     */
    public boolean isPossibleForIncreasedScholarship() {
        if (!isBudgetForm) {
            return false;
        }

        List<AcademicRecord> currentSemesterRecords = records.stream()
                .filter(record -> record.semester() == currentSemester)
                .toList();

        if (currentSemesterRecords.isEmpty()) {
            return false;
        }

        return currentSemesterRecords.stream()
                .allMatch(record -> record.grade() == Grade.EXCELLENT);
    }

    /**
     * Transfers student to budget form of education if Possible.
     * @return true if transfer was successful, false otherwise
     */
    public boolean transferToBudget() {
        if (isPossibleForBudgetTransfer()) {
            isBudgetForm = true;
            return true;
        }
        return false;
    }

    public List<AcademicRecord> getRecords() { return new ArrayList<>(records); }
    public boolean isBudgetForm() { return isBudgetForm; }
    public int getCurrentSemester() { return currentSemester; }
}