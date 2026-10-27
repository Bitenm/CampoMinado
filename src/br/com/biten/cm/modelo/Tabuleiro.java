package br.com.biten.cm.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservador {
    private final List<Campo> campos = new ArrayList<>();
    private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();
    private final Campo[][] grade;
    private final int linhas;
    private final int colunas;
    private final int minas;

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;
        this.grade = new Campo[linhas][colunas];

        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }

    public void registrarObservador(Consumer<ResultadoEvento> observador){
        observadores.add(observador);
    }

    private void notificarObservadores(boolean resultado){
        observadores.stream().forEach(o-> o.accept(new ResultadoEvento(resultado)));
    }

    public void abrir(int linha, int coluna){

        getCampo(linha, coluna).abrir();

    }

    public void alternarMarcacao(int linha, int coluna){
        getCampo(linha, coluna).alternarMarcacao();
    }

    public void reiniciar(){
        campos.forEach(Campo::reiniciar);
        sortearMinas();
    }

    public boolean objetivoAlcancado(){
        return campos.stream().allMatch(Campo::objetivoAlcancado);

    }

    private void gerarCampos() {
        for (int i = 0 ; i < linhas; i++){
            for(int j = 0 ; j < colunas;j++){
                Campo campo = new Campo(i, j);
                grade[i][j] = campo;
                campo.registrarObservador(this);
                campos.add(campo);
            }
        }
    }

    private void associarVizinhos() {
        for(Campo c1: campos){
            for(Campo c2: campos){
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo> minado = Campo::isMinado;

        while(minasArmadas < minas){
            int aleatorio = (int)(Math.random() * campos.size());
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();
        }
    }

    public Campo getCampo(int linha, int coluna){
        return grade[linha][coluna];
    }

    public void paraCada(Consumer<Campo> funcao){
        campos.forEach(funcao);
    }

    @Override
    public void eventoOcorreu(Campo c, CampoEvento evento) {
        if(evento ==  CampoEvento.EXPLODIR){
            mostrarMinas();
            notificarObservadores(false);
        } else if(objetivoAlcancado()){

            notificarObservadores(true);
        }
    }

    private void mostrarMinas(){
        campos.stream()
                .filter(c -> c.isMinado())
                .filter(c -> !c.isMarcado())
                .forEach(c -> c.setAberto(true));
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public int getMinas() {
        return minas;
    }
}
