package my.comunity.common.provider;

import lombok.extern.slf4j.Slf4j;
import my.comunity.common.exception.CustomizeErrorCode;
import my.comunity.common.exception.CustomizeException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.UUID;

/**
 * Created by codedrinker on 2019/6/28.
 */
@Service
@Slf4j
public class UCloudProvider {
    public static final String FILE_PATH = "D:/file1234/";//文件指定存放的路径

    public static boolean creatFile(String fileName) {
        File folder = new File(FILE_PATH);
        //文件夹路径不存在
        if (!folder.exists() && !folder.isDirectory()) {
            System.out.println("文件夹路径不存在，创建路径:" + FILE_PATH);
            folder.mkdirs();
        } else {
            System.out.println("文件夹路径存在:" + FILE_PATH);
        }

        // 如果文件不存在就创建
        File file = new File(FILE_PATH + fileName);
        if (!file.exists()) {
            System.out.println("文件不存在，创建文件:" + FILE_PATH + fileName);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("文件已存在，文件为:" + FILE_PATH + fileName);
        }
        return true;
    }

    public String upload(InputStream fileStream, String mimeType, String fileName) throws Exception {
        String generatedFileName;
        String[] filePaths = fileName.split("\\.");
        if (filePaths.length > 1) {
            generatedFileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
            creatFile(generatedFileName);
            OutputStream outFile = new FileOutputStream(FILE_PATH + generatedFileName);
            ConvertUtil.parse(fileStream,outFile);
            outFile.close();
            return  generatedFileName;
        } else {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
        }

    }
}
