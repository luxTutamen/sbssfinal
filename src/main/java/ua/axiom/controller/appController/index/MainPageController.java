package ua.axiom.controller.appController.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.MultiViewController;

import java.util.function.Supplier;

@Controller
@RequestMapping("/")
//  todo remove FailureHandler
public class MainPageController extends MultiViewController {

    private static final Supplier<Boolean> ANONYMOUS_VIEW_PREDICATE =
            () -> SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getAuthorities()
                        .stream()
                        .anyMatch(a -> a.toString().equals("ROLE_ANONYMOUS"));

    private static final Supplier<Boolean> LOGGED_VIEW_PREDICATE =
            () -> SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getAuthorities()
                        .stream()
                        .anyMatch(a -> { return a.toString().equals("DRIVER")
                                                || a.toString().equals("CLIENT")
                                                || a.toString().equals("ADMIN"); });

    @Autowired
    public MainPageController(
            AnonymousMainPageController anonymousMainPageController,
            AuthorisedMainPageController authorisedMainPageController
    ) {

        addController(ANONYMOUS_VIEW_PREDICATE, anonymousMainPageController);
        addController(LOGGED_VIEW_PREDICATE, authorisedMainPageController);
    }

    @RequestMapping
    public ModelAndView mapRequest(@RequestParam(required = false) boolean error) {
        Model model = new ConcurrentModel();
        model.asMap().put("error", error);

        return super.getRequestMapping(model);
    }

    @ExceptionHandler({NullPointerException.class})
    public ModelAndView nullPointerExceptionErrorHandling(Model model) {
        model.addAttribute("error", "Usr not found");

        return super.getRequestMapping(model);
    }
}
