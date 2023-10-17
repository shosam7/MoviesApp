/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.moviesapp.controllers;

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
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            //System.out.println("Inside search");
            String keyword = request.getParameter("keyword");
            String searchBy = request.getParameter("search-by");
            System.out.println(searchBy);
            request.setAttribute("keyword", keyword);
            request.setAttribute("search_by", searchBy);
            RequestDispatcher rd = request.getRequestDispatcher("result.jsp");
            rd.forward(request, response);
        } else if (action.equalsIgnoreCase("add")) {
            response.sendRedirect("add-movie.jsp");
        } else if (action.equalsIgnoreCase("new")) {
            PrintWriter out = response.getWriter();
            Connection connection = null;
            ResultSet rs = null;
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
//                StringBuilder sql;
//                sql = new StringBuilder("INSERT INTO moviedb").append(" (");
//                StringBuilder values;
//                values = new StringBuilder("(");
//                rs = stmt.executeQuery("SELECT * FROM TABLE");
//                Map<String, String[]> movie = request.getParameterMap();
//                for (Map.Entry<String, String[]> entry : movie.entrySet()) {
//                    sql.append(entry.getKey());
//                    values.append(entry.getValue()[0]);
//                }
//                Map<String, String[]> movie = request.getParameterMap();
//                //movie.remove("action");
//                StringBuilder sql = new StringBuilder("INSERT INTO movies").append(" (");
//                StringBuilder placeholders = new StringBuilder();
//
//                for (Iterator<String> iter = movie.keySet().iterator(); iter.hasNext();) {
//                    String param = iter.next();
//                    if (param.equalsIgnoreCase("action")) {
//                        continue;
//                    }
//                    sql.append(param);
//                    placeholders.append("?");
//
//                    if (iter.hasNext()) {
//                        sql.append(",");
//                        placeholders.append(",");
//                    }
//                }
//
//                sql.append(") VALUES (").append(placeholders).append(")");
//                System.out.println(sql);
//                PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
//                System.out.println(preparedStatement);
//                int i = 0;
//                int count = 0;
//                System.out.println(movie.values().size());
//                for (String[] value : movie.values()) {
//                    if (value[0].equalsIgnoreCase("new")) {
//                        System.out.println(value[0] + " Skipped");
//                        continue;
//                    }
//                    count++;
//                    System.out.println(count);
//                    preparedStatement.setString(i++, value[0]);
//                }
//                System.out.println(i);
//                int affectedRows = preparedStatement.executeUpdate();
//                System.out.println("Number of affected rows: " + affectedRows);
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
