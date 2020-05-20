package controler;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.rentmanager.exception.ServiceException;
import com.ensta.rentmanager.model.Vehicle;
import com.epf.RentManager.service.VehicleService;


@WebServlet("/cars/create")
public class AddVehicleServlet extends HttpServlet{
	
	VehicleService vehicleService = VehicleService.getInstance();
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp");
	
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String constructeur = request.getParameter("constructeur");
		String modele= request.getParameter("modele");
		int nb_place = Integer.parseInt(request.getParameter("nb_places"));
		
		Vehicle newVehicle = new Vehicle();
		newVehicle.setConstructeur(constructeur);
		newVehicle.setModele(modele);;
		newVehicle.setNb_places(nb_place);
		RequestDispatcher dispatcher;
		
		try {
			vehicleService.create(newVehicle);
			request.setAttribute("vehicle", vehicleService.findAll());
			response.sendRedirect(request.getContextPath() + "/cars");
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", e.getMessage());
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp");
			dispatcher.forward(request, response);
		}
	}
}