package kwitches.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-01-04 23:50:14")
/** */
public final class BBSDataModelMeta extends org.slim3.datastore.ModelMeta<kwitches.model.BBSDataModel> {

    /** */
    public final org.slim3.datastore.ModelRefAttributeMeta<kwitches.model.BBSDataModel, org.slim3.datastore.ModelRef<kwitches.model.UserModel>, kwitches.model.UserModel> UserModelRef = new org.slim3.datastore.ModelRefAttributeMeta<kwitches.model.BBSDataModel, org.slim3.datastore.ModelRef<kwitches.model.UserModel>, kwitches.model.UserModel>(this, "UserModelRef", "UserModelRef", org.slim3.datastore.ModelRef.class, kwitches.model.UserModel.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<kwitches.model.BBSDataModel> comment = new org.slim3.datastore.StringAttributeMeta<kwitches.model.BBSDataModel>(this, "comment", "comment");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<kwitches.model.BBSDataModel, java.util.Date> createdDate = new org.slim3.datastore.CoreAttributeMeta<kwitches.model.BBSDataModel, java.util.Date>(this, "createdDate", "createdDate", java.util.Date.class);

    /** */
    public final org.slim3.datastore.UnindexedAttributeMeta<kwitches.model.BBSDataModel, com.google.appengine.api.datastore.Blob> file = new org.slim3.datastore.UnindexedAttributeMeta<kwitches.model.BBSDataModel, com.google.appengine.api.datastore.Blob>(this, "file", "file", com.google.appengine.api.datastore.Blob.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<kwitches.model.BBSDataModel, java.lang.Integer> id = new org.slim3.datastore.CoreAttributeMeta<kwitches.model.BBSDataModel, java.lang.Integer>(this, "id", "id", int.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<kwitches.model.BBSDataModel> ipAddress = new org.slim3.datastore.StringAttributeMeta<kwitches.model.BBSDataModel>(this, "ipAddress", "ipAddress");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<kwitches.model.BBSDataModel, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<kwitches.model.BBSDataModel, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<kwitches.model.BBSDataModel, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<kwitches.model.BBSDataModel, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final BBSDataModelMeta slim3_singleton = new BBSDataModelMeta();

    /**
     * @return the singleton
     */
    public static BBSDataModelMeta get() {
       return slim3_singleton;
    }

    /** */
    public BBSDataModelMeta() {
        super("BBSDataModel", kwitches.model.BBSDataModel.class);
    }

    @Override
    public kwitches.model.BBSDataModel entityToModel(com.google.appengine.api.datastore.Entity entity) {
        kwitches.model.BBSDataModel model = new kwitches.model.BBSDataModel();
        if (model.getUserModelRef() == null) {
            throw new NullPointerException("The property(UserModelRef) is null.");
        }
        model.getUserModelRef().setKey((com.google.appengine.api.datastore.Key) entity.getProperty("UserModelRef"));
        model.setComment((java.lang.String) entity.getProperty("comment"));
        model.setCreatedDate((java.util.Date) entity.getProperty("createdDate"));
        model.setFile((com.google.appengine.api.datastore.Blob) entity.getProperty("file"));
        model.setId(longToPrimitiveInt((java.lang.Long) entity.getProperty("id")));
        model.setIpAddress((java.lang.String) entity.getProperty("ipAddress"));
        model.setKey(entity.getKey());
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        kwitches.model.BBSDataModel m = (kwitches.model.BBSDataModel) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        if (m.getUserModelRef() == null) {
            throw new NullPointerException("The property(UserModelRef) must not be null.");
        }
        entity.setProperty("UserModelRef", m.getUserModelRef().getKey());
        entity.setProperty("comment", m.getComment());
        entity.setProperty("createdDate", m.getCreatedDate());
        entity.setProperty("file", m.getFile());
        entity.setProperty("id", m.getId());
        entity.setProperty("ipAddress", m.getIpAddress());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        kwitches.model.BBSDataModel m = (kwitches.model.BBSDataModel) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        kwitches.model.BBSDataModel m = (kwitches.model.BBSDataModel) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        kwitches.model.BBSDataModel m = (kwitches.model.BBSDataModel) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
        kwitches.model.BBSDataModel m = (kwitches.model.BBSDataModel) model;
        if (m.getUserModelRef() == null) {
            throw new NullPointerException("The property(UserModelRef) must not be null.");
        }
        m.getUserModelRef().assignKeyIfNecessary(ds);
    }

    @Override
    protected void incrementVersion(Object model) {
        kwitches.model.BBSDataModel m = (kwitches.model.BBSDataModel) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
    }

    @Override
    public String getSchemaVersionName() {
        return "slim3.schemaVersion";
    }

    @Override
    public String getClassHierarchyListName() {
        return "slim3.classHierarchyList";
    }

    @Override
    protected boolean isCipherProperty(String propertyName) {
        return false;
    }

    @Override
    protected void modelToJson(org.slim3.datastore.json.JsonWriter writer, java.lang.Object model, int maxDepth, int currentDepth) {
        kwitches.model.BBSDataModel m = (kwitches.model.BBSDataModel) model;
        writer.beginObject();
        org.slim3.datastore.json.JsonCoder encoder = null;
        if(m.getUserModelRef() != null && m.getUserModelRef().getKey() != null){
            writer.setNextPropertyName("UserModelRef");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getUserModelRef(), maxDepth, currentDepth);
        }
        if(m.getComment() != null){
            writer.setNextPropertyName("comment");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getComment());
        }
        if(m.getCreatedDate() != null){
            writer.setNextPropertyName("createdDate");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getCreatedDate());
        }
        if(m.getFile() != null && m.getFile().getBytes() != null){
            writer.setNextPropertyName("file");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getFile());
        }
        writer.setNextPropertyName("id");
        encoder = new org.slim3.datastore.json.Default();
        encoder.encode(writer, m.getId());
        if(m.getIpAddress() != null){
            writer.setNextPropertyName("ipAddress");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getIpAddress());
        }
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getKey());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getVersion());
        }
        writer.endObject();
    }

    @Override
    public kwitches.model.BBSDataModel jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        kwitches.model.BBSDataModel m = new kwitches.model.BBSDataModel();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.JsonCoder decoder = null;
        reader = rootReader.newObjectReader("UserModelRef");
        decoder = new org.slim3.datastore.json.Default();
        decoder.decode(reader, m.getUserModelRef(), maxDepth, currentDepth);
        reader = rootReader.newObjectReader("comment");
        decoder = new org.slim3.datastore.json.Default();
        m.setComment(decoder.decode(reader, m.getComment()));
        reader = rootReader.newObjectReader("createdDate");
        decoder = new org.slim3.datastore.json.Default();
        m.setCreatedDate(decoder.decode(reader, m.getCreatedDate()));
        reader = rootReader.newObjectReader("file");
        decoder = new org.slim3.datastore.json.Default();
        m.setFile(decoder.decode(reader, m.getFile()));
        reader = rootReader.newObjectReader("id");
        decoder = new org.slim3.datastore.json.Default();
        m.setId(decoder.decode(reader, m.getId()));
        reader = rootReader.newObjectReader("ipAddress");
        decoder = new org.slim3.datastore.json.Default();
        m.setIpAddress(decoder.decode(reader, m.getIpAddress()));
        reader = rootReader.newObjectReader("key");
        decoder = new org.slim3.datastore.json.Default();
        m.setKey(decoder.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("version");
        decoder = new org.slim3.datastore.json.Default();
        m.setVersion(decoder.decode(reader, m.getVersion()));
    return m;
}
}