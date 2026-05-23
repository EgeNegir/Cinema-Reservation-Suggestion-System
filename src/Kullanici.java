public class Kullanici {
    // Kapsülleme: Değişkenler private, erişim get/set ile
    private int kullaniciId;
    private String ad;
    private String soyad;
    private String email;
    private String sifre;

    // Boş Constructor (Veritabanından veri çekerken işe yarar)
    public Kullanici() {
    }

    // Dolu Constructor (Yeni kayıt oluştururken işe yarar)
    public Kullanici(String ad, String soyad, String email, String sifre) {
        this.ad = ad;
        this.soyad = soyad;
        this.email = email;
        this.sifre = sifre;
    }

    // GETTER VE SETTER METOTLARI
    public int getKullaniciId() { return kullaniciId; }
    public void setKullaniciId(int kullaniciId) { this.kullaniciId = kullaniciId; }

    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }

    public String getSoyad() { return soyad; }
    public void setSoyad(String soyad) { this.soyad = soyad; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSifre() { return sifre; }
    public void setSifre(String sifre) { this.sifre = sifre; }


}