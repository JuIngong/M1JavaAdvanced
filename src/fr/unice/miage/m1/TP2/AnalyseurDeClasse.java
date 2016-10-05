package fr.unice.miage.m1.TP2;

import java.lang.reflect.*;
import java.io.*;


/**
 * @author Michel Buffa
 *         Inspiré par la classe Reflectiontest.java de
 *         Cay S. Horstmann & Gary Cornell, publiée dans le livre Core Java, Sun Press
 * @version 1.00 23 Mars 2001
 */

public class AnalyseurDeClasse {

    public static void analyseClasse(String nomClasse) throws ClassNotFoundException {
        // Récupération d'un objet de type Class correspondant au nom passé en paramètres
        Class cl = Class.forName(nomClasse);

        afficheEnTeteClasse(cl);

        System.out.println();
        afficheAttributs(cl);

        System.out.println();
        afficheConstructeurs(cl);

        System.out.println();
        afficheMethodes(cl);

        // L'accolade fermante de fin de classe !
        System.out.println("}");
    }


    /**
     * Retourne la classe dont le nom est passé en paramètre
     */
    public static Class getClasse(String nomClasse) throws ClassNotFoundException {
        return Class.forName(nomClasse);
    }

    /**
     * Cette méthode affiche par ex "public class Toto extends Tata implements Titi, Tutu {"
     */
    public static void afficheEnTeteClasse(Class cl) {
        //  Affichage du modifier et du nom de la classe
        System.out.print(cl.toGenericString());

        // Récupération de la superclasse si elle existe (null si cl est le type Object)
        Class supercl = cl.getSuperclass();

        // On ecrit le "extends " que si la superclasse est non nulle et
        // différente de Object
        if (supercl != Object.class && supercl != null) {
            System.out.print(" extends " + supercl.getName());
        }

        // Affichage des interfaces que la classe implemente
        if (cl.getInterfaces().length > 0) {
            System.out.print(" implements ");
            Class[] inter = cl.getInterfaces();
            for (int i = 0; i < inter.length; i++) {
                System.out.print(inter[i].getName());
                if (i < inter.length - 1) {
                    System.out.print(", ");
                }
            }
        }

        // Enfin, l'accolade ouvrante !
        System.out.print(" {\n");
    }

    public static void afficheAttributs(Class cl) {
        for (Field f : cl.getDeclaredFields()) {
            System.out.println("  " + Modifier.toString(f.getModifiers()) + " " + f.getGenericType().getTypeName() + " " + f.getName() + ";");
        }
    }

    public static void afficheConstructeurs(Class cl) {
        for (Constructor c : cl.getDeclaredConstructors()) {
            System.out.println("  " + c.toGenericString() + ";");
        }
    }


    public static void afficheMethodes(Class cl) {
        for (Method m : cl.getDeclaredMethods()) {
            System.out.print( "  " + Modifier.toString(m.getModifiers()) + " "
                    + m.getReturnType().getTypeName() + " " + m.getName() + "(");
            Type [] types = m.getParameterTypes();
            for (int i = 0; i < types.length; i++) {
                System.out.print(types[i].getTypeName());
                if (i < types.length - 1) {
                    System.out.print(", ");
                }
            }

            System.out.print(");\n");
        }
    }

    public static String litChaineAuClavier() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }

    public static void main(String[] args) {
        boolean ok = false;

        while (!ok) {
            try {
                System.out.print("Entrez le nom d'une classe (ex : java.util.Date): ");
                String nomClasse = litChaineAuClavier();

                analyseClasse(nomClasse);

                ok = true;
            } catch (ClassNotFoundException e) {
                System.out.println("Classe non trouvée.");
            } catch (IOException e) {
                System.out.println("Erreur d'E/S!");
            }
        }
    }
}
