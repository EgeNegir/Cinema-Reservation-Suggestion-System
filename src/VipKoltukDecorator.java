public class VipKoltukDecorator extends BiletDecorator {

    //Bu sınıflar ana BiletDecoratordan kalıtım alarak içindeki biletin fiyatına ve açıklamasına müdahale eden asıl sınıf
    public VipKoltukDecorator(IBilet bilet) { super(bilet); }
    @Override
    public String getAciklama() { return bilet.getAciklama() + ", VIP Koltuk"; }
    @Override
    public double getUcret() { return bilet.getUcret() + 50.0; }
}