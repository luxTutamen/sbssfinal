package ua.axiom;

import ua.axiom.model.Role;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public interface PostLoggedRedirectConfiguration {

    Map<Role, String> configuration = Stream.of(
            new AbstractMap.SimpleEntry<>(Role.GUEST, "/error"),
            new AbstractMap.SimpleEntry<>(Role.CLIENT, "/clientpage"),
            new AbstractMap.SimpleEntry<>(Role.DRIVER, "/driverpage"),
            new AbstractMap.SimpleEntry<>(Role.ADMIN, "/adminpage"))
            .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

}
