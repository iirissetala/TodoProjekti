package fi.academy;

public class Tehtava {
    private int id;
    private String tehtava;

    public Tehtava(){

    }
    public Tehtava (String tehtava){
        this.tehtava = tehtava;
    }

    public String getTehtava() {
        return tehtava;
    }

    public void setTehtava(String tehtava) {
        this.tehtava = tehtava;
    }

    public int getId () {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString () {
        return id + ": " + tehtava;
    }
}
