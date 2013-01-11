package pt.ist.worklr.domain;

public enum DataObjectType {

    STRING("string"), FILE("file");

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

}
