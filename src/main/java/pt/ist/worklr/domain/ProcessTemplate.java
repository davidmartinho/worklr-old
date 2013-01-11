package pt.ist.worklr.domain;

import org.joda.time.DateTime;

public class ProcessTemplate extends ProcessTemplate_Base {

    public ProcessTemplate(ProcessLabel processLabel, ProcessGoal processGoal) {
	setProcessGoal(processGoal);
	setCreationTimestamp(new DateTime());
    }

}
