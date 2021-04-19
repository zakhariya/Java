package ua.lpr.dao;

import com.mysql.cj.exceptions.WrongArgumentException;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import ua.lpr.entity.Recipient;
import ua.lpr.util.PropertiesReader;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.slf4j.LoggerFactory.getLogger;

public class RecipientDaoTest {
    private static final Logger log = getLogger(RecipientDaoTest.class);

    private static RecipientDao recipientDao;
    private static Recipient recipient;

    @BeforeClass
    public static void init() {
        recipient = new Recipient(1, "Александр", "LPR.UA", null, "admin@lpr.ua", false, true, "");

        PropertiesReader reader = new PropertiesReader();

        recipientDao =
                new RecipientDaoImpl(reader.getDBUrl(), reader.getDBUser(), reader.getDBPassword());
    }

    @AfterClass
    public static void destroy() {

    }

    @Before
    public void beforeTest() {
        truncateDB();
        updateDB();
    }

    @After
    public void afterTest() {

    }

    @Test
    public void findAll() throws SQLException {
        List<Recipient> recipients = recipientDao.findAll();

        Assert.assertNotNull(recipients);
        Assert.assertEquals(2, recipients.size());
    }

    @Test
    public void findOne() throws SQLException {
        int id = RecipientDaoTest.recipient.getId();
        Recipient recipient = recipientDao.findById(id);

        Assert.assertEquals(RecipientDaoTest.recipient, recipient);
    }

    @Test
    public void create() throws SQLException {
        Recipient newRecipient = new Recipient("Alex", null, null, "admin4ik@lpr.ua");
        Recipient created = recipientDao.save(newRecipient);

        Assert.assertNotEquals(created, newRecipient);
        Assert.assertEquals(created, recipientDao.findById(created.getId()));
    }

    @Test
    public void update() throws SQLException {
        Recipient newRecipient = new Recipient(1, "Александр", "LPR.UA", null, "admin@lpr.ua", true, true, null);
        Recipient updated = recipientDao.save(newRecipient);

        Assert.assertNotEquals(newRecipient, recipient);
        Assert.assertEquals(updated, updated);
        Assert.assertEquals(updated, recipientDao.findById(updated.getId()));
    }

    @Test(expected = NoSuchElementException.class)
    public void findNotExists() throws SQLException {
        recipientDao.findById(5);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createWithNullEmail() throws SQLException {
        thrown.expect(WrongArgumentException.class);
        thrown.expectMessage("Введите корректный email");

        Recipient nullEmail = new Recipient("Alex", null, null, null);
        recipientDao.save(nullEmail);
    }

    @Test
    public void createWithEmptyEmail() {
        Recipient emptyEmail = new Recipient("Alex", null, null, "");

        Exception exception =
                Assert.assertThrows(WrongArgumentException.class, () -> recipientDao.save(emptyEmail));

        Assert.assertEquals("Введите корректный email", exception.getMessage());
    }

    @Test
    public void createWithExistsEmail() throws SQLException {
        thrown.expect(SQLIntegrityConstraintViolationException.class);
        thrown.expectMessage("admin@lpr.ua");

        recipientDao.save(new Recipient("Александр", "LPR.UA", null, "admin@lpr.ua"));
    }

    @Test(expected = NoSuchElementException.class)
    public void updateWithNonExistsId() throws SQLException {
        recipientDao.save(new Recipient(5, "Александр", "LPR.UA", null, "admin@lpr.ua", false, false, null));
    }

    @Test
    public void updateWithExistsEmail() throws SQLException {
        thrown.expect(SQLIntegrityConstraintViolationException.class);
        thrown.expectMessage("admin@lpr.ua");

        recipientDao.save(new Recipient(2, "", "LPR.UA", null, "admin@lpr.ua", false, true, null));
    }

    @Test
    public void delete() throws SQLException {
        Assert.assertTrue(recipientDao.delete(3));
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteWithNotExists() throws SQLException {
        recipientDao.delete(5);
    }

    private void truncateDB() {
        String sql =
                "TRUNCATE polygraphy_email_list;";

        try {
            recipientDao.executeSql(sql);
            log.debug("Database truncated");
        } catch (SQLException e) {
            log.error("Cannot truncate table", e);
        }
    }

    private void updateDB() {
        String sql = "INSERT INTO polygraphy_email_list (`name`, `company`, `email`) VALUES ('Александр', 'LPR.UA', 'admin@lpr.ua');";

        try {
            recipientDao.executeSql(sql);
            sql = "INSERT INTO polygraphy_email_list (`email`) VALUES ('alexander.zakhariya@gmail.com');";

            recipientDao.executeSql(sql);

            sql = "INSERT INTO polygraphy_email_list (`name`, `email`, `subscribed`) VALUES ('Alexander', 'master-kap@ukr.net', 0);";

            recipientDao.executeSql(sql);

            log.debug("Database updated");
        } catch (SQLException e) {
            log.error("Cannot update table", e);
        }
    }
}