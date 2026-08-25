package br.com.biten.cm;

import br.com.biten.cm.execao.ExplosionException;
import br.com.biten.cm.modelo.Campo;
import br.com.biten.cm.modelo.Tabuleiro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TabuleiroTest {

    private Tabuleiro tabuleiro;

    @BeforeEach
    void startTabuleiro(){
        tabuleiro = new Tabuleiro(3,3,3);

    }


    @Nested
    class Open {
        @Test
        void shouldOpen_whenLineAndColumnWasEquals(){
            Campo campo = tabuleiro.getCampo(1, 1);
            campo.reset();

            tabuleiro.open(1, 1);

            Assertions.assertTrue(campo.isOpen());
        }

        @Test
        void shouldOpenAllCampos_whenLineAndColumnOpenABomb(){
            Campo campo = tabuleiro.getCampo(1, 1);
            campo.reset();
            campo.bombard();

            Assertions.assertThrows(ExplosionException.class, () -> tabuleiro.open(1, 1));

            boolean allOpen = tabuleiro.getCampos().stream().allMatch(Campo::isOpen);

            Assertions.assertTrue(allOpen);
        }

    }

    @Nested
    class Marked {
        @Test
        void shouldMarked_whenLineAndColumnWasEquals(){
            Campo campo = tabuleiro.getCampo(1, 1);
            campo.reset();

            tabuleiro.marked(1, 1);

            Assertions.assertTrue(campo.isMarked());
        }

    }

    @Nested
    class ResetGame {
        @Test
        void shouldResetGame_whenCallResetGame(){
            tabuleiro.getCampo(1, 1).reset();
            tabuleiro.open(1,1);
            tabuleiro.marked(2,2);
            tabuleiro.resetGame();

            boolean allClosed = tabuleiro.getCampos().stream()
                    .noneMatch(c -> c.isOpen() || c.isMarked());

            Assertions.assertTrue(allClosed);
        }

    }

    @Nested
    class GoalAchieved {
        @Test
        void shouldAchievedGoal_whenAllBombsAreMarkedAndAllSafeCamposAreOpen(){
            List<Campo> campos = tabuleiro.getCampos();
            campos.forEach(Campo::reset);

            Campo bombCampo = campos.get(0);
            bombCampo.bombard();
            bombCampo.changeMark();

            campos.stream().filter(c -> c != bombCampo).forEach(Campo::open);

            Assertions.assertTrue(tabuleiro.goalAchieved());

        }

    }

    @Nested
    class ToString {

        @Test
        void shouldRenderHeader_whenBoardIsFreshlyCreated(){
            String header = tabuleiro.toString().lines().findFirst().orElseThrow();

            Assertions.assertEquals("   [0][1][2]", header);
        }

        @Test
        void shouldRenderAllClosedCells_whenBoardIsFreshlyCreated(){
            List<String> rows = tabuleiro.toString().lines().skip(1).toList();

            List<String> expectedRows = List.of(
                    "[0] ?  ?  ? ",
                    "[1] ?  ?  ? ",
                    "[2] ?  ?  ? "
            );

            Assertions.assertLinesMatch(expectedRows, rows);
        }
    }





}
