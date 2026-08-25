package br.com.biten.cm.modelo;

import br.com.biten.cm.execao.ExplosionException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {
    private final List<Campo> campos = new ArrayList<>();
    private final Campo[][] grade;
    private int lines;
    private int column;
    private int bombs;

    public Tabuleiro(int lines, int column, int bombs) {
        this.lines = lines;
        this.column = column;
        this.bombs = bombs;
        this.grade = new Campo[lines][column];

        createCampo();
        associateNeighbor();
        sortedBombs();
    }

    public void open(int lines, int column){
        try {
            getCampo(lines, column).open();

        } catch (ExplosionException e){
            campos.forEach(c-> c.setOpen(true));
            throw e;
        }
    }

    public void marked(int lines, int column){
        getCampo(lines, column).changeMark();
    }

    public void resetGame(){
        campos.forEach(Campo::reset);
        sortedBombs();

    }

    public boolean goalAchieved(){
        return campos.stream().allMatch(Campo::goalAchieved);

    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        int count = 0;
        sb.append("   ");
        for (int c = 0; c < column; c++){
            sb.append("[").append(c).append("]");
        }
        sb.append("\n");

        for (int i = 0 ; i < lines; i++){
            sb.append("[").append(i).append("]");
            for(int j = 0 ; j < column ; j++){
                sb.append(" ");
                sb.append(campos.get(count));
                sb.append(" ");

                count ++;

            }
            sb.append("\n");

        }

        return sb.toString();
    }

    public void createCampo() {
        for (int i = 0 ; i < lines; i++){
            for(int j = 0 ; j < column;j++){
                Campo campo = new Campo(i, j);
                grade[i][j] = campo;
                campos.add(campo);
            }
        }
    }

    public void associateNeighbor() {
        for(Campo c1: campos){
            for(Campo c2: campos){
                c1.addNeighbors(c2);
            }
        }
    }

    public void sortedBombs() {
        long minasArmadas = 0;
        Predicate<Campo> minado = Campo::isBomb;

        while(minasArmadas < bombs){
            int aleatorio = (int)(Math.random() * campos.size());
            campos.get(aleatorio).bombard();
            minasArmadas = campos.stream().filter(minado).count();
        }
    }

    public Campo getCampo(int lines, int column){
        return grade[lines][column];
    }

    public List<Campo> getCampos(){
        return Collections.unmodifiableList(campos);
    }

}
