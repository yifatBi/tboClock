package tboclock

import grails.test.mixin.TestFor
import groovy.time.TimeCategory
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(UtilsTagLib)
class UtilsTagLibSpec extends Specification {

    @Shared
    Date date = new Date()
    @Shared
    def weekDays =["Sunday","Monday","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"]
    @Shared
    String today = weekDays[date.getDay()]

    @Unroll
    def "test total for #testName from #start to #end"(){
        expect: "the time"

        applyTemplate('<tbo:total start="${start}" end="${end}" />',
                [start: start ,end:end]) == expectedTotal
        where:
        test|start|end|expectedTotal
        "NoDifferent"|date|date| "0H"
        "Only Hours Diff"|date|date.updated([hourOfDay:date[Calendar.HOUR_OF_DAY] + 1])| "1H"
        "Hour and Minute"|date|date.updated([hourOfDay:date[Calendar.HOUR_OF_DAY] + 1, minute: date[Calendar.MINUTE] + 1])| "1H AND 1M"
        "Less then Hour"|date|date.updated([hourOfDay:date[Calendar.HOUR_OF_DAY] + 1, minute: date[Calendar.MINUTE] - 2])| "0H AND 58M"

    }


    @Unroll
    def "test day of week today #Ddate plus #num"(){
        expect:
        applyTemplate('<tbo:dow dateTime="${Ddate}" />', [dateTime: Ddate])== expectDate
        where:
            Ddate|expectDate|num
            date|today| 0
            date.plus(7)|today| 7
    }
    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
    }
    @Unroll
    void "Conversion of #testName matches #expectedNiceDate"() {
        // The last two tests fail on days when the clocks go backwards
        // or forwards!

        expect:
        applyTemplate('<hub:dateFromNow date="${date}" />',
                [date: testDate]) == expectedNiceDate

        where:
        testName       |    testDate      |   expectedNiceDate
        "Current Time" | new Date()       | "Right now"
        "Now - 1 day"  | new Date() - 1   | "1 day ago"
        "Now - 2 days" | new Date() - 2   | "2 days ago"
        "Now - second" | use(TimeCategory){1.second.from.now}   | "2 days ago"
    }
}
