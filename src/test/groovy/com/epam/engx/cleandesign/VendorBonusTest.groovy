package com.epam.engx.cleandesign

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Subject(VendorBonus)
@Title('Bonus from vendor')
@Narrative('a positive number representing a bonus from the vendor for the work performed')
class VendorBonusTest extends Specification {

    def "should create an instance of VendorBonus for a positive number"() {

        when: 'we create a vendor bonus using a positive number'
        def bonus = VendorBonus.of(value as double)

        then: 'the object created successfully, no exceptions was thrown'
        notThrown IllegalArgumentException

        and: 'this object can be used as a regular high precision number'
        bonus instanceof BigDecimal

        where: 'various positive numbers'
        value << [1, 0.001, Math.nextUp(0.0), 38, Double.MAX_VALUE]
    }

    def "should throw an exception for a negative number"() {

        when: 'we are trying to create a vendor bonus using a negative number'
        def bonus = VendorBonus.of(value as double)

        then: 'we cannot create this object and an exception is thrown'
        def message = thrown IllegalArgumentException

        and: 'the error message indicates the reason for the exception'
        message =~ "must be a positive"

        where:
        value << [-1_000_000, -1, -0.01, Math.nextDown(0.0)]
    }

}
