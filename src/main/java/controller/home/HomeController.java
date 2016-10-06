package controller.home;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@Controller
public class HomeController {
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String showIndex(Model model) {

		return "index";
	}

}
