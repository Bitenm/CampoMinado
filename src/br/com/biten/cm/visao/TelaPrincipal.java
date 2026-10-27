package br.com.biten.cm.visao;
import br.com.biten.cm.modelo.Tabuleiro;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal(){
        Tabuleiro tabuleiro = new Tabuleiro(16,30,50);


        PainelTabuleiro painelTabuleiro = new PainelTabuleiro(tabuleiro);
        CabecarioTabuleiro cabecarioTabuleiro = new CabecarioTabuleiro(tabuleiro);
        add(cabecarioTabuleiro, BorderLayout.NORTH);
        add(painelTabuleiro,BorderLayout.CENTER);

        setTitle("Campo Minado");
        setSize(1200,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }


    public static void main(String[] args) {
        new TelaPrincipal();
    }

}
