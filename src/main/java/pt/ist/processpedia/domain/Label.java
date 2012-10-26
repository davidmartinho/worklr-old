package pt.ist.processpedia.domain;

import org.joda.time.DateTime;

public class Label extends Label_Base {
    
    public Label(String label, User author) {
        setProcesspedia(Processpedia.getInstance());
        setLabel(label);
        setAuthor(author);
        setCreationTimestamp(new DateTime());
    }
}
