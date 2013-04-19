package a2.grid;

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

    public void print() {
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                if (cells[i][j].hasContents()) {
                    System.out.print (hasContentsChar);
                } else {
                    System.out.print (isClearChar);
                }
            }
            System.out.println();
        }
    }

    public void setCellData (int x, int y, Data d) {
        cells[x][y].setContents(d);
    }

    public void clearCellData (int x, int y) {
        cells[x][y].clearContents();
    }

    public Data getCellData (int x, int y) {
        return cells[x][y].getContents();
    }
}
