package pt.ist.worklr.domain;

public enum DataObjectType {

    TEXT("text"), FILE("file");

    private String type;

    private DataObjectType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return this.type;
    }

    public static DataObjectType fromString(String text) {
        if (text != null) {
            for (DataObjectType dataObjectType : DataObjectType.values()) {
                if (text.equalsIgnoreCase(dataObjectType.type)) {
                    return dataObjectType;
                }
            }
        }
        return null;
    }

}
