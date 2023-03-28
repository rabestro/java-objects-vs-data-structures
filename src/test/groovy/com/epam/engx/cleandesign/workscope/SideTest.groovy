package com.epam.engx.cleandesign.workscope


import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Subject(Side)
@Title('Side of surface')
@Narrative('a positive number representing the side of the surface')
class SideTest extends Specification {

    def "should create an instance of Side class for a positive number"() {

        when: 'we create a side using a positive real number'
        def side = Side.of(value as double)

        then: 'no exceptions are thrown'
        notThrown IllegalArgumentException

        and: 'we get an object of type side'
        side instanceof Side

        and: 'this object can be used as a regular high precision number'
        side instanceof BigDecimal

        where:
        value << [1, 0.001, Math.nextUp(0.0), 38, Double.MAX_VALUE]
    }

    def "should throw an exception for a negative or zero number"() {

        when: 'we are trying to create a side using a negative number or zero'
        def side = Side.of(value as double)

        then: 'we cannot create this object and an exception is thrown'
        def message = thrown IllegalArgumentException

        and: 'the error message indicates the reason for the exception'
        message =~ "must be a positive"

        where:
        value << [-7, 0, -0.001, Math.nextDown(0.0)] //To-Do: test fails for Double.MIN_VALUE
    }

}
