package ua.axiom.controller;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class MultiViewController {
    private final Map<Supplier<Boolean>, Function<Model, ModelAndView>> requestMapping = new HashMap<>();

    protected void addController(Supplier<Boolean> useCase,  Function<Model, ModelAndView> controller) {
        requestMapping.put(useCase, controller);
    }

    public ModelAndView getRequestMapping(Model model) {
        return getViewController()
                .apply(model);
    }

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
