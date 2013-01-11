package pt.ist.worklr.domain;

public class DataObjectLabel extends DataObjectLabel_Base {

    public DataObjectLabel(String label) {
	setValue(label);
	setWorklr(Worklr.getInstance());
    }

}
