package pt.ist.worklr.domain;

import org.joda.time.DateTime;

public class DataObject extends DataObject_Base {

    public DataObject(DataObjectType type, DataObjectLabel label, Request requestCreationContext, User author) {
	setType(type);
	setLabel(label);
	setAuthor(author);
	setRequestCreationContext(requestCreationContext);
	setCreationTimestamp(new DateTime());
    }

}
