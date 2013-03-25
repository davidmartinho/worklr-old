package pt.ist.worklr.domain;

public class RequestLabel extends RequestLabel_Base {

    public RequestLabel(String label) {
        setValue(label);
        setWorklr(Worklr.getInstance());
    }

    @Override
    public String toString() {
        return getValue();
    }

}
