<%@page import="myorg.util.BundleUtil"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Collection"%>
<%@page import="pt.ist.fenixframework.pstm.AbstractDomainObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="module.workflow.domain.ProcessFile"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>
<%@page import="module.workflow.presentationTier.WorkflowLayoutContext"%>
<%@page import="myorg.presentationTier.actions.ContextBaseAction"%>

<style>
table.files-list {
width: 100%;
}
table.files-list td {
}
table th.file-name {
text-align: left;
}
table th.file-desc {
text-align: left;
}
table th.file-type { }
table th.file-ash { }
table th.file-state { }
table th.file-download { }
table td.file-name {
text-align: left;
}
table td.file-name div {
width: 150px !important;
word-wrap: break-word;
}
table td.file-desc div {
width: 150px !important;
word-wrap: break-word;
text-align: left;
}
table td.file-type { }
table td.file-ash {
text-transform: uppercase;
color: #888;
font-size: 10px;
text-align: center;
}
table td.file-ash div {
text-align: center;
margin: auto;
width: 150px !important;
word-wrap: break-word;
}
table td.file-state {
width: 75px !important;
}
table td.file-download {
width: 75px !important;
}
</style>

<h2><bean:message bundle="WORKFLOW_RESOURCES" key="label.fileDetails" /></h2>
<p class="mtop05 mbottom15">
	<html:link page="/workflowProcessManagement.do?method=viewProcess" paramId="processId" paramName="process" paramProperty="externalId">
		« <bean:message key="link.backToProcess" bundle="WORKFLOW_RESOURCES"/>
	</html:link>
</p>


<%
	final WorkflowLayoutContext layoutContext = (WorkflowLayoutContext) ContextBaseAction.getContext(request);
%>

<jsp:include page='<%= layoutContext.getWorkflowShortBody() %>'/>

<logic:empty name="listFiles">
	<p class="mtop15"><em><bean:message key="label.noFiles" bundle="WORKFLOW_RESOURCES"/>.</em></p>
</logic:empty>

<logic:notEmpty name="listFiles">
	<table class="tview1 files-list">
			<tr>
				<th class="file-name"><bean:message bundle="WORKFLOW_RESOURCES" key="label.filename"/>:</th>
				<th class="file-desc"><bean:message bundle="WORKFLOW_RESOURCES" key="label.presentationName"/>:</th>
				<th class="file-type"><bean:message bundle="WORKFLOW_RESOURCES" key="label.fileType"/>:</th>
				<th class="file-ash"><bean:message bundle="WORKFLOW_RESOURCES" key="label.digestSha1"/>:</th>
				<th class="file-state"><bean:message bundle="WORKFLOW_RESOURCES" key="label.fileStatus"/>:</th>
				<th class="file-download"></th>
			</tr>
		<logic:iterate id="file" name="listFiles" type="module.workflow.domain.ProcessFile">
			<bean:define id="fileId" name="file" property="externalId" type="java.lang.String"/>
			<logic:empty name="file" property="processWithDeleteFile">
				<tr>
			</logic:empty>
			<logic:notEmpty name="file" property="processWithDeleteFile">
				<tr>
			</logic:notEmpty>
					<td class="file-name"><div><bean:write name="file" property="filename"/></div></td>
					<td class="file-desc"><div><bean:write name="file" property="presentationName"/></div></td>
					<td class="file-type"><%=BundleUtil.getLocalizedNamedFroClass(file.getClass())%></td>
					<td class="file-ash"><div><bean:write name="file" property="hexSHA1MessageDigest"/></div></td>
					<logic:empty name="file" property="processWithDeleteFile">
						<td class="file-state"><bean:message bundle="WORKFLOW_RESOURCES" key="label.fileStatus.active"/></td>
					</logic:empty>
					<logic:notEmpty name="file" property="processWithDeleteFile">
						<td class="file-state"><bean:message bundle="WORKFLOW_RESOURCES" key="label.fileStatus.deleted"/></td>
					</logic:notEmpty>
					<td class="file-download"><span>(<html:link page='<%= "/workflowProcessManagement.do?method=downloadFile&fileId=" + fileId %>' paramId="processId" paramName="process" paramProperty="externalId"><bean:message key="link.downloadFile" bundle="WORKFLOW_RESOURCES"/></html:link>)</span></td>
				</tr>
		</logic:iterate>
	</table>
</logic:notEmpty>
