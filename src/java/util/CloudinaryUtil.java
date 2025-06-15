/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 *
 * @author LEGION
 */
public class CloudinaryUtil {

    private Cloudinary cloudinary = new Cloudinary();

    public CloudinaryUtil() {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dszkninft",
                "api_key", "825471649162573",
                "api_secret", "9bu_Mv1MTSl1ZLaXNu1b8i8dEnY"
        ));
    }

    public String upload(Part filePart) throws IOException {
        File tempFile = File.createTempFile("upload-", filePart.getSubmittedFileName());
        filePart.write(tempFile.getAbsolutePath());

        // Upload file
        Map uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.emptyMap());

        // Xóa file tạm sau khi upload
        tempFile.delete();
        return (String) uploadResult.get("secure_url");
    }

    public String uploadVideo(Part filePart) throws IOException {
        File tempFile = File.createTempFile("upload-", filePart.getSubmittedFileName());
        filePart.write(tempFile.getAbsolutePath());

        // Gán resource_type là video
        Map uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.asMap(
                "resource_type", "video"
        ));

        tempFile.delete();
        return (String) uploadResult.get("secure_url");
    }

}
