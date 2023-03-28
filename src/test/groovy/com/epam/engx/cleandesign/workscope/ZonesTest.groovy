package com.epam.engx.cleandesign.workscope

import spock.lang.Specification
import spock.lang.Subject

@Subject(Zones)
class ZonesTest extends Specification {
    def zone1 = Stub(WorkScope)
    def zone2 = Stub(WorkScope)
    def zone3 = Stub(WorkScope)

    def "should calculate total area of zones"() {
        given:
        zone1.area() >> area1
        zone2.area() >> area2
        zone3.area() >> area3

        when:
        def zones = Zones.of(zone1, zone2, zone3)

        then:
        zones.area() == expectedArea

        where:
        area1 | area2 | area3 | expectedArea
        12    | 30    | 22    | 64
    }
}
