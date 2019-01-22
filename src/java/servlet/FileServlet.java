/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.UploadPath;

/**
 *
 * @author pupil
 */
@WebServlet(name = "FileServlet", urlPatterns = {"/fileServlet/*"})
public class FileServlet extends HttpServlet {
    
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    
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
        request.setCharacterEncoding("UTF-8");
        //String filePath = "C:\\Users\\pupil\\Documents\\UploadFolder";
        String pathToImageFolder = UploadPath.getPath("path");
        // Путь к запрашиваемому файлу.
        String requestedFile = request.getPathInfo();
        // Проверим, действительно ли передан путь в запросе.
        if (requestedFile == null) {
            // Здесь можно послать сообщение о случившемся.
            // Бросить исключение, или послать 404, или показать страницу ошибки, 
            // или просто игнорировать ошибку.
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
        //String filePath = null;
        // Создадим объект типа File, при этом декодируем имя файла полученного из request
        // т.к оно может содержать пробелы и другие символы
        File file = new File(pathToImageFolder, URLDecoder.decode(requestedFile, "UTF-8"));
        // Проверим, существует ли файл в файловой системе
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
        // Получим тип контента по имени файла
        String contentType = getServletContext().getMimeType(file.getName());
        //Если тип контента неизвестен, установите значение по умолчанию.
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        // Init servlet response.
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        // Prepare streams.
        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        try {
            // Open streams.
            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            // Gently close streams.
            close(output);
            close(input);
        }
    }
    
    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it.
                e.getMessage();
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