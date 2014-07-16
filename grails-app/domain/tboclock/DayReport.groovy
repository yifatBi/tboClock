package tboclock

import grails.plugin.jodatime.JodaTimeUtils
import org.hibernate.annotations.Type
import org.jadira.usertype.dateandtime.joda.PersistentLocalDate
import org.joda.time.DateTime
import org.joda.time.LocalTime
import org.joda.time.Period
import org.joda.time.field.PreciseDateTimeField
import tboclock.auth.User

import javax.persistence.Column
import java.text.ParseException
import java.text.SimpleDateFormat
import org.joda.time.*


class DayReport {

    LocalDate day
    LocalTime income
    LocalTime outcome
    enum ReportType{SICK,WORK}
    ReportType type
    static belongsTo = [user: User]
    static constraints = {
        day nullable: false
        income nullable: true
        outcome nullable: true
        type nullable: true
//        income validator: {
//            try {
//                Date date = DATEFORMAT.parse(it)
//                return DATEFORMAT.format(date) == it
//            } catch (ParseException e) {
//                return false
//            }
//        }
    }

    static mapping = {
       // day type: PersistentLocalDate
    }
    String total
    static transients = ['total'];
    String getTotal() {

        String totalVal = "";
        if(outcome && income) {
            //  if(outcome)
            Integer hours = outcome.getHourOfDay() - income.getHourOfDay()
            Integer minutes = outcome.getMinuteOfHour() - income.getMinuteOfHour();
            if (minutes < 0) {
                minutes += 60;
                hours--
            }
            totalVal = hours + 'H'
            if (minutes > 0) {
                totalVal += " AND " + minutes + "M "
            }
        }
        return totalVal;
    }

}
