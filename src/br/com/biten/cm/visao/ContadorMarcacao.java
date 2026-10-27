package br.com.biten.cm.visao;

import br.com.biten.cm.modelo.Campo;
import br.com.biten.cm.modelo.CampoEvento;
import br.com.biten.cm.modelo.Tabuleiro;

import javax.swing.*;
import java.awt.*;

public class ContadorMarcacao extends JLabel {

    int numeroDeMarcacoes = 0;
    int numeroDeBombas;

    public ContadorMarcacao(Tabuleiro tabuleiro) {
        this.numeroDeBombas = tabuleiro.getMinas();

        setForeground(Color.BLACK);
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(new Font("Arial", Font.BOLD, 38));
        setText(numeroDeMarcacoes + "/" + numeroDeBombas);

        tabuleiro.paraCada(c -> c.registrarObservador( (campo , e) -> {
            if (e == CampoEvento.MARCAR){
                numeroDeMarcacoes ++;
                atualizarTexto();
            } else if(e == CampoEvento.DESMARCAR){
                numeroDeMarcacoes --;
                atualizarTexto();
            } else if(e == CampoEvento.REINICIAR){
                numeroDeMarcacoes = 0;
                atualizarTexto();
            }
        }));
    }

    private void atualizarTexto( ) {
        setText(String.format("%d/%d", numeroDeMarcacoes, numeroDeBombas ));
    }
}
