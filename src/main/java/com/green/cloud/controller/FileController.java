package com.green.cloud.controller;

import com.green.cloud.entity.File;
import com.green.cloud.service.IDirectoryIndexService;
import com.green.cloud.service.IFileIndexService;
import com.green.cloud.service.IProcedureIndexService;
import com.green.cloud.service.impl.FileIndexServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.green.cloud.entity.DirectoryIndex;
import com.green.cloud.entity.ProcedureIndex;
import com.green.cloud.service.impl.DirectoryIndexServiceImpl;
import com.green.cloud.service.impl.ProcedureIndexServiceImpl;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;


@RequestMapping("/file")
@RestController
public class FileController {


    @Autowired
    private IProcedureIndexService procedureIndexService;

    @Autowired
    private IDirectoryIndexService directoryIndexService;

    @Autowired
    private IFileIndexService fileIndexService;


    private final String uploadPath = "/cloud_data/upload";

    private final String jsonFilePath = "/cloud_data/newOperatorLib.json";


    ProcedureIndex record = new ProcedureIndex();

    DirectoryIndex Record = new DirectoryIndex();


    @GetMapping("/getJson")
    public ResponseEntity<String> getjson() throws IOException {
        java.io.File jsonFile = new java.io.File(jsonFilePath);
        String fileAttributesJson = FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8);
        return ResponseEntity.ok(fileAttributesJson);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleUploadRequest(
            @RequestParam("model") String modelJson,
            @RequestParam("selectedCategory") String selectedCategory,
            @RequestParam("selectedSubcategory") String selectedSubcategory,
            @RequestParam("file") MultipartFile dllfile) throws IOException {
        Model model = new Gson().fromJson(modelJson, Model.class);
        String filePath = jsonFilePath;
        String baseFolderPath = uploadPath;


//        // mkdir if not exists
//        java.io.File uploadDir = new java.io.File(uploadPath);
//        if (!uploadDir.exists()) {
//            uploadDir.mkdirs();
//        }

        // Check if the selectedCategory folder exists, create if it doesn't
        java.io.File categoryFolder = new java.io.File(baseFolderPath, selectedCategory);
        if (!categoryFolder.exists()) {
            if (!categoryFolder.mkdirs()) {
                return new ResponseEntity<>("无法创建所选类别文件夹", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // Check if the selectedSubcategory folder exists, create if it doesn't
        java.io.File subcategoryFolder = new java.io.File(categoryFolder, selectedSubcategory);
        if (!subcategoryFolder.exists()) {
            if (!subcategoryFolder.mkdirs()) {
                return new ResponseEntity<>("无法创建所选子类别文件夹", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        System.out.println(dllfile.getInputStream());

        String modelName = model.getModelName();
        String modelFilePath = modelName + ".dll";
        java.io.File modelFile = new java.io.File(subcategoryFolder, modelFilePath);
        if (modelFile.exists()) {
            return new ResponseEntity<>("文件已经存在", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            if (modelFile.createNewFile()) {
                // File created successfully
                // You can perform any additional operations on the modelFile if needed

                // Write the content of dllfile to the modelFile
                try (InputStream inputStream = dllfile.getInputStream();
                        OutputStream outputStream = new FileOutputStream(modelFile)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return new ResponseEntity<>("无法写入文件", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>("无法创建文件", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("处理请求时出现错误！", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        File Category = fileIndexService.queryByFileName(selectedCategory);
        File Subcategory = fileIndexService.queryByFileName(selectedSubcategory);

        if (Category == null) {
            File record = new File();
            record.setFileName(selectedCategory);
            record.setFatherId(0);
            record.setIsDir(1);
            record.setLocalRemote(0);
            fileIndexService.save(record);
        }
        if (Subcategory == null) {
            File record = new File();
            record.setFileName(selectedSubcategory);
            record.setFatherId(fileIndexService.queryByFileName(selectedCategory).getId());
            record.setIsDir(1);
            record.setLocalRemote(0);
            fileIndexService.save(record);
        }
        File file = fileIndexService.queryByFileName(model.getModelName());
        if (file != null) {
            return new ResponseEntity<>("文件已经存在", HttpStatus.INTERNAL_SERVER_ERROR);

        }
        File record = new File();
        record.setFileName(model.getModelName());
        record.setFatherId(fileIndexService.queryByFileName(selectedSubcategory).getId());
        record.setIsDir(0);
        record.setLocalRemote(0);
        fileIndexService.save(record);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // 启用缩进输出

        try {
            // 读取 JSON 文件
            JsonNode jsonNode = mapper.readTree(new java.io.File(filePath));

            // 定位需要插入记录的位置
            JsonNode selectedCategoryNode = jsonNode.get(selectedCategory);
            if (selectedCategoryNode.isArray()) {
                for (JsonNode subcategoryObj : selectedCategoryNode) {
                    JsonNode lfPropertiesNode = subcategoryObj.get("lfProperties");
                    if (lfPropertiesNode != null && lfPropertiesNode.get("name").asText().equals(selectedSubcategory)) {
                        JsonNode propertiesNode = subcategoryObj.get("properties");
                        if (propertiesNode != null) {
                            JsonNode modelsNode = propertiesNode.get("models");
                            if (modelsNode != null && modelsNode.isArray()) {
                                ObjectNode newModelNode = mapper.createObjectNode();
                                // 设置模型的属性
                                newModelNode.put("modelName", model.getModelName());
                                newModelNode.put("helpMsg", model.getHelpMsg());
                                newModelNode.put("dllPath", model.getDllPath());
                                newModelNode.put("modelID", model.getModelID());

                                // 设置 inPara
                                ArrayNode inParaNode = newModelNode.putArray("inPara");
                                for (Parameter inPara : model.getInPara()) {
                                    ObjectNode inParaObjNode = inParaNode.addObject();
                                    inParaObjNode.put("varName", inPara.getVarName());
                                    inParaObjNode.put("varType", inPara.getVarType());
                                    // 设置 inPara 中其他属性
                                }

                                // 设置 outPara
                                ArrayNode outParaNode = newModelNode.putArray("outPara");
                                for (Parameter outPara : model.getOutPara()) {
                                    ObjectNode outParaObjNode = outParaNode.addObject();
                                    outParaObjNode.put("varName", outPara.getVarName());
                                    outParaObjNode.put("varType", outPara.getVarType());
                                    // 设置 outPara 中其他属性
                                }

                                ((ArrayNode) modelsNode).add(newModelNode);
                            }
                        }
                        break;
                    }
                }
            }

            // 将更新后的 JSON 写回文件（保持格式）
            mapper.writeValue(new java.io.File(filePath), jsonNode);

            return new ResponseEntity<>("已成功插入记录到 JSON 文件中！", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("处理请求时出现错误！", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/changeJson")
    public ResponseEntity<String> changejson(
            @RequestParam("file") MultipartFile jsonfile) throws IOException {
        // 将上传的文件内容写入目标文件
        String fileContent = new String(jsonfile.getBytes());
        java.nio.file.Files.writeString(Path.of(jsonFilePath), fileContent);

        return ResponseEntity.ok("文件内容已成功替换。");
    }

    // 定义请求数据类

    // @PostMapping("/upload")
    // public String uploadFile(@RequestParam("file") MultipartFile
    // file,@RequestParam("algorithmType") String
    // algorithmType,@RequestParam("fileAttributes") String fileAttributes) throws
    // IOException {
    // if (file.isEmpty()) {
    // return "文件为空";
    // }
    // JSONObject existingFileAttributes;
    // if (new File(jsonFilePath).exists()) {
    // String existingFileAttributesJson = FileUtils.readFileToString(new
    // File(jsonFilePath), StandardCharsets.UTF_8);
    // existingFileAttributes = new JSONObject(existingFileAttributesJson);
    // } else {
    // existingFileAttributes = new JSONObject();
    // existingFileAttributes.put("records", new JSONArray());
    // }
    //
    // // 将新的文件属性添加到记录中
    // JSONArray records = existingFileAttributes.getJSONArray("records");
    // JSONObject newRecord = new JSONObject(fileAttributes);
    // records.put(newRecord);
    //
    // // 将更新后的文件属性保存到文件中
    // FileUtils.writeStringToFile(new File(jsonFilePath),
    // existingFileAttributes.toString(), StandardCharsets.UTF_8);
    //
    // if(directory_indexService.selectByPrimaryKey(algorithmType)==null){
    // Record.setDirectoryname(algorithmType);
    // Record.setLocalremote(0);
    // Record.setLocation(uploadPath);
    // directory_indexService.insert(Record);
    // }
    // System.out.println(fileAttributes);
    //
    // String fileName = file.getOriginalFilename();
    // String destPath =
    // directory_indexService.selectByPrimaryKey(algorithmType).getLocation() +
    // File.separator + algorithmType + File.separator + fileName; // 拼接保存文件的完整路径
    // File destFile = new File(destPath);
    //
    // if (!destFile.getParentFile().exists()) { // 如果目录不存在，创建目录
    // destFile.getParentFile().mkdirs();
    // }
    //
    // file.transferTo(destFile); // 将文件保存到本地
    // record.setProcedurename(fileName);
    // record.setDirectoryname(algorithmType);
    // procedure_indexService.insert(record);
    //
    // return "文件上传成功";
    // }

    @GetMapping("/download/{fileName:.+}")
    public Resource downloadFile(@PathVariable String fileName) throws IOException {

        List<String> fileNames = new ArrayList<>();
        fileNames.add(fileName);
        String currentFileName = fileName;
        while (true) {
            // 根据当前文件名查询 fatherId
            int fatherId = fileIndexService.queryFatherIdByFileName(currentFileName);
            if (fatherId == 0) {
                break;
            }
            // 根据 fatherId 查询对应的文件名

            String fatherFileName = fileIndexService.getById(fatherId).getFileName();

            fileNames.add(fatherFileName);
            currentFileName = fatherFileName;
        }

        // 反转文件名列表，并拼接成完整的文件路径
        Collections.reverse(fileNames);

        Path filePath = Paths.get(uploadPath, fileNames.toArray(new String[0]));
        Path filePathWithExtension = filePath.resolveSibling(filePath.getFileName() + ".dll");

        System.out.println(filePathWithExtension);
        Resource resource = new UrlResource(filePathWithExtension.toUri());

        if (resource.exists()) {
            return resource;
        } else {
            throw new IOException("文件不存在: " + fileName);
        }
    }

    @GetMapping("/downloadjson/{fileName:.+}")
    public ResponseEntity<?> downloadjson(@PathVariable String fileName) throws IOException {
        java.io.File jsonFile = new java.io.File(jsonFilePath);
        String fileAttributesJson = FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8);

        // 解析JSON内容为JSON对象
        JSONObject fileAttributes = new JSONObject(fileAttributesJson);

        // 查找与文件名相匹配的记录并输出
        JSONArray records = fileAttributes.getJSONArray("records");
        for (int i = 0; i < records.length(); i++) {
            JSONObject record = records.getJSONObject(i);
            JSONObject lfProperties = record.getJSONObject("lfProperties");
            String lfPropertiesName = lfProperties.getString("name");

            if (lfPropertiesName.equals(fileName)) {
                System.out.println(record);
                return ResponseEntity.ok(record.toString());
            }
        }
        JSONObject errorResponse = new JSONObject();
        errorResponse.put("error", "No record found for the given file name: " + fileName);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse.toString());

    }

    @GetMapping("/algorithms")
    public List<ProcedureIndex> getAllAlgorithms() {
        return procedureIndexService.list();
    }

    @PostMapping("/deleteFile/{fileName}")
    public int deleteFile(@PathVariable("fileName") String fileName) {

        fileIndexService.deleteByFileName(fileName);
        return 0;
        //return procedureIndexService.deleteFile(fileName);
    }


    @Data
    public static class RequestData {
        private MultipartFile file;
        private String selectedCategory;
        private String selectedSubcategory;
        private Model model;
    }

    // 定义参数类
    @Data
    public static class Parameter {
        private String varName;
        private String varType;
        // 定义参数的其他属性

        // 参数的其他属性的getter和setter方法
    }

    // 定义模型类
    @Data
    public static class Model {
        private String modelName;
        private String helpMsg;
        private String dllPath;
        private String modelID;
        private List<Parameter> inPara;
        private List<Parameter> outPara;
        // 定义模型的其他属性

        // 模型的其他属性的getter和setter方法
    }


}
