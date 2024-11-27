package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import factory.ConnectionFactory;
import modelo.Aluno;
import java.time.LocalDate;

public class AlunoDAO {

    private Connection connection;

    public AlunoDAO() throws SQLException {
        this.connection = ConnectionFactory.getConnection(); // Supondo que o Factory está configurado
    }

    // Método para inserir aluno
    public void inserirAluno(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO alunos (cpf, nome, data_nascimento, peso, altura) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, aluno.getCpf());
            stmt.setString(2, aluno.getNome());
            stmt.setDate(3, Date.valueOf(aluno.getDataNascimento()));
            stmt.setDouble(4, aluno.getPeso());
            stmt.setDouble(5, aluno.getAltura());
            stmt.executeUpdate();
        }
    }

    // Método para atualizar aluno
    public void atualizarAluno(Aluno aluno) throws SQLException {
        String sql = "UPDATE alunos SET nome = ?, data_nascimento = ?, peso = ?, altura = ? WHERE cpf = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setDate(2, Date.valueOf(aluno.getDataNascimento()));
            stmt.setDouble(3, aluno.getPeso());
            stmt.setDouble(4, aluno.getAltura());
            stmt.setString(5, aluno.getCpf());
            stmt.executeUpdate();
        }
    }

    // Método para excluir aluno
    public void excluirAluno(String cpf) throws SQLException {
        String sql = "DELETE FROM alunos WHERE cpf = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        }
    }

    // Método para buscar aluno por CPF
    public Aluno consultarAluno(String cpf) throws SQLException {
        String sql = "SELECT * FROM alunos WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Aluno(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getDate("data_nascimento").toLocalDate(),
                        rs.getDouble("peso"),
                        rs.getDouble("altura")
                );
            }
        }
        return null;  // Caso não encontre aluno
    }
}