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
import com.ensta.rentmanager.model.Client;
import com.epf.RentManager.service.ClientService;


@WebServlet("/users/create")
public class AddClientServlet extends HttpServlet{
	
	ClientService clientService = ClientService.getInstance();
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/create.jsp");
	
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String last_name = request.getParameter("last_name");
		String first_name = request.getParameter("first_name");
		String email = request.getParameter("email");
		Date birthdate = Date.valueOf(request.getParameter("birthdate"));
		
		Client newClient = new Client();
		newClient.setNom(last_name);
		newClient.setPrenom(first_name);
		newClient.setEmail(email);
		newClient.setNaissance(birthdate);
		RequestDispatcher dispatcher;
		try {
			clientService.create(newClient);
			request.setAttribute("clients", clientService.findAll());
			response.sendRedirect(request.getContextPath() + "/users");
			
		} catch (ServiceException e) {
			request.setAttribute("errorMessage", e.getMessage());
			dispatcher = request.getRequestDispatcher("/WEB-INF/views/users/create.jsp");
			dispatcher.forward(request, response);
		}
		
	}
}