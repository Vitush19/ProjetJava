package controler;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.model.Client;
import com.ensta.rentmanager.model.Reservation;
import com.ensta.rentmanager.model.Vehicle;
import com.epf.RentManager.service.ClientService;
import com.epf.RentManager.service.ReservationService;
import com.epf.RentManager.service.VehicleService;

public class CliControler {
	
	private ClientService clientService = ClientService.getInstance();
	private VehicleService vehicleService = VehicleService.getInstance();
	private ReservationService reservationService = ReservationService.getInstance();
	
	public static void main(String[] args) {

		CliControler cli = new CliControler();
		boolean done = false;
		while(!done) {
			
		System.out.println("Liste des opérations");
		System.out.println("0 - Quitter");
		System.out.println("1 - Affiche la liste des clients");
		System.out.println("2 - Ajoute un client");
		System.out.println("3 - Affiche la liste des voitures");
		System.out.println("4 - Ajoute un véhicule");
		System.out.println("5 - Affiche un véhicule par son ID");
		System.out.println("6 - Supprime un véhicule par son ID");
		System.out.println("7 - Supprime un client par son ID");
		System.out.println("8 - Créer une réservation");
		System.out.println("9 - Afficher toutes les réservations");
		System.out.println("10 - Afficher toutes les réservations par ID client");
		System.out.println("11 - Afficher toutes les réservations par ID vehicule");
		System.out.println("12 - Supprimer une réservation");
		System.out.println("13 - Affiche un client par son ID");
		System.out.println("14 - Mettre à jour un client par son ID");
		System.out.println("15 - Mettre à jour un véhicule par son ID");
		System.out.println("16 - Mettre à jour une réservation par son ID");
		Scanner sc = new Scanner(System.in);
		
		int choix = sc.nextInt();
		sc.nextLine();
		
		switch(choix) {
		case 0:
			done = true;
			break;
		case 1:
			printAllClient(cli);
			break;
		case 2:
			Client client = new Client();
			
			System.out.println("Entrez le nom");
			client.setNom(sc.nextLine());
			System.out.println("Entrez le prénom");
			client.setPrenom(sc.nextLine());
			System.out.println("Entrez l'email");
			client.setEmail(sc.nextLine());
			System.out.println("Entrez la date au format yyyy-mm-dd");
			client.setNaissance(Date.valueOf(sc.nextLine()));
			
			try {
				cli.clientService.create(client);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			
			break;
		case 3:
			printAllVehicle(cli);
			break;
		case 4:
			Vehicle vehicle = new Vehicle();
			
			System.out.println("Entrez le constructeur");
			vehicle.setConstructeur(sc.nextLine());
			System.out.println("Entrez le modele");
			vehicle.setModele(sc.nextLine());
			System.out.println("Entrez le nb de places");
			vehicle.setNb_places(sc.nextInt());
			try {
				cli.vehicleService.create(vehicle);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			break;
		case 5:
			try {
			System.out.println("Veuillez rentrer l'ID du vehicle que vous souhaitez obtenir :");
			int id = sc.nextInt();
			cli.vehicleService.findById(id);
			} catch(ServiceException e) {
				System.out.println("Une erreur est survenue :"+ e.getMessage());
			}
			break;
		case 6:
			try {
			System.out.println("Veuillez rentrer l'ID de la voiture que vous souhaitez enlever");
			int id = sc.nextInt();
			cli.vehicleService.delete(id);
			} catch (ServiceException e) {
				System.out.println("Une erreur est survenue : "+ e.getMessage());
			}
			break;
		case 7:
			try {
				System.out.println("Veuillez rentrer l'ID du client que vous souhaitez enlever");
				int id = sc.nextInt();
				cli.vehicleService.delete(id);
				} catch (ServiceException e) {
					System.out.println("Une erreur est survenue : "+ e.getMessage());
				}
			break;
		case 8:
			Reservation reservation = new Reservation();
			
			System.out.println("Veuillez rentrer l'ID du client");
			reservation.setClient_id(sc.nextInt()); 
			System.out.println("Veuillez rentrer l'ID du vehicule");
			reservation.setVehicle_id(sc.nextInt());
			System.out.println("Entrez la date de debut au format yyyy-mm-dd");
			reservation.setDebut(Date.valueOf(sc.nextLine()));
			System.out.println("Entrez la date de fin au format yyyy-mm-dd");
			reservation.setFin(Date.valueOf(sc.nextLine()));
			try {
				cli.reservationService.create(reservation);
			} catch (ServiceException e1) {
				e1.printStackTrace();
			}
			break;
		case 9:
			printAllReservation(cli);
			break;
		case 10:
			try {
				System.out.println("Veuillez rentrer l'ID du client pour le vehicule en question :");
				int id = sc.nextInt();
				cli.reservationService.findByClientId(id);
				} catch(ServiceException e) {
					System.out.println("Une erreur est survenue :"+ e.getMessage());
				}
			break;
		case 11:
			try {
				System.out.println("Veuillez rentrer l'ID du vehicule pour le vehicule en question :");
				int id = sc.nextInt();
				cli.reservationService.findByClientId(id);
				} catch(ServiceException e) {
					System.out.println("Une erreur est survenue :"+ e.getMessage());
				}
			break;
		case 12:
			try {
				System.out.println("Veuillez rentrer l'ID de la réservation que vous souhaitez enlever");
				int id = sc.nextInt();
				cli.reservationService.delete(id);
				} catch (ServiceException e) {
					System.out.println("Une erreur est survenue : "+ e.getMessage());
				}
			break;
		case 13:
			try {
				System.out.println("Veuillez rentrer l'ID du client que vous souhaitez obtenir :");
				int id = sc.nextInt();
				cli.clientService.findById(id);
				} catch(ServiceException e) {
					System.out.println("Une erreur est survenue :"+ e.getMessage());
				}
				break;
		case 14:
			Client clientbis = new Client();
			
			System.out.println("Entrez l'id du client");
			clientbis.setId(sc.nextInt());
			System.out.println("Entrez le nom");
			clientbis.setNom(sc.nextLine());
			System.out.println("Entrez le prénom");
			clientbis.setPrenom(sc.nextLine());
			System.out.println("Entrez l'email");
			clientbis.setEmail(sc.nextLine());
			System.out.println("Entrez la date au format yyyy-mm-dd");
			clientbis.setNaissance(Date.valueOf(sc.nextLine()));
			
			try {
				cli.clientService.update(clientbis, clientbis.getEmail());
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			break;
		case 15:
			Vehicle vehiclebis = new Vehicle();
			
			System.out.println("Entrez l'id du vehicule");
			vehiclebis.setId(sc.nextInt());
			System.out.println("Entrez le constructeur");
			vehiclebis.setConstructeur(sc.nextLine());
			System.out.println("Entrez le modele");
			vehiclebis.setModele(sc.nextLine());
			System.out.println("Entrez le nb de places");
			vehiclebis.setNb_places(sc.nextInt());
			try {
				cli.vehicleService.update(vehiclebis);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			break;
		case 16:
			Reservation reservationbis = new Reservation();
			
			System.out.println("Entrez l'id de la réservation");
			reservationbis.setId(sc.nextInt());
			System.out.println("Veuillez rentrer l'ID du client");
			reservationbis.setClient_id(sc.nextInt()); 
			System.out.println("Veuillez rentrer l'ID du vehicule");
			reservationbis.setVehicle_id(sc.nextInt());
			System.out.println("Entrez la date de debut au format yyyy-mm-dd");
			reservationbis.setDebut(Date.valueOf(sc.nextLine()));
			System.out.println("Entrez la date de fin au format yyyy-mm-dd");
			reservationbis.setFin(Date.valueOf(sc.nextLine()));
			try {
				cli.reservationService.update(reservationbis, reservationbis.getDebut(), reservationbis.getFin());
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			break;
		default:
			System.out.println("PAS LE BON CHOIX");
			}
		//sc.close();
		}
		
	}

	private static void printAllVehicle(CliControler cli) {
		try {
			List<Vehicle> list = cli.vehicleService.findAll();
			
			for(Vehicle vehicle : list) {
				System.out.println(vehicle);
			}
		}catch(ServiceException e) {
			System.out.println("Une erreur est survenue : "+e.getMessage());
		}
	}

	private static void printAllClient(CliControler cli) {
		try {
			List<Client> list = cli.clientService.findAll();
			
			for(Client client : list) {
				System.out.println(client);
		}
		
		} catch (ServiceException e) {
			System.out.println("Une erreur est survenue : "+e.getMessage());
		}
	}
	
	private static void printAllReservation(CliControler cli) {
		try {
			List<Reservation> list = cli.reservationService.findAll();
			
			for(Reservation reservation : list) {
				System.out.println(reservation);
		}
		
		} catch (ServiceException e) {
			System.out.println("Une erreur est survenue : "+e.getMessage());
		}
	}
}
