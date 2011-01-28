package kwitches.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-01-04 23:50:14")
/** */
public final class ImageModelMeta extends org.slim3.datastore.ModelMeta<kwitches.model.ImageModel> {

    /** */
    public final org.slim3.datastore.StringAttributeMeta<kwitches.model.ImageModel> fileContentType = new org.slim3.datastore.StringAttributeMeta<kwitches.model.ImageModel>(this, "fileContentType", "fileContentType");

    /** */
    public final org.slim3.datastore.UnindexedAttributeMeta<kwitches.model.ImageModel, com.google.appengine.api.datastore.Blob> fileImage = new org.slim3.datastore.UnindexedAttributeMeta<kwitches.model.ImageModel, com.google.appengine.api.datastore.Blob>(this, "fileImage", "fileImage", com.google.appengine.api.datastore.Blob.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<kwitches.model.ImageModel> filename = new org.slim3.datastore.StringAttributeMeta<kwitches.model.ImageModel>(this, "filename", "filename");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<kwitches.model.ImageModel, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<kwitches.model.ImageModel, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<kwitches.model.ImageModel, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<kwitches.model.ImageModel, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final ImageModelMeta slim3_singleton = new ImageModelMeta();

    /**
     * @return the singleton
     */
    public static ImageModelMeta get() {
       return slim3_singleton;
    }

    /** */
    public ImageModelMeta() {
        super("ImageModel", kwitches.model.ImageModel.class);
    }

    @Override
    public kwitches.model.ImageModel entityToModel(com.google.appengine.api.datastore.Entity entity) {
        kwitches.model.ImageModel model = new kwitches.model.ImageModel();
        model.setFileContentType((java.lang.String) entity.getProperty("fileContentType"));
        model.setFileImage((com.google.appengine.api.datastore.Blob) entity.getProperty("fileImage"));
        model.setFilename((java.lang.String) entity.getProperty("filename"));
        model.setKey(entity.getKey());
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        kwitches.model.ImageModel m = (kwitches.model.ImageModel) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("fileContentType", m.getFileContentType());
        entity.setProperty("fileImage", m.getFileImage());
        entity.setProperty("filename", m.getFilename());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        kwitches.model.ImageModel m = (kwitches.model.ImageModel) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        kwitches.model.ImageModel m = (kwitches.model.ImageModel) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        kwitches.model.ImageModel m = (kwitches.model.ImageModel) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        kwitches.model.ImageModel m = (kwitches.model.ImageModel) model;
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
        kwitches.model.ImageModel m = (kwitches.model.ImageModel) model;
        writer.beginObject();
        org.slim3.datastore.json.JsonCoder encoder = null;
        if(m.getFileContentType() != null){
            writer.setNextPropertyName("fileContentType");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getFileContentType());
        }
        if(m.getFileImage() != null && m.getFileImage().getBytes() != null){
            writer.setNextPropertyName("fileImage");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getFileImage());
        }
        if(m.getFilename() != null){
            writer.setNextPropertyName("filename");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getFilename());
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
    public kwitches.model.ImageModel jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        kwitches.model.ImageModel m = new kwitches.model.ImageModel();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.JsonCoder decoder = null;
        reader = rootReader.newObjectReader("fileContentType");
        decoder = new org.slim3.datastore.json.Default();
        m.setFileContentType(decoder.decode(reader, m.getFileContentType()));
        reader = rootReader.newObjectReader("fileImage");
        decoder = new org.slim3.datastore.json.Default();
        m.setFileImage(decoder.decode(reader, m.getFileImage()));
        reader = rootReader.newObjectReader("filename");
        decoder = new org.slim3.datastore.json.Default();
        m.setFilename(decoder.decode(reader, m.getFilename()));
        reader = rootReader.newObjectReader("key");
        decoder = new org.slim3.datastore.json.Default();
        m.setKey(decoder.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("version");
        decoder = new org.slim3.datastore.json.Default();
        m.setVersion(decoder.decode(reader, m.getVersion()));
    return m;
}
}