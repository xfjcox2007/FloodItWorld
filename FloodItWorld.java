
// Assignment 9 Part 1
// Jiangyun Wang
// jiangyun
// Fangji Xu
// xfjcox2007

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import tester.Tester;

//Represents a single square of the game area
class Cell {
  // In logical coordinates, with the origin at the top-left corner of the
  // screen
  int x;
  int y;
  String color;
  boolean flooded;
  // the four adjacent cells to this one
  Cell left;
  Cell top;
  Cell right;
  Cell bottom;

  // initial constructor
  Cell(int x, int y, String color, boolean flooded) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.flooded = flooded;
    this.left = null;
    this.top = null;
    this.right = null;
    this.bottom = null;

  }

  // generates a world image of the cell
  public WorldImage cellImage() {
    if (color.equals("red")) {
      return new RectangleImage(10, 10, "solid", Color.red);
    }
    if (color.equals("cyan")) {
      return new RectangleImage(10, 10, "solid", Color.cyan);
    }
    if (color.equals("green")) {
      return new RectangleImage(10, 10, "solid", Color.green);
    }
    if (color.equals("orange")) {
      return new RectangleImage(10, 10, "solid", Color.orange);
    }
    if (color.equals("yellow")) {
      return new RectangleImage(10, 10, "solid", Color.yellow);
    }
    if (color.equals("pink")) {
      return new RectangleImage(10, 10, "solid", Color.pink);
    }
    if (color.equals("gray")) {
      return new RectangleImage(10, 10, "solid", Color.gray);
    }
    if (color.equals("blue")) {
      return new RectangleImage(10, 10, "solid", Color.blue);
    }
    if (color.equals("magenta")) {
      return new RectangleImage(10, 10, "solid", Color.magenta);
    }
    else {
      throw new RuntimeException("Illegal Color!");
    }
  }

}

// a class represents a world named FloodIt
class FloodItWorld extends World {
  // All the cells of the game
  ArrayList<Cell> board;
  int size;
  int color;
  ArrayList<String> colors = new ArrayList<String>(Arrays.asList("red", "pink", "green", "cyan",
      "orange", "yellow", "gray", "black", "blue", "magenta"));

  // constructor
  FloodItWorld(int size, int color) {
    this.board = new ArrayList<Cell>();
    this.size = size;
    this.color = color;
    this.generateBoard(size, color);
  }

  // EFFECT: generates the initial world based on the given size and color
  public void generateBoard(int size, int color) {
    for (int row = 0; row < this.size; row++) {
      for (int col = 0; col < this.size; col++) {
        this.board.add(this.randomCell(row, col, color));
      }
    }
  }

  // generates a cell with random color
  public Cell randomCell(int x, int y, int userColor) {
    return new Cell(x, y, this.colors.get(randomInt(userColor)), false);
  }

  // produces a random number
  public int randomInt(int user) {
    return 0 + (new Random().nextInt(user));
  }

  // produces the scene of the world by adding the board
  public WorldScene makeScene() {
    WorldScene bg = new WorldScene(this.size, this.size);
    for (Cell c : board) {
      WorldImage piece = c.cellImage();
      bg.placeImageXY(piece, 5 + c.x * 10, 5 + c.y * 10);
    }
    return bg;
  }

}

// examples for flood it
class ExampleFloodIt {
  // examples for cell
  Cell cell1;
  Cell cell2;
  Cell cell3;
  Cell cell4;
  Cell cell5;

  Cell cell6;
  Cell cell7;
  Cell cell8;
  Cell cell9;
  Cell cell10;
  Cell cell11;
  Cell cell12;
  Cell cell13;
  Cell cell14;

  // examples for ArrayList
  ArrayList<Cell> arr1;

  // examples for flood-it-world
  FloodItWorld f1;

  // the initial condition
  void initCondition() {
    // examples for cells
    cell1 = new Cell(0, 0, "red", false);
    cell2 = new Cell(0, 10, "green", false);
    cell3 = new Cell(40, 30, "red", false);
    cell4 = new Cell(15, 25, "purple", true);
    cell5 = new Cell(40, 30, "pink", false);

    cell6 = new Cell(0, 0, "red", false);
    cell7 = new Cell(0, 1, "pink", false);
    cell8 = new Cell(1, 0, "green", false);
    cell9 = new Cell(1, 1, "blue", false);

    // examples for ArrayList
    arr1 = new ArrayList<Cell>(Arrays.asList(cell6, cell7, cell8, cell9));

    // an example
    f1 = new FloodItWorld(30, 6);
    // f1.bigBang(f1.size * 10, f1.size * 10, 0.5);
  }

  // testing generateBoard method
  void testGenerateBoard(Tester t) {
    FloodItWorld f2 = new FloodItWorld(2, 2);
    for (int i = 0; i < f2.board.size(); i++) {
      t.checkOneOf("generateBoard", f2.board.get(i).color, "red", "pink");
      t.checkNoneOf("generateBoard", f2.board.get(i).color, "green", "blue", "black");
      t.checkOneOf("generateBoard", f2.board.get(i).x, 0, 1);
      t.checkNoneOf("generateBoard", f2.board.get(i).x, -2, 7, 10);
      t.checkOneOf("generateBoard", f2.board.get(i).y, 0, 1);
      t.checkNoneOf("generateBoard", f2.board.get(i).y, 9, 10);
    }
    t.checkExpect(f2.board.size(), 4);
  }

  // testing cellImage method
  void testCellImage(Tester t) {
    this.initCondition();
    t.checkExpect(cell1.cellImage(), new RectangleImage(10, 10, "solid", Color.red));
    t.checkExpect(cell2.cellImage(), new RectangleImage(10, 10, "solid", Color.green));
    t.checkException(new RuntimeException("Illegal Color!"), cell4, "cellImage");
  }

  // testing randomInt method
  boolean testRandomInt(Tester t) {
    this.initCondition();
    return t.checkOneOf("randomInt", f1.randomInt(1), 0, 1)
        && t.checkNoneOf("randomInt", f1.randomInt(1), -5, -1)
        && t.checkOneOf("randomInt", f1.randomInt(3), 0, 1, 2, 3)
        && t.checkNoneOf("randomInt", f1.randomInt(3), -8, -1);
  }

  // testing randomCell method
  boolean testRandomCell(Tester t) {
    this.initCondition();
    return t.checkOneOf("randomCell", f1.randomCell(0, 0, 1), cell1)
        && t.checkNoneOf("randomCell", f1.randomCell(0, 0, 1), cell2, cell3)
        && t.checkOneOf("randomCell", f1.randomCell(40, 30, 2), cell3, cell5)
        && t.checkNoneOf("randomCell", f1.randomCell(40, 30, 2), cell1, cell2);
  }

  // testing makeScene method
  void testMakeScene(Tester t) {
    this.initCondition();
    FloodItWorld f2 = new FloodItWorld(2, 2);
    f2.board = arr1;
    WorldScene emp = new WorldScene(2, 2);
    WorldImage r1 = new RectangleImage(10, 10, OutlineMode.SOLID, Color.red);
    WorldImage r2 = new RectangleImage(10, 10, OutlineMode.SOLID, Color.pink);
    WorldImage r3 = new RectangleImage(10, 10, OutlineMode.SOLID, Color.green);
    WorldImage r4 = new RectangleImage(10, 10, OutlineMode.SOLID, Color.blue);
    emp.placeImageXY(r1, 5, 5);
    emp.placeImageXY(r2, 5, 15);
    emp.placeImageXY(r3, 15, 5);
    emp.placeImageXY(r4, 15, 15);
    t.checkExpect(f2.makeScene(), emp);
    t.checkExpect(f1.makeScene().height, 30);
    t.checkExpect(f1.makeScene().width, 30);
    t.checkExpect(f2.makeScene().height, 2);
    t.checkExpect(f2.makeScene().width, 2);
  }

}
