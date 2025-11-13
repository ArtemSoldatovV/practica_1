package org.example;

public class test {
    public static void main(String[] args) {
        try {
            // Установка соединения
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // Создаем таблицу (если нужно)
            String createTableSql = "CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, username VARCHAR(50), password VARCHAR(255))";
            Statement stmt = conn.createStatement();
            stmt.execute(createTableSql);

            // Добавляем данные
            String insertSql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement pstmtInsert = conn.prepareStatement(insertSql);
            pstmtInsert.setString(1, "user1");
            pstmtInsert.setString(2, "hashed_password");
            pstmtInsert.executeUpdate();

            // Получаем данные
            String selectSql = "SELECT * FROM users";
            ResultSet rs = stmt.executeQuery(selectSql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                System.out.printf("ID: %d, Username: %s, Password: %s%n", id, username, password);
            }

            // Закрываем ресурсы
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
