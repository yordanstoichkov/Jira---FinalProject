package controller.mainpage;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggedPages extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession(false) == null) {
			response.sendRedirect("./");
			return;
		}

		request.getRequestDispatcher((String) request.getAttribute("destinationPage")).forward(request, response);
	}

}
