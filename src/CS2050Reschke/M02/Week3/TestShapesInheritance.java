package CS2050Reschke.M02.Week3;

import java.util.Scanner;

/**
 * Add comments
 */

public class TestShapesInheritance {

    public static void main(String[] args) {
/*
        CircleFromSimpleGeometricShape circle =
                new CircleFromSimpleGeometricShape(1, "Red", true);
        System.out.println("circle toString: " + circle.toString());
        System.out.println("The color is " + circle.getColor());
        System.out.printf("The radius is  %.2f \n", circle.getRadius());
        System.out.printf("The area is  %.2f \n", circle.getArea());
        System.out.printf("The diameter is  %.2f \n", circle.getDiameter());
*/

        Scanner input = new Scanner(System.in);
        RectangleFromSimpleGeometricShape rectangle = new RectangleFromSimpleGeometricShape(10, 10);
        System.out.println("Rectangle with free length and width");
        System.out.println("The area is: " + rectangle.getArea());
        System.out.println("The perimeter is: " + rectangle.getPerimeter());

        System.out.print("What color would you like your rectangle to be? ");
        String colorInput = input.next();
        rectangle.setColor(colorInput);
        System.out.println("The rectangles color is: " + rectangle.getColor());

        RectangleFromSimpleGeometricShape square1 = new RectangleFromSimpleGeometricShape(12, "Green");
        System.out.println("Rectangle with equal length and width (square)");
        System.out.println("The area is: " + square1.getArea());
        System.out.println("The perimeter is: " + square1.getPerimeter());

        System.out.print("What color would you like your square to be? ");
        String colorInput2 = input.next();
        square1.setColor(colorInput2);
        System.out.println("The rectangles color is: " + square1.getColor());


    }//end main

}//end TestShapeInheritance Class


/**
 * Simple Geometric Shape Superclass
 */
abstract class SimpleGeometricShape {
    private String color = "white";
    private boolean filled;
    private java.util.Date dateCreated;


    /**
     * Construct a default geometric object
     */
    public SimpleGeometricShape() {
        dateCreated = new java.util.Date();
    }


    public SimpleGeometricShape(String color, boolean filled) {
        dateCreated = new java.util.Date();
        this.color = color;
        this.filled = filled;
    }

    /**
     * get current color
     *
     * @return String color
     */
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Since filled is boolean,
     * its get method is named isFilled
     * get current filled value
     *
     * @return boolean filled
     */
    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    /**
     * Get dateCreated
     *
     * @return dateCreated
     */
    public java.util.Date getDateCreated() {
        return dateCreated;
    }

    @Override
    public String toString() {
        System.out.println("In SimpleGeometricShape toString method ");
        return "created on " + dateCreated + "\ncolor: " + color +
                " and filled: " + filled;
    }

}//end SimpleGeometricShape class


/**
 * Add comments
 */
class CircleFromSimpleGeometricShape
        extends SimpleGeometricShape {
    private double radius;

    /**
     * Construct a default circle object
     */
    public CircleFromSimpleGeometricShape() {
    }

    /**
     * @param radius
     */
    public CircleFromSimpleGeometricShape(double radius) {
        this.radius = radius;
    }

    public CircleFromSimpleGeometricShape(double radius,
                                          String color, boolean filled) {
        this.radius = radius;
        setColor(color);
        setFilled(filled);
    }


    /**
     * Get radius
     *
     * @return double radius
     */
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * Get Area
     *
     * @return double
     */
    public double getArea() {
        return radius * radius * Math.PI;
    }

    /**
     * Get Diameter
     *
     * @return double
     */
    public double getDiameter() {
        return 2 * radius;
    }

    /**
     * Get Perimeter
     *
     * @return double
     */
    public double getPerimeter() {
        return 2 * radius * Math.PI;
    }

    @Override
    public String toString() {
        System.out.println("In CircleFromSimpleGeometricShape toString method ");
        return super.toString() + " radius: " + radius;
    }

}//end CircleFromSimpleGeometricShape class


class RectangleFromSimpleGeometricShape extends SimpleGeometricShape {

    private double length;
    private double width;

    RectangleFromSimpleGeometricShape() {

    }

    RectangleFromSimpleGeometricShape(double length, double width) {
        setLength(length);
        setWidth(width);
    }

    RectangleFromSimpleGeometricShape(double length, double width, String color) {
        setLength(length);
        setWidth(width);
        this.setColor(color);
    }

    RectangleFromSimpleGeometricShape(double size, String color) {
        setLength(size);
        setWidth(size);
        this.setColor(color);
    }


    public void setLength(double length) {
        this.length = length;
    }


    public void setWidth(double width) {
        this.width = width;
    }


    public double getLength() {
        return this.length;
    }

    public double getWidth() {
        return this.width;
    }

    public double getArea() {
        return length * width;
    }

    public double getPerimeter() {
        return (length * 2) + (width * 2);
    }

    public void setColor(String input) {
        super.setColor(input);
    }

    @Override
    public String toString() {
        return "Test";
    }

}
