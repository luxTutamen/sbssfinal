package ua.axiom.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Provides abstraction of a ViewController, that has different View and Controllers, depending on
 */
public abstract class MultiViewController {
    private final Map<Supplier<Boolean>, Function<Model, ModelAndView>> requestMapping = new HashMap<>();

    /**
     * Adds new controller
     * @param useCase controller is used, if this is true
     * @param controller the controller itself, consumes Model, so chains of an controllers can be used
     */
    protected void addController(Supplier<Boolean> useCase,  Function<Model, ModelAndView> controller) {
        requestMapping.put(useCase, controller);
    }

    /**
     * Consumes model, and returns ModelAndView with data from Model and also data, provided by controller
     * @param model with data from previous controller
     * @return ModelAndView with data and pattern template
     */
    public ModelAndView getRequestMapping(Model model) {
        return getViewController()
                .apply(model);
    }

    /**
     * Gets one controller from "requestMapping", based on predicate
     * @return the only Controller, that fits the predicate requirements
     */
    private Function<Model, ModelAndView> getViewController() {
        List<Function<Model, ModelAndView>> mapping = requestMapping
                .entrySet()
                .stream()
                .filter(entryS -> entryS.getKey().get())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        assert mapping.size() == 1;

        return mapping.get(0);
    }
}
