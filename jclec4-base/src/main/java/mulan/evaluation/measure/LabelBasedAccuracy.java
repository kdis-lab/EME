package mulan.evaluation.measure;

public abstract class LabelBasedAccuracy extends LabelBasedBipartitionMeasureBase {

    /**
     * Constructs a new object with given number of labels
     *
     * @param numOfLabels the number of labels
     */
    public LabelBasedAccuracy(int numOfLabels) {
        super(numOfLabels);
    }

    @Override
    public double getIdealValue() {
        return 1;
    }

}
