package com.epam.engx.cleandesign

import com.epam.engx.cleandesign.worker.Worker
import com.epam.engx.cleandesign.workscope.WorkScope
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Subject(Assignment)
@Title('Work Assignment')
class AssignmentTest extends Specification {
    def vendorBonus = VendorBonus.noBonus()
    def workScope = Stub WorkScope
    def worker = Stub Worker

    def "should calculate fund balance for work assignment"() {

        given: ''
        workScope.bill() >> zonesBill
        worker.salary(workScope) >> workerSalary
        worker.personalBonus(vendorBonus) >> workerBonus

        when:
        def assignment = new Assignment(worker, workScope, vendorBonus)

        then:
        assignment.fundBalance() == expectedFundBalance

        where:
        workerSalary | vendorBonus | workerBonus | zonesBill
        400          | 100         | 100         | 1000

        and: 'the balance of funds must be calculated using the formula'
        expectedFundBalance = zonesBill - (workerSalary + workerBonus)
    }
}
