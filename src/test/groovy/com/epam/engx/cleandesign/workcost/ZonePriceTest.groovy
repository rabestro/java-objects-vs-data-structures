package com.epam.engx.cleandesign.workcost

import com.epam.engx.cleandesign.workscope.Surface
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title


@Subject(ZonePrice)
@Title('Zone price function')
@Narrative('the price of the zone is obtained by adding up all the costs')
class ZonePriceTest extends Specification {
    def surface = Stub Surface

    def "should return zero if there is no Zona processing cost"() {

        given: 'we do not have any costs associated with the processing of the zone'
        def zonePrice = ZonePrice.of()

        when: 'we have a surface to be treated'
        surface.area() >> zoneArea

        then: 'we always get zero cost of work'
        zonePrice.apply(surface) == 0

        where: 'the surface area of the zone can be of any size'
        zoneArea << [0, 1, 100, 1_000_000, Integer.MAX_VALUE]
    }

    def "should calculate the costs for the zone with one item of expenditure"() {

        given: 'a surface to be treated'
        surface.area() >> zoneArea

        and: 'one item of expenses for processing the zone'
        def expenditure = Stub(PriceFunction) {
            apply(surface) >> expenses
        }

        when: 'zone treatment price consists of only one expense item'
        def zonePrice = ZonePrice.of(expenditure)

        then: 'we always get zero cost of work'
        zonePrice.apply(surface) == expenses

        where: 'the surface area of the zone can be of any size'
        zoneArea | expenses
        0        | 0
        10       | 0
        10       | 22.5
        1000     | 99.9
    }

    def "should calculate the costs for the zone with several items of expenditure"() {

        given: 'a surface to be treated'
        surface.area() >> zoneArea

        and: 'several items of expenses for processing the zone'
        def expenditure = expenses.collect({ cost ->
            Stub(PriceFunction) { apply(surface) >> cost }
        }) as PriceFunction[]

        when: 'zone treatment price consists of all expenses'
        def zonePrice = ZonePrice.of(expenditure)

        then: 'we get an accurately calculated zone treatment price'
        zonePrice.apply(surface) == price

        where:
        zoneArea | expenses           | price
        0        | []                 | 0
        10       | [5, 10]            | 15
        10       | [22.5, 13, 27, 14] | 76.5
        90       | [99.9, 0, 100.1]   | 200
        120      | [0.1, 0.1, 0.1]    | 0.3
    }
}
