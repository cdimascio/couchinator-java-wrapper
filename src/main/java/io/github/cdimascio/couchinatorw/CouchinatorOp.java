package io.github.cdimascio.couchinatorw;

public enum CouchinatorOp {
    CREATE("create"),
    RECREATE("recreate"),
    DESTROY("destroy");

    private final String op;

    CouchinatorOp(String op) {
        this.op = op;
    }

    public String getOp() {
        return this.op;
    }
}
