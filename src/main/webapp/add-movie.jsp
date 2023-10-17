<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Movie</title>
        <style>
            form label, input{
                display: flex;
                flex-direction: column;
            }
        </style>
    </head>
    <body>
        <h1>Please enter details of new movie:</h1>
        <form action="movie.do?action=new" method="post">
            <label for="title">Movie Title</label>
            <input type="text" id="title" name="title" placeholder="Enter movie title" />
            <br>
            <label for="actor">Lead Actor</label>
            <input type="text" id="actor" name="actor" placeholder="Enter lead actor name" />
            <br>
            <label for="actress">Lead Actress</label>
            <input type="text" id="actress" name="actress" placeholder="Enter lead actress name" />
            <br>
            <label for="genre">Genre</label>
            <input type="text" id="genre" name="genre" placeholder="Enter genre" />
            <br>
            <label for="year">Year</label>
            <input type="text" id="year" name="year" placeholder="Enter release year" />
            <br>
            <button type="submit">Add Movie</button>
        </form>
    </body>
</html>
