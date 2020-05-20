package controler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.rentmanager.exception.ServiceException;
import com.epf.RentManager.service.ClientService;
import com.epf.RentManager.service.ReservationService;
import com.epf.RentManager.service.VehicleService;

@WebServlet("/rents")
public class ReservationServlet extends HttpServlet {
ReservationService reservationService= ReservationService.getInstance();
VehicleService vehicleService= VehicleService.getInstance();
ClientService clientService= ClientService.getInstance();

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/rents/list.jsp");
		try {
			request.setAttribute("rents", reservationService.findAll());
			request.setAttribute("vehicles", vehicleService.findAll());
			request.setAttribute("users", clientService.findAll());
		} catch (ServiceException e) {
			request.setAttribute("nbUtilisateurs", "Une erreur est survenue");
		}
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
}
