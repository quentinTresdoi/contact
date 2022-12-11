import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import model.Contact;
import comparer.comparercontact;

public class App {
    int i;
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        afficherMenu();
        while (true) {
            String choix = scan.nextLine();
            switch (choix) {
                case "1":
                    ajouterContact();
                    break;
                case "2":
                    listerContact();
                    break;
                case "3":
                    modif();
                    break;
                case "4":
                    supprimer();
                    break;
                case "5":
                    trierContact();
                    break;
                case "6":
                    trierParDate();
                    break;
                case "7":
                    rechercheContact();
                    break;
                case "q":
                    scan.close();
                    return;
                default:
                    System.out.println("erreur");
                    break;
            }
            afficherMenu();
        }
    }

    private static void listerContact() {
        int i = 1;
        // Contact c = new Contact();
        try {
            ArrayList<Contact> liste = Contact.lister();

            for (Contact contact : liste) {
                System.out.println("contact "+i+": "+contact.getPrenom() + " " + contact.getNom());
                i++;
            }
        } catch (IOException e) {
            System.out.println("Erreur avec le fichier");
        }

    }

    private static void trierParDate() {
        try {
            ArrayList<Contact> liste;
            liste = Contact.lister();
            Collections.sort(liste, new comparercontact());

            for (Contact c : liste) {
                System.out.println(c.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void trierContact() {
        try {
            ArrayList<Contact> liste = Contact.lister();
            Collections.sort(liste, null);

            for (Contact contact : liste) {
                System.out.println(contact.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void modif(){        
        try {
            listerContact();
            Contact modif = null;
            List<Contact> list = Contact.lister();
            System.out.println("selectionné le mail du contact a modifier");
            String mail = scan.nextLine();
            for (Contact contact : list) {
                if (contact.getMail().equals(mail));
                    modif = contact;
            }

            if (modif != null) {
                System.out.println("Saisir le nom:");
                modif.setNom(scan.nextLine());
                System.out.println("Saisir le prénom:");
                modif.setPrenom(scan.nextLine());

                do {
                    try {
                        System.out.println("Saisir le téléphone:");
                        modif.setNumero(scan.nextLine());
                        break;
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                } while (true);

                do {
                    try {
                        System.out.println("Saisir le mail:");
                        modif.setMail(scan.nextLine());
                        break;
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                } while (true);

                do {
                    try {
                        System.out.println("Saisir la date de naissance:");
                        modif.setDateNaissance(scan.nextLine());
                        break;
                    } catch (ParseException e) {
                        System.out.println("Error, try again!");
                    }
                } while (true);
                Contact.enregistrerTous(list);
                System.out.println("Contact modifié");
            } else {
                System.out.println("Pas de contact a modifier");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void rechercheContact() {
        try {
            Stream<Contact> liste = Contact.lister().stream();
            System.out.println("Saisir recherche ?");
            String saisie = scan.nextLine();
            Stream<Contact> resultat = liste.filter(t -> t.getNom().contains(saisie));

            resultat.forEach(c -> System.out.println(c.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void supprimer() {
        try {
            listerContact();
            List<Contact> liste = Contact.lister();
            System.out.println("Mail du contact a supprimer ?");
            String mail = scan.nextLine();
            Contact contactSupprimer = null;
            for (Contact contact : liste) {
                if (contact.getMail().equals(mail)) {
                    contactSupprimer = contact;
                }
            }
            if (contactSupprimer != null) {
                liste.remove(contactSupprimer);
                Contact.enregistrerTous(liste);
                System.out.println("Contact supprimé");
            } else {
                System.out.println("Pas de contact a supprimer");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ajouterContact() throws IOException {

        Contact c = new Contact();
        System.out.println("Saisir le nom:");
        c.setNom(scan.nextLine());
        System.out.println("Saisir le prénom:");
        c.setPrenom(scan.nextLine());

        do {
            try {
                System.out.println("Saisir le nouveau téléphone:");
                c.setNumero(scan.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        do {
            try {
                System.out.println("Saisir le nouveau mail:");
                c.setMail(scan.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        do {
            try {
                System.out.println("Saisir la nouvelle date de naissance:");
                c.setDateNaissance(scan.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println("Erreur");
            }
        } while (true);

        c.enregistrer();

    }

    public static void afficherMenu() {
        ArrayList<String> menus = new ArrayList<>();
        menus.add("-- MENU --");
        menus.add("1- Ajouter un contact");
        menus.add("2- Lister les contacts");
        menus.add("3- Modifier contact");
        menus.add("4- Supprimer contact");
        menus.add("5- Trier les contacts");
        menus.add("6- Trier les contacts par date");
        menus.add("7- Rechercher les contacts sur nom");
        menus.add("q- Quitter");
        for (String s : menus) {
            System.out.println(s);
        }
    }
}