package br.com.biten.cm.visao;

import br.com.biten.cm.modelo.Tabuleiro;

import javax.swing.*;
import java.awt.*;

public class CabecarioTabuleiro extends JPanel{
    public CabecarioTabuleiro(Tabuleiro tabuleiro){
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(0, 60));
        setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        // BOTAO DE TABULEIRO
        JButton reiniciar = new JButton(/*aq recebe um icone dentro */);
        reiniciar.setContentAreaFilled(false);   // não pinta o fundo do botão
        reiniciar.setBorderPainted(false);       // não desenha a borda
        reiniciar.setPreferredSize(new Dimension(80, 0));
        reiniciar.setFocusPainted(false);
        ImageIcon original = new ImageIcon(getClass().getResource("/br/com/biten/cm/Icons/reiniciar.png"));
        Image menor = original.getImage().getScaledInstance(34, 34, Image.SCALE_SMOOTH);
        reiniciar.setIcon(new ImageIcon(menor));

        add(reiniciar, BorderLayout.EAST);

        // Efeito de funco ao clicar
        reiniciar.getModel().addChangeListener(e ->
                reiniciar.setContentAreaFilled(reiniciar.getModel().isPressed()));

        // Chama um action listener quando clicar no botao q faz a logica do mesmo
        reiniciar.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(null, "Deseja mesmo reiniciar?",
                    "Reiniciar", JOptionPane.YES_NO_OPTION);


            if (resposta == JOptionPane.YES_OPTION) {
                tabuleiro.reiniciar();
            }
        });

        //  MARCACAO POR BOMBA
        add(new ContadorMarcacao(tabuleiro), BorderLayout.CENTER);

        // CRONOMETRO
        add(new Cronometro(tabuleiro), BorderLayout.WEST);



    }

}
