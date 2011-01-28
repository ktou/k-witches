package kwitches.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-01-04 23:50:13")
/** */
public final class UserModelMeta extends org.slim3.datastore.ModelMeta<kwitches.model.UserModel> {

    /** */
    public final org.slim3.datastore.ModelRefAttributeMeta<kwitches.model.UserModel, org.slim3.datastore.ModelRef<kwitches.model.ImageModel>, kwitches.model.ImageModel> IconRef = new org.slim3.datastore.ModelRefAttributeMeta<kwitches.model.UserModel, org.slim3.datastore.ModelRef<kwitches.model.ImageModel>, kwitches.model.ImageModel>(this, "IconRef", "IconRef", org.slim3.datastore.ModelRef.class, kwitches.model.ImageModel.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<kwitches.model.UserModel, java.lang.Boolean> authUser = new org.slim3.datastore.CoreAttributeMeta<kwitches.model.UserModel, java.lang.Boolean>(this, "authUser", "authUser", boolean.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<kwitches.model.UserModel, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<kwitches.model.UserModel, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<kwitches.model.UserModel> name = new org.slim3.datastore.StringAttributeMeta<kwitches.model.UserModel>(this, "name", "name");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<kwitches.model.UserModel, com.google.appengine.api.users.User> user = new org.slim3.datastore.CoreAttributeMeta<kwitches.model.UserModel, com.google.appengine.api.users.User>(this, "user", "user", com.google.appengine.api.users.User.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<kwitches.model.UserModel, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<kwitches.model.UserModel, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final UserModelMeta slim3_singleton = new UserModelMeta();

    /**
     * @return the singleton
     */
    public static UserModelMeta get() {
       return slim3_singleton;
    }

    /** */
    public UserModelMeta() {
        super("UserModel", kwitches.model.UserModel.class);
    }

    @Override
    public kwitches.model.UserModel entityToModel(com.google.appengine.api.datastore.Entity entity) {
        kwitches.model.UserModel model = new kwitches.model.UserModel();
        if (model.getIconRef() == null) {
            throw new NullPointerException("The property(IconRef) is null.");
        }
        model.getIconRef().setKey((com.google.appengine.api.datastore.Key) entity.getProperty("IconRef"));
        model.setAuthUser(booleanToPrimitiveBoolean((java.lang.Boolean) entity.getProperty("authUser")));
        model.setKey(entity.getKey());
        model.setName((java.lang.String) entity.getProperty("name"));
        model.setUser((com.google.appengine.api.users.User) entity.getProperty("user"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        kwitches.model.UserModel m = (kwitches.model.UserModel) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        if (m.getIconRef() == null) {
            throw new NullPointerException("The property(IconRef) must not be null.");
        }
        entity.setProperty("IconRef", m.getIconRef().getKey());
        entity.setProperty("authUser", m.isAuthUser());
        entity.setProperty("name", m.getName());
        entity.setProperty("user", m.getUser());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        kwitches.model.UserModel m = (kwitches.model.UserModel) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        kwitches.model.UserModel m = (kwitches.model.UserModel) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        kwitches.model.UserModel m = (kwitches.model.UserModel) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
        kwitches.model.UserModel m = (kwitches.model.UserModel) model;
        if (m.getIconRef() == null) {
            throw new NullPointerException("The property(IconRef) must not be null.");
        }
        m.getIconRef().assignKeyIfNecessary(ds);
    }

    @Override
    protected void incrementVersion(Object model) {
        kwitches.model.UserModel m = (kwitches.model.UserModel) model;
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
        kwitches.model.UserModel m = (kwitches.model.UserModel) model;
        writer.beginObject();
        org.slim3.datastore.json.JsonCoder encoder = null;
        if(m.getIconRef() != null && m.getIconRef().getKey() != null){
            writer.setNextPropertyName("IconRef");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getIconRef(), maxDepth, currentDepth);
        }
        writer.setNextPropertyName("authUser");
        encoder = new org.slim3.datastore.json.Default();
        encoder.encode(writer, m.isAuthUser());
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getKey());
        }
        if(m.getName() != null){
            writer.setNextPropertyName("name");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getName());
        }
        if(m.getUser() != null){
            writer.setNextPropertyName("user");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getUser());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getVersion());
        }
        writer.endObject();
    }

    @Override
    public kwitches.model.UserModel jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        kwitches.model.UserModel m = new kwitches.model.UserModel();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.JsonCoder decoder = null;
        reader = rootReader.newObjectReader("IconRef");
        decoder = new org.slim3.datastore.json.Default();
        decoder.decode(reader, m.getIconRef(), maxDepth, currentDepth);
        reader = rootReader.newObjectReader("authUser");
        decoder = new org.slim3.datastore.json.Default();
        m.setAuthUser(decoder.decode(reader, m.isAuthUser()));
        reader = rootReader.newObjectReader("key");
        decoder = new org.slim3.datastore.json.Default();
        m.setKey(decoder.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("name");
        decoder = new org.slim3.datastore.json.Default();
        m.setName(decoder.decode(reader, m.getName()));
        reader = rootReader.newObjectReader("user");
        decoder = new org.slim3.datastore.json.Default();
        m.setUser(decoder.decode(reader, m.getUser()));
        reader = rootReader.newObjectReader("version");
        decoder = new org.slim3.datastore.json.Default();
        m.setVersion(decoder.decode(reader, m.getVersion()));
    return m;
}
}