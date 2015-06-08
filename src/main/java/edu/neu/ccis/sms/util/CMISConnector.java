package edu.neu.ccis.sms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisContentAlreadyExistsException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;

public class CMISConnector {
    // Connect to CMS repository and get the session
    private static final Session session = connectToRepository();

    public static Session getCMISSession() {
        return session;
    }

    /**
     * Connects to CMS Repository and creates the session for further work
     * 
     * @return
     */
    private static Session connectToRepository() {
        // TODO Auto-generated method stub
        SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(SessionParameter.USER, "admin");
        parameters.put(SessionParameter.PASSWORD, "admin");
        parameters.put(SessionParameter.BINDING_TYPE,
                BindingType.ATOMPUB.value());
        parameters
                .put(SessionParameter.ATOMPUB_URL,
                // "http://localhost:8080/alfresco/cmisatom");
                        "http://localhost:8080/alfresco/api/-default-/public/cmis/versions/1.1/atom");

        List<Repository> repos = sessionFactory.getRepositories(parameters);
        System.out.println("Total Repositories in CMS = " + repos.size());

        Repository defaultRepo = repos.get(0);
        System.out.println("Default Repo CMIS version supported = "
                + defaultRepo.getCmisVersionSupported());

        Session session = defaultRepo.createSession();
        System.out.println("Connected to the repository!!");
        return session;
    }

    /**
     * Create Folder under given parent folder path
     * 
     * @param parentFolderPath
     * @param folderName
     * @return
     * @throws CmisObjectNotFoundException
     */
    public static Folder createFolder(final String parentFolderPath,
            final String folderName) throws CmisObjectNotFoundException {

        Folder parentFolder = null;
        Folder subFolder = null;
        try {
            parentFolder = (Folder) session.getObjectByPath(parentFolderPath);
            System.out.println("parentFolder found - " + parentFolder.getId());
        } catch (final CmisObjectNotFoundException onfe) {
            System.out.println("No such parentFolder found!!");
            throw onfe;
        }

        // Then check if the new TOBE created folder already exists
        try {
            subFolder = (Folder) session.getObjectByPath(parentFolder.getPath()
                    + "/" + folderName);
            System.out.println("Folder already existed!");
        } catch (final CmisObjectNotFoundException onfe) {
            Map<String, String> props = new HashMap<String, String>();
            props.put("cmis:objectTypeId", "cmis:folder");
            props.put("cmis:name", folderName);

            subFolder = parentFolder.createFolder(props);
            String subFolderId = subFolder.getId();

            System.out.println("Created new folder: " + subFolderId);
        }
        return subFolder;
    }

    /**
     * Create Folder under root folder directory
     * 
     * @param folderName
     * @return
     */
    public static Folder createFolderUnderRoot(final String folderName) {

        Folder root = session.getRootFolder();
        Folder subFolder;
        // Then check if the new TOBE created folder already exists
        try {
            subFolder = (Folder) session.getObjectByPath(root.getPath() + "/"
                    + folderName);
            System.out.println("Folder already existed!");
        } catch (final CmisObjectNotFoundException onfe) {
            Map<String, String> props = new HashMap<String, String>();
            props.put("cmis:objectTypeId", "cmis:folder");
            props.put("cmis:name", folderName);

            subFolder = root.createFolder(props);
            String subFolderId = subFolder.getId();

            System.out.println("Created new folder: " + subFolderId);
        }
        return subFolder;
    }

    /**
     * Overloaded method - uploadToCMSUsingFileToFolderPath
     * 
     * @param parentFolderPath
     * @param file
     * @return
     * @throws IOException
     */
    public static Document uploadToCMSUsingFileToFolderPath(
            final String parentFolderPath, final File file) throws Exception {
        return uploadToCMSUsingFileToFolderPath(parentFolderPath,
                file.getName(), Files.probeContentType(file.toPath()), file);
    }

    /**
     * Overloaded method - uploadToCMSUsingFileToFolderPath
     * 
     * @param parentFolderPath
     * @param fileName
     * @param file
     * @return
     * @throws Exception
     */
    public static Document uploadToCMSUsingFileToFolderPath(
            final String parentFolderPath, final String fileName,
            final File file) throws Exception {
        return uploadToCMSUsingFileToFolderPath(parentFolderPath, fileName,
                Files.probeContentType(file.toPath()), file);
    }

    /**
     * Uploads the local document to CMS repo
     * 
     * @return - Document - newly uploaded document
     * @throws Exception
     */
    public static Document uploadToCMSUsingFileToFolderPath(
            final String parentFolderPath, final String fileName,
            final String fileType, final File file) throws Exception {

        Map<String, Object> props = null;
        Folder parentFolder = null;
        Document document = null;
        ContentStream contentStream = null;

        try {
            parentFolder = (Folder) session.getObjectByPath(parentFolderPath);
            System.out.println("parentFolder found - " + parentFolder.getId());

            props = new HashMap<String, Object>();
            props.put("cmis:objectTypeId", "cmis:document");
            props.put("cmis:name", fileName);

            contentStream = session.getObjectFactory().createContentStream(
                    fileName, file.length(), fileType,
                    new FileInputStream(file));

            document = parentFolder.createDocument(props, contentStream,
                    VersioningState.MAJOR);

            System.out.println("Created new document: " + document.getId()
                    + " - Path - " + document.getPaths());
        } catch (final CmisContentAlreadyExistsException ccaee) {
            document = (Document) session.getObjectByPath(parentFolder
                    .getPath() + "/" + fileName);
            System.out.println("Document already exists: " + fileName);
            throw ccaee;
        } catch (final FileNotFoundException e) {
            System.out.println("No such file found!");
            throw e;
        } catch (final CmisObjectNotFoundException onfe) {
            System.out.println("No such parentFolder found!!");
            throw onfe;
        }
        return document;
    }

    /**
     * Check if given Object is directory or not
     * 
     * @param obj
     * @return boolean result if its a folder or document
     */
    public static boolean isFolder(CmisObject obj) {
        return ((String) obj.getPropertyValue("cmis:baseTypeId"))
                .equalsIgnoreCase(BaseTypeId.CMIS_FOLDER.value());
    }
}
