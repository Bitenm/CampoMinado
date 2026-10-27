package br.com.biten.cm.visao;

import br.com.biten.cm.modelo.CampoEvento;
import br.com.biten.cm.modelo.Tabuleiro;

import javax.swing.*;
import java.awt.*;

public class Cronometro extends JLabel{
    private int segundos = 0;
    private Timer timer;
    private boolean jogoAcabou = false;

    public Cronometro(Tabuleiro tabuleiro){
        setForeground(Color.WHITE);
        setBackground(Color.BLACK);
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder(5, 12, 5, 12));
        setFont(new Font("Arial", Font.ITALIC, 25));
        setText("00:00:00");

        tabuleiro.paraCada(c -> c.registrarObservador((campo,e ) -> {
            if(e == CampoEvento.ABRIR){
                iniciar();
            } else if(e == CampoEvento.REINICIAR){
                zerar();
            }
        } ));

        tabuleiro.registrarObservador(r -> parar());

        timer = new Timer(1000, e -> { segundos++; atualizarTexto();});

    }

    private void atualizarTexto() {
        int h = segundos / 3600;
        int m = (segundos % 3600) / 60;
        int s = segundos % 60;
        setText(String.format("%02d:%02d:%02d", h, m, s));
    }

    private void iniciar(){
        if(!timer.isRunning() && !jogoAcabou){
            timer.start();
        }
    }

    private void parar(){
        timer.stop();
        jogoAcabou = false;
    }

    private void zerar(){
        timer.stop();
        segundos = 0;
        atualizarTexto();
        jogoAcabou = false;
    }

}
