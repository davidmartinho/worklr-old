package pt.ist.worklr.domain;

import org.joda.time.DateTime;

import pt.ist.worklr.domain.exception.WorklrDomainException;

public class DataObject extends DataObject_Base {

    public DataObject(DataObjectType type, String label, Request requestCreationContext, User author) {
        setType(type);
        setLabel(Worklr.getInstance().getDataObjectLabel(label));
        setRequestCreationContext(requestCreationContext);
        setAuthor(author);
        setCreationTimestamp(new DateTime());
        requestCreationContext.updateTemplate();
    }

    public DataObject(DataObjectType type, String label, Request requestCreationContext, User author, String value) {
        this(type, label, requestCreationContext, author);
        setValue(value);
    }

    public void updateValue(String value, User author) {
        if (canWrite(author)) {
            setValue(value);
        } else {
            throw WorklrDomainException.forbiddenResource();
        }
    }

    private boolean canWrite(User writer) {
        return hasAuthor(writer);
    }

    private boolean hasAuthor(User user) {
        return hasAuthor() && getAuthor().equals(user);
    }

    @Override
    public String toString() {
        return getLabel().toString();
    }

}
