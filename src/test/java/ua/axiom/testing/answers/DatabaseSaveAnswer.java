package ua.axiom.testing.answers;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Example;

import java.util.Collections;
import java.util.Set;
import java.util.function.Function;

public class DatabaseSaveAnswer<T, K> implements Answer<T> {
    private Set<K> persistedEntityKeys;
    private Function<T, K> keyExtractionFunction;

    public DatabaseSaveAnswer(Set<K> existingKeySet, Function<T, K> keyFunction) {
        this.persistedEntityKeys = existingKeySet;

        this.keyExtractionFunction = keyFunction;
    }

    public DatabaseSaveAnswer(Function<T, K> keyFunction) {
        this(Collections.emptySet(), keyFunction);
    }

    @Override
    public T answer(InvocationOnMock invocationOnMock) throws Throwable {
        T object = (T)(invocationOnMock.getArgument(0));
        K key =  keyExtractionFunction.apply(object);

       if(persistedEntityKeys.contains(key)) {
            throw new IllegalStateException();
        }

        persistedEntityKeys.add(key);
        return object;
    }
}
