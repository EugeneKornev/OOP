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
    SATISFACTORY(3);

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
     */
    public int getNumericValue() {
        return numericValue;
    }
}