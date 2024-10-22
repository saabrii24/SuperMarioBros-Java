package entidades.powerups;

public interface PowerUpVisitor {
    void visitar(Moneda moneda);
    void visitar(FlorDeFuego flor_de_fuego);
    void visitar(SuperChampi super_champi);
    void visitar(ChampiVerde champi_verde);
    void visitar(Estrella estrella);
}
