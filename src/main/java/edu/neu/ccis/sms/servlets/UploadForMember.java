package edu.neu.ccis.sms.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

/**
 * Servlet implementation class UploadForMember
 */
@WebServlet(name = "UploadForMember", urlPatterns = { "/UploadForMember" })
@MultipartConfig
public class UploadForMember extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadForMember() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    // location to store file uploaded
    private final String UPLOAD_DIRECTORY = "F:/uploads";

    // upload settings
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 5; // 5MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 50; // 50MB
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 60; // 60MB

    /**
     * Upon receiving file upload submission, parses the request to read upload
     * data and saves the file on disk.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        // checks if the request actually contains upload file
        if (!ServletFileUpload.isMultipartContent(request)) {
            // if not, we stop here
            PrintWriter writer = response.getWriter();
            writer.println("Error: Form must has enctype=multipart/form-data.");
            writer.flush();
            return;
        }

        // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // sets memory threshold - beyond which files are stored in disk
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // sets temporary location to store files
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // sets maximum size of upload file
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // sets maximum size of request (include file + form data)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // constructs the directory path to store upload file
        // this path is relative to application's directory
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;

        // String uploadPath = UPLOAD_DIRECTORY;

        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            // parses the request's content to extract file data
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);

            if (formItems != null && formItems.size() > 0) {
                // iterates over form's fields
                for (FileItem item : formItems) {
                    // processes only fields that are not form fields
                    if (!item.isFormField()) {
                        System.out.println("Non-Form-Field - item.getName() - " + item.getName());
                        // String fileName = new File(item.getName()).getName();
                        String fileName = FilenameUtils.getName(item.getName());
                        String fileNameSuffix = FilenameUtils.getExtension(fileName);
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = File.createTempFile(fileName, "." + fileNameSuffix);
                        // File storeFile = new File(filePath);

                        String fieldname = item.getFieldName();

                        InputStream filecontent = item.getInputStream();
                        System.out.println("fieldname - " + fieldname + " - filename - " + fileName
                                + " - storeFile Full Path - " + storeFile.getAbsolutePath());
                        // saves the file on disk
                        item.write(storeFile);
                        request.setAttribute("message", "Upload has been done successfully!");
                    } else {
                        System.out.println("item.getName() - " + item.getString() + " - item.getFieldName() - "
                                + item.getFieldName());
                    }
                }
                // redirects client to message page
                getServletContext().getRequestDispatcher("/pages/success.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            request.setAttribute("message", "There was an error: " + ex.getMessage());
            // redirects client to message page
            System.out.println(ex.getMessage());
            getServletContext().getRequestDispatcher("/pages/error.jsp").forward(request, response);
        }
    }
}
