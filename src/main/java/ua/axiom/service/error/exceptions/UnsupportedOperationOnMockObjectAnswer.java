package ua.axiom.service.error.exceptions;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import ua.axiom.service.error.LightVerboseException;

public class UnsupportedOperationOnMockObjectAnswer implements Answer<Object> {

    @Override
    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        throw new RuntimeException ( invocationOnMock.getMethod().getName() + " is not stubbed" );
    }
}
