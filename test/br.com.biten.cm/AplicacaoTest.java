package br.com.biten.cm;

import br.com.biten.cm.modelo.Tabuleiro;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AplicacaoTest {
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    void setup(){

        originalOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreOut(){
        System.setOut(originalOut);
    }

    @Test
    void shouldRunGame_WhenCallMain(){
        System.setIn(new ByteArrayInputStream("sair\n".getBytes()));
        Aplicacao.main(new String[]{});

        Assertions.assertTrue(outContent.toString().contains("Digite(x,y)"));
    }
}
