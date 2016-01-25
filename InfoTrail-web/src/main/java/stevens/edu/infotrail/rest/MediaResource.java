package stevens.edu.infotrail.rest;

import com.amazonaws.services.s3.model.S3Object;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import stevens.edu.infotrail.amazonS3.AmazonS3Helper;
import stevens.edu.infotrail.dao.DAO;
import stevens.edu.infotrail.dao.daoFactory.DAOFactory;
import stevens.edu.infotrail.domain.entity.Media;
import stevens.edu.infotrail.domain.entityFactory.MediaFactory;

import javax.ejb.TransactionAttribute;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.logging.Logger;

/**
 * @author xshi90
 * @version %I%,%G%
 */
@Path("/media")
@RequestScoped
@Transactional
public class MediaResource {
    private static final Logger logger = Logger.getLogger(MapResource.class.getCanonicalName());
    @Inject
    private DAOFactory daoFactory;
    @Inject
    private AmazonS3Helper amazonS3Helper;

    public MediaResource() {
    }

    @GET
    @Path("/{object}")
    @Produces("*/*")
    public Response getObject(@PathParam("object") String keyName) {
        S3Object object = amazonS3Helper.getObject(keyName);
        String oName = object.getKey();
        return Response.ok(oName).build();
    }



    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional()
    public Response updateMedia(@FormDataParam("image_file") InputStream uploadedInputStream,
                                @FormDataParam("image_file") FormDataContentDisposition fileDetail,
                                @FormDataParam("parentId") int parentId,
                                @FormDataParam("type") String type,
                                @FormDataParam("link") String link,
                                @FormDataParam("name") String name,
                                @FormDataParam("description") String description) {
        if (uploadedInputStream != null && fileDetail != null) {
            String uploadDirPath = System.getenv("HOME") + "/tmpData/";
            logger.info(uploadDirPath);
            File tmpFile = new File(uploadDirPath);
            boolean flag = true;
            if (!tmpFile.exists() && !tmpFile.isDirectory()) {
                flag = tmpFile.mkdir();
            }

            String uploadedFileLocation = uploadDirPath + fileDetail.getFileName();

            // save it
            File file = writeToFile(uploadedInputStream, uploadedFileLocation);
            if (flag && file != null) {
                logger.info("=======================why=================");
                amazonS3Helper.uploadObject(file, fileDetail.getFileName());
                //map.uploadImage(file, fileDetail.getFileName());
            }

            boolean delete = file != null && file.delete();
            if (!delete) {
                logger.warning("Delete local file failed. File name; " + fileDetail.getFileName());
            }
            try {
                updateMediaInfo(fileDetail.getFileName(), parentId, type, name, description);
            } catch (DAO.DaoExn daoExn) {
                return Response.accepted("Update Media failed. name" + name).build();
            }
            return Response.status(200).entity(fileDetail.getFileName()).build();
        } else {
            Media media = new MediaFactory().createMedia(link, type, parentId, name, description);
            try {
                daoFactory.getMediaDAO().update(media);
                return Response.ok().build();
            } catch (DAO.DaoExn daoExn) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
        }

    }

    @POST
    @Path("/data")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    public Response insertMedia(@FormDataParam("parentId") int parentId,
                                @FormDataParam("type") String type,
                                @FormDataParam("link") String link,
                                @FormDataParam("name") String name,
                                @FormDataParam("description") String description){
        Media media = new MediaFactory().createMedia(link, type, parentId, name, description);
        try {
            daoFactory.getMediaDAO().create(media);
            return Response.ok().build();
        } catch (DAO.DaoExn daoExn) {
            return Response.accepted("Create Media failed. name" + name).build();
        }
    }

    /**
     * @param uploadedInputStream input stream
     * @param fileDetail          file detailed data
     * @param parentId            media parent ID
     * @param type                media type
     * @param name                media name
     * @param description         media description
     * @return Response
     */
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    public Response createMedia(
            @FormDataParam("image_file") InputStream uploadedInputStream,
            @FormDataParam("image_file") FormDataContentDisposition fileDetail,
            @FormDataParam("parentId") int parentId,
            @FormDataParam("type") String type,
            @FormDataParam("link") String link,
            @FormDataParam("name") String name,
            @FormDataParam("description") String description) {

        logger.info("====+++======"+name+"=====+++======");

        if (uploadedInputStream != null && fileDetail != null) {
            logger.info("====+++======"+fileDetail.getFileName()+"=====+++======");
            String uploadDirPath = System.getenv("HOME") + "/tmpData/";
            logger.info(uploadDirPath);
            File tmpFile = new File(uploadDirPath);
            boolean flag = true;
            if (!tmpFile.exists() && !tmpFile.isDirectory()) {
                flag = tmpFile.mkdir();
            }

            String uploadedFileLocation = uploadDirPath + fileDetail.getFileName();

            // save it
            File file = writeToFile(uploadedInputStream, uploadedFileLocation);
            if (flag && file != null) {
                logger.info("=======================why=================");
                amazonS3Helper.uploadObject(file, name);
                //map.uploadImage(file, fileDetail.getFileName());
            }

            boolean delete = file != null && file.delete();
            if (!delete) {
                logger.warning("Delete local file failed. File name; " + fileDetail.getFileName());
            }

            try {
                saveMediaInfo(fileDetail.getFileName(), parentId, type, name, description);
            } catch (DAO.DaoExn daoExn) {
                daoExn.printStackTrace();
                return Response.accepted("Create Media failed. name" + name).build();
            }
            return Response.status(200).entity(fileDetail.getFileName()).build();
        } else {

            try {
                Media media = new MediaFactory().createMedia(link, type, parentId, name, description);
                daoFactory.getMediaDAO().create(media);
                return Response.ok().build();
            } catch (Exception e) {
                e.printStackTrace();
                return Response.accepted("Create Media failed. name" + name).build();
            }
        }
    }

    /**
     * save uploaded file to new location
     *
     * @param uploadedInputStream  InputStream
     * @param uploadedFileLocation String
     * @return File
     */
    private File writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
        try {
            int read;
            byte[] bytes = new byte[1024];

            OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();

        } catch (IOException e) {
            logger.severe("Write File failed.");
            return null;
        }
        return new File(uploadedFileLocation);
    }

    /**
     * @param filename File name
     * @param parentId media parent ID
     * @param type     media Type
     * @param name     media name
     * @param des      media description
     */
    @Transactional
    private void saveMediaInfo(String filename, int parentId, String type, String name, String des) throws DAO.DaoExn {
        String keyName = "http://infotrail-resources.s3.amazonaws.com/resource/" + filename;
        Media media = new MediaFactory().createMedia(keyName, type, parentId, name, des);
        try {
            daoFactory.getMediaDAO().create(media);
        } catch (DAO.DaoExn daoExn) {
            throw new DAO.DaoExn("Create Media failed. media name: " + name);
        }
    }

    @Transactional
    private void updateMediaInfo(String filename, int parentId, String type, String name, String des) throws DAO.DaoExn {
        String keyName = "http://infotrail-resources.s3.amazonaws.com/resource/" + filename;
        Media media = new MediaFactory().createMedia(keyName, type, parentId, name, des);
        try {
            daoFactory.getMediaDAO().update(media);
        } catch (DAO.DaoExn daoExn) {
            throw new DAO.DaoExn("Update Media failed. media name: " + name);
        }
    }


}
