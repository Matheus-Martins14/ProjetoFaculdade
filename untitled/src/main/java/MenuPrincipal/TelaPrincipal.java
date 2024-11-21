package MenuPrincipal;

import Aluno.AlunoView;
import Matricula.MatriculaView;
import Notas.NotaView;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    private static TelaPrincipal instance; // Instância única

    // Construtor privado para implementar o padrão Singleton
    private TelaPrincipal() {
        setTitle("Tela Principal - Sistema de Gestão Escolar");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 10, 10)); // Layout com 3 botões organizados verticalmente
        setLocationRelativeTo(null); // Centraliza a janela na tela

        // Botão para Gerenciar Alunos
        JButton gerenciarAlunosButton = new JButton("Gerenciar Alunos");
        gerenciarAlunosButton.addActionListener(e -> {
            this.setVisible(false); // Oculta a tela principal
            new AlunoView().setVisible(true); // Abre a tela de alunos
        });
        add(gerenciarAlunosButton);

        // Botão para Gerenciar Matrículas
        JButton gerenciarMatriculasButton = new JButton("Gerenciar Matrículas");
        gerenciarMatriculasButton.addActionListener(e -> {
            this.setVisible(false); // Oculta a tela principal
            new MatriculaView().setVisible(true); // Abre a tela de matrículas
        });
        add(gerenciarMatriculasButton);

        // Botão para Gerenciar Notas
        JButton gerenciarNotasButton = new JButton("Gerenciar Notas");
        gerenciarNotasButton.addActionListener(e -> {
            this.setVisible(false); // Oculta a tela principal
            new NotaView().setVisible(true); // Abre a tela de notas
        });
        add(gerenciarNotasButton);
    }

    // Método para obter a instância única da TelaPrincipal
    public static TelaPrincipal getInstance() {
        if (instance == null) {
            instance = new TelaPrincipal();
        }
        return instance;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> TelaPrincipal.getInstance().setVisible(true));
    }
}
