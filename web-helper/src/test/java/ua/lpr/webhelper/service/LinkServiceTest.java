package ua.lpr.webhelper.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.lpr.webhelper.entity.Link;
import ua.lpr.webhelper.service.impl.LinkServiceImpl;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class LinkServiceTest {

    private LinkService service;

    @BeforeAll
    public static void beforeAll() {

    }

    @AfterAll
    public static void afterAll() {

    }

    @BeforeEach
    public void initMock() {
        service = Mockito.mock(LinkServiceImpl.class);
    }

    @Test
    public void test() throws URISyntaxException {
        String actual = "";
        Mockito.when(service.get("", null)).thenReturn(Collections.EMPTY_SET);

        assertThat(actual).isNotNull()
            .isEqualTo("");
    }

}
