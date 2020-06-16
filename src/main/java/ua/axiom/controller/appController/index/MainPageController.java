package ua.axiom.controller.appController.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.MultiViewController;
import ua.axiom.model.UserLocale;
import ua.axiom.service.LocalisationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

@Controller
@RequestMapping("/")
//  todo remove FailureHandler
public class MainPageController extends MultiViewController implements AuthenticationFailureHandler {

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

    private LocalisationService localisationService;

    @Autowired
    public MainPageController(
            LocalisationService localisationService,
            AnonymousMainPageController anonymousMainPageController,
            AuthorisedMainPageController authorisedMainPageController
    ) {
        this.localisationService = localisationService;

        addController(ANONYMOUS_VIEW_PREDICATE, anonymousMainPageController);
        addController(LOGGED_VIEW_PREDICATE, authorisedMainPageController);
    }

    @RequestMapping
    public ModelAndView mapRequest(@RequestParam(required = false) boolean error) {
        Model model = new ConcurrentModel();
        model.asMap().put("error", error);

        return super.getRequestMapping(model);
    }

    //  todo move out
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ModelAndView exceptionHandler() {
        Map<String, Object> model = super
                .getRequestMapping(new ConcurrentModel())
                .getModel();

        model.put("error", true);
        model.put("register-alert", true);

        localisationService.setLocalisedMessages(
                model,
                UserLocale.DEFAULT_LOCALE,
                "word.error",
                "sentence.wrong-credential-msg"
        );

        return new ModelAndView("index/anonymous", model);
    }

    //  todo move out
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws
            IOException,
            ServletException {
        response.sendRedirect("/?error=true");
    }

}

//  todo error in ClientPage on pagination FIX
