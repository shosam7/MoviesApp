/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.moviesapp.controllers;

import com.mycompany.moviesapp.pojo.Movie;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sho7
 */
@WebServlet(name = "MovieController", urlPatterns = {"/movie.do"})
public class MovieController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String action = (String) request.getParameter("action");
        //System.out.println(action);

        if (action.equalsIgnoreCase("browse")) {
            response.sendRedirect("search.html");
        } else if (action.equalsIgnoreCase("search")) {
            PrintWriter out = response.getWriter();
            Connection connection = null;
            ResultSet rs = null;
            Statement stmt = null;
            try {
                //System.out.println("Inside search");
                String keyword = request.getParameter("keyword");
                String searchBy = request.getParameter("search-by");
                System.out.println(searchBy);
                request.setAttribute("keyword", keyword);
                request.setAttribute("search_by", searchBy);

                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost/moviedb", "root", "12345678");
                stmt = connection.createStatement();
                StringBuilder sql = new StringBuilder("SELECT * from movies where ");
                sql.append(searchBy);
                sql.append(" = ");
                sql.append("'");
                sql.append(keyword);
                sql.append("'");
                sql.append(";");
                System.out.println(sql);
                rs = stmt.executeQuery(sql.toString());
                List<Movie> movieList = new ArrayList<Movie>();
                while (rs.next()) {
                    Movie movie = new Movie();
                    movie.setTitle(rs.getString("title"));
                    movie.setActor(rs.getString("actor"));
                    movie.setActress(rs.getString("actress"));
                    movie.setGenre(rs.getString("genre"));
                    movie.setYear(rs.getInt("year"));
                    movieList.add(movie);
                }
                request.setAttribute("movies", movieList);
                RequestDispatcher rd = request.getRequestDispatcher("result.jsp");
                rd.forward(request, response);
            } catch (SQLException ex) {
                out.println("SQL Error " + ex);
            } catch (ClassNotFoundException ex) {
                //Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
                out.println("JDBC Class Error" + ex);
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                    out.close();
                    System.out.println("All connections closed in search");
                } catch (SQLException e) {
                    out.println("SQL Error during closing connections in search" + e);
                }
            }
        } else if (action.equalsIgnoreCase("add")) {
            response.sendRedirect("add-movie.jsp");
        } else if (action.equalsIgnoreCase("new")) {
            PrintWriter out = response.getWriter();
            Connection connection = null;
            Statement stmt = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost/moviedb", "root", "12345678");
                stmt = connection.createStatement();
                String title = request.getParameter("title");
                String actor = request.getParameter("actor");
                String actress = request.getParameter("actress");
                String genre = request.getParameter("genre");
                String year = request.getParameter("year");
                String sql = "INSERT INTO movies (title,actor,actress,genre,year) VALUES (?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                System.out.println(preparedStatement);
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, actor);
                preparedStatement.setString(3, actress);
                preparedStatement.setString(4, genre);
                preparedStatement.setString(5, year);
                int affectedRows = preparedStatement.executeUpdate();
                response.sendRedirect("add-success.html");
            } catch (SQLException ex) {
                out.println("SQL Error " + ex);
            } catch (ClassNotFoundException ex) {
                //Logger.getLogger(MovieController.class.getName()).log(Level.SEVERE, null, ex);
                out.println("JDBC Class Error" + ex);
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                    out.close();
                    System.out.println("All connections closed");
                } catch (SQLException e) {
                    out.println("SQL Error during closing connections" + e);
                }
            }

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
