package model.history;

/**
 * Created by Billie Devolder on 3/05/2017.
 */
public class B extends A {

    private String c;

    public B(String a, String b, String c) {
        super(a, b);
        this.c = c;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }
}
