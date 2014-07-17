<g:setProvider library="jquery"/>
<%@ page import="tboclock.DayReport" %>
<tr>
<td><g:select class="selectpicker" data-size="auto" name="type" from="${DayReport.ReportType?.values()}" noSelection="${['':'-choose-']}" value="${fieldValue(bean:dayReportInstance,field:'type')}" onchange="${remoteFunction(action: 'saveType',
        params: '\'type=\' + this.value',id: "${dayReportInstance?.id}")}"  id="${dayReportInstance?.id}"></g:select></td>
    <td><joda:format value="${dayReportInstance?.day}" pattern="dd-MM-yyyy"/></td>
    <td><tbo:dow dateTime="${dayReportInstance?.day}"/></td>
    <g:set var="a" value="${dayReportInstance?.id}" hidden="hidden"/>
    <td><g:remoteField  action="ajaxTimeChange" class="text-center" name="income" paramName="income" value="${joda.format(pattern: "HH:mm", value: dayReportInstance?.income)}" id="${a}" update="total${dayReportInstance?.id}"/></td>
    <td><g:remoteField action="ajaxTimeChange" class="text-center" name="outcome" paramName="outcome" value="${joda.format(pattern: "HH:mm", value: dayReportInstance?.outcome)}" id="${a}" update="total${dayReportInstance?.id}"/></td>
    <td id="total${dayReportInstance?.id}"> ${dayReportInstance?.total}</td>
<script>
        var notWorkDays = ["Friday", "Saturday"]
        var idd = dayReportInstance.id
        alert(dayReportInstance.day.dayOfWeek().getAsText())
        if(notWorkDays.contains(dayReportInstance.day.dayOfWeek().getAsText())){
            $("#"+idd).attr("disabled", "disabled")
        }
        $("#"+idd + " .selectpicker").on("change", function () {
            alert("hh")
            alert("hh")
        })
</script>
</tr>
