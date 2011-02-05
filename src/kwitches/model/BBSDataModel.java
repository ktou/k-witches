package kwitches.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class BBSDataModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    /** レス番号 */
    private int id;
    /** ユーザ */
    private ModelRef<UserModel> UserModelRef =
        new ModelRef<UserModel>(UserModel.class);
    /** 書き込み日付 */
    private Date createdDate;
    /** 書き込み元IPアドレス */
    private String ipAddress;
    /** コメント */
    private String comment;
    /** コメント */
    @Attribute(lob = true)
    private String longComment;
    /** ファイル */
    private Blob file;
    /** 検索用転置インデックス */
    private List<String> invertedIndex;
    /** 書き込み名 */
    private String name;
    /** アイコンへの参照 */
    private ModelRef<ImageModel> iconRef =
        new ModelRef<ImageModel>(ImageModel.class);

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
        BBSDataModel other = (BBSDataModel) obj;
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
     * @param id セットする id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @param ipAddress セットする ipAddress
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * @return ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param comment セットする comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param createdDate セットする createdDate
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param file セットする file
     */
    public void setFile(Blob file) {
        this.file = file;
    }

    /**
     * @return file
     */
    public Blob getFile() {
        return file;
    }

    /**
     * @return userModelRef
     */
    public ModelRef<UserModel> getUserModelRef() {
        return UserModelRef;
    }

    /**
     * @param invertedIndex セットする invertedIndex
     */
    public void setInvertedIndex(List<String> invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    /**
     * @return invertedIndex
     */
    public List<String> getInvertedIndex() {
        return invertedIndex;
    }

    /**
     * @param longComment セットする longComment
     */
    public void setLongComment(String longComment) {
        this.longComment = longComment;
    }

    /**
     * @return longComment
     */
    public String getLongComment() {
        return longComment;
    }
    
    public String getBBSComment() {
        return comment != null ? comment : longComment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        UserModel model = getUserModelRef().getModel();
        return name != null ? name : model != null ? model.getName() : "null";
    }

    public ModelRef<ImageModel> getIconRef() {
        UserModel model = getUserModelRef().getModel();
        return iconRef != null && iconRef.getModel() != null ? iconRef : model != null ? model.getIconRef() : null;
    }

}
