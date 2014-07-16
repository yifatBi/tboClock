<%@ page import="org.joda.time.LocalDate; tboclock.DayReport" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:setProvider library="jquery"/>
    <g:javascript >
            $(document).ready(function(){
//        $('.datepicker').datepicker();
        %{--$('#mon').change= < g:remoteFunction action="createReportsAjax" id="1" />--}%
       $('#mon_month').on("change",function(){changeMonth()})
       $('#mon_year').on("change",function(){changeMonth()})
       function changeMonth(value){
        var a = document.getElementById("mon_year").value
        var month = document.getElementById("mon_month").value
        ${remoteFunction(
                update: "allReports",
                action: 'createReportsAjax',
                params: '{year: a, month: month}')}}
        })
    </g:javascript>
    <g:set var="entityName" value="${message(code: 'dayReport.label', default: 'DayReport')}" />
    <title><g:message code="tbo.userReport.title" args='["Yifat"]' /></title>
</head>
<body>
<a href="#list-dayReport" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
    </ul>
</div>
<div id="list-dayReport" class="content container-fluid" role="main">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    %{--<div class="datepicker"></div>--}%
    %{--<g:remoteLink update="table" action="createReportsAjax">--}%
        <joda:datePicker class="span" id="mon" name="mon" precision="month" relativeYears="[-2..2]" value="${new LocalDate()}" onchange="${remoteFunction(action:'createReportsAjax',params: '\'month=\' + this.value')}" ></joda:datePicker>
%{--<button class=" btn btn-danger" value="123"></button>--}%
    %{--</g:remoteLink>--}%
    <table id="table" class="table-striped">
        <thead>
        <tr>
            <g:sortableColumn property="type" title="${message(code: 'dayReport.type.label', default: 'Type')}" />
            <g:sortableColumn property="day" title="${message(code: 'dayReport.day.label', default: 'Date')}" />
            <g:sortableColumn property="day" title="Dow" />
            <g:sortableColumn property="income" title="${message(code: 'dayReport.income.label', default: 'In')}" />
            <g:sortableColumn property="outcome" title="${message(code: 'dayReport.outcome.label', default: 'Out')}" />
            <g:sortableColumn property="outcome"  title='Total' />
        </tr>
        </thead>
        <tbody id="allReports">
        <g:render template="dayReport" collection="${dayReportInstanceList}" var="dayReportInstance"/>
        </tbody>
    </table>
    %{--<div class="pagination">--}%
        %{--<g:paginate total="${dayReportInstanceCount ?: 0}" />--}%
    %{--</div>--}%
</div>
</body>
</html>
