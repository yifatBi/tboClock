package tboclock

import org.codehaus.groovy.grails.plugins.web.api.TagLibraryApi
import org.joda.time.LocalDate
import org.joda.time.LocalTime

class UtilsTagLib {
    static defaultEncodeAs = [taglib:'html']
    static namespace = "tbo"
    def dow = { attrs ->
        LocalDate date = attrs.dateTime

             out << date.dayOfWeek().getAsText()
    }

//    def setDisable = {attr ->
//        out << ""
//    }
    def total ={attrs ->
        Date start = attrs.start
        Date end = attrs.end
        Integer hours = end.getHours() - start.getHours()
        Integer minutes = end.getMinutes() - start.getMinutes();
        if(minutes < 0){
            minutes += 60;
            hours--
        }
        out << hours + 'H'
        if(minutes > 0) {
            out << " AND " + minutes + "M"
        }
    }
    def myTextField = { attrs, body ->
        def isDisabled = attrs.remove("disabled")
        def value = attrs.value;
        def day = attrs.day
        def id = attrs.id
        def update = attrs.update
        def name = attrs.name

        if ("true".equals(isDisabled)) {
            out << """<input  disabled="${isDisabled}" """
            attrs.each { k,v ->
                out << k << "=\"" << v << "\" "
            }
            out << "/>"
        } else {
            out << """<input  """
            attrs.each { k,v ->
                out << k << "=\"" << v << "\" "
            }
            out << "/>"
        }
//        if ("true".equals(isDisabled)) {
//            out << """<input  disabled='${isDisabled}'/>"""
//        } else {
//            out << """<input  onChange='"""
//        }
//            out << g.remoteFunction(controller:"dayReport" ,action: "ajaxTimeChange", name:name, paramName:name, value:value, id: id, update: ("total" + id))
//            attrs.each { k,v ->
//                out << k << "=\"" << v << "\" "
//            }
//            out << "/>"
    }
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
}
