public class Film {
    private int filmId;
    private String filmAdi;
    private int sure;
    private String yonetmen;
    private String tur;
    private boolean vizyondaMi;

    // 1. Boş Kurucu Metot
    public Film() {}


    public Film(String filmAdi, int sure, String yonetmen) {
        this.filmAdi = filmAdi;
        this.sure = sure;
        this.yonetmen = yonetmen;
    }


    public Film(String filmAdi, int sure, String yonetmen, String tur, boolean vizyondaMi) {
        this.filmAdi = filmAdi;
        this.sure = sure;
        this.yonetmen = yonetmen;
        this.tur = tur;
        this.vizyondaMi = vizyondaMi;
    }

    // GETTER VE SETTER METOTLARI

    public int getFilmId() { return filmId; }
    public void setFilmId(int filmId) { this.filmId = filmId; }

    public String getFilmAdi() { return filmAdi; }
    public void setFilmAdi(String filmAdi) { this.filmAdi = filmAdi; }

    public int getSure() { return sure; }
    public void setSure(int sure) { this.sure = sure; }

    public String getYonetmen() { return yonetmen; }
    public void setYonetmen(String yonetmen) { this.yonetmen = yonetmen; }

    public String getTur() { return tur; }
    public void setTur(String tur) { this.tur = tur; }

    public boolean isVizyondaMi() { return vizyondaMi; }
    public void setVizyondaMi(boolean vizyondaMi) { this.vizyondaMi = vizyondaMi; }
}