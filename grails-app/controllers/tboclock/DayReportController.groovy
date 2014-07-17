package tboclock

import grails.plugin.springsecurity.annotation.Secured
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import tboclock.auth.User

import java.sql.Timestamp

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
@Secured(['ROLE_USER'])
@Transactional(readOnly = false)
class DayReportController {
    def scaffold = true;
    def springSecurityService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond getMonthValues(new LocalDate()), model:[dayReportInstanceCount: DayReport.count()]
    }

    def saveType(){
        DayReport b = DayReport.findById(Long.parseLong(params.getIdentifier().toString()))
        b.type = tboclock.DayReport.ReportType.valueOf(params.type)
        if(b.validate()){
        b.save flush: true}
    }

    def getMonthValues(def date){
        LocalDate firstDay = date.dayOfMonth().withMinimumValue()
        LocalDate lastDay = date.dayOfMonth().withMaximumValue()

        def reports = [];
        for(i in 1..lastDay.getDayOfMonth()){
            def day = firstDay.plusDays(i-1)
            def curr = DayReport.findOrSaveByDayAndUser(day,springSecurityService.currentUser)
            reports.add(curr);
        }
        return reports.sort{it.day}
    }

    def selectUserValue(){
        def dates = getFirstAndLastDayOfMonth(params.month,params.year)
        def user = User.get(params.user);
        def reports = DayReport.findAllByDayBetweenAndUser(dates.first(),dates.last(),user);
        render template: 'dayReport',
                collection: reports.sort{it.day},
                var: 'dayReportInstance'
    }

    // get first and last day of month
    private getFirstAndLastDayOfMonth(String Smonth,String year){
        Integer month = (Integer.parseInt(Smonth))
        def date = new LocalDate().withMonthOfYear(month).withYear(Integer.parseInt(year))
        LocalDate firstDay = date.dayOfMonth().withMinimumValue()
        LocalDate lastDay = date.dayOfMonth().withMaximumValue()
        return [firstDay,lastDay]
    }


    def createReportsAjax() {

        def dates = getFirstAndLastDayOfMonth(params.month,params.year)
        render template: 'dayReport',
               collection: getMonthValues(dates.first()),
                var: 'dayReportInstance'
        }

    //Time change - income or out
    def ajaxTimeChange(){
        def val = 0;
        String property="";
        //validate
        if(params.income){
            val = params.income
            property = "income"
        }
        else{
            val =params.outcome
            property = "outcome"
        }
        // validate pattern
        def pattern = ~/^(?:2[0-3]|[01][0-9]):[0-5][0-9]$/

        // validate pattern
       if(val && pattern.matcher(val).matches()){
            DayReport b = DayReport.findById(Long.parseLong(params.getIdentifier().toString()))
            //val = Date.parse('HH:mm', val);
           def hours = Integer.parseInt(val.split(":")[0])
           def minutes = Integer.parseInt(val.split(":")[1])
            def time = new LocalTime().withHourOfDay(hours).withMinuteOfHour(minutes)
           b.setProperty(property,time)
           b.save flush: true
           render b.total
       }
    }

    @Transactional
    def save(DayReport dayReportInstance) {
        if (dayReportInstance == null) {
            notFound()
            return
        }

        if (dayReportInstance.hasErrors()) {
            respond dayReportInstance.errors, view:'create'
            return
        }

        dayReportInstance.save flush:true

        dayReportInstance.total;
        request.withFormat {
            '*' { respond dayReportInstance}
        }

    }

    @Transactional
    def update(DayReport dayReportInstance) {
        if (dayReportInstance == null) {
            notFound()
            return
        }

        if (dayReportInstance.hasErrors()) {
            respond dayReportInstance.errors, view:'edit'
            return
        }

        dayReportInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'DayReport.label', default: 'DayReport'), dayReportInstance.id])
                redirect dayReportInstance
            }
            '*'{ respond dayReportInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(DayReport dayReportInstance) {

        if (dayReportInstance == null) {
            notFound()
            return
        }

        dayReportInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'DayReport.label', default: 'DayReport'), dayReportInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'dayReport.label', default: 'DayReport'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
