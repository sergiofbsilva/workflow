<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr" %>

<h2><bean:message key="title.viewLogs" bundle="WORKFLOW_RESOURCES"/></h2>

<bean:define id="processClassName" name="process" property="class.name" type="java.lang.String"/>
<bean:define id="includeFolder" value="<%= processClassName.replace('.','/')%>"/>

<p class="mtop05 mbottom15">
	<html:link action="/workflowProcessManagement.do?method=viewProcess" paramId="processId" paramName="process" paramProperty="OID">
		«  <bean:message key="link.backToProcess" bundle="WORKFLOW_RESOURCES"/>
	</html:link>
</p>


<jsp:include page='<%= "/" + includeFolder + "/shortBody.jsp" %>'/>



<logic:empty name="operationLogs">
	<p>
		<em><bean:message key="label.noLogs" bundle="WORKFLOW_RESOURCES"/>.</em>
	</p>
</logic:empty>

<fr:view name="operationLogs" schema="viewLogs">
	<fr:layout name="tabular">
		<fr:property name="classes" value="tstyle2"/>
		<fr:property name="columnClasses" value="smalltxt, smalltxt aleft,smalltxt"/>
		<fr:property name="sortBy" value="whenOperationWasRan"/>
	</fr:layout>
</fr:view>
