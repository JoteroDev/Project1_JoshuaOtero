package smoketests;

import org.example.utils.ConnectionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionTest {
    public ConnectionTest() {
    }

    @Test
    void connection_available() throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        System.out.println(connection);
        Assertions.assertNotNull(connection);
    }
}
