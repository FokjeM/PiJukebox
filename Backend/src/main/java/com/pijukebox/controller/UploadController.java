package com.pijukebox.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.annotation.MultipartConfig;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/upload")
public class UploadController {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestParam CommonsMultipartFile[] attachFileObj) {

        if ((attachFileObj != null) && (attachFileObj.length > 0) && (!attachFileObj.equals(""))) {
            for (CommonsMultipartFile aFile : attachFileObj) {
                if (aFile.isEmpty()) {
                    continue;
                } else {
                    System.out.println("Attachment Name?= " + aFile.getOriginalFilename() + "\n");
                    if (!aFile.getOriginalFilename().equals("")) {
                        System.out.println(aFile.getOriginalFilename());
//                       fileDescription ;
                        System.out.println(aFile.getBytes());
                        // Calling The Db Method To Save The Uploaded File In The Db
//                        fileUploadObj;
                    }
                }
                System.out.println("File Is Successfully Uploaded & Saved In The Database.... Hurrey!\n");
            }
        }

        return new ResponseEntity<>("something", HttpStatus.OK);
    }

    @GetMapping("/testFile")
    public ResponseEntity<String> playCurrent() {
        try {
            String rootPath = "D:\\Users\\Ruben\\Desktop\\uploads\\";

            File file = new File(rootPath + "test.txt");
            if (file.createNewFile()) {
                System.out.println("tsuuuuu");
            } else {
                return new ResponseEntity<>("File already exists...", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>("test...", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Couldn't play track /play"), ex);
        }
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String myService(@RequestParam("file") MultipartFile file,
                                          @RequestParam("id") String id) throws Exception {

        if (!file.isEmpty()) {
            System.out.println("not empty");
            uploadThisFile(file);
        }
        return "some json";

    }

    @RequestMapping(value = "/upload", consumes = {"multipart/form-data"})
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        uploadThisFile(file);
    }

    private String uploadDir = "D:\\Users\\Ruben\\Desktop\\uploads\\";

    private void uploadThisFile(MultipartFile file) throws IOException{
        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadDir+"Sick_"+file.getOriginalFilename());
        Files.write(path, bytes);
    }

    @RequestMapping(value = "/upload1", method = RequestMethod.POST)
    public String upload1(@RequestBody MultipartFile file) {
        //TODO process the file here
        //file.getOriginalFilename() ...
        //file.getInputStream() ...
        System.out.println("file naam xD");
        System.out.println(file);
//        file.getName();
//        file.getOriginalFilename();

        return "done";
    }

    /*
    @RequestMapping(method = RequestMethod.POST, consumes = {"multipart/form-data"})
        public String importQuestion(@Valid @RequestParam("uploadedFileName")
        MultipartFile multipart,  BindingResult result, ModelMap model) {
           logger.debug("Post method of uploaded Questions ");

            logger.debug("Uploaded file Name : " + multipart.getOriginalFilename());
           return "importQuestion";
        }
    */
    /*
    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadFile(@RequestParam("myFile")
                     MultipartFile multipart, BindingResult result, ModelMap model) {

        System.out.println(multipart);
        System.out.println(multipart.getResource());
        System.out.println(multipart.getSize());
        System.out.println(multipart.getOriginalFilename());

        return new ResponseEntity<>("yes", HttpStatus.OK);
    }
    */


//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(HttpServletRequest request, HttpServletResponse response,
//            @RequestParam("myFile") File file) throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        try {
//
//            String rootPath = "D:\\Users\\Ruben\\Desktop\\uploads\\";
//
//            //file = new File(rootPath + "test.txt");
//
//            //FileOutputStream fileOutputStream = new FileOutputStream(rootPath + file);
//            //DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
//
//            File newF = new File(rootPath+file);
//
//            if (newF.createNewFile()) {
//                System.out.println("tsuuuuu");
//            } else {
//                return new ResponseEntity<>("File already exists...", HttpStatus.NOT_FOUND);
//            }
//
//            System.out.println("file");
//            System.out.println(file);
//            System.out.println(file.getName());
//
//            return new ResponseEntity<>("yes", HttpStatus.OK);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't upload", ex);
//        }
//    }

    /**
     * Upload single file using Spring Controller
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public @ResponseBody
    //String uploadFileHandler(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
    String uploadFileHandler(@RequestParam("myFile") MultipartFile file) {

        System.out.println("file");
        System.out.println(file);

        String name = "test";
        if (!file.isEmpty()) {
            try {
                System.out.println("2");
                byte[] bytes = file.getBytes();
                System.out.println("3");

                // Creating the directory to store file
                //String rootPath = System.getProperty("catalina.home");
                String rootPath = "D:\\Users\\Ruben\\Desktop\\uploads\\";
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                System.out.println(name);

                return "You successfully uploaded file=" + name;
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name
                    + " because the file was empty.";
        }
    }

//    @RequestMapping(value = "/uploadFile1", method= RequestMethod.POST)
//    public ModelAndView getParameters(HttpServletRequest request){
//        System.out.println("1");
//        Enumeration enumeration = request.getParameterNames();
//        Map<String, Object> modelMap = new HashMap<>();
//        while(enumeration.hasMoreElements()){
//            String parameterName = enumeration.nextElement().toString();
//            System.out.println("2" + parameterName);
//            modelMap.put(parameterName, request.getParameter(parameterName));
//        }
//        System.out.println("3");
//        ModelAndView modelAndView = new ModelAndView("sample");
//        modelAndView.addObject("parameters", modelMap);
//        return modelAndView;
//    }

}

