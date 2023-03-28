package com.epam.engx.cleandesign.workscope


import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Subject([Surface, Side])
@Title('Surface interface')
@Narrative('Component test for static methods of Surface interface')
class SurfaceTest extends Specification {

    def "should precisely calculate area of square surface"() {
        when:
        def square = Surface.square(side)

        then:
        square instanceof Surface

        and:
        square.area() == areaOfSquare

        where:
        side | areaOfSquare
        1.0  | 1.0
        2.0  | 4.0
        0.1  | 0.01
        0.3  | 0.09
    }

    def "should precisely calculate area of rectangle surface"() {
        when:
        def rectangle = Surface.rectangle(a, b)

        then:
        rectangle instanceof Surface

        and:
        rectangle.area() == areaOfRectangle

        where:
        a   | b   | areaOfRectangle
        0.1 | 0.1 | 0.01
        0.1 | 3   | 0.3
        2   | 1   | 2
        1   | 2   | 2
        3   | 7   | 21
        7   | 3   | 21
    }

    def "should throw an exception for the negative or zero side of the square"() {
        when:
        def square = Surface.square(side)

        then:
        def message = thrown(IllegalArgumentException)

        and:
        message =~ "must be a positive"

        where:
        side << [-7, 0, -0.001, Math.nextDown(0.0)]
    }

    def "should throw an exception for the negative or zero sides of the rectangle"() {
        when:
        def rectangle = Surface.rectangle(width, height)

        then:
        def message = thrown(IllegalArgumentException)

        and:
        message =~ "must be a positive"

        where:
        width | height
        0     | 1
        1     | 0
        -1    | 1
        1     | -1
        0     | 0
        -1    | -1
    }
}
