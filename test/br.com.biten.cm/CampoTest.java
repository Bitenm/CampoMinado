package br.com.biten.cm;

import br.com.biten.cm.execao.ExplosionException;
import br.com.biten.cm.modelo.Campo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CampoTest {
    private Campo campo;

    @BeforeEach
    void startCampo() {
        campo = new Campo(3, 3);
    }

    @Nested
    class AddNeighbors {

        @Test
        void shouldAdd_whenNeighborIsOnLeft() {
            Campo neighbor = new Campo(3, 2);

            boolean resultTest = campo.addNeighbors(neighbor);

            Assertions.assertTrue(resultTest);
        }

        @Test
        void shouldAdd_whenNeighborIsOnRight() {
            Campo neighbor = new Campo(3, 4);

            boolean resultTest = campo.addNeighbors(neighbor);

            Assertions.assertTrue(resultTest);
        }

        @Test
        void shouldAdd_whenNeighborIsAbove() {
            Campo neighbor = new Campo(2, 3);

            boolean resultTest = campo.addNeighbors(neighbor);

            Assertions.assertTrue(resultTest);
        }

        @Test
        void shouldAdd_whenNeighborIsBelow() {
            Campo neighbor = new Campo(4, 3);

            boolean resultTest = campo.addNeighbors(neighbor);

            Assertions.assertTrue(resultTest);
        }

        @Test
        void shouldAdd_whenNeighborIsDiagonal() {
            Campo neighbor = new Campo(2, 2);

            boolean resultTest = campo.addNeighbors(neighbor);

            Assertions.assertTrue(resultTest);
        }

        @Test
        void shouldNotAdd_whenNotANeighbor() {
            Campo neighbor = new Campo(1, 1);

            boolean resultTest = campo.addNeighbors(neighbor);

            Assertions.assertFalse(resultTest);
        }

        @Test
        void shouldNotAdd_whenTwoCellsApartInSameLine() {
            Campo neighbor = new Campo(3, 5);

            boolean resultTest = campo.addNeighbors(neighbor);

            Assertions.assertFalse(resultTest);
        }
    }

    @Nested
    class ChangeBooking {

        @Test
        void shouldNotBeMarked_whenCreated() {
            Assertions.assertFalse(campo.isMarked());
        }

        @Test
        void shouldMark_whenCalledOnce() {
            campo.changeMark();

            Assertions.assertTrue(campo.isMarked());
        }

        @Test
        void shouldUnmark_whenCalledTwice() {
            campo.changeMark();
            campo.changeMark();

            Assertions.assertFalse(campo.isMarked());
        }

        @Test
        void shouldNotMark_whenAlreadyOpen() {
            campo.open();
            campo.changeMark();

            Assertions.assertFalse(campo.isMarked());
        }
    }

    @Nested
    class Open {

        @Test
        void shouldOpen_whenNotBombAndNotMarked() {
            Assertions.assertTrue(campo.open());
        }

        @Test
        void shouldNotOpen_whenAlreadyOpen() {
            campo.open();

            Assertions.assertFalse(campo.open());
        }

        @Test
        void shouldNotOpen_whenMarked() {
            campo.changeMark();

            Assertions.assertFalse(campo.open());
        }

        @Test
        void shouldNotOpen_whenBombAndMarked() {
            campo.bombard();
            campo.changeMark();

            Assertions.assertFalse(campo.open());
        }

        @Test
        void shouldThrowExplosionException_whenBombAndNotMarked() {
            campo.bombard();

            Assertions.assertThrows(ExplosionException.class, () -> campo.open());
        }

        @Test
        void shouldPropagateOpening_whenNoNeighborIsBomb() {
            Campo neighbor1 = new Campo(1, 1);
            Campo neighbor2 = new Campo(2, 2);
            neighbor2.addNeighbors(neighbor1);
            campo.addNeighbors(neighbor2);

            campo.open();

            Assertions.assertAll(
                    () -> Assertions.assertTrue(neighbor2.isOpen(), "direct neighbor should open"),
                    () -> Assertions.assertTrue(neighbor1.isOpen(), "cascade should propagate when there is no bomb nearby")
            );
        }

        @Test
        void shouldNotPropagateOpening_whenNeighborHasBomb() {
            Campo diagonalNeighbor = new Campo(1, 1);
            Campo bombNeighbor = new Campo(1, 2);
            bombNeighbor.bombard();

            Campo neighbor2 = new Campo(2, 2);
            neighbor2.addNeighbors(diagonalNeighbor);
            neighbor2.addNeighbors(bombNeighbor);
            campo.addNeighbors(neighbor2);

            campo.open();

            Assertions.assertAll(
                    () -> Assertions.assertTrue(neighbor2.isOpen(), "direct neighbor should open"),
                    () -> Assertions.assertFalse(diagonalNeighbor.isOpen(), "cascade should not pass through a field with a bomb neighbor")
            );
        }
    }

    @Nested
    class GoalAchieved {

        @Test
        void shouldAchieve_whenBombAndMarked() {
            campo.bombard();
            campo.changeMark();

            Assertions.assertTrue(campo.goalAchieved());
        }

        @Test
        void shouldAchieve_whenNotBombAndAlreadyOpen() {
            campo.open();

            Assertions.assertTrue(campo.goalAchieved());
        }

        @Test
        void shouldNotAchieve_whenBombAndNotOpen() {
            campo.bombard();

            Assertions.assertFalse(campo.goalAchieved());
        }

        @Test
        void shouldNotAchieve_whenBombWasOpened() {
            campo.bombard();
            Assertions.assertThrows(ExplosionException.class, () -> campo.open());

            Assertions.assertFalse(campo.goalAchieved());
        }
    }

    @Nested
    class BombInNeighbor {

        @Test
        void shouldCountOne_whenNeighborIsBomb() {
            Campo neighbor = new Campo(2, 3);
            neighbor.bombard();
            campo.addNeighbors(neighbor);

            Assertions.assertEquals(1, campo.bombInNeighboard());
        }

        @Test
        void shouldCountZero_whenNeighborIsNotBomb() {
            Campo neighbor = new Campo(2, 3);
            campo.addNeighbors(neighbor);

            Assertions.assertEquals(0, campo.bombInNeighboard());
        }
    }

    @Nested
    class Reset {

        @Test
        void shouldReset_whenCampoIsOpen() {
            campo.open();
            campo.reset();

            Assertions.assertFalse(campo.isOpen());
        }

        @Test
        void shouldReset_whenCampoIsBomb() {
            campo.bombard();
            campo.reset();

            Assertions.assertFalse(campo.isBomb());
        }

        @Test
        void shouldReset_whenCampoIsMarked() {
            campo.changeMark();
            campo.reset();

            Assertions.assertFalse(campo.isMarked());
        }
    }

    @Nested
    class ToString {

        @Test
        void shouldReturnX_whenIsMarked() {
            campo.changeMark();

            Assertions.assertEquals("x", campo.toString());
        }

        @Test
        void shouldReturnAsterisk_whenIsOpenAndBomb() {
            campo.open();
            campo.bombard();

            Assertions.assertEquals("*", campo.toString());
        }

        @Test
        void shouldreturnnumberwhenisopenandhavebombinneighboard() {
            Campo neighbor = new Campo(2, 3);
            campo.addNeighbors(neighbor);
            neighbor.bombard();
            campo.open();

            Assertions.assertEquals("1", campo.toString());
        }

        @Test
        void shouldReturnBlank_whenAlreadyOpen() {
            campo.open();

            Assertions.assertEquals(" ", campo.toString());
        }

        @Test
        void shouldReturnQuestion_whenCreated() {
            Assertions.assertEquals("?", campo.toString());
        }
    }

    @Nested
    class Getters {

        @Test
        void shouldReturnTheLine_whenCallGetLine() {
            Assertions.assertEquals(3, campo.getLine());
        }

        @Test
        void shouldReturnTheColumn_whenCallGetColumn() {
            Assertions.assertEquals(3, campo.getColumn());
        }
    }
}
