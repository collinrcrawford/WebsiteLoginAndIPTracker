import java.util.Date;
import java.net.InetAddress;


public class User {
    public static void main(String[] args) {

    }
    private String username;
    private Date lastLogin;
    private String location;

    public User(String username, Date lastLogin, String location) {
        this.username = username;
        this.lastLogin = lastLogin;
        this.location = location;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLastLogin() {
        return this.lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static class loginTracker {
        private User[] users;
        private int numUsers;
        private IpGeolocationService geolocationService;

        public loginTracker(IpGeolocationService geolocationService) {
            this.users = new User[10];
            this.numUsers = 0;
            this.geolocationService = geolocationService;
        }

        public void addUser(String username, InetAddress ipAddress) {
            String location = geolocationService.getLocation(ipAddress);
            User user = new User(username, new Date(), location);
            addUser(user);
        }

        public void addUser(User user) {
            if (numUsers == users.length) {
                // Increase the size of the array
                User[] newArray = new User[users.length * 2];
                System.arraycopy(users, 0, newArray, 0, users.length);
                users = newArray;
            }
            users[numUsers++] = user;
        }

        public User getUser(String username) {
            for (int i = 0; i < numUsers; i++) {
                if (users[i].getUsername().equals(username)) {
                    return users[i];
                }
            }
            return null;
        }

        public void updateLogin(String username, InetAddress ipAddress) {
            String location = geolocationService.getLocation(ipAddress);
            updateLogin(username, new Date(), location);
        }

        public void updateLogin(String username, Date lastLogin, String location) {
            User user = getUser(username);
            if (user != null) {
                user.setLastLogin(lastLogin);
                user.setLocation(location);
            }
        }
    }
}

interface IpGeolocationService {
    String getLocation(InetAddress ipAddress);
}

