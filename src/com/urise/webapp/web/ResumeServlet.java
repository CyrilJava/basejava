package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.storage.Storage;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("resumes", storage.getAllSorted());
        request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
    }
}
