public interface ISubject {
    void aboneEkle(IObserver observer);
    void aboneCikar(IObserver observer);
    void abonelereBildir(Film yeniFilm);
}