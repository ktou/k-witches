package kwitches.model;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import kwitches.model.ImageModel;

@Model(schemaVersion = 1)
public class UserModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
    private User user;
    
    private String name;
    
    private ModelRef<ImageModel> IconRef =
        new ModelRef<ImageModel>(ImageModel.class);
    
    private boolean isAuthUser;
    
    private String amazonAccount;

    /**
     * Returns the key.
     *
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key
     *            the key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Returns the version.
     *
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UserModel other = (UserModel) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

    /**
     * @param user セットする user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param name セットする name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param isAuthUser セットする isAuthUser
     */
    public void setAuthUser(boolean isAuthUser) {
        this.isAuthUser = isAuthUser;
    }

    /**
     * @return isAuthUser
     */
    public boolean isAuthUser() {
        return isAuthUser;
    }

    /**
     * @return iconRef
     */
    public ModelRef<ImageModel> getIconRef() {
        return IconRef;
    }

    /**
     * @param amazonAccount セットする amazonAccount
     */
    public void setAmazonAccount(String amazonAccount) {
        this.amazonAccount = amazonAccount;
    }

    /**
     * @return amazonAccount
     */
    public String getAmazonAccount() {
        return amazonAccount;
    }
}
