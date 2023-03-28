package com.epam.engx.cleandesign.workcost


import com.epam.engx.cleandesign.workscope.Surface
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title('Zone Price')
@Subject([ZonePrice, MaterialPrice, WorkPrice])
@Narrative('Component test for the price of the zone, taking into account the costs of materials and labor')
class ZonePriceComponentTest extends Specification {

    def "should calculate the cost of work and materials for the zone"() {

        given: 'the price of the zone consists of the price of the materials and the cost of work'
        def zonePriceFunction = ZonePrice.of(MaterialPrice.norm(), WorkPrice.of(workPrice))

        when: 'we have a surface to be treated'
        def surface = Stub(Surface) { area() >> surfaceArea }

        then: 'we get accurately calculated price of the zone'
        zonePriceFunction.apply(surface) == expectedPrice

        where:
        workPrice | surfaceArea | expectedPrice | comment
        10        | 0           | 0             | 'zero price for zero area'
        0         | 10          | 100           | 'at zero labor cost, the zone price is equal to the cost of materials'
        10        | 40          | 800           | 'zone price for one day work'
        10        | 50          | 1050          | 'zone price for two-day work'
    }
}
