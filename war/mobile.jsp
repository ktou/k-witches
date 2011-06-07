<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="kwitches.util.TimeUtils" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Mobile</title>
</head>
<body>
<c:if test="${isKAuth}">
<form action="/signmobile" method="post">
  <textarea id="body" name="comment" class="textarea" cols="40" rows="5">${f:h(body)}</textarea><br />
  <input type="hidden" name="location" value="mobile">
  <input type="submit" value="送信">
</form>

<div class="content">
<c:forEach var="bbsData" items="${bbsDataList}">
  <span class="xfolkentry"><a href="./mobile?offset=${offset}&limit=${limit}&body=%3E%3E${bbsData.id}">${bbsData.id}</a>
  &nbsp;<a href=""><b>${bbsData.name}</b></a>&nbsp;
  <fmt:formatDate value="${bbsData.createdDate}" pattern="yyyy-MM-dd（E） kk:mm:ss" />
  <blockquote>
      ${f:br(f:nbsp(f:h(bbsData.comment)))}
  </blockquote>
  </span>
</c:forEach>
</div>
<p>
<c:choose>
  <c:when test="${prevPage != -1}">
    <a href="mobile?offset=${prevPage}&limit=${limit}">Prev</a>
  </c:when>
  <c:otherwise>
    Prev
  </c:otherwise>
</c:choose>
<c:choose>
  <c:when test="${nextPage != -1}">
    <a href="./mobile?offset=${nextPage}&limit=${limit}">Next</a>
  </c:when>
  <c:otherwise>
    Next
  </c:otherwise>
</c:choose>
</c:if>
<br><br>
<c:if test="${'LOGOUT' == isLogin}">
<a href="${urlpath}">login</a>
</c:if>
<c:if test="${'LOGIN' == isLogin}">
${userInfo.email} | <a href="${urlpath}">logout</a>
</c:if>
</body>
</html>
