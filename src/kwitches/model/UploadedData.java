package kwitches.model;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.Sort;

@Model(schemaVersion = 1)
public class UploadedData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    private String fileName;

    private int length;

    @Attribute(persistent = false)
    private InverseModelListRef<UploadedDataFragment, UploadedData> fragmentListRef =
        new InverseModelListRef<UploadedDataFragment, UploadedData>(
            UploadedDataFragment.class,
            "uploadedDataRef",
            this,
            new Sort("index"));
    
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
        UploadedData other = (UploadedData) obj;
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
     * @param length セットする length
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param fileName セットする fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return fragmentListRef
     */
    public InverseModelListRef<UploadedDataFragment, UploadedData> getFragmentListRef() {
        return fragmentListRef;
    }
}
