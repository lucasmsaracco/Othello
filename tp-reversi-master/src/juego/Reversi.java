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
 
    // Inicializa las variables globales
    int turnoActual = 1;
    String negras;
    String blancas;
    Casillero[][] tablero;
    Casillero[][] turnoAnterior;
    Casillero[][] tableroInicial;
    int tamaño;
    int fichasBlancas;
    int fichasNegras;
    int izquierda;
    int derecha;
    int arriba;
    int abajo;
    String turno = null;
    Casillero jugador;
    Casillero oponente;
    boolean imposibleMover = false;
    boolean mismoTablero = true;
 
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
 
    public Reversi(int dimensionTablero, String fichasNegras,
            String fichasBlancas) {
        if (((dimensionTablero % 2) == 0) && (dimensionTablero >= 4)
                && (dimensionTablero <= 10)) {
            tablero = new Casillero[dimensionTablero + 2][dimensionTablero + 2];
            turnoAnterior = new Casillero[dimensionTablero + 2][dimensionTablero + 2];
            negras = (fichasNegras);
            blancas = (fichasBlancas);
            tamaño = tablero.length - 2;
            for (int i = 0; i < tamaño; i++) {
                for (int j = 0; j < tamaño; j++) {
                    tablero[i + 1][j + 1] = Casillero.LIBRE;
                }
            }
            turno = negras;
            jugador = Casillero.NEGRAS;
            oponente = Casillero.BLANCAS;
 
            // Dibuja en el tablero la posición de las fichas iniciales
            tablero[(dimensionTablero / 2) + 1][(dimensionTablero / 2) + 1] = Casillero.NEGRAS;
            tablero[(dimensionTablero / 2)][(dimensionTablero / 2)] = Casillero.NEGRAS;
            tablero[(dimensionTablero / 2) + 1][(dimensionTablero / 2)] = Casillero.BLANCAS;
            tablero[(dimensionTablero / 2)][(dimensionTablero / 2) + 1] = Casillero.BLANCAS;
            tableroInicial = new Casillero[dimensionTablero + 2][dimensionTablero + 2];
            copiarTablero(tablero, tableroInicial);
 
        } else {
            Error tamañoErroneo = new Error(
                    "El tablero debe ser PAR y mayor a 4 y menor a 10");
            throw tamañoErroneo;
        }
 
    }
 
    /**
     * post: devuelve la cantidad de filas que tiene el tablero.
     */
    public int contarFilas() {
        return tamaño;
    }
 
    /**
     * post: devuelve la cantidad de columnas que tiene el tablero.
     */
    public int contarColumnas() {
        return tamaño;
    }
 
    /**
     * post: devuelve el nombre del jugador que debe colocar una ficha o null si
     * terminó el juego.
     */
    public String obtenerJugadorActual() {
        String jugadorActual;
        if ((turnoActual % 2) == 1) {
            jugadorActual = negras;
        } else {
            jugadorActual = blancas;
        }
 
        return jugadorActual;
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
        return tablero[fila][columna];
    }
 
    public boolean puedeColocarFicha(int fila, int columna) {
 
        boolean sePuedeColocar = false;
 
        if (tablero[fila][columna] == Casillero.LIBRE) {
            /*
             * En caso de que el casillero esté libre, busca que haya alguna
             * ficha del oponente adyacente. En caso de que esto suceda, procede
             * a buscar en la misma dirección hasta encontrar una ficha del
             * jugador.
             */
            if (detectarDerecha(fila, columna) != 0) {
                sePuedeColocar = true;
            } else if (detectarIzquierda(fila, columna) != 0) {
                sePuedeColocar = true;
            } else if (detectarAbajo(fila, columna) != 0) {
                sePuedeColocar = true;
            } else if (detectarArriba(fila, columna) != 0) {
                sePuedeColocar = true;
            } else if (detectarNoreste(fila, columna) != 0) {
                sePuedeColocar = true;
            } else if (detectarNoroeste(fila, columna) != 0) {
                sePuedeColocar = true;
            } else if (detectarSudoeste(fila, columna) != 0) {
                sePuedeColocar = true;
            } else if (detectarSudeste(fila, columna) != 0) {
                sePuedeColocar = true;
            }
        }
 
        return sePuedeColocar;
    }
 
    /**
     * pre: Hay una ficha del oponente adyacente a la ficha colocada en este
     * turno
     *
     * post: Cuenta la cantidad de fichas del oponente en la misma dirección, y
     * si al final hay una ficha del jugador actual, devuelve la cantidad de
     * fichas totales.
     *
     * @param fila
     * @param columna
     * @return
     */
    private int detectarDerecha(int fila, int columna) {
        int contadorFinal = 0;
        if (obtenerCasillero(fila + 1, columna) == oponente) {
            int contador = 1;
            while (obtenerCasillero(fila + contador, columna) == oponente) {
                contador++;
            }
            if (obtenerCasillero(fila + contador, columna) == jugador) {
                contadorFinal = contador;
            }
        }
        return contadorFinal;
 
    }
 
    private int detectarSudeste(int fila, int columna) {
        int contadorFinal = 0;
        if (obtenerCasillero(fila + 1, columna + 1) == oponente) {
            int contador = 1;
            while (obtenerCasillero(fila + contador, columna + contador) == oponente) {
                contador++;
            }
            if (obtenerCasillero(fila + contador, columna + contador) == jugador) {
                contadorFinal = contador;
            }
        }
        return contadorFinal;
    }
 
    private int detectarSudoeste(int fila, int columna) {
        int contadorFinal = 0;
        if (obtenerCasillero(fila - 1, columna + 1) == oponente) {
            int contador = 1;
            while (obtenerCasillero(fila - contador, columna + contador) == oponente) {
                contador++;
            }
            if (obtenerCasillero(fila - contador, columna + contador) == jugador) {
                contadorFinal = contador;
            }
        }
        return contadorFinal;
    }
 
    private int detectarNoreste(int fila, int columna) {
        int contadorFinal = 0;
        if (obtenerCasillero(fila + 1, columna - 1) == oponente) {
            int contador = 1;
            while (obtenerCasillero(fila + contador, columna - contador) == oponente) {
                contador++;
            }
            if (obtenerCasillero(fila + contador, columna - contador) == jugador) {
                contadorFinal = contador;
 
            }
        }
        return contadorFinal;
    }
 
    private int detectarNoroeste(int fila, int columna) {
        int contadorFinal = 0;
        if (obtenerCasillero(fila - 1, columna - 1) == oponente) {
            int contador = 1;
            while (obtenerCasillero(fila - contador, columna - contador) == oponente) {
                contador++;
            }
            if (obtenerCasillero(fila - contador, columna - contador) == jugador) {
                contadorFinal = contador;
            }
        }
        return contadorFinal;
    }
 
    private int detectarIzquierda(int fila, int columna) {
        int contadorFinal = 0;
        if (obtenerCasillero(fila - 1, columna) == oponente) {
            int contador = 1;
            while (obtenerCasillero(fila - contador, columna) == oponente) {
                contador++;
            }
            if (obtenerCasillero(fila - contador, columna) == jugador) {
                contadorFinal = contador;
            }
        }
        return contadorFinal;
    }
 
    private int detectarArriba(int fila, int columna) {
        int contadorFinal = 0;
        if (obtenerCasillero(fila, columna - 1) == oponente) {
            int contador = 1;
            while (obtenerCasillero(fila, columna - contador) == oponente) {
                contador++;
            }
            if (obtenerCasillero(fila, columna - contador) == jugador) {
                contadorFinal = contador;
            }
        }
        return contadorFinal;
    }
 
    private int detectarAbajo(int fila, int columna) {
        int contadorFinal = 0;
        if (obtenerCasillero(fila, columna + 1) == oponente) {
            int contador = 1;
            while (obtenerCasillero(fila, columna + contador) == oponente) {
                contador++;
            }
            if (obtenerCasillero(fila, columna + contador) == jugador) {
                contadorFinal = contador;
            }
        }
        return contadorFinal;
    }
 
    /**
     * pre: Se pudo detectar una ficha oponente adyacente a la ficha colocada en
     * este turno
     *
     * post: Convierte todas las fichas entre la ficha actual colocada y una
     * ficha del mismo color.
     *
     * @param fila
     * @param columna
     * @param contador
     */
    private void realizarOperacionDerecha(int fila, int columna, int contador) {
        for (int i = 1; i < contador; i++) {
            tablero[fila + i][columna] = jugador;
        }
    }
 
    private void realizarOperacionSudeste(int fila, int columna, int contador) {
        for (int i = 1; i < contador; i++) {
            tablero[fila + i][columna + i] = jugador;
        }
    }
 
    private void realizarOperacionSudoeste(int fila, int columna, int contador) {
        for (int i = 1; i < contador; i++) {
            tablero[fila - i][columna + i] = jugador;
        }
    }
 
    private void realizarOperacionNoreste(int fila, int columna, int contador) {
        for (int i = 1; i < contador; i++) {
            tablero[fila + i][columna - i] = jugador;
        }
    }
 
    private void realizarOperacionNoroeste(int fila, int columna, int contador) {
        for (int i = 1; i < contador; i++) {
            tablero[fila - i][columna - i] = jugador;
        }
    }
 
    private void realizarOperacionIzquierda(int fila, int columna, int contador) {
        for (int i = 1; i < contador; i++) {
            tablero[fila - i][columna] = jugador;
        }
    }
 
    private void realizarOperacionArriba(int fila, int columna, int contador) {
        for (int i = 1; i < contador; i++) {
            tablero[fila][columna - i] = jugador;
        }
    }
 
    private void realizarOperacionAbajo(int fila, int columna, int contador) {
        for (int i = 1; i < contador; i++) {
            tablero[fila][columna + i] = jugador;
        }
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
        copiarTablero(tablero, turnoAnterior);
        tablero[fila][columna] = jugador;
        int contador;
        /*
         * Primero intenta buscar una ficha oponente adyacente a la del jugador
         * actual. En caso de que al final de las fichas oponentes haya alguna
         * del jugador actual, convierte todas las fichas que estan entre ellas
         * en las del jugador actual
         */
        if ((contador = detectarDerecha(fila, columna)) != 0) {
            realizarOperacionDerecha(fila, columna, contador);
        }
        if ((contador = detectarIzquierda(fila, columna)) != 0) {
            realizarOperacionIzquierda(fila, columna, contador);
        }
        if ((contador = detectarArriba(fila, columna)) != 0) {
            realizarOperacionArriba(fila, columna, contador);
        }
        if ((contador = detectarAbajo(fila, columna)) != 0) {
            realizarOperacionAbajo(fila, columna, contador);
        }
        if ((contador = detectarSudeste(fila, columna)) != 0) {
            realizarOperacionSudeste(fila, columna, contador);
        }
        if ((contador = detectarNoreste(fila, columna)) != 0) {
            realizarOperacionNoreste(fila, columna, contador);
        }
 
        if ((contador = detectarNoroeste(fila, columna)) != 0) {
            realizarOperacionNoroeste(fila, columna, contador);
        }
        if ((contador = detectarSudoeste(fila, columna)) != 0) {
            realizarOperacionSudoeste(fila, columna, contador);
        }
 
        // Cambia de turno
        cambiarTurno();
 
    }
 
    /**
     * pre: Lee el tablero, buscando si se puede colocar alguna ficha
     *
     * post: Devuelve un valor de verdad según si se puede o no colocar fichas
     *
     * @param saltarTurno
     * @return
     */
    private void cambiarTurno() {
        if (turnoActual % 2 == 1) {
            jugador = Casillero.BLANCAS;
            oponente = Casillero.NEGRAS;
            turnoActual++;
            saltarTurno();
            if (checkearDisponibilidadTablero(0)) {
                imposibleMover = true;
            }
        } else {
            jugador = Casillero.NEGRAS;
            oponente = Casillero.BLANCAS;
            turnoActual++;
            saltarTurno();
            if (checkearDisponibilidadTablero(0)) {
                imposibleMover = true;
            }
        }
    }
 
    private boolean checkearDisponibilidadTablero(int saltarTurno) {
        boolean imposible = false;
        // Lee el tablero
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                if (puedeColocarFicha(i + 1, j + 1) == false) {
                    saltarTurno++;
                }
            }
        }
        if (saltarTurno == tamaño * tamaño) {
            imposible = true;
        }
        return imposible;
    }
 
    /**
     * pre: No puede colocar fichas en el turno actual
     *
     * post: Cambia de jugador. En caso de que tampoco pueda colocar fichas,
     * termina el juego.
     */
    private void saltarTurno() {
        if (checkearDisponibilidadTablero(0)) {
            turnoActual++;
            if (jugador == Casillero.NEGRAS) {
                // Cambia de turno
                jugador = Casillero.BLANCAS;
                oponente = Casillero.NEGRAS;
                if (checkearDisponibilidadTablero(0)) {
                    termino();
                }
            } else {
                // Termina el juego
                jugador = Casillero.NEGRAS;
                oponente = Casillero.BLANCAS;
                if (checkearDisponibilidadTablero(0)) {
                    termino();
                }
            }
        }
    }
 
    /**
     * post: devuelve la cantidad de fichas negras en el tablero.
     */
    public int contarFichasNegras() {
        int cantidadDeFichasNegras = 0;
        // Lee el tablero, contando las fichas negras
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                if (tablero[i + 1][j + 1] == Casillero.NEGRAS) {
                    cantidadDeFichasNegras++;
                }
            }
        }
        fichasNegras = cantidadDeFichasNegras;
        return fichasNegras;
    }
 
    /**
     * post: devuelve la cantidad de fichas blancas en el tablero.
     */
    public int contarFichasBlancas() {
        int cantidadDeFichasBlancas = 0;
        // Lee el tablero, contando las fichas blancas
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                if (tablero[i + 1][j + 1] == Casillero.BLANCAS) {
                    cantidadDeFichasBlancas++;
                }
            }
        }
        fichasBlancas = cantidadDeFichasBlancas;
        return fichasBlancas;
    }
 
    public boolean termino() {
        boolean terminarJuego = false;
        if (fichasNegras == 0 || fichasBlancas == 0
                || (fichasNegras + fichasBlancas) == (tamaño * tamaño)
                || imposibleMover) {
            // En caso de que no hayan fichas negras, o no hayan fichas blancas,
            // o el tablero esté lleno, o ninguno de los jugadores pueda
            // moverse, termina el juego.
            terminarJuego = true;
        }
 
        return terminarJuego;
    }
 
    /**
     * post: indica si el juego terminó y tiene un ganador.
     */
    public boolean hayGanador() {
        boolean ganador = false;
        if (termino()) {
            if (fichasNegras < fichasBlancas || fichasNegras > fichasBlancas) {
                // En caso de que hayan mas fichas de un jugador que de otro,
                // hay un ganador
                ganador = true;
            }
        }
 
        return ganador;
    }
 
    /**
     * pre : el juego terminó. post: devuelve el nombre del jugador que ganó el
     * juego.
     */
    public String obtenerGanador() {
        String ganador = "nadie";
        if (hayGanador()) {
            // Según la cantida de fichas negras o fichas blancas actuales,
            // elige al ganador
            if (fichasNegras > fichasBlancas) {
                ganador = negras;
            } else {
                ganador = blancas;
            }
        }
 
        return ganador;
    }
 
    /**
     * post: Inserta los valores del tablero de origen en el tablero destino
     *
     * @param origen
     * @param destino
     */
    public void copiarTablero(Casillero origen[][], Casillero destino[][]) {
        for (int i = 0; i < origen.length; i++) {
            for (int j = 0; j < origen.length; j++) {
                destino[i][j] = origen[i][j];
            }
        }
        mismoTablero = false;
    }
 
    /**
     * pre: En este turno aún no se volvio al turno anterior
     *
     * post: El tablero vuelve a estar igual que en el turno anterior (solo se puede deshacer un turno)
     */
    public void volverTurnoAnterior() {
        if (turnoActual != 1) {
            if (mismoTablero == false) {
                cambiarTurno();
            }
            for (int i = 0; i < tablero.length; i++) {
                for (int j = 0; j < tablero.length; j++) {
                    tablero[i][j] = turnoAnterior[i][j];
                }
            }
            mismoTablero = true;
        }
    }
}