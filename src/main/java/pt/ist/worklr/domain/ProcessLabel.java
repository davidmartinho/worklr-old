package pt.ist.worklr.domain;

public class ProcessLabel extends ProcessLabel_Base {

    public ProcessLabel(String processLabel) {
	setValue(processLabel);
	setWorklr(Worklr.getInstance());
    }

}
