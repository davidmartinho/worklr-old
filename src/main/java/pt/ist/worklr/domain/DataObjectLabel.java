package pt.ist.worklr.domain;

public class DataObjectLabel extends DataObjectLabel_Base {

    public DataObjectLabel(String label) {
        setValue(label);
        setWorklr(Worklr.getInstance());
    }

    public static DataObjectLabel fromString(String labelString) {
        for (DataObjectLabel label : Worklr.getInstance().getDataObjectLabelSet()) {
            if (label.getValue().equals(labelString)) {
                return label;
            }
        }
        return new DataObjectLabel(labelString);
    }

    @Override
    public String toString() {
        return getValue();
    }

}
