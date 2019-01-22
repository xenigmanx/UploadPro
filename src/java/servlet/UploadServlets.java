/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import utils.UploadPath;

/**
 *
 * @author pupil
 */
@WebServlet(name = "UploadServlets", urlPatterns = {"/upload"})
@MultipartConfig
public class UploadServlets extends HttpServlet {

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
        //String pathToImageFolder = "C:\\Users\\pupil\\Documents\\UploadFolder";
        String pathToImageFolder = UploadPath.getPath("path");
        Part filePart = request.getPart("file");
        String fileName = getFileName(filePart);
        
        OutputStream out = null;
        InputStream filecontent = null;
     try{   
        out = new FileOutputStream
        (new File(
                pathToImageFolder
                +File.separator
                +fileName
            )
        );
        filecontent = filePart.getInputStream();
        int read = 0;
        byte[] bytes = new byte[1024];
        while((read=filecontent.read(bytes)) != -1){
            out.write(bytes, 0,read);
        
        }
        
    }finally{
         if(out != null){
             out.close();
         }
         if(filecontent != null){
             filecontent.close();
         }
      }
    }   
    private String getFileName(Part part){
        String partHeader = part.getHeader("content-disposition");
        for(String content : part.getHeader("content-disposition").split(";")){
            if(content.trim().startsWith("filename")){
                return content.substring(
                        content.indexOf('=')+1
                ).trim()
                 .replace("\"","");
            }
        }
        return null;
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