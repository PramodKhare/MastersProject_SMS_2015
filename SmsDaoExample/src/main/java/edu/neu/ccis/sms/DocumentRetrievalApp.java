package edu.neu.ccis.sms;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import edu.neu.ccis.sms.dao.users.UserDao;
import edu.neu.ccis.sms.dao.users.UserDaoImpl;
import edu.neu.ccis.sms.entity.submissions.Document;
import edu.neu.ccis.sms.entity.users.User;
import edu.neu.ccis.sms.util.CMISConnector;

public class DocumentRetrievalApp {
    public static void main(String[] args) throws IOException {
        // FileUtils.deleteDirectory(new File("F:\\uploads"));
        try {
            Long userId = new Long(7);
            // Get parameter "memberId" for which submissions to be downloaded
            Long memberId = new Long(5);

            UserDao userDao = new UserDaoImpl();
            User reviewer = userDao.getUserByIdWithSubmittersToEvaluateMappings(userId);
            Set<User> evaluateSubmitters = reviewer.getSubmittersToEvaluateForMemberId(memberId);

            File tempDir = new File(System.getProperty("java.io.tmpdir"));
            File solutionsDir = new File(tempDir.getAbsolutePath() + File.separator + "solutions");
            solutionsDir.mkdirs();

            System.out.println("Folder path - " + solutionsDir.getAbsolutePath());

            for (User user : evaluateSubmitters) {
                Document doc = null;
                try {
                    doc = userDao.getSubmissionDocumentForMemberIdByUserId(user.getId(), memberId);

                    // Create a necessary folder structure and then download the
                    // submission document there
                    File dir = new File(solutionsDir.getAbsolutePath() + File.separator + user.getEmail());
                    dir.mkdirs();

                    // Create file inside this directory with submission file
                    // name
                    File file = new File(dir.getAbsolutePath() + File.separator + doc.getFilename());
                    file.createNewFile();
                    CMISConnector.downloadDocument(doc.getCmsDocId(), file.getAbsolutePath());
                } catch (final Exception e) {
                    System.out.println("Unable to download document - " + doc.getFilename());
                    e.printStackTrace();
                }
            }
            // TODO Cleanup the files which were downloaded
            // FileUtils.deleteDirectory(solutionsDir);

            System.out.println("Submissions downloaded successfully for evaluation!!");
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
