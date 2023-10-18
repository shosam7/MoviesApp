<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Result JSP Page</title>
    </head>
    <body>
        <h3>You searched for movies with ${requestScope.search_by} as
            ${requestScope.keyword}</h3>
        <h3>Here are the search results</h3>

        <table border="2" width="100%">  
            <tr>  
                <th>Movie Title</th>  
                <th>Lead Actor</th>
                <th>Lead Actress</th>  
                <th>Genre</th>
                <th>Year</th>
            </tr>
            <c:forEach var="movie" items="${requestScope.movies}">  
                <tr>  
                    <td><c:out value="${movie.title}"/></td>  
                    <td><c:out value="${movie.actor}"/></td>  
                    <td><c:out value="${movie.actress}"/></td>  
                    <td><c:out value="${movie.genre}"/></td>
                    <td><c:out value="${movie.year}"/></td> 
                </tr>  
            </c:forEach>  
        </table>
        <br>

        <a href="index.html">Return to home page</a>
    </body>
</html>
