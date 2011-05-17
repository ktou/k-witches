<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>admin Staticvalue</title>
</head>
<body>
<% if(request.getAttribute("message") != null){ %>
<p><font color ="red">${message}</font></p>
<% } %>
<p>Setting static value</p>
<form method="post" enctype="multipart/form-data" action="./staticvalue">
<label>Type</label>
<input type="text" name="type" >
<label>Value</label>
<input type="text" name="value">
<input type="submit" value="Set">
</form>

</body>
</html>
