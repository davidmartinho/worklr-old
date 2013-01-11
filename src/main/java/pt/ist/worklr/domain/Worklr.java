package pt.ist.worklr.domain;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.worklr.utils.CryptoUtils;

public class Worklr extends Worklr_Base {

    public Worklr() {
	createUser("David Martinho", "davidmartinho@gmail.com", "pass");
    }

    public AuthToken login(String email, String password, String ipAddress) {
	for (User user : getUserSet()) {
	    String passwordHash = CryptoUtils.calculatePasswordHash(password, user.getSalt());
	    if (user.getEmail().equals(email)) {
		System.out.println("User passhash: " + user.getPasswordHash());
		System.out.println("Computed Hash: " + passwordHash);
		if (user.getPasswordHash().equals(passwordHash)) {
		    return AuthToken.getAuthToken(user, ipAddress);
		} else {
		    return null;
		}
	    }
	}
	return null;
    }

    public static Worklr getInstance() {
	return FenixFramework.getRoot();
    }

    public ProcessLabel getProcessLabel(String processLabel) {
	for (ProcessLabel label : getProcessLabelSet()) {
	    if (label.getValue().equals(processLabel)) {
		return label;
	    }
	}
	ProcessLabel label = new ProcessLabel(processLabel);
	addProcessLabel(label);
	return label;
    }

    public RequestLabel getRequestLabel(String requestLabel) {
	for (RequestLabel label : getRequestLabelSet()) {
	    if (label.getValue().equals(requestLabel)) {
		return label;
	    }
	}
	RequestLabel label = new RequestLabel(requestLabel);
	addRequestLabel(label);
	return label;
    }

    public DataObjectLabel getDataObjectLabel(String dataObjectLabel) {
	for (DataObjectLabel label : getDataObjectLabelSet()) {
	    if (label.getValue().equals(dataObjectLabel)) {
		return label;
	    }
	}
	DataObjectLabel label = new DataObjectLabel(dataObjectLabel);
	addDataObjectLabel(label);
	return label;
    }

    /**
     * Creates a new process initiated by a particular user.
     * 
     * @param title
     *            the title of the process
     * @param initiator
     *            the user that initiates the process
     * @return
     */
    public Process createProcess(String title, User initiator) {
	ProcessLabel titleLabel = getProcessLabel(title);
	Process newProcess = new Process(titleLabel, initiator);
	addProcess(newProcess);
	return newProcess;
    }

    /**
     * Creates a new user, and his respective user queue.
     * 
     * @param name
     * @param email
     * @param password
     * @return
     */
    public User createUser(String name, String email, String password) {
	User newUser = new User(name, email, password);
	createUserQueue(newUser);
	addUser(newUser);
	return newUser;
    }

    /**
     * Creates a new role, and its respective role queue.
     * 
     * @param roleName
     *            the name of the role.
     * @return
     */
    public Role createRole(String roleName) {
	Role newRole = new Role(roleName);
	createRoleQueue(newRole);
	addRole(newRole);
	return newRole;
    }

    public RoleQueue createRoleQueue(Role role) {
	RoleQueue newRoleQueue = new RoleQueue(role);
	addQueue(newRoleQueue);
	return newRoleQueue;
    }

    public UserQueue createUserQueue(User user) {
	UserQueue newUserQueue = new UserQueue(user);
	addQueue(newUserQueue);
	return newUserQueue;
    }

    /**
     * Obtains a user given his email address.
     * 
     * @param email
     *            the email of the user to retrieve
     * @return the user associated to the provided email address
     */
    public User getUserByEmail(String email) {
	for (User user : getUserSet()) {
	    if (user.getEmail().equals(email)) {
		return user;
	    }
	}
	return null;
    }

}
