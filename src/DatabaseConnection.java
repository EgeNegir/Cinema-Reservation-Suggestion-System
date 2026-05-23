import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Singleton örneği (instance)
    private static DatabaseConnection instance;
    private Connection connection;

    // Veritabanı bilgileri
    private String url = "jdbc:postgresql://localhost:5432/sinema_db";
    private String user = "postgres";
    private String password = "1234";

    // Private Constructor (Dışarıdan new ile oluşturulamaz)
    private DatabaseConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Veritabanı bağlantısı başarıyla kuruldu."); //vt tuneli acma
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL Sürücüsü bulunamadı: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    // Singleton metodumuz
    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}