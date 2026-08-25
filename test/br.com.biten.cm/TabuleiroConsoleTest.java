package br.com.biten.cm;

import br.com.biten.cm.modelo.Campo;
import br.com.biten.cm.modelo.Tabuleiro;
import br.com.biten.cm.visao.TabuleiroConsole;
import org.junit.jupiter.api.*;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

class TabuleiroConsoleTest {
    private Tabuleiro tabuleiro;
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    void setup(){
        tabuleiro = new Tabuleiro(3, 3, 0);

        originalOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreOut(){
        System.setOut(originalOut);
    }

    @Nested
    class Run {
        @Test
        void shouldPrintGameOver_whenOpeningABomb(){
            tabuleiro.getCampo(0, 0).bombard();
            Scanner entrada = new Scanner("0,0\n1\nn\n");

            new TabuleiroConsole(tabuleiro, entrada).run();

            Assertions.assertTrue(outContent.toString().contains("Game over"));
        }

        @Test
        void shouldPrintGameWin_whenGoalAchieved(){
            tabuleiro.getCampos().forEach(Campo::reset);

            Campo bomba = tabuleiro.getCampo(0, 0);
            bomba.bombard();
            bomba.changeMark();

            tabuleiro.getCampos().stream()
                    .filter(c -> c != bomba)
                    .forEach(Campo::open);

            Scanner entrada = new Scanner("n\n");

            new TabuleiroConsole(tabuleiro, entrada).run();

            Assertions.assertTrue(outContent.toString().contains("Game Win"));
        }

        @Test
        void shouldPrintSaiu_whenTypingSair(){
            Scanner entrada = new Scanner("sair\n");

            new TabuleiroConsole(tabuleiro, entrada).run();

            Assertions.assertTrue(outContent.toString().contains("Saiu!!"));
        }

        @Test
        void shouldResetGame_whenTypingS(){
            tabuleiro.getCampo(0, 0).bombard();
            Scanner entrada = new Scanner("0,0\n1\ns\nsair\n");

            new TabuleiroConsole(tabuleiro, entrada).run();

            boolean allClosed = tabuleiro.getCampos().stream().noneMatch(Campo::isOpen);
            Assertions.assertTrue(allClosed);
        }

        @Test
        void shouldMarked_whenTyping2(){
            tabuleiro.getCampo(0, 0).bombard();
            Scanner entrada = new Scanner("0,0\n2\nSair");

            new TabuleiroConsole(tabuleiro, entrada).run();

            boolean isMarked = tabuleiro.getCampo(0,0).isMarked();
            Assertions.assertTrue(isMarked);
        }

        @Test
        void shouldUnmarked_whenTyping2ForMarkedCampo(){
            tabuleiro.getCampo(0, 0).bombard();
            tabuleiro.getCampo(0, 0).changeMark();
            Scanner entrada = new Scanner("0,0\n2\nSair");

            new TabuleiroConsole(tabuleiro, entrada).run();

            boolean isMarked = tabuleiro.getCampo(0,0).isMarked();
            Assertions.assertFalse(isMarked);
        }

        @Test
        void shouldInvalidedOption_whenTypingOtherNumber(){
            Scanner entrada = new Scanner("0,0\n3\nSair");

            new TabuleiroConsole(tabuleiro, entrada).run();

            Assertions.assertTrue(outContent.toString().contains("Opcao Invalida"));
        }

        @Test
        void shoulThrowError_whenTypeWrong(){
            Scanner entrada = new Scanner("abs\n1\nSair");

            new TabuleiroConsole(tabuleiro, entrada).run();

            Assertions.assertTrue(outContent.toString().contains("Entrada Invalida, tente novamente"));

        }


    }
}
