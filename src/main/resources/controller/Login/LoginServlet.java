package controller.Login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.employee.Employee;
import model.employee.EmployeeDAO;
import model.exceptions.EmployeeException;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		Employee login = null;
		int loginID = 0;
		try {
			login = new Employee(email, password);
			loginID = new EmployeeDAO().loginUser(login);
		} catch (EmployeeException e) {

		}

		if (loginID > 0) {
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(10);
			session.setAttribute("username", login.getFirstName());

		}
		response.sendRedirect("./index.jsp");

	}

}
