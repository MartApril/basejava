package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        response.getWriter().write(
                "<h1>Resume</h1>\n" +
                        "<table border=\"1\">\n" +
                        "<tr>\n" +
                        "<th>uuid</th>\n" +
                        "<th>Full Name</th>\n" +
                        "</tr>" +
                        "<table>\n");
        SqlStorage storage = (SqlStorage) Config.get().getStorage();
        for (Resume resume : storage.getAllSorted()) {
            response.getWriter().write("<tr>\n" +
                    "<th>" + resume.getUuid() + "</th>\n" +
                    "<th>" + resume.getFullName() + "</th>\n" +
                    "</tr>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
