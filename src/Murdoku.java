import java.util.Scanner;

public class Murdoku {

    static final int TAM = 7;

    // Identificadores de las zonas
    static final int PICNIC = 1;
    static final int JARDIN = 2;
    static final int ESTANQUE = 3;
    static final int CENADOR = 4;

    // Identificadores de los elementos
    static final int VACIO = 0;
    static final int SILLA = 1;
    static final int ALFOMBRA = 2;
    static final int ARBOL = 3;
    static final int FLORES = 4;
    static final int PIEDRA = 5;
    static final int MESA = 6;

    static Scanner teclado = new Scanner(System.in);

    // Tablero donde se colocan los personajes
    static char[][] tablero = new char[TAM][TAM];

    // Elementos fijos del escenario
    static int[][] elementos = {
        {0, 1, 1, 0, 4, 0, 4},
        {3, 2, 2, 0, 0, 0, 4},
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 5, 0, 0, 3},
        {1, 0, 0, 0, 0, 0, 1},
        {0, 0, 0, 0, 0, 0, 1},
        {2, 2, 1, 0, 1, 6, 0}
    };

    // Zona a la que pertenece cada casilla
    static int[][] zonas = {
        {1, 1, 1, 1, 2, 2, 2},
        {1, 1, 1, 1, 2, 2, 2},
        {1, 1, 3, 3, 2, 2, 2},
        {1, 1, 3, 3, 3, 2, 2},
        {1, 1, 3, 3, 4, 4, 4},
        {1, 1, 1, 3, 4, 4, 4},
        {1, 1, 1, 1, 4, 4, 4}
    };

    // Datos de los personajes
    static String[] nombres = {
        "Avery", "Bruno", "Christian",
        "Daphne", "Ethan", "Fabian", "Vicky"
    };

    static char[] letras = {
        'A', 'B', 'C', 'D', 'E', 'F', 'V'
    };

    // Indica qué personajes ya están colocados
    static boolean[] colocados = new boolean[7];


    public static void main(String[] args) {

      int opcion;

    do {

        mostrarTablero();

        mostrarPersonajes();

        mostrarMenu();

        opcion = teclado.nextInt();

        switch (opcion) {

            case 1:
                colocarPersonaje();
                break;

            case 2:
                System.out.println("Has elegido eliminar un personaje.");
                break;

            case 3:
                System.out.println("Has elegido resolver el caso.");
                break;

            case 0:
                System.out.println("Hasta pronto.");
                break;

            default:
                System.out.println("Opcion incorrecta.");

        }

    } while (opcion != 0);

}

    public static void mostrarTablero(){

        System.out.println("    1 2 3 4 5 6 7"); //Numero de las columnas para identificar las coordenadas

        for (int fila = 0; fila < TAM; fila++) {  //Recorrer las filas del tablero
            
             System.out.print((fila + 1) + " | ");

        for (int columna = 0; columna < TAM; columna++) { //Recorrer las 7 columnas de cada fila

            if (tablero[fila][columna] != '\0') {

                System.out.print(tablero[fila][columna] + " ");

                } else {

                    System.out.print(simboloElemento(elementos[fila][columna]) + " ");

            }

        }

        System.out.println();
    }

}

    // Muestra los personajes que todavía NO se han colocado en el tablero
    public static void mostrarPersonajes(){

        System.out.println("Personajes pendientes: ");

        for (int i = 0; i < nombres.length; i++) {
        
            if (colocados[i] == false) {

            System.out.println(letras[i] + " - " + nombres[i]);
            }
        }
    }

    public static void mostrarMenu() {

        System.out.println("===== MURDOKU =====");
        System.out.println("1. Colocar personaje");
        System.out.println("2. Eliminar personaje");
        System.out.println("3. Resolver el caso");
        System.out.println("0. Salir");
        System.out.print("Elige una opcion: ");

}

    // Permitir colocar un personaje en el tablero
    public static void colocarPersonaje() {

        System.out.print("Introduce la letra del personaje: ");
        char letra = teclado.next().toUpperCase().charAt(0);

        int indice = -1;

        // Buscar el personaje en el array
        for (int i = 0; i < letras.length; i++) {

            if (letras[i] == letra) {

                indice = i;

            }

        }

        // Comprobacion que el personaje existe
        if (indice == -1) {

            System.out.println("Personaje no encontrado.");
            return;

        }

        // Comprobacion que no este colocado anteriormente
        if (colocados[indice] == true) {

            System.out.println("Este personaje ya esta colocado.");
            return;

        }

        System.out.print("Introduce la fila (1-7): ");
        int fila = teclado.nextInt() - 1;

        System.out.print("Introduce la columna (1-7): ");
        int columna = teclado.nextInt() - 1;

        // Comprobacion de los limites del tablero
        if (fila < 0 || fila >= TAM || columna < 0 || columna >= TAM) {

            System.out.println("Coordenadas incorrectas.");
            return;

        }

        // Comprobacion si la casilla es transitable
        if (esTransitable(fila, columna) == false) {

            System.out.println("Esta casilla no es transitable.");
            return;

        }

        // Comprobacion si la casilla esta ocupada
        if (tablero[fila][columna] != '\0') {

            System.out.println("Esta casilla ya esta ocupada.");
            return;

        }

        // Comprobacion de la pista de Avery
        if (letra == 'A' && elementos[fila][columna] != ALFOMBRA) {

            System.out.println("Avery debe estar sobre una alfombra.");
            return;

        }

        // Comprobacion de la pista de Bruno
        if (letra == 'B' && (elementos[fila][columna] != SILLA || zonas[fila][columna] != CENADOR)) {

            System.out.println("Bruno debe estar en una silla del cenador.");
            return;

        }

        // Comprobacion de la pista de Christian
        if (letra == 'C' && zonas[fila][columna] != PICNIC) {

            System.out.println("Christian debe estar en la zona de picnic.");
            return;

        }

        // Comprobacion de la pista de Daphne
        if (letra == 'D' &&
            (!tieneAlrededor(fila, columna, FLORES) ||
            !tieneAlrededor(fila, columna, ARBOL))) {

            System.out.println("Daphne debe estar junto a las flores y a un arbol.");
            return;

        }

        // Comprobacion de la pista de Ethan
        if (letra == 'E' && elementos[fila][columna] != SILLA) {

            System.out.println("Ethan debe estar en una silla.");
            return;

        }

        // Comprobacion que la pista de Fabian
        if (letra == 'F' && zonas[fila][columna] != ESTANQUE) {

            System.out.println("Fabian debe estar en la zona del estanque.");
            return;

        }


        // Comprobación de si hay otro personaje en la misma fila
        for (int i = 0; i < TAM; i++) {

            if (tablero[fila][i] != '\0') {

                System.out.println("Ya hay un personaje en esa fila.");
                return;

            }

        }

        // Comprobamos si hay otro personaje en la misma columna
        for (int i = 0; i < TAM; i++) {

            if (tablero[i][columna] != '\0') {

                System.out.println("Ya hay un personaje en esa columna.");
                return;

            }

        }

        // Colocamos el personaje
        tablero[fila][columna] = letra;

        colocados[indice] = true;

        System.out.println("Personaje colocado correctamente.");

    }


    // Devuelve el símbolo correspondiente a un elemento
    public static char simboloElemento(int elemento) {

        switch (elemento) {

            case SILLA:
                return 'S';

            case ALFOMBRA:
                return '=';

            case ARBOL:
                return 'T';

            case FLORES:
                return '*';

            case PIEDRA:
                return 'P';

            case MESA:
                return 'M';

            default:
                return '.';

        }

    }

    // Comprueba si una casilla puede ser ocupada
    public static boolean esTransitable(int fila, int columna) {

        int elemento = elementos[fila][columna];

        return elemento == VACIO
            || elemento == SILLA
            || elemento == ALFOMBRA;

    }

    public static boolean tieneAlrededor(int fila, int columna, int elemento) {

        // arriba
        if (fila > 0 && elementos[fila - 1][columna] == elemento) {
            return true;
        }

        // abajo
        if (fila < TAM - 1 && elementos[fila + 1][columna] == elemento) {
            return true;
        }

        // izquierda
        if (columna > 0 && elementos[fila][columna - 1] == elemento) {
            return true;
        }

        // derecha
        if (columna < TAM - 1 && elementos[fila][columna + 1] == elemento) {
            return true;
        }

        return false;
    }

}