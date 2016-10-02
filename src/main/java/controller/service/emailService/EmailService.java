package controller.service.emailService;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.employee.EmployeeDAO;
import model.exceptions.EmployeeException;

@WebServlet("/checkemail")
public class EmailService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
		int check = 0;
		String email = request.getParameter("email");
		
		try {
			check = new EmployeeDAO().validEmail(email);
		} catch (EmployeeException e) {
			response.getWriter().println("{}");
		}
		if (check != 0) {
			response.getWriter().println("{\"photo\" : \"no.png\"}");
		} else {
			response.getWriter().println("{\"photo\" : \"yes.png\"}");
		}

	}

}
