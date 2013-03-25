package pt.ist.worklr.domain;

public class Recommendation implements Comparable<Recommendation> {

    public static final double W_INPUT = 0.2;
    public static final double W_OUTPUT = 0.2;
    public static final double W_REQUEST = 0.2;
    public static final double W_PROCESS = 0.2;
    public static final double W_INITIATOR = 0.2;

    private final RequestTemplate requestTemplate;

    private final double goalMatch;
    private final double requestFitness;
    private final int support;

    private int order;

    public Recommendation(RequestTemplate requestTemplate, double goalMatch, double requestFitness, int support) {
        this.requestTemplate = requestTemplate;
        this.goalMatch = goalMatch;
        this.requestFitness = requestFitness;
        this.support = support;
        this.order = 0;
    }

    public RequestTemplate getRequestTemplate() {
        return requestTemplate;
    }

    public double getGoalMatch() {
        return goalMatch;
    }

    public double getRequestFitness() {
        return requestFitness;
    }

    public double getSupport() {
        return support;
    }

    @Override
    public int compareTo(Recommendation otherReq) {
        double goalMatchDiff = getGoalMatch() - otherReq.getGoalMatch();
        if (goalMatchDiff != 0) {
            return goalMatchDiff > 0 ? 1 : -1;
        } else {
            double requestFitnessDiff = getRequestFitness() - otherReq.getRequestFitness();
            if (requestFitnessDiff != 0) {
                return requestFitnessDiff > 0 ? 1 : -1;
            } else {
                return (getSupport() - otherReq.getSupport()) > 0 ? 1 : -1;
            }
        }
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
