package ua.axiom.testing.answers;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FindByUsernamePersistingAnswer<T, K> implements Answer<T> {
    private Collection<K> persistedEntityKeys;
    private Function<T, K> keyExtractionFunction;

    public FindByUsernamePersistingAnswer(Collection<T> persistedEntities, Function<T, K> keyFunction) {
        this.persistedEntityKeys = persistedEntities.stream()
                .map(keyFunction)
                .collect(Collectors.toSet());

        this.keyExtractionFunction = keyFunction;
    }

    public FindByUsernamePersistingAnswer(Function<T, K> keyFunction) {
        this(new HashSet<>(), keyFunction);
    }

    @Override
    public T answer(InvocationOnMock invocationOnMock) throws Throwable {
        K persistedEntityKey = keyExtractionFunction.apply(invocationOnMock.getArgument(0));

        if(persistedEntityKeys.contains(persistedEntityKey)) {
            throw new IllegalStateException();
        }

        persistedEntityKeys.add(persistedEntityKey);
        return invocationOnMock.getArgument(0);
    }
}
