package entidades.powerups;

public interface PowerUpVisitor {
    void visitar(Moneda moneda);
    void visitar(FlorDeFuego florDeFuego);
    void visitar(SuperChampi superChampi);
    void visitar(ChampiVerde champiVerde);
    void visitar(Estrella estrella);
}
