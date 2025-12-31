package com.hotel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Gestió de reserves d'un hotel. 
 * SGM 31/12/2025 17:00
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
    public static final int CAPACITAT_ESTANDARD = 30; 
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

        System.out.println("\nEixint del sistema... Gràcies per utilitzar el gestor de reserves!\n");
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
        case 2: alliberarHabitacio();
            
            break;
            
        case 3: consultarDisponibilitat();
            
            break;
        case 4: obtindreReservaPerTipus();
            
            break;
            
        case 5: obtindreReserva();
            
            break;  

        case 6: break;          
       
        default: System.out.println("\n\nHas de introduir un nombre entre el 1 i el 6.");
            break;            
    
       }
    }

    /**
     * Gestiona tot el procés de reserva: selecció del tipus d'habitació,
     * serveis addicionals, càlcul del preu total i generació del codi de reserva.
     */
    public static void reservarHabitacio() {    
        System.out.println("\n===== RESERVAR HABITACIÓ =====\n");

        float preuTotal = 0;
        ArrayList <String> dadesReserva = new ArrayList<>();
     
        int codi;

            // cride a seleccionarTipusHabitacioDisponible.
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

            // String preutotalString = String.format("%.2f", preuTotal);  // pase le preu a String amb format de 2 digits.
            // System.out.println(s);
            // s = s.replace(",",".");
            // System.out.println("---" +s);        

            // cridem a generarCodiReserva per a que ens genere el codi i el mostre.
            codi = generarCodiReserva();
            System.out.println("\nCodi de reserva: " + codi + "\n");


             // cambie el valor del camp 0 per possar el nom de l'habitacio.
            dadesReserva.set(0,habitacioSeleccionada);

             // cambie el valor del camp 1 per possar el preu total de la reserva. 
             // tinc que pasarlo a String. De paso el formatetge per a que mostre dos digits.
            String preutotalString = String.format("%.2f", preuTotal);  
            dadesReserva.set(1,String.valueOf(preutotalString));   
            
            // ara afegim la reserva amb el codi i les demes dades al HashMap reserves.
            reserves.put(codi,dadesReserva);
        }        
    }

    /**
     * Pregunta a l'usuari un tipus d'habitació en format numèric i
     * retorna el nom del tipus.
     */
    public static String seleccionarTipusHabitacio() {

        int opcio = 0;
        String text =" disponibles - "; // per a que no siga tant llarg la línea de baix.

        System.out.println("1. Estàndar - " + disponibilitatHabitacions.get(TIPUS_ESTANDARD) + text + preusHabitacions.get(TIPUS_ESTANDARD) + " €");
        System.out.println("2. Suite    - " + disponibilitatHabitacions.get(TIPUS_SUITE) + text + preusHabitacions.get(TIPUS_SUITE) + " €"); 
        System.out.println("3. Deluxe   - " + disponibilitatHabitacions.get(TIPUS_DELUXE) + text + preusHabitacions.get(TIPUS_DELUXE) + " €");

        do{
           
        opcio = llegirEnter("\nSeleccione tipus d’habitació: : ");

                // switch que directamente retorna el tipus d'habitacio.
            switch(opcio){

                case 1: return TIPUS_ESTANDARD;                
                case 2: return TIPUS_SUITE;
                case 3: return TIPUS_DELUXE;
                default: System.out.println("\n ===== Opcio incorrecta. Sel·lecciona 1,2 ó 3.=====");
                opcio = 0;
            }
        }while(opcio == 0);     

        return null; // retorna null si no ha fet una seleccio valida
    }
    /**
     * Mostra la disponibilitat i el preu de cada tipus d'habitació,
     * demana a l'usuari un tipus i només el retorna si encara hi ha
     * habitacions disponibles. En cas contrari, retorna null.
     */
    public static String seleccionarTipusHabitacioDisponible() {        

        // cridem la funcio per obtindre quina habitacio es vol i la guarden en la variable 'TipoHabitacio'.

        String TipoHabitacio = seleccionarTipusHabitacio();  
  
        // Comprobem la disponibilitat. Si major que cero, es que hi han lliure.      
        
        if (disponibilitatHabitacions.get(TipoHabitacio) > 0) {

            // si hi ha disponibilitat, torna el tipus de habitacion.

            return TipoHabitacio;

        }
        else System.out.println("\n***** No tenim ninguna habitacio " + TipoHabitacio + " disponible. *****");
                
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
                
                
                    opcio = llegirEnter("\nSeleccione servei: "); // tornara un enter
                
                    // en este switch, anyadisc un servei extra si encara no estaba anyadit. Si ja estaba, mostrarà avis.
                    switch (opcio) {

                        case 0: break;

                        case 1:  
                                if (!serveis.get(2).equals("Esmorzar (10€)")){     
                                    serveis.set(2,"Esmorzar (10€)");
                                    preuServeis += 10;
                                    System.out.println("\nServei afegit: Esmorzar");
                                }
                                else System.out.println("Servici ja afegit"); // mostre missatge si ja estaba.
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

        float preuServeis = 0;
        String reserva="";
        float Subtotal = 0;
        float preuTotal= 0;
        float calculIva = 0;
        

            // en el ArrayList el preu està en String. El pase a int per a sumar preu de l`habitacio.  i despres de nou a String.
        preuServeis = Float.valueOf(serveisSeleccionats.get(1));

        boolean PrimerServici = true;  // variable que utilitze per saber si al menys hi ha un servici.
            for(int i = 2; i <=5; i++ ){


                // comproba si esxisteis el servici, i si existeix el coloca en la cadena de text resserva. 
                // coloca una coma si hi ha mes d'un servici.
                if (!serveisSeleccionats.get(i).equals("")) {

                    if (PrimerServici) PrimerServici = false; // si es el primer servici que trove, cambie a fals i no ficare coma.
                    else reserva += ", ";  // si possa fals, es que ja hi ha un servici, i posará una coma.
                    reserva += serveisSeleccionats.get(i); // i ara afegisc el servici.
                }

            }
            // calcule el subtotal, el IVA a pagar i el total del cost de la reserva.
            Subtotal = preuServeis + preusHabitacions.get(tipusHabitacio);
            calculIva = (IVA*Subtotal); 
            preuTotal = calculIva + Subtotal;
              // si no hi ha cap servici, ho dira. Si hi ha, posarà el servei
            reserva = reserva.equals("") ? "No afegits." : reserva;  

            // ho mostre en aquest métode, perque trove una tonteria enviar el preu total, 
            // i despres anar restant l'iva i traguen el subtotal
            // mostre iva en dos decimals 

            System.out.println("\n=== Dades Reserva ===");
            System.out.printf("\nPreu habitació: %.2f €", preusHabitacions.get(tipusHabitacio));  
            System.out.printf("\nServeis: %s", reserva);
            System.out.printf("\nSubtotal: %.2f €", Subtotal);            
            System.out.printf("\nIVA (21%%): %.2f €", calculIva); 
            System.out.printf("\nTOTAL: %.2f €", preuTotal); // calcule el iva i sume el total.
            System.out.println("\n\nReserva creada amb èxit!"); 
            
        return preuTotal;
    }

    /**
     * Genera i retorna un codi de reserva únic de tres xifres
     * (entre 100 i 999) que no estiga repetit.
     */
    public static int generarCodiReserva() {
        
        int codiReserva;  
        
        boolean trovat = false;    
            do {
                    // asigna un codi aleatori i comproba que no existisca, mirant en le hashmap de reserves. 
                    // si existeix torna a asignar un altre codi.
                codiReserva = random.nextInt(899)+100;  // +100 per a que el mínim siga 100               
                
                if(reserves.containsKey(codiReserva))  trovat = true;
                else trovat = false;
         
            } while (trovat);       

        return codiReserva;
    }

    /**
     * Permet alliberar una habitació utilitzant el codi de reserva
     * i actualitza la disponibilitat.
     */
    public static void alliberarHabitacio() {
        System.out.println("\n===== ALLIBERAR HABITACIÓ =====");
        
        int codi;  
        String tipusHabitacio="";   //almacenará el tipus d'habitacio a eliminar 

        codi = llegirEnter("\nIntrodueix el codi de reserva: "); // demane el codi
       
        // si el codi existeix elimine la reserva. Sino, mostra misatge
        if (reserves.containsKey(codi)){    
            
            System.out.println("\nReserva trobada!\n");

            tipusHabitacio = reserves.get(codi).get(0);  // obtinc el tipus d'habitacio a eliminar 

                // primer obtinc la disponibilitat actual i afegisc una mes.
            int totatDisponible = disponibilitatHabitacions.get(tipusHabitacio);
            totatDisponible++;
            disponibilitatHabitacions.put(tipusHabitacio,totatDisponible);

            // elimine la reserva del HashMap que correspone a eixe codi.
            reserves.remove(codi);
            System.out.println("Habitació alliberada correctament.");
            

        } else System.out.println("\nNo s'ha trobat cap reserva amb aquest codi.\n"); // si no trova el codi mostra avis.       
    }

    /**
     * Mostra la disponibilitat actual de les habitacions (lliures i ocupades).
     */
    public static void consultarDisponibilitat() {        
   
        String cadena; // text que utilitzaré si les places lliures tenen un digit o dos.

        System.out.println("\n ===== DISPONIBILITAT D'HABITACIONS ===== \n");
        System.out.println("Tipus    | Lliures | Ocupades |");
        System.out.println("------------------------------");

        // si les places lliures sols tenen un digit, cambie la cadena per a que es vega be visualment.
       
        if ((disponibilitatHabitacions.get(TIPUS_ESTANDARD))<10) cadena = "Estàndar |     "; else cadena= "Estàndar |    ";
        System.out.println(cadena + disponibilitatHabitacions.get(TIPUS_ESTANDARD) + "    |    " + (CAPACITAT_ESTANDARD-disponibilitatHabitacions.get(TIPUS_ESTANDARD))); 
        System.out.println("------------------------------");
        if ((disponibilitatHabitacions.get(TIPUS_SUITE))<10) cadena = "Suite    |     "; else cadena= "Suite    |    ";
        System.out.println(cadena + disponibilitatHabitacions.get(TIPUS_SUITE) + "    |    " + (CAPACITAT_SUITE-disponibilitatHabitacions.get(TIPUS_SUITE)));
        System.out.println("------------------------------");
        if ((disponibilitatHabitacions.get(TIPUS_DELUXE))<10) cadena = "Deluxe   |     "; else cadena= "Deluxe   |    ";
        System.out.println(cadena + disponibilitatHabitacions.get(TIPUS_DELUXE) + "    |    " + (CAPACITAT_DELUXE-disponibilitatHabitacions.get(TIPUS_DELUXE)));
    
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

        int codi;             

       codi = llegirEnter("\nIntrodueix el codi de reserva: "); // demane el codi       

       // si existeix el codi mostre la reserva, sino, missatge d'error.
          
       if (reserves.containsKey(codi)){
                System.out.println("\nDades de la reserva: \n");
                mostrarDadesReserva(codi);
            

       } else System.out.println("\nNo s'ha trobat cap reserva amb aquest codi.\n"); // si no trova el codi mostra avis.
    }

    /**
     * Mostra totes les reserves existents per a un tipus d'habitació
     * específic.
     */
    public static void obtindreReservaPerTipus() {
        System.out.println("\n===== CONSULTAR RESERVES PER TIPUS =====");

        int opcio = 0;
        String tipo =""; 

        do{
            System.out.println("Seleccione tipus: ");
            System.out.println("1. Estàndard");
            System.out.println("2. Suite");
            System.out.println("3. Deluxe");

            opcio = llegirEnter("\nOpcio: ");          
            
            switch (opcio) {
                case 1: tipo="Estàndard";                    
                    break;
                case 2: tipo="Suite";                    
                    break;
                case 3: tipo="Deluxe";                    
                    break;
            
                default: System.out.println("\n Elegix una opcio correcta. \n"); opcio = 0;
                    break;
            }


        }while(opcio==0);

        System.out.println("\nReserves del tipus: " + tipo);
        boolean trobades= false;
        
        // busque reserva per reserva, quina es del tipus indicat. 
        // el for torna tots el codis de les reserves i sabent el codi busque els valors de cada codi. 
        // He vist que ficant reserves.get(cod).get(0) em mostra un camp del array del HashMap. 
        // Antes ho he fet amb un for y un array, perque no sabia que es podia aixi.       
        // Mire si en el camp habitacion es del tipus demanat, i entonces envie el codi a mostrarDadesReserva.
        for(Integer cod : reserves.keySet()){
            
            // Mire si en el camp habitacion(0) es del tipus demanat, i entonces envie el codi a mostrarDadesReserva.
            if (reserves.get(cod).get(0).equals(tipo)){ 

                System.out.println("\nCodi: " + cod);
                mostrarDadesReserva(cod);
                trobades=true;
            }
       }
        // si s'han trobat al menys una reserva, indique que no hi han mes. 
        // si no s'ha trobat cap, indique que no hi han.
       if (trobades) System.out.println("\nNo hi ha més reserves d'aquest tipus.");
       else System.out.println("No hi ha reserves d'aquest tipus.");
    }

    /**
     * Consulta i mostra en detall la informació d'una reserva.
     */
    public static void mostrarDadesReserva(int codi) {

        // cree un ArraYList per guardar els valors a mostrar, ya que per defecte venen amb el preu
        ArrayList <String> dadesReserva = new ArrayList<>(); 

       // Obtinc els camps de serveis extra y els copie en el ArrayList. 
       // Es a dir, el for retorna els valor asociats de la clau del codi demanat.
            for (String st : reserves.get(codi)){

                    dadesReserva.add(st);        

            }
            // Imprimisc els valors de la reserva. 
            // arrayreserves.get(codi).get(0) = el primer get va a la clau del codi que busque i em torna els valors del array. 
            // En el segon get, faig referencia a un camp en concret de ixe array. 
            
            System.out.println("* Tipus d'habitació: " + reserves.get(codi).get(0));
            System.out.println("* Cost total: " + reserves.get(codi).get(1)+ " €");

            System.out.println("* Serveis addicionals: ");

                // cree un for per saber si hi ha algún servei o no.
            boolean hiHaServeis = false;
            for(int i = 2; i <=5; i++){
                if(!reserves.get(codi).get(i).equals("")) hiHaServeis = true;
            }
            // si hiHaServeis = false, es que no hi ha cap servei i mostre missage.
            if (hiHaServeis == false) System.out.println("    - Sense serveis");
            else{
                // Si el campo NO esta vuit, l'imprimisc.
                if(!reserves.get(codi).get(2).equals("")) System.out.println("    - Esmorzar");
                if(!reserves.get(codi).get(3).equals("")) System.out.println("    - Gimnàs");
                if(!reserves.get(codi).get(4).equals("")) System.out.println("    - Spa");
                if(!reserves.get(codi).get(5).equals("")) System.out.println("    - Piscina");
            }

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
