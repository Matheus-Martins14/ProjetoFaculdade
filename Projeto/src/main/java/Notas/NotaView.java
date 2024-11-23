package Notas;

import Aluno.AlunoController;
import MenuPrincipal.TelaPrincipal;

import javax.swing.*;
import java.awt.*;

public class NotaView extends JFrame {
    private JComboBox<String> alunoSelect;
    private JTextField disciplinaField;
    private JTextField notaField;
    private JTextField dataAvaliacaoField;

    private NotaController controller;

    public NotaView() {
        controller = new NotaController();
        setTitle("Cadastro de Notas");
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

        formPanel.add(new JLabel("Disciplina:"));
        disciplinaField = new JTextField();
        formPanel.add(disciplinaField);

        formPanel.add(new JLabel("Nota:"));
        notaField = new JTextField();
        formPanel.add(notaField);

        formPanel.add(new JLabel("Data da Avaliação:"));
        dataAvaliacaoField = new JTextField();
        formPanel.add(dataAvaliacaoField);

        add(formPanel, BorderLayout.CENTER);

        // Painel inferior para os botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Botão "Salvar"
        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> {
            try {
                Nota nota = new Nota();
                nota.setAlunoId(Integer.parseInt(alunoSelect.getSelectedItem().toString().split(" - ")[0]));
                nota.setDisciplina(disciplinaField.getText());
                nota.setNota(Double.parseDouble(notaField.getText()));
                nota.setDataAvaliacao(dataAvaliacaoField.getText());
                controller.adicionarNota(nota);
                JOptionPane.showMessageDialog(this, "Nota cadastrada com sucesso!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, insira uma nota válida!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(salvarButton);

        // Botão "Listar"
        JButton listarButton = new JButton("Listar");
        listarButton.addActionListener(e -> {
            StringBuilder notasList = new StringBuilder("Notas Cadastradas:\n");
            controller.listarNotasComNomes().forEach(nota ->
                    notasList.append(nota).append("\n")
            );
            JOptionPane.showMessageDialog(this, notasList.toString());
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
