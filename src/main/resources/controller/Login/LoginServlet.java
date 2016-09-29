package controller.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
		String rememberMe = request.getParameter("remember");
		Employee login = null;
		int loginID = 0;
		try {
			login = new Employee(email, password);
			loginID = new EmployeeDAO().loginUser(login);
		} catch (EmployeeException e) {
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("./").forward(request, response);
			return;
		}

		if (loginID > 0) {
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(1000);
			session.setAttribute("username", login.getFirstName());
			if (rememberMe != null && rememberMe.equals("Remember Me")) {
				Cookie remMe = new Cookie("email", email);
				Cookie remMe2 = new Cookie("password", password);
				response.addCookie(remMe2);
				response.addCookie(remMe);
			}

		} else {
			request.setAttribute("message", "Wrong username or password");
			request.getRequestDispatcher("./").forward(request, response);
		}
		response.sendRedirect("./index.jsp");

	}

}
