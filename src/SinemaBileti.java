public class SinemaBileti implements IBilet {
    private double tabanFiyat;
    //saf fiyatı tutan temel nesnedir constructor

    public SinemaBileti(double tabanFiyat) {
        this.tabanFiyat = tabanFiyat; //bileti ilk olusturdugumuz anda veritabanından cektigimiz gercek seans fiyatını bu nesnenin icine enjekte etmemizi saglar
    }
    //kendisinden aciklama ve ücret istendiginde saf durumuyla hicbir şey eklemeden kendi degerlerini disari firlatir
    @Override
    public String getAciklama() {
        return "Sinema Bileti";
    }

    @Override
    public double getUcret() {
        return tabanFiyat;
    }
}