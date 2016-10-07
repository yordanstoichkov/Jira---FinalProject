package controller.service.emailService;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.employee.Employee;
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
		if (!Employee.isEmailValid(email)) {
			response.getWriter().println("{\"photo\" : \"no.png\"}");
			return;
		}
		EmployeeDAO dao;
		try {
			check = new EmployeeDAO().validEmail(email);
		} catch (EmployeeException e) {
			response.getWriter().println("{}");
			return;
		}
		if (check != 0) {
			response.getWriter().println("{\"photo\" : \"no.png\"}");
		} else {
			response.getWriter().println("{\"photo\" : \"yes.png\"}");
		}

	}
	
	

}
