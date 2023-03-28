package com.epam.engx.cleandesign.worker

import com.epam.engx.cleandesign.workscope.Surface
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Subject(JuniorWorker)
@Title('Junior Worker')
@Narrative('calculation of wages and personal bonus for a junior worker')
class JuniorWorkerTest extends Specification {

    def "should calculate personal bonus for Junior work"() {

        given: 'junior worker with any payment and performance parameters'
        def worker = new JuniorWorker(_ as Payment, _ as Performance)

        expect: 'personal bonus is equals to vendor bonus'
        worker.personalBonus(vendorBonus) == personalBonus

        where:
        vendorBonus | personalBonus
        0.0         | 0
        55.55       | 55.55
        100.0       | 100
    }

    def "should calculate salary for Junior worker"() {

        given: 'daily rate and worker productivity'
        def payment = Payment.of(dailyRate)
        def performance = Performance.of(amountPerDay)

        and: 'junior worker with given payment and performance parameters'
        def worker = new JuniorWorker(payment, performance)

        when: 'we have a surface to be treated'
        def surface = Stub(Surface) { area() >> surfaceArea }

        then: 'we get accurate calculated wages of this worker for a given surface'
        worker.salary(surface) == juniorSalary

        where:
        dailyRate | amountPerDay | surfaceArea | juniorSalary
        120.0     | 20.0         | 100         | 600
        109.5     | 21.3         | 287         | 1533
    }
}
