import java.sql.Date;
import java.sql.Time;

public class Seans {
    private int seansId;
    private int filmId;
    private int salonId;
    private Date tarih;
    private Time saat;
    private double biletFiyati;

    public Seans() {}
    public Seans(int filmId, int salonId, Date tarih, Time saat, double biletFiyati) {
        this.filmId = filmId;
        this.salonId = salonId;
        this.tarih = tarih;
        this.saat = saat;
        this.biletFiyati = biletFiyati;
    }

    // Get-Set metotları
    public int getSeansId() { return seansId; }
    public void setSeansId(int seansId) { this.seansId = seansId; }
    public int getFilmId() { return filmId; }
    public void setFilmId(int filmId) { this.filmId = filmId; }
    public int getSalonId() { return salonId; }
    public void setSalonId(int salonId) { this.salonId = salonId; }
    public Date getTarih() { return tarih; }
    public void setTarih(Date tarih) { this.tarih = tarih; }
    public Time getSaat() { return saat; }
    public void setSaat(Time saat) { this.saat = saat; }
    public double getBiletFiyati() { return biletFiyati; }
    public void setBiletFiyati(double biletFiyati) { this.biletFiyati = biletFiyati; }
}