<%@ page import="org.joda.time.LocalDate; tboclock.DayReport" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:setProvider library="jquery"/>
    %{--<g:setProvider library="bootstrap"/>--}%
    <asset:javascript src="bootstrap-select"/>
    <asset:stylesheet href="bootstrap-select"/>
    <g:javascript >
            $(document).ready(function(){
//        $('.datepicker').datepicker();
        %{--$('#mon').change= < g:remoteFunction action="createReportsAjax" id="1" />--}%
        $('#users').on("change",function(){
        $("#pageTitle").text($(this.selectedOptions).text() + " " + "reports ")
                 var year = document.getElementById("mon_year").value
            var month = document.getElementById("mon_month").value
            var user = this.value
        ${remoteFunction(
                update: "allReports",
                action: 'selectUserValue',
                params: '{year: year, month: month, user: user}')}}
        )

       $('#mon_month').on("change",function(){changeMonth()})
       $('#mon_year').on("change",function(){changeMonth()})
       function changeMonth(value){
        var a = document.getElementById("mon_year").value
        var month = document.getElementById("mon_month").value
        ${remoteFunction(
                update: "allReports",
                action: 'createReportsAjax',
                params: '{year: a, month: month}')}}
         $('#sandbox-container input')
        })
    </g:javascript>
    <g:set var="entityName" value="${message(code: 'dayReport.label', default: 'DayReport')}" />
    <title><g:message code="tbo.userReport.title" args="${sec.loggedInUserInfo(field: 'username')}" /></title>
</head>
<body>
<input type="text" id="sandbox-container" type="text" class="form-control">
<div id="list-dayReport" class="content container-fluid" role="main">
    <h1 id="pageTitle"><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <div id="select" class="form-horizontal">
    <label for="mon">date: </label>
        <joda:datePicker class="span" id="mon" name="mon" precision="month" relativeYears="[-2..2]" value="${new LocalDate()}" onchange="${remoteFunction(action:'createReportsAjax',params: '\'month=\' + this.value')}" ></joda:datePicker>
      <div class="pull-right">
        <sec:ifLoggedIn>
            <g:form name="logoutForm" controller="logout" action="index">
                <g:submitButton class="btn-primary" name="signOut" value="sign out"/>
            </g:form>
        </sec:ifLoggedIn>
    </div>
        %{--<div class="">--}%
        <sec:access expression="hasRole('ROLE_ADMIN')">
            <label for="users"> user: </label>
<g:select name="users" class="selectpicker" from="${tboclock.auth.User.list()}" optionValue="fullname" optionKey="id"></g:select>
            </sec:access>
            %{--</div>--}%
        </div>
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
