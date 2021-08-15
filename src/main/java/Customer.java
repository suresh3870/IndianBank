public class Customer {
    private String cname;
    private int accno;
    private int pass;
    private long bal;

    public Customer(String cname, int accno, int pass, long bal) {
        this.cname = cname;
        this.accno = accno;
        this.pass = pass;
        this.bal = bal;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getAccno() {
        return accno;
    }

    public void setAccno(int accno) {
        this.accno = accno;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public long getBal() {
        return bal;
    }

    public void setBal(long bal) {
        this.bal = bal;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cname='" + cname + '\'' +
                ", accno=" + accno +
                ", pass=" + pass +
                ", bal=" + bal +
                '}';
    }
}
