package br.com.biten.cm.visao;

import br.com.biten.cm.execao.ExplosionException;
import br.com.biten.cm.execao.SairException;
import br.com.biten.cm.modelo.Tabuleiro;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TabuleiroConsole {
    private Tabuleiro tabuleiro;
    private Scanner entrada;

    public TabuleiroConsole(Tabuleiro tabuleiro, Scanner entrada) {
        this.tabuleiro = tabuleiro;
        this.entrada = entrada;
    }

    public void run(){
        runGame();
    }

    private void runGame(){
        try {
            boolean keep = true;

            while(keep){
                gameLoop();


                System.out.println("Outra Partida? (S/n) ");
                String answer = entrada.nextLine();
                if("n".equalsIgnoreCase(answer)){
                    keep = false;
                } else {
                    tabuleiro.resetGame();
                }

            }

        }catch (SairException e){
            System.out.println("Saiu!!");
        }finally {
            entrada.close();
        }
    }

    private void gameLoop() {
        try {

            while(!tabuleiro.goalAchieved()){
                System.out.println(tabuleiro);


                try{
                    String digitado = captureEnteredValue("Digite(x,y): ");

                    Iterator<Integer> xy = Arrays.stream(digitado.split(","))
                            .map(Integer::parseInt).iterator();

                    digitado = captureEnteredValue("1- Abrir ou 2 - (Des)Marcar ");

                    if("1".equals(digitado)){
                        tabuleiro.open(xy.next(), xy.next());
                    } else if("2".equals(digitado)){
                        tabuleiro.marked(xy.next(), xy.next());
                    } else{
                        System.out.println("\nOpcao Invalida\n");
                    }
                }catch(NumberFormatException | NoSuchElementException | ArrayIndexOutOfBoundsException e){
                    System.out.println("\nEntrada Invalida, tente novamente\n");
                }

            }

            System.out.println(tabuleiro);
            System.out.println("Game Win !!");
        } catch (ExplosionException e) {
            System.out.println(tabuleiro);
            System.out.println("Game over !!");

        }
    }

    private String captureEnteredValue(String texto){
        System.out.print(texto);
        String digitado = entrada.nextLine();

        if("sair".equalsIgnoreCase(digitado)){
            throw new SairException();
        }

        return digitado;
    }

}
