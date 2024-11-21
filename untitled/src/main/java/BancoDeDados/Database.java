package BancoDeDados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    // Caminho do banco de dados SQLite
    private static final String URL = "jdbc:sqlite:escola.db";

    // Método para obter conexão
    public static Connection connect() throws SQLException {
        try {
            // Registrar o driver do SQLite
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do SQLite não encontrado. Verifique se o .jar está no classpath.", e);
        }
        return DriverManager.getConnection(URL);
    }

    // Método para configurar o banco de dados
    public static void setupDatabase() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // Criar tabela Alunos
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS alunos (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    data_nascimento TEXT NOT NULL,
                    curso TEXT NOT NULL,
                    email TEXT NOT NULL
                );
            """);

            // Criar tabela Matrículas
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS matriculas (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    aluno_id INTEGER NOT NULL,
                    curso TEXT NOT NULL,
                    ano_letivo INTEGER NOT NULL,
                    status TEXT NOT NULL,
                    FOREIGN KEY(aluno_id) REFERENCES alunos(id)
                );
            """);

            // Criar tabela Notas
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS notas (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    aluno_id INTEGER NOT NULL,
                    disciplina TEXT NOT NULL,
                    nota REAL NOT NULL,
                    data_avaliacao TEXT NOT NULL,
                    FOREIGN KEY(aluno_id) REFERENCES alunos(id)
                );
            """);

            System.out.println("Banco de dados configurado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao configurar o banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Testar conexão (método opcional para depuração)
    public static void main(String[] args) {
        setupDatabase();
    }
}
