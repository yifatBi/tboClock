import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.joda.time.Period
import tboclock.DayReport
import tboclock.auth.Role
import tboclock.auth.User
import tboclock.auth.UserRole

import java.text.SimpleDateFormat

class BootStrap {

    def init = { servletContext ->
        environments {
            development {
                def adminRole = Role.findOrSaveWhere(authority: "ROLE_ADMIN");
                def user = User.findOrSaveWhere(username: "w", password: "2",firstName: "yifat", lastName: "biezuner")
                def user1 = User.findOrSaveWhere(username: "q", password: "1",firstName: "yifat", lastName: "biezuner")
                if(!user.authorities.contains(adminRole))
                {
                    UserRole.create(user,adminRole,true)
                    UserRole.create(user1,adminRole,true)
                }
               // def hourFormat = new SimpleDateFormat("hh:mm")
                def day = new LocalDate();
//                if(!User.count){
//                    new User(name: "yifat", password: "122").save()
//                }
                if(!DayReport.count) {
//                   def jul = day.updated([month: day[Calendar.MONTH] + 1])
//                    jul.setDate(1)
//                    def augost = jul.updated([month: day[Calendar.MONTH] + 1])
//                   augost.setMonth(7)
                    def augost = day.withMonthOfYear(8);
                    new DayReport(type: DayReport.ReportType.WORK, day: day, income: new LocalTime(), outcome: new LocalTime(), user: user).save()
                    new DayReport(type: DayReport.ReportType.WORK, day: augost, income: new LocalTime(), outcome: new LocalTime(), user: user).save()
                    new DayReport(type: DayReport.ReportType.WORK, day: augost.withMonthOfYear(9), income: new LocalTime(), outcome: new LocalTime(), user: user).save()
                    def rep = new DayReport(type: DayReport.ReportType.WORK, day: day, income: new LocalTime(), outcome: new LocalTime().plusMinutes(4),user:user)
//                    def rep = new DayReport(type: DayReport.ReportType.WORK, day: day, income: day.parse("08:22"), outcome: day.parse("18:22"))
//                   def rep1 = new DayReport(type: DayReport.ReportType.WORK, day: jul.plus(0), income: day.parse("HH:mm", "08:00"), outcome: day.parse("hh:mm", "18:22"))
//                    def rep5 = new DayReport(type: DayReport.ReportType.WORK, day: jul.plus(7), income: day.parse("HH:mm", "08:00"), outcome: day.parse("hh:mm", "18:22"))
//                    def rep2 = new DayReport(type: DayReport.ReportType.WORK, day: augost.plus(2), income: day.parse("HH:mm", "08:44"), outcome: day.parse("hh:mm", "18:22"))
//                    def rep3 = new DayReport(type: DayReport.ReportType.WORK, day: augost.plus(3), income: day.parse("HH:mm", "08:30"), outcome: day.parse("hh:mm", "18:00"))
//                    def rep4 = new DayReport(type: DayReport.ReportType.WORK, day: day.plus(4), income: day.parse("HH:mm", "08:30"), outcome: day.parse("hh:mm", "08:30"))
                    rep.save()
//                    rep1.save()
//                    rep5.save()
//                    rep2.save()
//                    rep3.save()
//                    rep4.save()

                }

                print(DayReport.count)
            }
        }
    }
    def destroy = {
    }
}
