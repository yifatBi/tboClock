package tboclock

import grails.plugin.springsecurity.annotation.Secured
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime


import java.sql.Timestamp

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
@Secured(['ROLE_ADMIN'])
@Transactional(readOnly = false)
class DayReportController {
    def scaffold = true;

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.month = new LocalDate().getMonthOfYear();
        params.year = new LocalDate().getYear();
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

       def reports = DayReport.findByDayBetween(firstDay,lastDay)?.findAllWhere(user: getAuthenticatedUser())
//        def reports = DayReport.createCriteria().list {
//            and {
//                between("day",firstDay,lastDay)
//                eq("user",getAuthenticatedUser())
//            }
//            setMaxResults(lastDay.getDayOfMonth())
//            order("day", "ASC")
//        }
        def days = [];
        days.addAll([])
        for(def i in reports?.asList()){

            days.add(i.getDay().getDayOfMonth().toString())
        }
        for ( i in 1..lastDay.getDayOfMonth()) {
            def day = firstDay.plusDays(i-1)
            if(!days.contains((i).toString()))
            {
                def currDay = DayReport.findOrSaveByDayAndUser(day,getAuthenticatedUser())
             //   currDay.save()
                reports.add(currDay)
            }
        }
        return reports.sort{it.day}
    }


    def createReportsAjax() {
            Integer month = (Integer.parseInt(params.month))
           def date = new LocalDate().withMonthOfYear(month).withYear(Integer.parseInt(params.year))

        render template: 'dayReport',
               collection: getMonthValues(date),
                var: 'dayReportInstance'
        }


    def show(DayReport dayReportInstance) {
        respond dayReportInstance
    }

    def create() {
        respond new DayReport(params)
    }

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

       if(val && pattern.matcher(val).matches()){
            DayReport b = DayReport.findById(Long.parseLong(params.getIdentifier().toString()))
            //val = Date.parse('HH:mm', val);
           def hours = Integer.parseInt(val.split(":")[0])
           def minutes = Integer.parseInt(val.split(":")[1])
            def time = new LocalTime().withHourOfDay(hours).withMinuteOfHour(minutes)
           b.setProperty(property,time)
           // b.setProperty(property,b.getProperty(property).updated([hourOfDay:val.getHours(), minute:val.getMinutes()]))
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
//            form multipartForm {
//              //  flash.message = message(code: 'default.created.message', args: [message(code: 'dayReport.label', default: 'DayReport'), dayReportInstance.id])
//                redirect dayReportInstance
//            }
           // '*'{respond dayReportInstance.total}

            '*' { respond dayReportInstance}
        }

    }

    def edit(DayReport dayReportInstance) {
        respond dayReportInstance
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
