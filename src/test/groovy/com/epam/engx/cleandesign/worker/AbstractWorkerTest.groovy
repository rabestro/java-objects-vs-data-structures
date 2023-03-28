package com.epam.engx.cleandesign.worker

import com.epam.engx.cleandesign.workscope.Surface
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Subject(AbstractWorker)
@Title('Abstract Worker')
@Narrative('base class for workers that implements standard payroll calculations')
class AbstractWorkerTest extends Specification {

    def surface = Stub Surface
    def payment = Mock Payment
    def performance = Mock Performance

    def "should calculate salary"() {

        given: 'stubbed surface returns predefined area'
        surface.area() >> processingArea

        and: 'we create an implementation of the abstract worker class'
        def worker = new AbstractWorker(payment, performance) {
            @Override
            BigDecimal personalBonus(BigDecimal vendorBonus) {
                return null
            }
        }

        when: 'we calculate the salary of a worker for processing a given surface'
        def salary = worker.salary(surface)

        then: 'a method is called to calculate the required number of days'
        1 * performance.daysToProcess(surface) >> workingDays

        and: 'a method is called to calculate the amount of salary for the number of days worked'
        1 * payment.payForDays(workingDays) >> payForWork

        and: 'the calculated salary value is returned as a result'
        salary == payForWork

        where:
        processingArea | workingDays | payForWork
        100            | 4           | 400
    }
}
