package edu.neu.ccis.sms.servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.neu.ccis.sms.constants.SessionKeys;
import edu.neu.ccis.sms.dao.categories.MemberDao;
import edu.neu.ccis.sms.dao.categories.MemberDaoImpl;
import edu.neu.ccis.sms.dao.users.UserDao;
import edu.neu.ccis.sms.dao.users.UserDaoImpl;
import edu.neu.ccis.sms.entity.categories.Member;
import edu.neu.ccis.sms.entity.submissions.Document;
import edu.neu.ccis.sms.entity.users.User;

import java.io.*;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Servlet implementation class DocumentRetrievalServlet
 * 
 * @author Pramod R. Khare
 * @date 12-June-2015
 * @lastUpdate 13-June-2015
 */
@WebServlet(name = "DocumentRetrievalServlet", urlPatterns = { "/DocumentRetrievalForEvaluation" })
@MultipartConfig
public class DocumentRetrievalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DocumentRetrievalServlet() {
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

    /**
     * Upon receiving file upload submission, parses the request to read upload
     * data and saves the file on disk.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        try {
            HttpSession session = request.getSession(false);
            Long userId = (Long) session.getAttribute(SessionKeys.keyUserId);

            Long activeMemberId = (Long) session.getAttribute(SessionKeys.activeMemberId);
            activeMemberId = new Long(2);

            // Get request parameter values - memberId for which submissions to
            // be downloaded
            MemberDao memberDao = new MemberDaoImpl();
            Long memberId = Long.parseLong(request.getParameter("memberId"));
            Member memberToDownloadFor = memberDao.getMemberByIdWithSubmissions(memberId);
            Set<Document> submissions = memberToDownloadFor.getSubmissions();

            UserDao userDao = new UserDaoImpl();
            User reviewer = userDao.getUserByIdWithSubmittersToEvaluateMappings(userId);
            Set<User> evaluateSubmitters = reviewer.getSubmittersToEvaluateForMemberId(memberId);

            File tempDir = new File(System.getProperty("java.io.tmpdir"));

            // Get the documents downloaded in temporary directory
            for (Document doc : submissions) {
                // Check if this document is submitted by user for whom this
                // evaluator is assigned
                doc.getSubmittedBy()
            }
            // The path below is the root directory of data to be compressed.
            String path = getServletContext().getRealPath("data");

            File directory = new File(path);
            String[] files = directory.list();

            // Checks to see if the directory contains some files.
            if (files != null && files.length > 0) {
                // Call the zipFiles method for creating a zip stream.
                byte[] zip = zipFiles(directory, files);

                // Sends the response back to the user / browser. The content
                // for zip file type is "application/zip". We also set the
                // content disposition as attachment for the browser to show a
                // dialog that will let user choose what action will he do to
                // the sent content.
                ServletOutputStream sos = response.getOutputStream();
                response.setContentType("application/zip");
                response.setHeader("Content-Disposition", "attachment; filename=\"solutions.zip\"");

                sos.write(zip);
                sos.flush();
            }
        } catch (Exception ex) {
            request.setAttribute("message", "There was an error: " + ex.getMessage());
            // redirects client to message page
            System.out.println(ex.getMessage());
            response.sendRedirect("pages/error.jsp");
        }
    }

    /**
     * Compress the given directory with all its files.
     */
    private byte[] zipFiles(File directory, String[] files) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        byte bytes[] = new byte[2048];

        for (String fileName : files) {
            FileInputStream fis = new FileInputStream(directory.getPath() + ZipDownloadServlet.FILE_SEPARATOR
                    + fileName);
            BufferedInputStream bis = new BufferedInputStream(fis);

            zos.putNextEntry(new ZipEntry(fileName));

            int bytesRead;
            while ((bytesRead = bis.read(bytes)) != -1) {
                zos.write(bytes, 0, bytesRead);
            }
            zos.closeEntry();
            bis.close();
            fis.close();
        }
        zos.flush();
        baos.flush();
        zos.close();
        baos.close();

        return baos.toByteArray();
    }
}
