package Notas;

import BancoDeDados.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaController {
    public void adicionarNota(Nota nota) {
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO notas (aluno_id, disciplina, nota, data_avaliacao) VALUES (?, ?, ?, ?)")) {
            stmt.setInt(1, nota.getAlunoId());
            stmt.setString(2, nota.getDisciplina());
            stmt.setDouble(3, nota.getNota());
            stmt.setString(4, nota.getDataAvaliacao());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Novo método para listar notas com o nome do aluno
    public List<String> listarNotasComNomes() {
        List<String> notasComNomes = new ArrayList<>();
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("""
                SELECT n.id, n.disciplina, n.nota, n.data_avaliacao, a.nome
                FROM notas n
                INNER JOIN alunos a ON n.aluno_id = a.id
             """)) {
            while (rs.next()) {
                String notaInfo = "ID: " + rs.getInt("id") +
                        ", Aluno: " + rs.getString("nome") +
                        ", Disciplina: " + rs.getString("disciplina") +
                        ", Nota: " + rs.getDouble("nota") +
                        ", Data: " + rs.getString("data_avaliacao");
                notasComNomes.add(notaInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notasComNomes;
    }

    public List<Nota> listarNotas() {
        List<Nota> notas = new ArrayList<>();
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM notas")) {
            while (rs.next()) {
                Nota nota = new Nota();
                nota.setId(rs.getInt("id"));
                nota.setAlunoId(rs.getInt("aluno_id"));
                nota.setDisciplina(rs.getString("disciplina"));
                nota.setNota(rs.getDouble("nota"));
                nota.setDataAvaliacao(rs.getString("data_avaliacao"));
                notas.add(nota);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notas;
    }

    public void excluirNota(int id) {
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM notas WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarNota(Nota nota) {
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE notas SET aluno_id = ?, disciplina = ?, nota = ?, data_avaliacao = ? WHERE id = ?")) {
            stmt.setInt(1, nota.getAlunoId());
            stmt.setString(2, nota.getDisciplina());
            stmt.setDouble(3, nota.getNota());
            stmt.setString(4, nota.getDataAvaliacao());
            stmt.setInt(5, nota.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
