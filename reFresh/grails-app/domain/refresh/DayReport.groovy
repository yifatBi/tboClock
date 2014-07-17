package refresh

import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.springframework.format.datetime.joda.LocalTimeParser
//import sun.util.resources.LocaleData

class DayReport {
    LocalDate day;
    LocalTime income;
    LocalTime outcome;
    String total
    enum ReportType{WORK,SICK}
    ReportType type;

    static transients = ['total'];

    String getTotal() {
        String total = ""
        if(income&&outcome){
            Integer hours = outcome.getHourOfDay() - income.getHourOfDay()
            Integer minutes = outcome.getMinuteOfHour() - income.getMinuteOfHour()
            if(minutes < 0 && hours > 0){
                hours--
                minutes +=60
            }
            total = hours + ":"  + minutes
        }
    }
    static constraints = {
    }
}
