package com.epam.engx.cleandesign.workcost


import com.epam.engx.cleandesign.workscope.Surface
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Subject(MaterialPrice)
@Title('Price of materials')
@Narrative('the function of materials price for the surface')
class MaterialPriceTest extends Specification {
    def surface = Stub(Surface)

    def "should calculate the price of materials with default material area factor"() {

        given: 'the material price function with default area factor'
        def priceFunction = MaterialPrice.norm()

        when: 'we have a surface to be treated'
        surface.area() >> surfaceArea

        then: 'we get accurately calculated the cost of the material for the surface'
        priceFunction.apply(surface) == expectedPrice

        where:
        surfaceArea | expectedPrice
        0           | 0
        1           | 10
        10.2        | 102
    }

    def "should calculate the price of materials with certain material area factor"() {

        given: 'the material price function with certain material area factor'
        def priceFunction = MaterialPrice.of(materialAreaFactor)

        when: 'we have a surface to be treated'
        surface.area() >> surfaceArea

        then: 'we get accurately calculated the cost of the material for the surface'
        priceFunction.apply(surface) == expectedPrice

        where:
        materialAreaFactor | surfaceArea | expectedPrice
        0                  | 0           | 0
        0                  | 100         | 0
        1                  | 1           | 1
        1                  | 0.1         | 0.1
        3                  | 0.1         | 0.3
        10                 | 1           | 10
    }
}
