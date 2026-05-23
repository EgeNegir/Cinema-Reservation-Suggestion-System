public class Musteri extends Kullanici {
    private String telefonNo;

    public Musteri() {
    }

    public Musteri(String ad, String soyad, String email, String sifre, String telefonNo) {
        super(ad, soyad, email, sifre); // Üst sınıfın constructor'ını çağırır
        this.telefonNo = telefonNo;
    }

    public String getTelefonNo() { return telefonNo; }
    public void setTelefonNo(String telefonNo) { this.telefonNo = telefonNo; }
}