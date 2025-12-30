package com.hotel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Gestió de reserves d'un hotel.
 */
public class App {

    // --------- CONSTANTS I VARIABLES GLOBALS ---------

    // Tipus d'habitació
    public static final String TIPUS_ESTANDARD = "Estàndard";
    public static final String TIPUS_SUITE = "Suite";
    public static final String TIPUS_DELUXE = "Deluxe";

    // Serveis addicionals
    public static final String SERVEI_ESMORZAR = "Esmorzar";
    public static final String SERVEI_GIMNAS = "Gimnàs";
    public static final String SERVEI_SPA = "Spa";
    public static final String SERVEI_PISCINA = "Piscina";

    // Capacitat inicial
    public static final int CAPACITAT_ESTANDARD = 30; //30
    public static final int CAPACITAT_SUITE = 20;
    public static final int CAPACITAT_DELUXE = 10;

    // IVA
    public static final float IVA = 0.21f;

    // Scanner únic
    public static Scanner sc = new Scanner(System.in);

    // HashMaps de consulta
    public static HashMap<String, Float> preusHabitacions = new HashMap<String, Float>();
    public static HashMap<String, Integer> capacitatInicial = new HashMap<String, Integer>();
    public static HashMap<String, Float> preusServeis = new HashMap<String, Float>();

    // HashMaps dinàmics
    public static HashMap<String, Integer> disponibilitatHabitacions = new HashMap<String, Integer>();    
    public static HashMap<Integer, ArrayList<String>> reserves = new HashMap<Integer, ArrayList<String>>();

    // Generador de nombres aleatoris per als codis de reserva
    public static Random random = new Random();

    // --------- MÈTODE MAIN ---------

    /**
     * Mètode principal. Mostra el menú en un bucle i gestiona l'opció triada
     * fins que l'usuari decideix eixir.
     */
    public static void main(String[] args) {
        inicialitzarPreus();

        int opcio = 0;
        do {
            mostrarMenu();
            opcio = llegirEnter("\nSeleccione una opció: ");
            gestionarOpcio(opcio);
        } while (opcio != 6);

        System.out.println("Eixint del sistema... Gràcies per utilitzar el gestor de reserves!");
    }

    // --------- MÈTODES DEMANATS ---------

    /**
     * Configura els preus de les habitacions, serveis addicionals i
     * les capacitats inicials en els HashMaps corresponents.
     */
    public static void inicialitzarPreus() {
        // Preus habitacions
        preusHabitacions.put(TIPUS_ESTANDARD, 50f);
        preusHabitacions.put(TIPUS_SUITE, 100f);
        preusHabitacions.put(TIPUS_DELUXE, 150f);

        // Capacitats inicials
        capacitatInicial.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        capacitatInicial.put(TIPUS_SUITE, CAPACITAT_SUITE);
        capacitatInicial.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Disponibilitat inicial (comença igual que la capacitat)
        disponibilitatHabitacions.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        disponibilitatHabitacions.put(TIPUS_SUITE, CAPACITAT_SUITE);
        disponibilitatHabitacions.put(TIPUS_DELUXE, CAPACITAT_DELUXE);
        

        // Preus serveis
        preusServeis.put(SERVEI_ESMORZAR, 10f);
        preusServeis.put(SERVEI_GIMNAS, 15f);
        preusServeis.put(SERVEI_SPA, 20f);
        preusServeis.put(SERVEI_PISCINA, 25f);
    }

    /**
     * Mostra el menú principal amb les opcions disponibles per a l'usuari.
     */
    public static void mostrarMenu() {
        System.out.println("\n===== MENÚ PRINCIPAL =====\n");
        System.out.println("1. Reservar una habitació");
        System.out.println("2. Alliberar una habitació");
        System.out.println("3. Consultar disponibilitat");
        System.out.println("4. Llistar reserves per tipus");
        System.out.println("5. Obtindre una reserva");
        System.out.println("6. Ixir");
    }

    /**
     * Processa l'opció seleccionada per l'usuari i crida el mètode corresponent.
     */
    public static void gestionarOpcio(int opcio) {
       switch (opcio) {
        case 1: reservarHabitacio();
            
            break;
        case 2:
            
            break;
            
        case 3: consultarDisponibilitat();
            
            break;
        case 4:
            
            break;
            
        case 5:
            
            break;            
       
        default: System.out.println("\n\nHas de introduir un nombre entre el 1 i el 6.");
            break;
            
            
            // int totatDisponible = disponibilitatHabitacions.get(TipoHabitacio);
            //  totatDisponible--;
            //  disponibilitatHabitacions.put(TipoHabitacio,totatDisponible);
       }
    }

    /**
     * Gestiona tot el procés de reserva: selecció del tipus d'habitació,
     * serveis addicionals, càlcul del preu total i generació del codi de reserva.
     */
    public static void reservarHabitacio() {    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        System.out.println("\n===== RESERVAR HABITACIÓ =====\n");

        float preuTotal = 0;
        int preusServeis = 0;
        ArrayList <String> dadesReserva = new ArrayList<>();
     
        int codi;

        String habitacioSeleccionada = seleccionarTipusHabitacioDisponible(); // retorna el tipus de habitacio.
    


        if (!habitacioSeleccionada.equals("null")){  // Si no es NULL es que hi han disponibles.
        
            // si hi ha disponibilitat, li reste una habitacio. Cree una variable per poder restar.

            int totatDisponible = disponibilitatHabitacions.get(habitacioSeleccionada);
            totatDisponible--;
            disponibilitatHabitacions.put(habitacioSeleccionada,totatDisponible);


            // cridem a seleccionarServeis per a obtindre els serveis  i el preu total.
            //sera una arrayList de 6 camps. Els 4 ultims son les serveis extra. 

            dadesReserva = seleccionarServeis();

            // cridem a calcularPreuTotal que ens torna el total i tambe ens mostra el preu de la reserva.
            preuTotal = calcularPreuTotal(habitacioSeleccionada,dadesReserva);  // calcule el preu total.

            // ara cridem a generarCodiReserva pero a que ens genere el codi i el mostre,
            codi = generarCodiReserva();
            System.out.println("\nCodi de reserva: " + codi);


           
            //System.exit(0);

             // cambie el valor del camp 0 per possar el nom de l'habitacio.
            dadesReserva.set(0,habitacioSeleccionada);

             // cambie el valor del camp 1 per possar el preu total de la reserva.
            dadesReserva.set(1,String.valueOf(preuTotal));   
            
            // ara afegim la reserva amb el codi i les demes dades.
            reserves.put(codi,dadesReserva);

            /////////System.out.println("\n --- final: " + reserves);

            System.out.print("Pulse una tecla mes intro per tornar al menú. ");  // per a poder veure mes clar la reserva.
            sc.next();

        }
            
        
    }

    /**
     * Pregunta a l'usuari un tipus d'habitació en format numèric i
     * retorna el nom del tipus.
     */
    public static String seleccionarTipusHabitacio() {
        //TODO:
        int opcio = 0;
        String text =" - disponibles - "; // per a que no siga tant llarg la línea de baix.

        System.out.println("1. Estàndar    - " + disponibilitatHabitacions.get(TIPUS_ESTANDARD) + text + preusHabitacions.get(TIPUS_ESTANDARD) + " €");
        System.out.println("2. Suite       - " + disponibilitatHabitacions.get(TIPUS_SUITE) + text + preusHabitacions.get(TIPUS_SUITE) + " €"); 
        System.out.println("3. Deluxe      - " + disponibilitatHabitacions.get(TIPUS_DELUXE) + text + preusHabitacions.get(TIPUS_DELUXE) + " €");


        do{
           
        opcio = llegirEnter("\nSeleccione tipus d’habitació: : ");

            switch(opcio){

                case 1: return TIPUS_ESTANDARD;                
                case 2: return TIPUS_SUITE;
                case 3: return TIPUS_DELUXE;
                default: System.out.println("\n ===== Opcio incorrecta. Sel·lecciona 1,2 ó 3.=====");
                opcio = 0;
            }
        }while(opcio == 0);     

        return null;
    }
    /**
     * Mostra la disponibilitat i el preu de cada tipus d'habitació,
     * demana a l'usuari un tipus i només el retorna si encara hi ha
     * habitacions disponibles. En cas contrari, retorna null.
     */
    public static String seleccionarTipusHabitacioDisponible() {        
    
        // cridem a la funcio per mostrar la disponibilitat.
        //****consultarDisponibilitat();

        // cridem la funcio per obtindre quina habitacio es vol i la guarden en la variable 'TipoHabitacio'.

        String TipoHabitacio = seleccionarTipusHabitacio();  
  
        // Comprobem la disponibilitat. Si major que cero, es que hi han lliure.      
        
        if (disponibilitatHabitacions.get(TipoHabitacio) > 0) {

            // si hi ha disponibilitat, torna el tipus de habiutacion.

            return TipoHabitacio;

        }
        else System.out.println("\nNo tenim ninguna habitacio " + TipoHabitacio + " disponible.");
                
        return "null";
    }

    /**
     * Permet triar serveis addicionals (entre 0 i 4, sense repetir) i
     * els retorna en un ArrayList de String
     */
    public static ArrayList<String> seleccionarServeis() {

        int opcio = -1;
        String volServei = "s";
        int preuServeis = 0;
        ArrayList <String> serveis = new ArrayList<>();  // cree l'arrayList i linitzialise.
        // inicialitze el ArrayList amb els camps corresponents. Aixi despres, nomes es cambiar el valor. 
        // Fique nom per a que quede clar cada valor.
        serveis.add("tipus hab");
        serveis.add("preuServeis");
        serveis.add("");
        serveis.add("");
        serveis.add("");
        serveis.add("");       
        
        
        do{
            System.out.println("\nServeis addicionals (0-4):");
            System.out.println("----------------------------");
            System.out.println("0. Finalitzar");
            System.out.println("1. Esmorzar (10€)");
            System.out.println("2. Gimnàs (15€)");
            System.out.println("3. Spa (20€)");
            System.out.println("4. Piscina (25€)");

            //do{
                System.out.print("\nVol afegir un servei? (s/n): ");
                volServei= sc.next();
                if (volServei.equalsIgnoreCase("s")) {
                
                
                    opcio = llegirEnter("\nSeleccione servei: ");
                
                    switch (opcio) {

                        case 0: break;

                        case 1:  
                                if (!serveis.get(2).equals("Esmorzar (10€)")){
                                    serveis.set(2,"Esmorzar (10€)");
                                    preuServeis += 10;
                                    System.out.println("\nServei afegit: Esmorzar");
                                }
                                else System.out.println("Servici ja afegit");
                            break;

                        case 2: if (!serveis.get(3).equals("Gimnàs (15€)")){
                                    serveis.set(3,"Gimnàs (15€)");
                                    preuServeis += 15;
                                    System.out.println("\nServei afegit: Gimnàs");
                                }
                                else System.out.println("Servici ja afegit");
                        
                                break;

                        case 3: if (!serveis.get(4).equals("Spa (20€)")){
                                    serveis.set(4,"Spa (20€)");
                                    preuServeis += 20;
                                    System.out.println("\nServei afegit: Spa");
                                }
                                else System.out.println("Servici ja afegit");
                        
                                break;

                        case 4: if (!serveis.get(5).equals("Piscina (25€)")){
                                    serveis.set(5,"Piscina (25€)");
                                    preuServeis += 25;
                                    System.out.println("\nServei afegit: Piscina");
                                }
                                else System.out.println("Servici ja afegit");
                        
                                break;
                    
                        default: {
                            
                            opcio = -1;
                            System.out.println("\n Seleccio incorrecta. \n");
                            break;
                        }
                    }

   
                }else if(volServei.equalsIgnoreCase("n")) opcio = 0; // si no vol ningun servici, es com pulsar 0.
                 else System.out.println("\n Opcio incorrecta. ");

           // }while(opcio != 0);

                // pase el preu total del serveis a String per afegirlo al ArrayList
            serveis.set(1,String.valueOf(preuServeis));           
            
        }while(opcio != 0);    
        return serveis;
    }

    /**
     * Calcula i retorna el cost total de la reserva, incloent l'habitació,
     * els serveis seleccionats i l'IVA.
     */
    public static float calcularPreuTotal(String tipusHabitacio, ArrayList<String> serveisSeleccionats) {

        int preuServeis = 0;
        String reserva="";
        float Subtotal = 0;
        float preuTotal= 0;
        float calculIva = 0f;
        

                // en el ArrayList el preu està en String. El pase a int per a sumar preu de l`habitacio.  i despres de nou a String.
            preuServeis = Integer.valueOf(serveisSeleccionats.get(1));


            // ara afegisc al ArrayList el tipus de habitacio y el preu total passat a String.

            ///serveisSeleccionats.set(1,String.valueOf(preuServeis));
            ///serveisSeleccionats.set(0,tipusHabitacio);

            
            boolean PrimerServici = true;
            for(int i = 2; i <=5; i++ ){


                // comproba si esxisteis el servici, i si existeix el coloca en la cadena de text resserva.
                if (!serveisSeleccionats.get(i).equals("")) {

                    if (PrimerServici) PrimerServici = false; // si es el primer servici que trove, cambie a fals i no ficare coma.
                    else reserva += ", ";  // si possa fals, es que ja hi ha un servici, i posará una coma.
                    reserva += serveisSeleccionats.get(i); // i ara afegisc el servici.
                }

            }
            Subtotal = preuServeis + preusHabitacions.get(tipusHabitacio);
            calculIva = Math.round(IVA*Subtotal);
            //calculIva = Math.round((IVA*Subtotal) * 10f) / 10f;    
            preuTotal = calculIva + Subtotal;

            System.out.println("\n=== Dades Reserva ===");
            System.out.println("\nPreu habitació: " + preusHabitacions.get(tipusHabitacio) + " €");
            System.out.println("Serveis: " + reserva);
            System.out.println("Subtotal: " + Subtotal + " €");
            System.out.println("IVA (21%): " + calculIva + " €"); // calcule el iva.
            System.out.println("\nTOTAL: " + preuTotal + " €"); // calcule el iva i sume el total.
            System.out.println("\nReserva creada amb èxit!"); 
            
        return preuTotal;
    }

    /**
     * Genera i retorna un codi de reserva únic de tres xifres
     * (entre 100 i 999) que no estiga repetit.
     */
    public static int generarCodiReserva() {
        //TODO:
        
        int codiReserva;  
        
        boolean trovat = false;    
            do {
                    // asigna un codi aleatori i comproba que no existisca, mirant el le hashmap de reserves. 
                    // si existeix, asigna un altre.
                codiReserva = random.nextInt(899)+100;  
                trovat = false;
      
                for (Integer valor : reserves.keySet()){

                    if(codiReserva == valor)  trovat = true;

                }
               
              

                
            } while (trovat);       

        return codiReserva;
    }

    /**
     * Permet alliberar una habitació utilitzant el codi de reserva
     * i actualitza la disponibilitat.
     */
    public static void alliberarHabitacio() {
        System.out.println("\n===== ALLIBERAR HABITACIÓ =====");
         // TODO: Demanar codi, tornar habitació i eliminar reserva
    }

    /**
     * Mostra la disponibilitat actual de les habitacions (lliures i ocupades).
     */
    public static void consultarDisponibilitat() {
        // TODO: Mostrar lliures i ocupades
        //disponibilitatHabitacion.get(TIPUS_ESTANDARD);
        //mostrarDisponibilitatTipus();

       


        // System.out.println("\n ===== Consultar disponibilitat ===== \n");
        // System.out.println("Tipus    | Lliures | Ocupades |");
        // System.out.println("------------------------------");
        // System.out.println("Estàndar |    " + disponibilitatHabitacions.get(TIPUS_ESTANDARD) + "   |    " + (30-disponibilitatHabitacions.get(TIPUS_ESTANDARD))); 
        // System.out.println("------------------------------");
        // System.out.println("Suite    |    " + disponibilitatHabitacions.get(TIPUS_SUITE) + "   |    " + (20-disponibilitatHabitacions.get(TIPUS_SUITE)));
        // System.out.println("------------------------------");
        // System.out.println("Deluxe   |    " + disponibilitatHabitacions.get(TIPUS_DELUXE) + "   |    " + (10-disponibilitatHabitacions.get(TIPUS_DELUXE)));
    
    }

    /**
     * Funció recursiva. Mostra les dades de totes les reserves
     * associades a un tipus d'habitació.
     */
    public static void llistarReservesPerTipus(int[] codis, String tipus) {
         // TODO: Implementar recursivitat
       
         
    }

    /**
     * Permet consultar els detalls d'una reserva introduint el codi.
     */
    public static void obtindreReserva() {
        System.out.println("\n===== CONSULTAR RESERVA =====");
        // TODO: Mostrar dades d'una reserva concreta
 
    }

    /**
     * Mostra totes les reserves existents per a un tipus d'habitació
     * específic.
     */
    public static void obtindreReservaPerTipus() {
        System.out.println("\n===== CONSULTAR RESERVES PER TIPUS =====");
        // TODO: Llistar reserves per tipus
    }

    /**
     * Consulta i mostra en detall la informació d'una reserva.
     */
    public static void mostrarDadesReserva(int codi) {
       // TODO: Imprimir tota la informació d'una reserva
    }

    // --------- MÈTODES AUXILIARS (PER MILLORAR LEGIBILITAT) ---------

    /**
     * Llig un enter per teclat mostrant un missatge i gestiona possibles
     * errors d'entrada.
     */
    static int llegirEnter(String missatge) {
        int valor = 0;
        boolean correcte = false;
        while (!correcte) {
                System.out.print(missatge);
                valor = sc.nextInt();
                correcte = true;
        }
        return valor;
    }

    /**
     * Mostra per pantalla informació d'un tipus d'habitació: preu i
     * habitacions disponibles.
     */
    static void mostrarInfoTipus(String tipus) {
        int disponibles = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        float preu = preusHabitacions.get(tipus);
        System.out.println("- " + tipus + " (" + disponibles + " disponibles de " + capacitat + ") - " + preu + "€");
    }

    /**
     * Mostra la disponibilitat (lliures i ocupades) d'un tipus d'habitació.
     */
    static void mostrarDisponibilitatTipus(String tipus) {
        int lliures = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        int ocupades = capacitat - lliures;

        String etiqueta = tipus;
        if (etiqueta.length() < 8) {
            etiqueta = etiqueta + "\t"; // per a quadrar la taula
        }

        System.out.println(etiqueta + "\t" + lliures + "\t" + ocupades);
    }
}
