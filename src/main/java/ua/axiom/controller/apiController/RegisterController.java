package ua.axiom.controller.apiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.model.Role;
import ua.axiom.model.UserLocale;
import ua.axiom.model.dto.RegisterFormDto;
import ua.axiom.service.apiservice.RegisterService;
import ua.axiom.service.error.LightVerboseException;
import ua.axiom.service.error.exceptions.UserAlreadyPresentException;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping
    protected ModelAndView formResponse(ModelAndView modelAndView) {
        fillWithAuxiliaryData(modelAndView);
        modelAndView.setViewName("appPages/register");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView registerPost(
            @ModelAttribute @Valid RegisterFormDto registerFormData,
            BindingResult bindingResult
    ) throws LightVerboseException, UserAlreadyPresentException {

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("appPages/register");
            modelAndView.addObject("roles", Role.getRegisterAccessibleRoles());
            return modelAndView;
        }

        registerService.registerNewUser(registerFormData);

        return new ModelAndView("redirect:/");
    }

    private void fillWithAuxiliaryData(ModelAndView model) {
        model.addObject("locales", UserLocale.values());
        model.addObject("roles", Role.getRegisterAccessibleRoles());
        model.addObject("registerFormDto",  new RegisterFormDto());
    }

    @ExceptionHandler(UserAlreadyPresentException.class)
    private ModelAndView userAlreadyExistsExceptionHandling() {
        ModelAndView modelAndView = formResponse(new ModelAndView());
        modelAndView.addObject("user_already_exists_error", true);

        return modelAndView;
    }
}
