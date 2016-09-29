package controller.register;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.employee.Employee;
import model.employee.Employee.Jobs;
import model.employee.EmployeeDAO;
import model.exceptions.EmployeeException;

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String jobPar = request.getParameter("job");
		Jobs job = null;
		if (jobPar.equals(Jobs.DEVELOPER.toString())) {
			job = Jobs.DEVELOPER;
		}
		if (jobPar.equals(Jobs.MANAGER.toString())) {
			job = Jobs.MANAGER;
		}
		if (jobPar.equals(Jobs.QA.toString())) {
			job = Jobs.QA;
		}

		Employee regUser;
		int empID = 0;
		try {
			regUser = new Employee(firstName, lastName, job, password, email);
			empID = new EmployeeDAO().registerUser(regUser);
		} catch (EmployeeException e) {
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("./register.jsp").forward(request, response);
		}

		if (empID != 0) {
			response.sendRedirect("./index.jsp");
		} else {
			request.getRequestDispatcher("./menu.jsp").forward(request, response);

		}

	}

}
