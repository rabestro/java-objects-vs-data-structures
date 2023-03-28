package com.epam.engx.cleandesign.worker

import com.epam.engx.cleandesign.workscope.Surface
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Subject(SeniorWorker)
@Title('Junior Worker')
@Narrative('calculation of wages and personal bonus for a senior worker')
class SeniorWorkerTest extends Specification {

    def "should calculate personal bonus for senior work"() {

        given: 'senior worker with any payment and performance parameters'
        def worker = new SeniorWorker(_ as Payment, _ as Performance)

        expect: ''
        worker.personalBonus(vendorBonus) == personalBonus

        where:
        vendorBonus | personalBonus
        0.0         | 0
        100.0       | 150
        55.55       | 83.325
    }

    def "should calculate salary for senior worker"() {

        given: 'daily rate and worker productivity'
        def payment = Payment.of(dailyRate)
        def performance = Performance.of(amountPerDay)

        and: 'senior worker with given payment and performance parameters'
        def worker = new SeniorWorker(payment, performance)

        when: 'we have a surface to be treated'
        def surface = Stub(Surface) { area() >> surfaceArea }

        then: 'we get accurate calculated wages of this worker for a given surface'
        worker.salary(surface) == seniorSalary

        where:
        dailyRate | amountPerDay | surfaceArea | seniorSalary
        120.0     | 20.0         | 100         | 720
        109.5     | 21.3         | 287         | 1839.6
    }
}
