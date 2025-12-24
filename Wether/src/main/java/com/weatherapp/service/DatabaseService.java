package com.weatherapp.service;
import com.weatherapp.exception.DatabaseException;
import com.weatherapp.model.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.Optional;

public class DatabaseService {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);
    private static final String Url = "jdbc:sqlite:weather.db";
    private Connection connection;

    public DatabaseService() throws DatabaseException {
        initializeDatabase();
    }

    private void initializeDatabase() throws DatabaseException {
        try {
            connection = DriverManager.getConnection(Url);
            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS favorite_city (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    country TEXT NOT NULL,
                    lat REAL NOT NULL,
                    lon REAL NOT NULL,
                    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
                """;

            try (Statement stmt = connection.createStatement()) {
                stmt.execute(createTableSQL);
            }

            logger.info("Database initialized succes");
        } catch (SQLException e) {
            logger.error("Failed to initialize database", e);
            throw new DatabaseException("Database initialization failed", e);
        }
    }

    public void saveFavoriteCity(City city) throws DatabaseException {
        String sql = "INSERT OR REPLACE INTO favorite_city (name, country, lat, lon) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, city.getName());
            pstmt.setString(2, city.getCountry());
            pstmt.setDouble(3, city.get_x());
            pstmt.setDouble(4, city.get_y());
            pstmt.executeUpdate();
            logger.info("Saved favorite city: {}", city.getName());
        } catch (SQLException e) {
            logger.error("Failed to save favorite city", e);
            throw new DatabaseException("Failed to save city", e);
        }
    }

    public Optional<City> loadFavoriteCity() throws DatabaseException
    {
        String sql = "SELECT name, country, lat, lon FROM favorite_city ORDER BY last_updated DESC LIMIT 1";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                City city = new City(
                        rs.getString("name"),
                        rs.getString("country"),
                        rs.getDouble("lat"),
                        rs.getDouble("lon")
                );
                logger.info("Loaded favorite city: {}", city.getName());
                return Optional.of(city);
            }
            return Optional.empty();
        } catch (SQLException e) {
            logger.error("Failed to load favorite city", e);
            throw new DatabaseException("Failed to load city", e);
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Database connection closed");
            }
        } catch (SQLException e) {
            logger.error("Error closing database connection", e);
        }
    }
}