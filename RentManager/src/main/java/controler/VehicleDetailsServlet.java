package controler;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.model.Vehicle;
import com.epf.RentManager.service.ClientService;
import com.epf.RentManager.service.ReservationService;
import com.epf.RentManager.service.VehicleService;


@WebServlet("/cars/details")
public class VehicleDetailsServlet extends HttpServlet{
	
	ClientService clientService = ClientService.getInstance();
	VehicleService vehicleService = VehicleService.getInstance();
	ReservationService reservationService = ReservationService.getInstance();
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp");
		
		try {
			Optional<Vehicle> test = vehicleService.findById(Integer.parseInt(request.getParameter("id")));
			if(test.isPresent()) {
				Vehicle vehicle = test.get();
				request.setAttribute("vehicle", vehicle);
				request.setAttribute("clients", clientService.findAll());
				request.setAttribute("rent_number", reservationService.findReservationNumber(vehicle));
				request.setAttribute("rent_info", reservationService.findByVehicleId(vehicle.getId())); //check again 
			}
			
		}catch (ServiceException e) {
			e.printStackTrace();
		}
		
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
	}
}