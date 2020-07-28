package ua.axiom.testing.answers;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Example;
import ua.axiom.model.User;

import java.util.Set;
import java.util.function.Function;

public class DatabaseExistsAnswer<T extends User, K> implements Answer<Boolean> {
    private Set<K> existingKeySet;
    private Function<T, K> keyExtractionFunction;

    public DatabaseExistsAnswer(Set<K> existingKeySet, Function<T, K> keyExtractionFunction) {
        this.existingKeySet = existingKeySet;
        this.keyExtractionFunction = keyExtractionFunction;
    }

    @Override
    public Boolean answer(InvocationOnMock invocationOnMock) throws Throwable {
        return existingKeySet.contains(keyExtractionFunction.apply((T)(((Example)invocationOnMock.getArgument(0)).getProbe())));

    }
}
