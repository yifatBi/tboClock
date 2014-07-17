<%@ page import="org.joda.time.LocalDate; tboclock.DayReport" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:setProvider library="jquery"/>
    %{--<g:setProvider library="bootstrap"/>--}%
    <asset:javascript src="bootstrap-select.js"/>
    <asset:stylesheet href="bootstrap-select.min.css"/>
    <g:javascript >
            $(document).ready(function(){

        //user box was changed
        //change title and reports according to data
        $('#users').on("change",function(){
            $("#pageTitle").text($(this.selectedOptions).text() + " " + "reports ")
            var year = document.getElementById("mon_year").value
            var month = document.getElementById("mon_month").value
            var user = this.value
            ${remoteFunction(
                update: "allReports",
                action: 'selectUserValue',
                params: '{year: year, month: month, user: user}')}
        })
        // when date change update total
       $('#mon_month').on("change",function(){changeMonth()})
       $('#mon_year').on("change",function(){changeMonth()})
       //create the relevant params and send to server
       function changeMonth(){
        var user= $("#users").val()
        var year = document.getElementById("mon_year").value
        var month = document.getElementById("mon_month").value
        ${remoteFunction(
                update: "allReports",
                action: 'createReportsAjax',
                params: '{year: year, month: month, user:user}')}}

        })
    </g:javascript>
    <title>${sec.loggedInUserInfo(field: 'username')}</title>
</head>
<body>
<div id="list-dayReport" class="content container-fluid" role="main">
    <h1 id="pageTitle"><g:message code="tbo.userReport.title" args="${sec.loggedInUserInfo(field: 'username')}" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <div id="select" class="row">
    <label for="mon">date: </label>
        <joda:datePicker class="span" id="mon" name="mon" precision="month" years="[2013, 2014, 2015, 2016]" value="${new LocalDate()}" onchange="${remoteFunction(action:'createReportsAjax',params: '\'month=\' + this.value')}" ></joda:datePicker>
      <div class="pull-right">
        <sec:ifLoggedIn>
            <g:form name="logoutForm" controller="logout" action="index">
                <g:submitButton class="btn-primary" name="signOut" value="sign out"/>
            </g:form>
        </sec:ifLoggedIn>
    </div>
        <div class="col-sm-6 col-lg-3">
         <!--happen only when admin user!-->
        <sec:access expression="hasRole('ROLE_ADMIN')">
            <label for="users"> user: </label>
        <g:select name="users" class="selectpicker" from="${tboclock.auth.User.list()}" optionValue="fullname" optionKey="id"></g:select>
            </sec:access>
            </div>
        </div>
    <table id="table" class="table-striped">
        <thead>
        <tr>
            <g:sortableColumn property="type" title="type"/>
            <g:sortableColumn property="day" title="day"/>
            <g:sortableColumn property="dow" title="dow"/>
            <g:sortableColumn property="income" title="income"/>
            <g:sortableColumn property="outcome" title="outcome"/>
            <g:sortableColumn property="total" title='Total' />
        </tr>
        </thead>
        <tbody id="allReports">
        <g:render template="dayReport" collection="${dayReportInstanceList}" var="dayReportInstance"/>
        </tbody>
    </table>
</div>
</body>
</html>
