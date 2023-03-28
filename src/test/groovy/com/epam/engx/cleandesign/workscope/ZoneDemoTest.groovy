package com.epam.engx.cleandesign.workscope

import com.epam.engx.cleandesign.workcost.MaterialPrice
import com.epam.engx.cleandesign.workcost.PriceFunction
import com.epam.engx.cleandesign.workcost.WorkPrice
import com.epam.engx.cleandesign.workcost.ZonePrice
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title('Work Zone')
@Subject([Zone, Surface, PriceFunction, MaterialPrice, WorkPrice, ZonePrice])
@Narrative('demo test showing how to set the cost of work')
class ZoneDemoTest extends Specification {

    def "should correctly calculate the surface and cost of work"() {

        given: 'a task to create a blind area for the house'
        def house = Surface.square(60)
        def houseWithBlindArea = Surface.square(60 + 2)
        def blindAreaSurface = houseWithBlindArea.subtract(house)

        and: 'formula for calculating the rent for a concrete mixer'
        PriceFunction concreteMixer = { surface -> 10 + surface.area() * 2 }

        and: 'formulas for counting materials and work'
        PriceFunction concrete = MaterialPrice.of(3)
        PriceFunction work = WorkPrice.of(5)

        and: 'the total cost of creating a blind area for a house'
        PriceFunction zonePrice = ZonePrice.of(concreteMixer, concrete, work)

        when: 'we determine the scope of work'
        def zone = new Zone(zonePrice, blindAreaSurface)

        then: 'we get the exact area'
        zone.area() == expectedArea

        and: 'we get the exact cost of all work'
        zone.bill() == expectedBill

        where:
        expectedArea = 244
        expectedBill = 2572
    }
}
