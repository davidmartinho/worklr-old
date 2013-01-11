package pt.ist.worklr.domain;

import org.joda.time.DateTime;

public class Commentary extends Commentary_Base {

    public Commentary(String text, User author) {
	setText(text);
	setAuthor(author);
	setTimestamp(new DateTime());
    }

}
