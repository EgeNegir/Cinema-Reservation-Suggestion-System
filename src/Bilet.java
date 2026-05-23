import java.sql.Timestamp;

public class Bilet {
    private int biletId;
    private int musteriId;
    private int seansId;
    private String koltukNo;
    private Timestamp islemTarihi;

    public Bilet() {}
    public Bilet(int musteriId, int seansId, String koltukNo) {
        this.musteriId = musteriId;
        this.seansId = seansId;
        this.koltukNo = koltukNo;
    }

    // GetSet Metotları
    public int getBiletId() { return biletId; }
    public void setBiletId(int biletId) { this.biletId = biletId; }
    public int getMusteriId() { return musteriId; }
    public void setMusteriId(int musteriId) { this.musteriId = musteriId; }
    public int getSeansId() { return seansId; }
    public void setSeansId(int seansId) { this.seansId = seansId; }
    public String getKoltukNo() { return koltukNo; }
    public void setKoltukNo(String koltukNo) { this.koltukNo = koltukNo; }
    public Timestamp getIslemTarihi() { return islemTarihi; }
    public void setIslemTarihi(Timestamp islemTarihi) { this.islemTarihi = islemTarihi; }
}