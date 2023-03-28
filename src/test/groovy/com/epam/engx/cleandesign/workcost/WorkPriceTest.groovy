package com.epam.engx.cleandesign.workcost


import com.epam.engx.cleandesign.workscope.Surface
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Subject(WorkPrice)
@Title('Price of work')
@Narrative('the function of work price for the surface')
class WorkPriceTest extends Specification {

    def "should calculate the price of work for the surface"() {

        given: 'formula for calculating the cost of work based on the cost of processing a unit area'
        def priceFunction = WorkPrice.of(workPrice)

        when: 'we have a surface to be treated'
        def surface = Stub(Surface) { area() >> surfaceArea }

        then: 'we get accurately calculated the cost of the work'
        priceFunction.apply(surface) == expectedPrice

        where: 'the cost of processing a unit area, the surface area and expected price'
        workPrice | surfaceArea || expectedPrice
        0         | 10          || 0
        10        | 0           || 0
        1         | 1           || 1
        10        | 40          || 400
        10        | 50          || 550
        3         | 10.1        || 30.3
    }
}
