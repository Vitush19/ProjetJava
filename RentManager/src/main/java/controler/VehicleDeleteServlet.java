package controler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ensta.rentmanager.exception.ServiceException;
import com.epf.RentManager.service.VehicleService;


@WebServlet("/cars/delete")
public class VehicleDeleteServlet extends HttpServlet{
	
	VehicleService vehicleService = VehicleService.getInstance();
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int id = Integer.parseInt(request.getParameter("id"));
		
		try {
				request.setAttribute("vehicle", vehicleService.delete(id));
				response.sendRedirect(request.getContextPath() + "/cars");
		}catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
}