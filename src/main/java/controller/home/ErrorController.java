package controller.home;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class ErrorController {
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Order") // 404
	public class ResourceNotFoundException extends Exception {
		private static final long serialVersionUID = -3892048102324570919L;

		public ResourceNotFoundException() {
			super();
		}

		public ResourceNotFoundException(String arg0, Throwable arg1) {
			super(arg0, arg1);
		}

		public ResourceNotFoundException(String arg0) {
			super(arg0);
		}

		public ResourceNotFoundException(Throwable arg0) {
			super(arg0);
		}
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public String handle(Exception ex) {
		return "redirect:/error";
	}

	@RequestMapping(value = { "/error" }, method = RequestMethod.GET)
	public String NotFoudPage() {
		return "error";

	}
}
