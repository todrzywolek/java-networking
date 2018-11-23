package pl.todrzywolek.design.principles;

class Rectangle {
    protected int width, height;

    public Rectangle() {
    }

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getArea() {
        return width * height;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }

    // Solution 1 - no need for Square class and add a flag that checks if rectangle is a square
    public boolean isSquare() {
        return width == height;
    }
}

class Square extends Rectangle {

    public Square() {
    }

    public Square(int size) {
        this.width = this.height = size;
    }

    @Override
    public void setWidth(int width) {
        super.setWidth(width);
        super.setHeight(width);
    }

    @Override
    public void setHeight(int height) {
        super.setHeight(height);
        super.setWidth(height);
    }
}

// Solution 2 - use Factory design pattern
class RectangleFactory {

    public static Rectangle newRectangle(int width, int height) {
        return new Rectangle(width, height);
    }

    public static Rectangle newSquare(int size) {
        return new Rectangle(size, size);
    }
}

class LiskovDemo {

    static void useIt(Rectangle r) {
        int width = r.getWidth();
        r.setHeight(10);
        // area = 10 * width
        System.out.println("Expected area of " + (width * 10) +
        " got, " + r.getArea());
    }

    public static void main(String[] args) {
        Rectangle rc = new Rectangle(2, 3);
        useIt(rc);

        Square sq = new Square();
        sq.setHeight(5);
        useIt(sq);

        Rectangle factorySquare = RectangleFactory.newSquare(20);
        useIt(factorySquare);
    }

}