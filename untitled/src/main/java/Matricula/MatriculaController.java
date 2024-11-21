package Matricula;

import BancoDeDados.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculaController {
    public void adicionarMatricula(Matricula matricula) {
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO matriculas (aluno_id, curso, ano_letivo, status) VALUES (?, ?, ?, ?)")) {
            stmt.setInt(1, matricula.getAlunoId());
            stmt.setString(2, matricula.getCurso());
            stmt.setInt(3, matricula.getAnoLetivo());
            stmt.setString(4, matricula.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> listarMatriculasComNomes() {
        List<String> matriculasComNomes = new ArrayList<>();
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("""
                 SELECT m.id, m.curso, m.ano_letivo, m.status, a.nome
                 FROM matriculas m
                 INNER JOIN alunos a ON m.aluno_id = a.id
             """)) {
            while (rs.next()) {
                String matriculaInfo = "ID: " + rs.getInt("id") +
                        ", Aluno: " + rs.getString("nome") +
                        ", Curso: " + rs.getString("curso") +
                        ", Ano Letivo: " + rs.getInt("ano_letivo") +
                        ", Status: " + rs.getString("status");
                matriculasComNomes.add(matriculaInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matriculasComNomes;
    }

    public List<Matricula> listarMatriculas() {
        List<Matricula> matriculas = new ArrayList<>();
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM matriculas")) {
            while (rs.next()) {
                Matricula matricula = new Matricula();
                matricula.setId(rs.getInt("id"));
                matricula.setAlunoId(rs.getInt("aluno_id"));
                matricula.setCurso(rs.getString("curso"));
                matricula.setAnoLetivo(rs.getInt("ano_letivo"));
                matricula.setStatus(rs.getString("status"));
                matriculas.add(matricula);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matriculas;
    }

    public void excluirMatricula(int id) {
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM matriculas WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarMatricula(Matricula matricula) {
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE matriculas SET aluno_id = ?, curso = ?, ano_letivo = ?, status = ? WHERE id = ?")) {
            stmt.setInt(1, matricula.getAlunoId());
            stmt.setString(2, matricula.getCurso());
            stmt.setInt(3, matricula.getAnoLetivo());
            stmt.setString(4, matricula.getStatus());
            stmt.setInt(5, matricula.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
