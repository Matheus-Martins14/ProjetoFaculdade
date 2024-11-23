package Matricula;

import Aluno.AlunoController;
import MenuPrincipal.TelaPrincipal;

import javax.swing.*;
import java.awt.*;

public class MatriculaView extends JFrame {
    private JComboBox<String> alunoSelect;
    private JTextField cursoField;
    private JTextField anoLetivoField;
    private JComboBox<String> statusSelect;

    private MatriculaController controller;

    public MatriculaView() {
        controller = new MatriculaController();
        setTitle("Cadastro de Matrículas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel central para o formulário
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Aluno:"));
        alunoSelect = new JComboBox<>();
        carregarAlunos(); // Carrega os alunos no JComboBox
        formPanel.add(alunoSelect);

        formPanel.add(new JLabel("Curso:"));
        cursoField = new JTextField();
        formPanel.add(cursoField);

        formPanel.add(new JLabel("Ano Letivo:"));
        anoLetivoField = new JTextField();
        formPanel.add(anoLetivoField);

        formPanel.add(new JLabel("Status:"));
        statusSelect = new JComboBox<>(new String[]{"Ativo", "Inativo"});
        formPanel.add(statusSelect);

        add(formPanel, BorderLayout.CENTER);

        // Painel inferior para os botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Botão "Salvar"
        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> {
            Matricula matricula = new Matricula();
            matricula.setAlunoId(Integer.parseInt(alunoSelect.getSelectedItem().toString().split(" - ")[0]));
            matricula.setCurso(cursoField.getText());
            matricula.setAnoLetivo(Integer.parseInt(anoLetivoField.getText()));
            matricula.setStatus(statusSelect.getSelectedItem().toString());
            controller.adicionarMatricula(matricula);
            JOptionPane.showMessageDialog(this, "Matrícula cadastrada com sucesso!");
        });
        buttonPanel.add(salvarButton);

        // Botão "Listar"
        JButton listarButton = new JButton("Listar");
        listarButton.addActionListener(e -> {
            StringBuilder matriculasList = new StringBuilder("Matrículas Cadastradas:\n");
            controller.listarMatriculasComNomes().forEach(matricula ->
                    matriculasList.append(matricula).append("\n")
            );
            JOptionPane.showMessageDialog(this, matriculasList.toString());
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

    private void carregarAlunos() {
        AlunoController alunoController = new AlunoController();
        alunoSelect.removeAllItems(); // Limpa os itens existentes
        alunoController.listarAlunos().forEach(aluno ->
                alunoSelect.addItem(aluno.getId() + " - " + aluno.getNome())
        );
    }
}
