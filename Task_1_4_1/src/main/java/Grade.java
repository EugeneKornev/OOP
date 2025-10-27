/**
 * Enum representing possible grades in the grade book.
 */
public enum Grade {
    /**
     * Excellent grade with numeric value 5.
     */
    EXCELLENT(5),

    /**
     * Good grade with numeric value 4.
     */
    GOOD(4),

    /**
     * Satisfactory grade with numeric value 3.
     */
    SATISFACTORY(3),

    /**
     * Pass grade for non-differentiated credits (no numeric value).
     */
    PASS(0),

    /**
     * Fail grade for non-differentiated credits (no numeric value).
     */
    FAIL(0);

    private final int numericValue;

    /**
     * Constructs a Grade enum with the specified numeric value.
     *
     * @param numericValue the numeric representation of the grade
     */
    Grade(int numericValue) {
        this.numericValue = numericValue;
    }

    /**
     * Returns the numeric value associated with this grade.
     *
     * @return the numeric value of the grade
     * @throws UnsupportedOperationException if called for PASS or FAIL grades
     */
    public int getNumericValue() {
        if (this == PASS || this == FAIL) {
            throw new UnsupportedOperationException(
                    "PASS and FAIL grades do not have numeric values. "
                            + "Use hasNumericValue() to check before calling this method."
            );
        }
        return numericValue;
    }

    /**
     * Checks if this grade has a numeric value.
     *
     * @return true if the grade has numeric value (EXCELLENT, GOOD, SATISFACTORY), false otherwise
     */
    public boolean hasNumericValue() {
        return this != PASS && this != FAIL;
    }

    /**
     * Checks if this grade represents a passing grade.
     *
     * @return true for PASS, EXCELLENT, GOOD, SATISFACTORY; false for FAIL
     */
    public boolean isPassingGrade() {
        return this != FAIL;
    }
}