package gui;

import dao.AlunoDAO;
import modelo.Aluno;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.sql.SQLException;

public class AlunoGUI {
    private JFrame frame;
    private JTextField tfCpf, tfNome, tfPeso, tfAltura, tfDataNascimento;
    private AlunoDAO alunoDAO;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AlunoGUI window = new AlunoGUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AlunoGUI() {
        try {
            alunoDAO = new AlunoDAO(); // Inicializa o DAO
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados.");
            System.exit(0); // Fecha o programa se não conseguir conectar com o banco
        }
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Sistema de Alunos");
        frame.setBounds(100, 100, 500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Labels
        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setBounds(10, 10, 80, 25);
        frame.getContentPane().add(lblCpf);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(10, 40, 80, 25);
        frame.getContentPane().add(lblNome);

        JLabel lblDataNascimento = new JLabel("Data Nascimento (AAAA-MM-DD):");
        lblDataNascimento.setBounds(10, 70, 200, 25);
        frame.getContentPane().add(lblDataNascimento);

        JLabel lblPeso = new JLabel("Peso:");
        lblPeso.setBounds(10, 100, 80, 25);
        frame.getContentPane().add(lblPeso);

        JLabel lblAltura = new JLabel("Altura:");
        lblAltura.setBounds(10, 130, 80, 25);
        frame.getContentPane().add(lblAltura);

        // Text Fields
        tfCpf = new JTextField();
        tfCpf.setBounds(100, 10, 200, 25);
        frame.getContentPane().add(tfCpf);

        tfNome = new JTextField();
        tfNome.setBounds(100, 40, 200, 25);
        frame.getContentPane().add(tfNome);

        tfDataNascimento = new JTextField();
        tfDataNascimento.setBounds(210, 70, 100, 25);
        frame.getContentPane().add(tfDataNascimento);

        tfPeso = new JTextField();
        tfPeso.setBounds(100, 100, 200, 25);
        frame.getContentPane().add(tfPeso);

        tfAltura = new JTextField();
        tfAltura.setBounds(100, 130, 200, 25);
        frame.getContentPane().add(tfAltura);

        // Botão "Inserir Aluno"
        JButton btnInserir = new JButton("Inserir Aluno");
        btnInserir.setBounds(10, 160, 150, 25);
        frame.getContentPane().add(btnInserir);

        // Botão "Alterar Aluno"
        JButton btnAlterar = new JButton("Alterar Aluno");
        btnAlterar.setBounds(170, 160, 150, 25);
        frame.getContentPane().add(btnAlterar);

        // Botão "Excluir Aluno"
        JButton btnExcluir = new JButton("Excluir Aluno");
        btnExcluir.setBounds(10, 200, 150, 25);
        frame.getContentPane().add(btnExcluir);

        // Botão "Consultar Aluno"
        JButton btnConsultar = new JButton("Consultar Aluno");
        btnConsultar.setBounds(170, 200, 150, 25);
        frame.getContentPane().add(btnConsultar);

        // Ação do botão Inserir
        btnInserir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String cpf = tfCpf.getText();
                    String nome = tfNome.getText();
                    LocalDate dataNasc = LocalDate.parse(tfDataNascimento.getText());
                    double peso = Double.parseDouble(tfPeso.getText());
                    double altura = Double.parseDouble(tfAltura.getText());

                    // Cria o objeto Aluno
                    Aluno aluno = new Aluno(cpf, nome, dataNasc, peso, altura);

                    // Calcula o IMC e grava os dados em um arquivo
                    aluno.calculaIMC();
                    aluno.gravarIMCEmArquivo();

                    // Insere no banco
                    alunoDAO.inserirAluno(aluno);

                    JOptionPane.showMessageDialog(frame, "Aluno inserido e IMC calculado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ação do botão Alterar
        btnAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String cpf = tfCpf.getText();
                    Aluno aluno = alunoDAO.consultarAluno(cpf);
                    if (aluno != null) {
                        aluno.setNome(tfNome.getText());
                        aluno.setDataNascimento(LocalDate.parse(tfDataNascimento.getText()));
                        aluno.setPeso(Double.parseDouble(tfPeso.getText()));
                        aluno.setAltura(Double.parseDouble(tfAltura.getText()));

                        alunoDAO.atualizarAluno(aluno);
                        JOptionPane.showMessageDialog(frame, "Aluno atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Aluno não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao atualizar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ação do botão Excluir
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String cpf = tfCpf.getText();
                    alunoDAO.excluirAluno(cpf);
                    JOptionPane.showMessageDialog(frame, "Aluno excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao excluir: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ação do botão Consultar
        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String cpf = tfCpf.getText();
                    Aluno aluno = alunoDAO.consultarAluno(cpf);
                    if (aluno != null) {
                        tfNome.setText(aluno.getNome());
                        tfDataNascimento.setText(aluno.getDataNascimento().toString());
                        tfPeso.setText(String.valueOf(aluno.getPeso()));
                        tfAltura.setText(String.valueOf(aluno.getAltura()));
                    } else {
                        JOptionPane.showMessageDialog(frame, "Aluno não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao consultar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}