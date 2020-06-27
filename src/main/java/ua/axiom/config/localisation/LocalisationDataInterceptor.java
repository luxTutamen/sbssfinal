package ua.axiom.config.localisation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import ua.axiom.model.UserLocale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LocalisationDataInterceptor implements HandlerInterceptor {
    private LocaleResolver localeResolver;

    public LocalisationDataInterceptor(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        request.setAttribute("locales", UserLocale.values());
        request.setAttribute("current_locale", localeResolver.resolveLocale(request).getDisplayLanguage());
    }
}
