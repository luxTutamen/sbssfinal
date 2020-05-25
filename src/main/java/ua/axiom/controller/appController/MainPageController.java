package ua.axiom.controller.appController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.controller.MultiViewController;
import ua.axiom.controller.MustacheController;
import ua.axiom.controller.appController.index.AnonymousMainPageController;
import ua.axiom.controller.appController.index.AuthorisedMainPageController;
import ua.axiom.model.objects.User;
import ua.axiom.model.objects.UserLocale;
import ua.axiom.repository.UserRepository;
import ua.axiom.service.LocalisationService;
import ua.axiom.service.userpersistance.UserProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

@Controller
@RequestMapping("/")
//  todo remove FailureHandler
public class MainPageController extends MultiViewController implements AuthenticationFailureHandler {

    private static final Supplier<Boolean> anonymousViewPredicate =
            () -> SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getAuthorities()
                        .stream()
                        .anyMatch(a -> a.toString().equals("ROLE_ANONYMOUS"));

    private static final Supplier<Boolean> loggedViewPredicates =
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

        addController(anonymousViewPredicate, anonymousMainPageController);
        addController(loggedViewPredicates, authorisedMainPageController);
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
                UserLocale.DEFAULT_LOCALE.toJavaLocale(),
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
