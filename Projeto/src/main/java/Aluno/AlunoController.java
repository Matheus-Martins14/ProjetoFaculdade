package Aluno;

import BancoDeDados.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoController {

    // Adiciona um novo aluno ao banco de dados
    public boolean adicionarAluno(Aluno aluno) {
        String sql = "INSERT INTO alunos (nome, data_nascimento, curso, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getDataNascimento());
            stmt.setString(3, aluno.getCurso());
            stmt.setString(4, aluno.getEmail());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Aluno cadastrado com sucesso: " + aluno.getNome());
                return true; // Indica sucesso
            } else {
                System.err.println("Falha ao cadastrar o aluno: " + aluno.getNome());
                return false; // Indica falha
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar aluno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Retorna uma lista com todos os alunos cadastrados
    public List<Aluno> listarAlunos() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM alunos";
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setDataNascimento(rs.getString("data_nascimento"));
                aluno.setCurso(rs.getString("curso"));
                aluno.setEmail(rs.getString("email"));
                alunos.add(aluno);
            }
            System.out.println("Alunos listados com sucesso. Total: " + alunos.size());
        } catch (SQLException e) {
            System.err.println("Erro ao listar alunos: " + e.getMessage());
            e.printStackTrace();
        }
        return alunos;
    }

    // Exclui um aluno pelo ID
    public boolean excluirAluno(int id) {
        String sql = "DELETE FROM alunos WHERE id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Aluno excluído com sucesso. ID: " + id);
                return true; // Indica sucesso
            } else {
                System.err.println("Nenhum aluno encontrado com o ID: " + id);
                return false; // Indica falha
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir aluno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Atualiza os dados de um aluno existente
    public boolean atualizarAluno(Aluno aluno) {
        String sql = "UPDATE alunos SET nome = ?, data_nascimento = ?, curso = ?, email = ? WHERE id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getDataNascimento());
            stmt.setString(3, aluno.getCurso());
            stmt.setString(4, aluno.getEmail());
            stmt.setInt(5, aluno.getId());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Aluno atualizado com sucesso. ID: " + aluno.getId());
                return true; // Indica sucesso
            } else {
                System.err.println("Nenhum aluno encontrado com o ID: " + aluno.getId());
                return false; // Indica falha
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar aluno: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
