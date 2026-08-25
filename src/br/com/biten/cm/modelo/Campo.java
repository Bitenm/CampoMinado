package br.com.biten.cm.modelo;

import br.com.biten.cm.execao.ExplosionException;

import java.util.ArrayList;
import java.util.List;

public class Campo {
    private final int line;
    private final int column;

    private boolean bomb;
    private boolean open;
    private boolean marked;

    private List<Campo> neighbors = new ArrayList<>();

    public Campo(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public boolean addNeighbors(Campo neighbor){
        boolean differentLine = line != neighbor.line ;
        boolean differentColumn = column != neighbor.column;
        boolean diagonal = differentLine && differentColumn;

        int deltaLine = Math.abs(line - neighbor.line);
        int deltaColumn = Math.abs(column - neighbor.column);
        int deltaGeral = deltaColumn + deltaLine;

        if(deltaGeral == 1){
            neighbors.add(neighbor);
            return true;
        } else if (deltaGeral == 2 && diagonal){
            neighbors.add(neighbor);
            return true;
        } else{
            return false;
        }
    }

    public void changeMark(){
        if(!open){
            marked = !marked;
        }
    }

    public boolean open(){

        if(!open && !marked){
            open = true;

            if(bomb){
                throw new ExplosionException();
            }

            if(neighborSecurity()){
                neighbors.forEach(Campo::open);
            }
            return true;
        } else{
            return false;
        }
    }

    boolean neighborSecurity(){
        return neighbors.stream().noneMatch(v -> v.bomb);
    }

    public void bombard(){
        bomb = true;
    }

    public void reset(){
        open = false;
        bomb = false;
        marked = false;
    }

    public boolean goalAchieved(){
        boolean opened =  !bomb && open;
        boolean security = marked && bomb;
        return opened || security;
    }

    public boolean isMarked() {
        return marked;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isBomb() {
        return bomb;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public long bombInNeighboard(){
        return neighbors.stream().filter(v -> v.bomb).count();
    }

    public String toString(){
        if(marked){
            return "x";

        } else if(open && bomb){
            return "*";
        } else if(open && bombInNeighboard() > 0){
            return Long.toString(bombInNeighboard());
        } else if (open){
            return " ";
        } else {
            return "?";
        }
    }
}
