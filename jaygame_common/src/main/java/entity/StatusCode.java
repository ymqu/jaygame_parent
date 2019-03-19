package entity;

public class StatusCode {
    public static final int OK=20000;
    public static final int ERROR=20001;
    public static final int LOGINERROR=20002;
    public static final int ACCESSERROR=20003; //Not enough auth
    public static final int REMOTEERROR=20004;  //remote fail
    public static final int REPERROR=20005; //repeat ops
    public static final int JWTEXPIRED=20006;
}
