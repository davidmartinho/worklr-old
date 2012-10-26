package pt.ist.processpedia.domain;

public class Tag extends Tag_Base {
    
    public Tag(Label label, User author) {
        setLabel(label);
        setAuthor(author);
    }
}
