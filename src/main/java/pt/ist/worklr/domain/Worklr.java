package pt.ist.worklr.domain;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.worklr.domain.exception.WorklrDomainException;
import pt.ist.worklr.utils.CryptoUtils;

public class Worklr extends Worklr_Base {

    public void init() {
        Role phdStudent = createRole("PhD Student");
        Role mscStudent = createRole("MSc Student");
        Role deiProfessor = createRole("DEI Professor");

        Set<Role> davidRoles = new HashSet<Role>();
        davidRoles.add(phdStudent);
        createAdmin("David Martinho", "davidmartinho@gmail.com", "pass", davidRoles);
        Set<Role> ritoRoles = new HashSet<Role>();
        ritoRoles.add(deiProfessor);
        createUser("António Rito Silva", "rito.silva@ist.utl.pt", "pass", ritoRoles);
        Set<Role> pauloRoles = new HashSet<Role>();
        pauloRoles.add(mscStudent);
        createUser("Paulo Pires", "paulo.pires@ist.utl.pt", "pass", pauloRoles);
        /**
         * Process p = new Process("Gestão da Tese", "Processo que visa gerir a tese do David Martinho", david);
         * Request rootRequest = p.getRootRequest();
         * 
         * Set<DataObject> inputDataObjectSet = new HashSet<DataObject>();
         * inputDataObjectSet.add(new DataObject(DataObjectType.STRING, "Documento da CAT", rootRequest,
         * david));
         * inputDataObjectSet
         * .add(new DataObject(
         * DataObjectType.FILE,
         * "Ficheiro XPTO",
         * rootRequest,
         * david,
         * "http://www.younglandscapearchitects.org/sites/default/files/styles/colorbox_style/public/EFLA%20competition%20-%20file%20and%20page%20format.pdf"
         * ));
         * 
         * Set<Queue> queueSet = new HashSet<Queue>();
         * queueSet.add(rito.getUserQueue());
         * Request r =
         * rootRequest.createSubRequest("Corrigir Documento CAT",
         * "Professor, pode por favor corrigir o documento da CAT? Obrigado.", david, queueSet, inputDataObjectSet);
         * 
         * rito.getUserQueue().addPublishedRequest(r);
         * rito.claim(r);
         **/
    }

    public AuthToken login(String email, String password, String ipAddress) {
        if (email.isEmpty() || password.isEmpty()) {
            throw WorklrDomainException.invalidCredentials();
        }
        for (User user : getUserSet()) {
            String passwordHash = CryptoUtils.calculatePasswordHash(password, user.getSalt());
            if (user.getEmail().equals(email)) {
                if (user.getPasswordHash().equals(passwordHash)) {
                    return AuthToken.getAuthToken(user, ipAddress);
                } else {
                    throw WorklrDomainException.invalidCredentials();
                }
            }
        }
        throw WorklrDomainException.invalidCredentials();
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
        if (StringUtils.isEmpty(requestLabel)) {
            throw WorklrDomainException.subjectCannotBeEmpty();
        } else {
            for (RequestLabel label : getRequestLabelSet()) {
                if (label.getValue().equals(requestLabel)) {
                    return label;
                }
            }
            RequestLabel label = new RequestLabel(requestLabel);
            addRequestLabel(label);
            return label;
        }
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
     * Creates a new user, and his respective user queue.
     * 
     * @param name
     * @param email
     * @param password
     * @return
     */
    public User createUser(String name, String email, String password, Set<Role> roleSet) {
        User existingUser = getUserByEmail(email);
        if (existingUser != null) {
            throw WorklrDomainException.emailAlreadyInUse();
        }
        User newUser = new User(name, email, password, roleSet);
        createUserQueue(newUser);
        return newUser;
    }

    public User createAdmin(String name, String email, String password, Set<Role> roleSet) {
        User newUser = createUser(name, email, password, roleSet);
        newUser.setAdmin(true);
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

    public Set<Process> getOngoingProcessSet(User requestingUser) {
        if (requestingUser.isAdmin()) {
            Set<Process> ongoingProcessSet = new HashSet<Process>();
            for (Process process : this.getProcessSet()) {
                if (!process.isCompleted())
                    ongoingProcessSet.add(process);
            }
            return ongoingProcessSet;
        } else {
            throw WorklrDomainException.forbiddenResource();
        }
    }

    public Set<Process> getCompletedProcessSet(User requestingUser) {
        if (requestingUser.isAdmin()) {
            Set<Process> completedProcessSet = new HashSet<Process>();
            for (Process process : this.getProcessSet()) {
                if (process.isCompleted())
                    completedProcessSet.add(process);
            }
            return completedProcessSet;
        } else {
            throw WorklrDomainException.forbiddenResource();
        }
    }

    public Set<Process> getProcessSet(User requestingUser) {
        if (requestingUser.isAdmin()) {
            return getProcessSet();
        } else {
            throw WorklrDomainException.forbiddenResource();
        }
    }

}
