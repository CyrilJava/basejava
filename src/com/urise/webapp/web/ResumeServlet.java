package com.urise.webapp.web;

import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.storage.Storage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ResumeServlet extends HttpServlet {
    private static final Storage STORAGE = new SqlStorage(
            "jdbc:postgresql://localhost:5432/resumes", "postgres", "f;c6iw4q1n-,73bz");

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        String name = request.getParameter("name");
        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + "!\n\n\n");
        List<Resume> resumes = STORAGE.getAllSorted();
        String resultString = resumes.stream().map(r -> "  <tr>\n" +
                "<td>" + r.getUuid() + "</td>\n" +
                "<td>" + r.getFullName() + "</td>\n" +
                "<td>" + r.getContacts().get(ContactType.EMAIL) + "</td>\n" +
                "  <tr>\n").collect(Collectors.joining("", "<table>\n" + "  <tr>\n" +
                "    <th>UUID</th>\n" + "    <th>Name</th>\n" + "    <th>E-Mail</th>\n" + "  </tr>\n",
                "</table>"));
        response.getWriter().write(resultString);
    }
}
// http://localhost:8080/resumes/resume?name=%D0%9A%D0%B8%D1%80%D0%B8%D0%BB%D0%BB
// http://localhost:8080/resumes/resume?name=Кирилл