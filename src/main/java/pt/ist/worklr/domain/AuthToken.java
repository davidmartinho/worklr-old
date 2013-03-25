package pt.ist.worklr.domain;

import org.joda.time.DateTime;

import pt.ist.worklr.domain.exception.WorklrDomainException;
import pt.ist.worklr.utils.CryptoUtils;

public class AuthToken extends AuthToken_Base {

    public AuthToken(User user, String ipAddress) {
        setWorklr(Worklr.getInstance());
        setToken(CryptoUtils.generateKey());
        setUser(user);
        user.setActiveAuthToken(this);
        setIpAddress(ipAddress);
        setLoginTimestamp(new DateTime());
    }

    public static AuthToken getAuthToken(User user, String ipAddress) {
        if (user.getActiveAuthToken() != null) {
            if (user.getActiveAuthToken().getIpAddress().equals(ipAddress)) {
                if (user.getActiveAuthToken().isExpired()) {
                    throw WorklrDomainException.expiredAuthToken();
                } else {
                    return user.getActiveAuthToken();
                }
            } else {
                throw WorklrDomainException.invalidIpAddress();
            }
        } else {
            return new AuthToken(user, ipAddress);
        }
    }

    private boolean isExpired() {
        int twoHoursInMillis = 7200000;
        if (getLoginTimestamp().plus(twoHoursInMillis).isBeforeNow()) {
            logout();
            return true;
        } else {
            return false;
        }
    }

    public static AuthToken validateAuthToken(User user, String token, String ipAddress) {
        if (user.getActiveAuthToken() != null) {
            if (user.getActiveAuthToken().getToken().equals(token)) {
                if (user.getActiveAuthToken().getIpAddress().equals(ipAddress)) {
                    return user.getActiveAuthToken();
                } else {
                    user.getActiveAuthToken().logout();
                }
            }
        }
        return null;
    }

    public void logout() {
        User user = getUser();
        setLogoutTimestamp(new DateTime());
        user.removeActiveAuthToken();
        user.addInactiveAuthToken(this);
        Worklr.getInstance().removeActiveAuthToken(this);
        Worklr.getInstance().addInactiveAuthToken(this);
    }

}
