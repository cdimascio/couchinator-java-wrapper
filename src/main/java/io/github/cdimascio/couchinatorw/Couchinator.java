package io.github.cdimascio.couchinatorw;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Couchinator {
    private String url;
    private String resourcePath;
    private String npxPath;

    public Couchinator(String npxPath, String url, String resourcePath) {
        this.npxPath = npxPath;
        this.url = url;
        this.resourcePath = resourcePath;
    }

    public Couchinator(String url, String resourcePath) {
        this.url = url;
        this.resourcePath = resourcePath;
        this.npxPath = "npx";
    }

    public void create() throws CouchinatorException {
        run(CouchinatorOp.CREATE);
    }

    public void reCreate() throws CouchinatorException {
        run(CouchinatorOp.RECREATE);
    }

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
