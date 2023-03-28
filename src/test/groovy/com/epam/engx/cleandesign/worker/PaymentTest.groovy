package com.epam.engx.cleandesign.worker


import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Subject(Payment)
@Title("Worker's pay")
@Narrative("this class is responsible for paying the worker")
class PaymentTest extends Specification {

    def "should accurately calculate the worker's salary for the days worked"() {

        when: 'we create a payment based on the daily rate of a worker'
        def payment = Payment.of(dailyRate as double)

        then: 'we get the accurately calculated wages of the worker for the days worked'
        payment.payForDays(workingDays) == expectedPayment

        where:
        dailyRate | workingDays || expectedPayment
        100       | 10          || 1000
        100       | 0           || 0
        0         | 10          || 0
        0.1       | 3           || 0.3
        3.3       | 3           || 9.9
        199.01    | 3           || 597.03
    }

    def "should throw an exception for a negative number"() {
        when:
        def payment = Payment.of(dailyRate as double)

        then:
        def message = thrown IllegalArgumentException

        and:
        message =~ "must be a positive"

        where:
        dailyRate << [-7, -0.001, Math.nextDown(0.0)]
    }
}
