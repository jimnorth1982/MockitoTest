package org.jimnorth1982.mock;

import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Service;

import java.util.List;

public class HelloService {
    private List<String> mockedList;

    public int getListSize() {
        return mockedList.size();
    }

    public List<String> getMockedList() {
        return mockedList;
    }
}
