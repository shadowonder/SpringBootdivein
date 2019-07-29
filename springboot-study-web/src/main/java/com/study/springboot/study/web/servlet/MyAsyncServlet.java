package com.study.springboot.study.web.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * My Servlet
 *
 * servlet start
 * servlet end
 * async start
 * async end
 */
@WebServlet(urlPatterns = "/my/async/servlet", asyncSupported = true) //默认是同步配置
public class MyAsyncServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("servlet start");
        AsyncContext asyncContext = req.startAsync();
        asyncContext.start(()->{
            try {
                System.out.println("async start");
                resp.getWriter().write("hello world");
                System.out.println("async end");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        asyncContext.complete(); //触发完成，必须操作

        System.out.println("servlet end");
    }
}
