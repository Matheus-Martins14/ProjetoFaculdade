package Aluno;

import MenuPrincipal.TelaPrincipal;

import javax.swing.*;
import java.awt.*;

public class AlunoView extends JFrame {
    private JTextField nomeField;
    private JTextField dataNascimentoField;
    private JTextField cursoField;
    private JTextField emailField;

    private AlunoController controller;

    public AlunoView() {
        controller = new AlunoController();
        setTitle("Cadastro de Alunos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel central para o formulário
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        formPanel.add(nomeField);

        formPanel.add(new JLabel("Data de Nascimento:"));
        dataNascimentoField = new JTextField();
        formPanel.add(dataNascimentoField);

        formPanel.add(new JLabel("Curso:"));
        cursoField = new JTextField();
        formPanel.add(cursoField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        add(formPanel, BorderLayout.CENTER);

        // Painel inferior para os botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Botão "Salvar"
        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> {
            Aluno aluno = new Aluno();
            aluno.setNome(nomeField.getText());
            aluno.setDataNascimento(dataNascimentoField.getText());
            aluno.setCurso(cursoField.getText());
            aluno.setEmail(emailField.getText());
            controller.adicionarAluno(aluno);
            JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");
        });
        buttonPanel.add(salvarButton);

        // Botão "Listar"
        JButton listarButton = new JButton("Listar");
        listarButton.addActionListener(e -> {
            StringBuilder alunosList = new StringBuilder("Alunos Cadastrados:\n");
            controller.listarAlunos().forEach(aluno ->
                    alunosList.append("ID: ").append(aluno.getId())
                            .append(", Nome: ").append(aluno.getNome())
                            .append(", Curso: ").append(aluno.getCurso()).append("\n")
            );
            JOptionPane.showMessageDialog(this, alunosList.toString());
        });
        buttonPanel.add(listarButton);

        // Botão "Voltar"
        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> {
            this.dispose(); // Fecha a janela atual
            TelaPrincipal.getInstance().setVisible(true); // Torna a tela principal visível novamente
        });
        buttonPanel.add(voltarButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
