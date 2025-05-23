package org.qubership.profiler.servlet;

import org.qubership.profiler.agent.Bootstrap;
import org.qubership.profiler.agent.DumperPlugin;
import org.qubership.profiler.agent.DumperPlugin_09;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Metrics extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        final PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "utf-8"), 65536), false);

        DumperPlugin_09 dumper = (DumperPlugin_09) Bootstrap.getPlugin(DumperPlugin.class);
        out.print(dumper.getMetrics());
        out.flush();

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
