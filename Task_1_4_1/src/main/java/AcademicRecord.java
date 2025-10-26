/**
 * Represents a single academic record (grade for a subject).
 * Immutable class to ensure data integrity.
 */
public record AcademicRecord(String subject, Grade grade, AssessmentType type, int semester) {

    @Override
    public String toString() {
        return String.format("AcademicRecord{subject='%s', grade=%s, type=%s, semester=%d}",
                subject, grade, type, semester);
    }

}