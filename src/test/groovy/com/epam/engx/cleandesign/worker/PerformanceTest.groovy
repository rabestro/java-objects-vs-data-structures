package com.epam.engx.cleandesign.worker

import com.epam.engx.cleandesign.workscope.Surface
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Subject(Performance)
@Title('The worker performance')
@Narrative("allows us to determine the required number of days to process a particular surface")
class PerformanceTest extends Specification {
    def surface = Stub(Surface)

    def "should accurately calculate days to process surface"() {

        given: 'surface that needs to be treated by the worker'
        surface.area() >> areaToProcess

        when: 'we create performance object based on the number of surfaces a worker can process in one day'
        def performance = Performance.of(amountPerDay as double)

        then: 'we can accurately calculate the required number of days to process the entire required surface'
        performance.daysToProcess(surface) == expectedDays

        where:
        amountPerDay | areaToProcess || expectedDays
        1            | 3             || 3
        1            | 2.01          || 3
        1            | 2.99          || 3
        1            | 3.01          || 4
        0.1          | 0.3           || 3
        0.001        | 0.0           || 0
    }

    def "should throw an exception for a negative number or zero"() {
        when: ''
        def performance = Performance.of(amountPerDay as double)

        then:
        def message = thrown IllegalArgumentException

        and:
        message =~ "must be a positive"

        where:
        amountPerDay << [-7, 0, -0.001, Math.nextDown(0.0)]
    }
}
