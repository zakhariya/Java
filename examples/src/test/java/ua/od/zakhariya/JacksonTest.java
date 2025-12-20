package ua.od.zakhariya;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import ua.od.zakhariya.serialization.jackson.model.Car;
import ua.od.zakhariya.serialization.jackson.ObjectMapperBuilder;
import ua.od.zakhariya.serialization.jackson.model.Request;

import java.util.Date;

public class JacksonTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public JacksonTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(JacksonTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        ObjectMapper mapper = new ObjectMapperBuilder()
                .enableIndentation()
                .dateFormat()
                .preserveOrder(true)
                .build();

        Car givenCar = new Car("White", "Sedan");
        String givenCarJsonStr = "{ \"color\" : \"White\", \"type\" : \"Sedan\" }";

        Car actualObj = null;
        try {
            actualObj = mapper.readValue(givenCarJsonStr, Car.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assertEquals("White", actualObj.getColor());
        assertEquals("Sedan", actualObj.getType());

        Request request = new Request();
        request.setCar(givenCar);
        Date date = new Date(1684909857000L);
        request.setDatePurchased(date);

        String actualStr = null;
        try {
            actualStr = mapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String expected = "{\n" + "  \"car\" : {\n" + "    \"color\" : \"White\",\n" +
                "    \"type\" : \"Sedan\"\n" + "  },\n" + "  \"datePurchased\" : \"2023-05-24 12:00 PM IST\"\n" +
                "}";
        assertEquals(expected, actualStr);

        assertTrue( true );
    }
}
