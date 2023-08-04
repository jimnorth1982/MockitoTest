package org.jimnorth1982.mock;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class AppTest {

    private static final Logger logger = LoggerFactory.getLogger(AppTest.class);

    @Rule
    public MockitoRule initRule = MockitoJUnit.rule();

    @Mock
    List<String> mockedList;

    @InjectMocks
    HelloService helloService = new HelloService();

    @Spy
    List<String> spiedList = new ArrayList<>();

    @BeforeEach
    public void init() {
        try (AutoCloseable mocks = MockitoAnnotations.openMocks(this)) {
            logger.info("Successfully opened mocks");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void whenNotUseMockAnnotation_thenCorrect() {
        mockedList.add("one");
        Mockito.verify(mockedList).add("one");
        assertEquals(0, mockedList.size());

        Mockito.when(mockedList.size()).thenReturn(100);
        assertEquals(100, mockedList.size());
    }

    @Test
    public void whenUseSpyAnnotation_thenSpyIsInjectedCorrectly() {
        spiedList.add("one");
        spiedList.add("two");

        Mockito.verify(spiedList).add("one");
        Mockito.verify(spiedList).add("two");

        assertEquals(2, spiedList.size());

        Mockito.doReturn(100).when(spiedList).size();
        assertEquals(100, spiedList.size());
    }

    @Test
    public void whenHelloServiceIsCalled_MockInjectionIsSuccessful() {
        helloService.getMockedList().add("one");
        Mockito.verify(helloService.getMockedList()).add("one");

        assertEquals(0, helloService.getListSize());

        Mockito.when(helloService.getMockedList().size()).thenReturn(100);
        assertEquals(100, helloService.getMockedList().size());

        Mockito.when(helloService.getMockedList().get(0)).thenReturn("one");
        assertEquals("one", helloService.getMockedList().get(0));
    }
}
