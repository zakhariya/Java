package ua.lpr.dao;

import org.junit.*;
import org.junit.runner.RunWith;
import ua.lpr.MailerApplication;
import ua.lpr.entity.Recipient;
import ua.lpr.util.PropertiesReader;

import java.sql.SQLException;
import java.util.List;

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

    @AfterClass
    public static void destroy() {
        System.out.println("After class");
    }

    @Before
    public void updateDB() throws SQLException {
        String sql =
                "TRUNCATE polygraphy_email_list;";

        recipientDao.executeSql(sql);

        System.out.println("Database truncated");

        sql = "INSERT INTO polygraphy_email_list (`name`, `company`, `email`) VALUES ('Александр', 'LPR.UA', 'admin@lpr.ua');";

        recipientDao.executeSql(sql);

        sql = "INSERT INTO polygraphy_email_list (`email`) VALUES ('alexander.zakhariya@gmail.com');";

        recipientDao.executeSql(sql);

        sql = "INSERT INTO polygraphy_email_list (`name`, `email`, `subscribed`) VALUES ('Alexander', 'master-kap@ukr.net', 0);";

        recipientDao.executeSql(sql);


        System.out.println("Database updated");
    }

    @After
    public void truncateDB() throws SQLException {
//        String sql =
//                "TRUNCATE polygraphy_email_list;";
//
//        recipientDao.executeSql(sql);
//
//        System.out.println("Database truncated");
    }

    @Test
    public void findAll() {
        List<Recipient> recipients = recipientDao.findAll();

        Assert.assertNotNull(recipients);
        Assert.assertEquals(2, recipients.size());
    }

    @Test
    public void findOne() {
        Recipient recipient = recipientDao.findById(recipientId);

        Assert.assertEquals(recipient, RecipientDaoTest.recipient);
    }

    @Test
    public void create() throws SQLException {
        Recipient newRecipient = new Recipient("Alex", null, null, "admin@lpr.ua");
        Recipient created = recipientDao.save(newRecipient);

        Assert.assertNotEquals(created, newRecipient);
        Assert.assertEquals(created, recipientDao.findById(created.getId()));
    }

    @Test
    public void update() throws SQLException {
        Recipient newRecipient = new Recipient(1, "Александр", "LPR.UA", null, "admin@lpr.ua", true, true);
        Recipient updated = recipientDao.save(newRecipient);

        Assert.assertNotEquals(newRecipient, recipient);
        Assert.assertEquals(updated, updated);
        Assert.assertEquals(updated, recipientDao.findById(updated.getId()));
    }
}
