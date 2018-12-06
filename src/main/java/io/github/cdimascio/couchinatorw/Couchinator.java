package io.github.cdimascio.couchinatorw;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Fixtures for CouchDB and IBM Cloudant databases
 * @see <a href="https://github.com/cdimascio/couchinator-java-wrapper">The complete documentation</a>
 */
public class Couchinator {
    private String url;
    private String resourcePath;
    private String npxPath;
    private String dbNamePrefix;
    private boolean ddocsOnly;

    /**
     * Create a new instance of couchinator
     * @param npxPath The full path to npx
     * @param url The url to CouchDB or IBM Cloudant. It should include the username and password if required.
     * @param resourcePath The path to the database fixtures. The file structure of fixtures should follow
     *                      that described in the Data Layout documentation found here
     * @param dbNamePrefix A prefix for each database name. A database name is prefix+db folder name
     * @param ddocsOnly update or create design docs only, leaving all other db records in tact
     * @see <a href="https://github.com/cdimascio/couchinator-java-wrapper">The complete documentation</a>
     */
    Couchinator(String npxPath, String url, String resourcePath, String dbNamePrefix, boolean ddocsOnly) {
        this.npxPath = npxPath;
        this.url = url;
        this.resourcePath = resourcePath;
        this.dbNamePrefix = dbNamePrefix;
        this.ddocsOnly = ddocsOnly;
    }

    /**
     * Configure a new Couchinator instance
     * @return
     */
    public static CouchinatorBuilder configure() {
        return new CouchinatorBuilder();
    }

    /**
     * Builds a new couchinator instance using defaults, whereby url = "http://localhost:5984", resources = "./fixtures",
     * affectDesignDocsOnly = false, npx = "npx"  and prefix = null
     * @return
     */
    public static Couchinator build() {
        return new CouchinatorBuilder().build();
    }

    /**
     * Creates the database fixtures, design docs, and records defined by the data layout.
     * @throws CouchinatorException when create fails
     * @see <a href="https://github.com/cdimascio/couchinator-java-wrapper#data-layout">Data Layout documentation</a>
     */
    public void create() throws CouchinatorException {
        run(CouchinatorOp.CREATE);
    }

    /**
     * re-Creates the database fixtures, design docs, and records defined by the data layout.. This method calls {@link #destroy()} followed by {@link #create()}
     * @see <a href="https://github.com/cdimascio/couchinator-java-wrapper#data-layout">Data Layout documentation</a>
     * @throws CouchinatorException when recreate fails
     */
    public void reCreate() throws CouchinatorException {
        run(CouchinatorOp.RECREATE);
    }

    /**
     * Creates the database fixtures, design docs, and records defined by the data layout.
     * @see <a href="https://github.com/cdimascio/couchinator-java-wrapper#data-layout">Data Layout documentation</a>
     * @throws CouchinatorException when delete fails
     */
    public void destroy() throws CouchinatorException {
        run(CouchinatorOp.DESTROY);
    }

    private Integer run(final CouchinatorOp op) throws CouchinatorException {
        ProcessBuilder p = new ProcessBuilder();
        List<String> command = new ArrayList<String>() {{
            add(npxPath);
            add("couchinator");
            add(op.getOp());
            add("--url");
            add(url);
            add("--path");
            add(resourcePath);
            add("--prefix");
            add(dbNamePrefix);
            add("--ddocsonly");
            add(Boolean.toString(ddocsOnly));
        }};

        try {
            Process process = p.command(command).start();
            process.waitFor();

            String output = convertStreamToString(process.getInputStream());
            String error = convertStreamToString(process.getErrorStream());
            System.out.println(output);
            System.out.println(error);

            int exitCode = process.exitValue();
            if (exitCode == 0) return 0;
            else throw new CouchinatorException(error);
        } catch (IOException | InterruptedException e) {
            throw new CouchinatorException(e);
        }
    }

    private String convertStreamToString(InputStream ins) {
        Scanner s = new Scanner(ins).useDelimiter("\\A");
        return (s.hasNext()) ? s.next() : "";
    }
}
