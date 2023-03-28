package com.epam.engx.cleandesign.worker

import com.epam.engx.cleandesign.VendorBonus
import com.epam.engx.cleandesign.workscope.Surface
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import java.util.function.BiFunction

@Title('Workers')
@Narrative('the demo test that shows the differences in payroll for different workers')
@Subject([JuniorWorker, SeniorWorker])
class WorkerFunctionalTest extends Specification {

    def "should calculate the correct salary for workers of different types"() {

        given: 'same productivity and pay per day for both types of workers'
        def payment = Payment.of(250)
        def performance = Performance.of(30)

        and: 'identical working surface for both types of workers'
        def surface = Surface.square(5)

        when: 'we create a worker of a certain type'
        def worker = workerFabric.apply(payment, performance)

        then: 'we get salary corresponds to the expected salary for this type'
        worker.salary(surface) == expectedSalary

        when: 'we have a bonus from the vendor'
        def vendorBonus = VendorBonus.of(100)

        then: 'we get a personal bonus corresponding to the type of worker'
        worker.personalBonus(vendorBonus) == expectedBonus

        where: 'different types of workers and their corresponding wages'
        workerType        | expectedSalary | expectedBonus
        JuniorWorker::new | 250            | 100
        SeniorWorker::new | 300            | 150

        and: 'a factory that creates a worker of the desired type with the given parameters'
        workerFabric = workerType as BiFunction<Payment, Performance, Worker>
    }
}
