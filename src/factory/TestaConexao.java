package factory;

import java.sql.Connection;
import java.sql.SQLException;

public class TestaConexao {
    public static void main(String[] args) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            if (conn != null) {
                System.out.println("Conexão bem-sucedida!");
            } else {
                System.out.println("Falha ao estabelecer a conexão.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao testar a conexão: " + e.getMessage());
        }
    }
}
