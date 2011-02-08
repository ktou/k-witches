package kwitches.model;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class UploadedDataFragment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    private int index;
    
    @Attribute(lob = true)
    private byte[] bytes;
    
    private ModelRef<UploadedData> uploadedDataRef =
        new ModelRef<UploadedData>(UploadedData.class);
    
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
        UploadedDataFragment other = (UploadedDataFragment) obj;
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
     * @param index セットする index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param bytes セットする bytes
     */
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * @return bytes
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * @return uploadedDataRef
     */
    public ModelRef<UploadedData> getUploadedDataRef() {
        return uploadedDataRef;
    }

}
