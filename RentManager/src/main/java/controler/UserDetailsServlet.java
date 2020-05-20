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
import com.ensta.rentmanager.model.Client;
import com.epf.RentManager.service.ClientService;
import com.epf.RentManager.service.ReservationService;
import com.epf.RentManager.service.VehicleService;


@WebServlet("/users/details")
public class UserDetailsServlet extends HttpServlet{
	
	ClientService clientService = ClientService.getInstance();
	VehicleService vehicleService = VehicleService.getInstance();
	ReservationService reservationService = ReservationService.getInstance();
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/details.jsp");
		
		try {
			Optional<Client> test = clientService.findById(Integer.parseInt(request.getParameter("id")));
			if(test.isPresent()) {
				Client client = test.get();
				request.setAttribute("client", client);
				request.setAttribute("vehicles", vehicleService.findAll());
				request.setAttribute("rent_number", reservationService.findNumber(client));
				request.setAttribute("rent_info", reservationService.findByClientId(client.getId()));
			}
			
		}catch (ServiceException e) {
			e.printStackTrace();
		}
		
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
	}
}