
/**
 * Enum representing different types of academic assessment.
 */
public enum AssessmentType {
    /**
     * Represents an examination assessment type.
     */
    EXAM,

    /**
     * Represents a differentiated credit assessment type.
     * Differentiated credits have grade distinctions (excellent, good, satisfactory).
     */
    DIFFERENTIATED_CREDIT,

    /**
     * Represents a regular (non-differentiated) credit assessment type.
     * Regular credits are pass/fail only (e.g., physical education).
     */
    CREDIT,

    /**
     * Represents qualification work such as thesis or diploma project.
     */
    QUALIFICATION_WORK
}