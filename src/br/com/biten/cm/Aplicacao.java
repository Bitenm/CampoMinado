package br.com.biten.cm;

import br.com.biten.cm.modelo.Tabuleiro;
import br.com.biten.cm.visao.TabuleiroConsole;

import java.util.Scanner;

public class Aplicacao {
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(6,6,6);
        new TabuleiroConsole(tabuleiro, new Scanner(System.in)).run();
    }
}
