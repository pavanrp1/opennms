<%--

//
// This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2002-2003 The OpenNMS Group, Inc.  All rights reserved.
// OpenNMS(R) is a derivative work, containing both original code, included code and modified
// code that was published under the GNU General Public License. Copyrights for modified 
// and included code are below.
//
// OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
//
// Modifications:
//
// Original code base Copyright (C) 1999-2001 Oculan Corp.  All rights reserved.
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
//
// For more information contact:
//      OpenNMS Licensing       <license@opennms.org>
//      http://www.opennms.org/
//      http://www.opennms.com/
//

--%>

<%@page language="java"
	contentType="text/html"
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="/includes/header.jsp" flush="false" >
  <jsp:param name="title" value="Distributed Poller Status" />
  <jsp:param name="headTitle" value="Distriuted Poller Status" />
  <jsp:param name="location" value="admin" />
  <jsp:param name="breadcrumb" value="<a href='admin/'>Admin</a>" />
  <jsp:param name="breadcrumb" value="Distributed Poller Status" />
</jsp:include>

<h3><c:out value="${webTable.title}" /></h3>

<table>
  <tr>
    <c:forEach items="${webTable.columnHeaders}" var="headerCell">
      <th class="<c:out value='${headerCell.styleClass}'/>">
        <c:choose>
          <c:when test="${! empty headerCell.link}">
            <a href="<c:out value='${headerCell.link}'/>"><c:out value="${headerCell.content}"/></a>
          </c:when>
          <c:otherwise>
            <c:out value="${headerCell.content}"/>
          </c:otherwise>
        </c:choose>
      </th>
    </c:forEach>
  </tr>
  
  <c:forEach items="${webTable.rows}" var="row">
    <tr class="<c:out value='${row[0].styleClass}'/>">
      <c:forEach items="${row}" var="cell">
        <td class="<c:out value='${cell.styleClass}'/> divider">
          <c:choose>
            <c:when test="${! empty cell.link}">
	            <a href="<c:out value='${cell.link}'/>"><c:out value="${cell.content}"/></a>
            </c:when>
            <c:otherwise>
 				 <c:out value="${cell.content}"/>
            </c:otherwise>
          </c:choose>
        </td>
      </c:forEach>
    </tr>
  </c:forEach>
</table>

<jsp:include page="/includes/footer.jsp" flush="false"/>
