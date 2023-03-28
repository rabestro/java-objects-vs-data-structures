package com.epam.engx.cleandesign

import com.epam.engx.cleandesign.worker.JuniorWorker
import com.epam.engx.cleandesign.worker.Payment
import com.epam.engx.cleandesign.worker.Performance
import com.epam.engx.cleandesign.worker.SeniorWorker
import com.epam.engx.cleandesign.workscope.Surface
import com.epam.engx.cleandesign.workscope.ZoneFactory
import com.epam.engx.cleandesign.workscope.Zones
import spock.lang.*

import static com.epam.engx.cleandesign.workscope.Surface.rectangle
import static com.epam.engx.cleandesign.workscope.Surface.square

@Title("Fund valuation")
@Subject(FundValuation)
@Narrative("Acceptance test for fund valuation")
class FundValuationAcceptanceTest extends Specification {

    def zoneFactory = new ZoneFactory()
            .add("Wall", 15.0)
            .add("Floor", 10.0)
            .add("Ceiling", 12.0)

    static VENDOR_BONUS = VendorBonus.of(50)
    static BIG_VENDOR_BONUS = VendorBonus.of(100)

    def "should calculate zero balance when no assignments"() {

        given: 'no assignments'
        def fundValuation = FundValuation.of()

        expect: 'zero balance'
        fundValuation.fundBalance() == 0
    }

    @Unroll('should calculate balance when one worker with #comment')
    def "should calculate balance when one senior worker"() {

        given: 'one senior painter'
        def painter = new SeniorWorker(Payment.of(rate), Performance.of(amountPerDay))

        and: 'one assigment with one zone'
        def surface = rectangle(width, height)
        def zone = zoneFactory.create(type, surface.subtract(apertures))
        def assigment = painter.assign(zone, VendorBonus.of(bonus))

        when: 'we create a fund valuation with this assignment'
        def fundValuation = FundValuation.of(assigment)

        and: 'calculate balance'
        def balance = fundValuation.fundBalance()

        then: 'we get expected result'
        balance == expected

        where: 'surface sides, zone type, vendor bonus and worker characteristics'
        width | height | type      | apertures
        5     | 5      | "Wall"    | Surface.zero()
        5     | 5      | "Wall"    | Surface.zero()
        10    | 10     | "Wall"    | rectangle(9, 5).add(rectangle(9, 4))
        5     | 5      | "Floor"   | Surface.zero()
        5     | 5      | "Ceiling" | Surface.zero()
        3     | 5      | "Ceiling" | Surface.zero()
        __
        bonus | rate | amountPerDay || expected | comment
        50    | 250  | 30           || 250      | 'one wall assignment'
        100   | 250  | 30           || 175      | 'one wall assignment with big vendor bonus'
        50    | 250  | 30           || 100      | 'one big wall with aperture assignment'
        50    | 180  | 30           || 209      | 'one floor assignment'
        50    | 200  | 30           || 235      | 'one ceiling assignment'
        50    | 200  | 30           || 15       | 'one small ceiling assignment'
    }

    def "should calculate balance when one worker with two ceiling assignment"() {

        given: 'one senior worker'
        def painter = new SeniorWorker(Payment.of(200), Performance.of(30))

        and: 'two ceiling assignment'
        def zone1 = zoneFactory.create("Ceiling", rectangle(2, 5))
        def zone2 = zoneFactory.create("Ceiling", rectangle(3, 5))
        def assigment = new Assignment(painter, Zones.of(zone1, zone2), VendorBonus.of(50))

        when: 'we create a fund valuation with this assignment'
        def fundValuation = FundValuation.of(assigment)

        and: 'calculate balance'
        def balance = fundValuation.fundBalance()

        then: 'we get expected fund balance'
        balance == 235
    }

    def "should calculate balance when one junior worker with one ceiling assignment and #comment"() {

        given: 'one junior worker'
        def junior = new JuniorWorker(Payment.of(200), Performance.of(30))

        and: 'one ceiling assignment'
        def zone = zoneFactory.create("Ceiling", square(5.0))
        def assigment = new Assignment(junior, zone, VendorBonus.of(bonus))

        when: 'we create a fund valuation with this assignment'
        def fundValuation = FundValuation.of(assigment)

        and: 'calculate balance'
        def balance = fundValuation.fundBalance()

        then: 'we get expected result'
        balance == expected

        where:
        bonus | expected | comment
        50    | 300      | 'regular vendor bonus'
        100   | 250      | 'big vendor bonus'
    }

    def "should calculate balance when one #worker with one ceiling assignment"() {

        given: 'one ceiling assignment'
        def zone = zoneFactory.create("Ceiling", square(5))
        def assigment = new Assignment(painter, Zones.of(zone), VendorBonus.of(50))

        when: 'we create a fund valuation with this assignment'
        def fundValuation = FundValuation.of(assigment)

        and: 'calculate balance'
        def balance = fundValuation.fundBalance()

        then: 'we get expected result'
        balance == expected

        where: 'worker defined as'
        painter                                               | expected | worker
        new JuniorWorker(Payment.of(100), Performance.of(30)) | 400      | 'junior cheap worker'
        new SeniorWorker(Payment.of(180), Performance.of(30)) | 259      | 'senior cheap worker'
    }

    def "should calculate balance when one worker with one wall assignment #comment"() {

        given: 'one senior worker'
        def senior = new SeniorWorker(Payment.of(250), Performance.of(amountPerDay))

        and: 'one wall assignment'
        def workScope = zoneFactory.create("Wall", surface)
        def assigment = senior.assign(workScope, VendorBonus.of(50))

        when: 'we create a work estimate with this assignment'
        def fundValuation = FundValuation.of(assigment)

        then: 'we get expected fund balance'
        fundValuation.fundBalance() == expected

        where:
        surface          | amountPerDay | expected | comment
        rectangle(9, 5)  | 30           | 450      | 'works two days but less amount then max per day'
        rectangle(11, 5) | 30           | 782.5    | 'works two days and more amount then max per day'
        rectangle(11, 5) | 60           | 1082.5   | 'works one day but more amount then max per day'
    }

    def "should calculate balance when two workers with one wall assignment"() {

        given: 'two senior workers'
        def highDailyRate = Payment.of(250)
        def ordinaryPerformance = Performance.of(30)

        def john = new SeniorWorker(highDailyRate, ordinaryPerformance)
        def mike = new SeniorWorker(highDailyRate, ordinaryPerformance)

        and: 'one wall zone'
        def zone = zoneFactory.create("Wall", square(5))

        and: 'two wall assignments'
        def johnAssigment = new Assignment(john, zone, VENDOR_BONUS)
        def mikeAssigment = new Assignment(mike, zone, VENDOR_BONUS)

        when: 'we create a fund valuation with this assignments'
        def fundValuation = FundValuation.of(johnAssigment, mikeAssigment)

        and: 'calculate balance'
        def balance = fundValuation.fundBalance()

        then: 'we get expected result'
        balance == 500
    }

    def "should calculate balance for complex testcase"() {

        given: 'several workers'
        def liam = new SeniorWorker(Payment.of(250), Performance.of(40))
        def jack = new JuniorWorker(Payment.of(200), Performance.of(30))
        def owen = new SeniorWorker(Payment.of(280), Performance.of(60))

        and:
        def apertures = rectangle(9, 5).add(rectangle(9, 4))

        and:
        def zone1 = zoneFactory.create("Floor", rectangle(5, 3))
        def zone2 = zoneFactory.create("Wall", square(10))
        def zone3 = zoneFactory.create("Ceiling", square(5))
        def zone4 = zoneFactory.create("Wall", square(10).subtract(apertures))
        def zone5 = zoneFactory.create("Wall", rectangle(1, 5))
        def zone6 = zoneFactory.create("Wall", rectangle(11, 5))

        and:
        def liamAssigment = liam.assign(Zones.of(zone1, zone2), BIG_VENDOR_BONUS)
        def jackAssigment = jack.assign(Zones.of(zone3, zone4), VENDOR_BONUS)
        def owenAssigment = owen.assign(Zones.of(zone5, zone6), VENDOR_BONUS)

        when: 'we create a fund valuation with this assignments'
        def fundValuation = FundValuation.of(liamAssigment, jackAssigment, jackAssigment, owenAssigment)

        and: 'calculate balance'
        def actual = fundValuation.fundBalance()

        then: 'we get expected result'
        actual == expected

        where: 'expected fund balance'
        expected = 4221.5
    }
}
