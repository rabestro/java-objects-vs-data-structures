package com.epam.engx.cleandesign.workscope


import com.epam.engx.cleandesign.workcost.PriceFunction
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Subject(Zone)
@Title('Zone of work')
@Narrative('class representing the work area')
class ZoneTest extends Specification {

    def expenses = Stub(PriceFunction)
    def totalSurface = Stub(Surface)
    def apertures = Stub(Surface)

    def "should accurately calculate the work area of the zone"() {

        given:
        totalSurface.area() >> totalArea
        apertures.area() >> aperturesArea

        when:
        def zone = new Zone(expenses, totalSurface, apertures)

        then:
        zone.area() == expectedArea

        where:
        totalArea | aperturesArea | expectedArea
        100       | 0             | 100
        100       | 20            | 80
        99.09     | 2.01          | 97.08
    }

    def "should calculate the expenses for the zone"() {

        given:
        expenses.apply(_ as Surface) >> totalBills

        when:
        def zone = new Zone(expenses, totalSurface, apertures)

        then:
        zone.bill() == totalBills

        where:
        totalBills = 100
    }
}
