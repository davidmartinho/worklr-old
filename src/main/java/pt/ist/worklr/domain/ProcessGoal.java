package pt.ist.worklr.domain;

import java.util.Set;

import com.google.common.collect.Sets;

public class ProcessGoal extends ProcessGoal_Base {

    public ProcessGoal(Set<DataObjectLabel> dataObjectLabelSet) {
        for (DataObjectLabel dataObjectLabel : dataObjectLabelSet) {
            addDataObjectLabel(dataObjectLabel);
        }
        setWorklr(Worklr.getInstance());
    }

    public double getGoalMatch(Process process) {
        Set<DataObjectLabel> intersectionDataObjectLabelSet =
                Sets.intersection(process.getDataObjectLabelSet(), getDataObjectLabelSet());
        Set<DataObjectLabel> additionalDataObjectLabelSet =
                Sets.difference(process.getDataObjectLabelSet(), getDataObjectLabelSet());
        int intersectionSize = intersectionDataObjectLabelSet.size();
        int additionalSize = additionalDataObjectLabelSet.size();
        return (intersectionSize * 1.0) / (intersectionSize + additionalSize);
    }

    public Set<DataObjectLabel> getMissingDataObjectLabelSet(Process process) {
        return Sets.difference(getDataObjectLabelSet(), process.getDataObjectLabelSet());
    }

    public static void identifyAndAssociateTo(Process process) {
        for (ProcessGoal processGoal : Worklr.getInstance().getProcessGoalSet()) {
            if (processGoal.getDataObjectLabelSet().equals(process.getDataObjectLabelSet())) {
                process.setAchievedGoal(processGoal);
                return;
            }
        }
        ProcessGoal newProcessGoal = new ProcessGoal(process.getDataObjectLabelSet());
        process.setAchievedGoal(newProcessGoal);
    }
}
