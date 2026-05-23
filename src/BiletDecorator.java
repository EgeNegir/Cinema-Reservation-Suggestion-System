public abstract class BiletDecorator implements IBilet { //tek basına anlamsız olacagından abstract
    protected IBilet bilet;
    public BiletDecorator(IBilet bilet) { this.bilet = bilet; }
    // Bu kutu oluşturulurken içine  sarmalayacağı bir bileti parametre olarak almak zorundadır

    //Kendisinden bir şey istendiğinde hiçbir şeye dokunmadan görevi direkt içindeki bilete devreder
    public String getAciklama() { return bilet.getAciklama(); }
    public double getUcret() { return bilet.getUcret(); }
}


//bir biletin üzerine özellik ekleyecek olan tüm özellkiklerin atası
// implements bilet sistem tarafından bir Bilet olarak algılanmalıdır ki başka kutuların içine de girebilsin