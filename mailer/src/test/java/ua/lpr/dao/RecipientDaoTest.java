package ua.lpr.dao;

import org.junit.*;
import org.junit.runner.RunWith;
import ua.lpr.MailerApplication;
import ua.lpr.entity.Recipient;
import ua.lpr.util.PropertiesReader;

//@RunWith(MailerApplication.class)
public class RecipientDaoTest {
    private static PropertiesReader reader;
    private static RecipientDao recipientDao;
    private static int recipientId;
    private static Recipient recipient;

    @BeforeClass
    public static void init() {
        System.out.println("Before class");

        recipient = new Recipient(1, "Александр", "LPR.UA", null, "admin@lpr.ua", false, true);
        recipientId = recipient.getId();

        reader = new PropertiesReader();

        recipientDao =
                new RecipientDaoImpl(reader.getDBUrl(), reader.getDBUser(), reader.getDBPassword());
    }

    @Before
    public void prepareTest() {
        System.out.println("Before test");
    }

    @Test
    public void findOne() {
        Recipient recipient = recipientDao.findById(recipientId);

        Assert.assertSame(recipient, RecipientDaoTest.recipient);
    }

    @Test
    public void create() {
        System.out.println("test 1");
    }

    @Test
    public void update() {
        System.out.println("test 2");
    }

    @After
    public void destroyTest() {
        System.out.println("After test");
    }

    @AfterClass
    public static void destroy() {
        System.out.println("After class");
    }
}
