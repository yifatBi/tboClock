<%@ page import="tboclock.DayReport" %>


<tr><td><g:select class="form-control text-center" name="type" from="${DayReport.ReportType?.values()}" noSelection="${['':'-choose-']}" value="${fieldValue(bean:dayReportInstance,field:'type')}" onchange="${remoteFunction(action: 'saveType',
              params: '\'type=\' + this.value',id: "${dayReportInstance?.id}")}"  id="${dayReportInstance?.id}"></g:select></td>
%{--<td><g:formatDate format="dd/MM" date="${dayReportInstance?.day}"/></td>--}%
<td><joda:format value="${dayReportInstance?.day}" pattern="dd-MM-yyyy"/></td>
<td><tbo:dow dateTime="${dayReportInstance?.day}"/></td>
<g:set var="a" value="${dayReportInstance?.id}" hidden="hidden"/>
<g:set var="d" value="false" hidden="hidden"/>
    %{--<td><joda:datePicker></joda:datePicker></td>--}%
<td>               <g:remoteField  action="ajaxTimeChange" class="text-center" name="income" paramName="income" value="${joda.format(pattern: "HH:mm", value: dayReportInstance?.income)}" id="${a}" update="total${dayReportInstance?.id}"/></td>
%{--<td>                    <tbo:myTextField  name="income" value="${fieldValue(bean:dayReportInstance,field:'income')}" id="${a}" disabled="true"/>--}%
%{--</td>--}%

    %{--<tbo:myTextField disabled="true"/>--}%
%{--<td>--}%
    %{--<g:remoteField action="ajaxTimeChange"  name="outcome" paramName="outcome" value="${formatDate(format:'HH:mm',date:dayReportInstance?.outcome)}" id="${a}" update="total${dayReportInstance?.id}"/></td>--}%
    %{--<td><g:remoteField action="ajaxTimeChange"  name="outcome" paramName="outcome" value="${joda.format(value: dayReportInstance?.outcome)}" id="${a}" update="total${dayReportInstance?.id}"/></td>--}%
    <td><g:remoteField action="ajaxTimeChange" class="text-center" name="outcome" paramName="outcome" value="${joda.format(pattern: "HH:mm", value: dayReportInstance?.outcome)}" id="${a}" update="total${dayReportInstance?.id}"/></td>
%{--<td><tbo:total start="${dayReportInstance.income}" end="${dayReportInstance.outcome}"/></td>--}%
<td id="total${dayReportInstance?.id}"> ${dayReportInstance?.total}</td></tr>

