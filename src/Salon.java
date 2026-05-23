public class Salon {
    private int salonId;
    private String salonAdi;
    private int kapasite;

    public Salon() {}
    public Salon(String salonAdi, int kapasite) {
        this.salonAdi = salonAdi;
        this.kapasite = kapasite;
    }

    public int getSalonId() { return salonId; }
    public void setSalonId(int salonId) { this.salonId = salonId; }
    public String getSalonAdi() { return salonAdi; }
    public void setSalonAdi(String salonAdi) { this.salonAdi = salonAdi; }
    public int getKapasite() { return kapasite; }
    public void setKapasite(int kapasite) { this.kapasite = kapasite; }
}