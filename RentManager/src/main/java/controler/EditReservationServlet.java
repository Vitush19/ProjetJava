package controler;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.model.Reservation;
import com.epf.RentManager.service.ClientService;
import com.epf.RentManager.service.ReservationService;
import com.epf.RentManager.service.VehicleService;


@WebServlet("/rents/edit")
public class EditReservationServlet extends HttpServlet{
	
	ClientService clientService = ClientService.getInstance();
	VehicleService vehicleService = VehicleService.getInstance();
	ReservationService reservationService = ReservationService.getInstance();
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/edit.jsp");
		int reservationid = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("reservationid", reservationid);
		try {
			request.setAttribute("rents", reservationService.findAll());
			request.setAttribute("vehicles", vehicleService.findAll());
			request.setAttribute("users", clientService.findAll());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int id = Integer.parseInt(request.getParameter("idreservation"));
		int client_id = Integer.parseInt(request.getParameter("client_id"));
		int vehicle_id = Integer.parseInt(request.getParameter("vehicle_id"));
		Date debut = Date.valueOf(request.getParameter("debut"));
		Date fin = Date.valueOf(request.getParameter("fin"));
		
		Reservation newReservation = new Reservation();
		
		newReservation.setClient_id(client_id);
		newReservation.setVehicle_id(vehicle_id);
		newReservation.setDebut(debut);
		newReservation.setFin(fin);
		newReservation.setId(id);
		RequestDispatcher dispatcher;
		
		try {
			reservationService.update(newReservation, debut, fin);
			request.setAttribute("rents", reservationService.findAll());
			response.sendRedirect(request.getContextPath() + "/rents");
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", e.getMessage());
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/edit.jsp");
			dispatcher.forward(request, response);
		}
		
		
	}
}