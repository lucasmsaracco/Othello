package juego;
 
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
 
/**
 * Acci�n ejecutada al presionar un bot�n para colocar deshacer el �ltimo turno
 *
 */
public class volverTurnoAnterior implements EventHandler<ActionEvent> {
 
    private Tablero tablero;
    private Reversi juego;
 
    /**
     * post: Vuelve al turno anterior
     *
     * @param tableroReversi
     * @param reversi
     */
    public volverTurnoAnterior(Tablero tableroReversi, Reversi reversi) {
 
        juego = reversi;
        tablero = tableroReversi;
    }
 
    @Override
    public void handle(ActionEvent evento) {
        juego.volverTurnoAnterior();
        tablero.dibujar();
 
        if (juego.termino()) {
 
            tablero.mostrarResultado();
        }
    }
}