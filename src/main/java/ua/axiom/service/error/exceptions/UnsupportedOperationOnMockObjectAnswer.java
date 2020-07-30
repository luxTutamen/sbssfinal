package ua.axiom.service.error.exceptions;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class UnsupportedOperationOnMockObjectAnswer implements Answer<Object> {

    @Override
    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        throw new RuntimeException ( invocationOnMock.getMethod().getName() + " is not stubbed" );
    }
}
