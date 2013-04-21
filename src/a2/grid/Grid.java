package a2.grid;

import a2.ant.Ant;
import a2.data.Data;

/**
 *
 * @author phlippie
 */
public class Grid {

    int maxX, maxY;
    Cell [][] cells;

    public Grid (int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.cells = new Cell[maxX][maxY];
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                this.cells[i][j] = new Cell (i, j);
            }
        }
    }

    char hasContentsChar = '#';
    char isClearChar = '.';
    char hasAntChar = ' ';
    public void print() {
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                if (cells[i][j].hasData()) {
                    System.out.print (hasContentsChar);
                } else if (cells[i][j].hasAnt()) {
                    System.out.print (hasAntChar);
                } else {
                    System.out.print (isClearChar);
                }
            }
            System.out.println();
        }
    }

    public void setCellData (int x, int y, Data d) {
        cells[x][y].setData(d);
        if ( d != null) d.setPos(x, y);
    }

    public Data clearCellData (int x, int y) {
        /*Data data = cells[x][y].getData();
        cells[x][y].clearData();
        return data;*/
        return cells[x][y].clearData();
        /** @TODO try this */
    }

    public Data getCellData (int x, int y) {
        return cells[x][y].getData();
    }

    public boolean cellHasData (int x, int y) {
        return cells[x][y].hasData();
    }

    public void setCellAnt (int x, int y, Ant a) {
        cells[x][y].setAnt(a);
    }

    public void clearCellAnt (int x, int y) {
        cells[x][y].clearAnt();
    }

    public Ant getCellAnt (int x, int y) {
        return cells[x][y].getAnt();
    }

    public boolean cellHasAnt (int x, int y) {
        return cells[x][y].hasAnt();
    }

    public boolean isValidPosition (int x, int y) {
        /** @TODO check if shouldn't be x < maxX */
        return (x >= 0 && x < maxX && y >= 0 && y < maxY);
    }

    public Cell getCell (int x, int y) {
        return cells[x][y];
    }

    public int getXSize () {
        return this.maxX;
    }

    public int getYSize () {
        return this.maxY;
    }

}
