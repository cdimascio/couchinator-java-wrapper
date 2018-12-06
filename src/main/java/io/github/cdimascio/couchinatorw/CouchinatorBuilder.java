package io.github.cdimascio.couchinatorw;

public class CouchinatorBuilder {

    private String npx = "npx";
    private String resources = "./fixtures";
    private String url = "http://localhost:5984";
    private String prefix = "";
    private boolean affectDesignDocsOnly = false;

    CouchinatorBuilder() {}

    public CouchinatorBuilder url(String url) {
        this.url = url;
        return this;
    }

    public CouchinatorBuilder resources(String resourcePath) {
        this.resources = resourcePath;
        return this;
    }

    /**
     * Determines whether create and/or update affects only design docs or design docs and db records
     * @param affectDesignDocsOnly true to affect design docs only, false to affect all db records, including design docs
     * @return this builder
     */
    public CouchinatorBuilder affectDesignDocsOnly(boolean affectDesignDocsOnly) {
        this.affectDesignDocsOnly = affectDesignDocsOnly;
        return this;
    }

    /**
     * Prepends a prefix to each each database name. By default, the name of each database is the folder name. Prefix
     * enables one to change the database name, by prepending this prefix
     * @param prefix The prefix
     * @return this builder
     */
    public CouchinatorBuilder prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * Set the full path to npx. By default, Couchinator expects npx to be on the PATH
     * @param absolutePath
     * @return this instance
     */
    public CouchinatorBuilder npx(String absolutePath) {
        npx = absolutePath;
        return this;
    }

    /**
     * Builds a new instance of Couchinator
     * @return this instance
     */
    public Couchinator build() {
        return new Couchinator(npx, url, resources, prefix, affectDesignDocsOnly);
    }
}
