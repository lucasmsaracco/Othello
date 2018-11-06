package juego;

/**
 * Juego Reversi
 * 
 * Reglas:
 * 
 * https://es.wikipedia.org/wiki/Reversi https://es.wikihow.com/jugar-a-Othello
 * 
 */

public class Reversi {

	/**
	 * pre : 'dimension' es un número par, mayor o igual a 4. post: empieza el
	 * juego entre el jugador que tiene fichas negras, identificado como
	 * 'fichasNegras' y el jugador que tiene fichas blancas, identificado como
	 * 'fichasBlancas'. El tablero tiene 4 fichas: 2 negras y 2 blancas. Estas
	 * fichas están intercaladas en el centro del tablero.
	 * 
	 * @param dimensionTablero
	 *            : cantidad de filas y columnas que tiene el tablero.
	 * @param fichasNegras
	 *            : nombre del jugador con fichas negras.
	 * @param fichasBlancas
	 *            : nombre del jugador con fichas blancas.
	 */
	private Casillero tablero[][];
	private String jugadorConFichasNegras;
	private String jugadorConFichasBlancas;
	private int fichasColocadas = 0;
	private int fichasNegras = 3;
	private int fichasBlancas = 2;
	private String ganador;
	private int fichasEnTotal = 0;

	public Reversi(int dimensionTablero, String fichasNegras,
			String fichasBlancas) {

		if ((dimensionTablero >= 4) && (dimensionTablero % 2 == 0)
				&& (dimensionTablero <= 10)) {

			tablero = new Casillero[dimensionTablero][dimensionTablero];

			jugadorConFichasNegras = fichasNegras;
			jugadorConFichasBlancas = fichasBlancas;
			fichasEnTotal = dimensionTablero * dimensionTablero;

			for (int i = 0; i < tablero.length; i++) {

				for (int j = 0; j < tablero[i].length; j++) {

					tablero[i][j] = Casillero.LIBRE;

				}

			}
			tablero[(dimensionTablero / 2) +1][(dimensionTablero / 2)] = Casillero.NEGRAS;
			tablero[(dimensionTablero / 2)][(dimensionTablero / 2)] = Casillero.NEGRAS;
			tablero[(dimensionTablero / 2) - 1][(dimensionTablero / 2) - 1] = Casillero.NEGRAS;
			tablero[(dimensionTablero / 2)][(dimensionTablero / 2) - 1] = Casillero.BLANCAS;
			tablero[(dimensionTablero / 2) - 1][(dimensionTablero / 2)] = Casillero.BLANCAS;

		} else {

			Error dimensionInvalida = new Error(
					"La dimensión del tablero no es correcta capo, ingrese una dimensión válida o te cogemos por virgo!");

			throw dimensionInvalida;

		}

	}

	/**
	 * post: devuelve la cantidad de filas que tiene el tablero.
	 */
	public int contarFilas() {

		return tablero.length;
	}

	/**
	 * post: devuelve la cantidad de columnas que tiene el tablero.
	 */
	public int contarColumnas() {

		return tablero.length;

	}

	/**
	 * post: devuelve el nombre del jugador que debe colocar una ficha o null si
	 * terminó el juego.
	 */
	public String obtenerJugadorActual() {

		if (fichasColocadas % 2 == 0) {

			return jugadorConFichasNegras;
		} else {

			return jugadorConFichasBlancas;
		}

	}

	/**
	 * pre : fila está en el intervalo [1, contarFilas()], columnas está en el
	 * intervalo [1, contarColumnas()]. post: indica quién tiene la posesión del
	 * casillero dado por fila y columna.
	 * 
	 * @param fila
	 * @param columna
	 */
	public Casillero obtenerCasillero(int fila, int columna) {

		return tablero[fila - 1][columna - 1];

	}

	public boolean puedeColocarFicha(int fila, int columna) {

		boolean sePuedeColocar = false;

		if (tablero[fila - 1][columna - 1] == Casillero.LIBRE){

			sePuedeColocar = true;

		}

		return sePuedeColocar;
	}

	/**
	 * pre : la posición indicada por (fila, columna) puede ser ocupada por una
	 * ficha. 'fila' está en el intervalo [1, contarFilas()]. 'columna' está en
	 * el intervalor [1, contarColumnas()]. y aún queda un Casillero.VACIO en la
	 * columna indicada. post: coloca una ficha en la posición indicada.
	 * 
	 * @param fila
	 * @param columna
	 */
	public void colocarFicha(int fila, int columna) {

		if (puedeColocarFicha(fila, columna) == true) {

			if (obtenerJugadorActual() == jugadorConFichasNegras) {

				tablero[fila - 1][columna - 1] = Casillero.NEGRAS;
				fichasNegras++;

			} else if (obtenerJugadorActual() == jugadorConFichasBlancas) {

				tablero[fila - 1][columna - 1] = Casillero.BLANCAS;
				fichasBlancas++;
			}

			fichasColocadas++;

		}

	}

	/**
	 * post: devuelve la cantidad de fichas negras en el tablero.
	 */
	public int contarFichasNegras() {

		return fichasNegras;
	}

	/**
	 * post: devuelve la cantidad de fichas blancas en el tablero.
	 */
	public int contarFichasBlancas() {

		return fichasBlancas;
	}

	/**
	 * post: indica si el juego terminó porque no existen casilleros vacíos o
	 * ninguno de los jugadores puede colocar una ficha.
	 */
	public boolean termino() {

		if (fichasEnTotal == fichasBlancas + fichasNegras) {

			return true;

		} else {
			
			return false;
			
		}
		
	}

	/**
	 * post: indica si el juego terminó y tiene un ganador.
	 */
	public boolean hayGanador() {

		if (termino() == true) {

			return true;

		} else {

			return false;

		}
	}

	/**
	 * pre : el juego terminó. post: devuelve el nombre del jugador que ganó el
	 * juego.
	 */
	public String obtenerGanador() {

		if (fichasBlancas > fichasNegras) {

			ganador = jugadorConFichasBlancas;

		} else if (fichasNegras > fichasBlancas) {

			ganador = jugadorConFichasNegras;
		}

		return ganador;
	}

}
