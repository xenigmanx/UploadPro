<%-- 
    Document   : index
    Created on : Jan 22, 2019, 11:05:30 AM
    Author     : pupil
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div>Upload to Servrer</div>
        <br>
        <form action="upload" method="POST" enctype="multipart/form-data">
            <input type="file" name="file"><br>
            <br>
            <input type="submit" value="Upload">
        </form>
        файл с pic\
        <br><img src ="pic\z.jpg" height="130px" width="180px">
        <br>
        файл с fileServlet\
        <br><img src="fileServlet\z.jpg" height="130px" width="180px">
        <br>
        файл с http://da.am
        <br><img src="http://i.da.am/7787.png">
        <br>
        файл с https://radikal.ru
        <br><img src="https://a.radikal.ru/a35/1901/43/9c99e630b667.png">
        <ul>
            <c:forEach var="fileName" items="${listFiles}">
                <li>${fileName}</li>
            </c:forEach>
        </ul>
    </body>
</html>
