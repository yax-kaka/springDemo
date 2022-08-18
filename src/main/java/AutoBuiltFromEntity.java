import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class AutoBuiltFromEntity {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String projectPath = System.getProperty("user.dir")+ "/src";//项目路径
        System.out.println("working in path: " + projectPath);
        File projectDir = new File(projectPath);
        System.out.println("input entity name:");
        String entityName = in.nextLine();

        AutoBuiltFromEntity autoBuiltFromEntity = new AutoBuiltFromEntity();
        File entity = autoBuiltFromEntity.findEntity(entityName + ".java", projectPath);
        if (entity == null) {
            System.out.println("cannot find entity!");
            return;
        }

        Map<String, String> attributes = autoBuiltFromEntity.getAttributes(entity);
        System.out.println(entityName + "'s attributes:" );
        System.out.println(attributes);

        autoBuiltFromEntity.createController(entityName, attributes, "controller", projectDir);

        autoBuiltFromEntity.createVO(entityName, attributes, "vo", projectDir);

        autoBuiltFromEntity.createDTO(entityName, attributes, "vo", projectDir);

        autoBuiltFromEntity.createMapper(entityName, attributes, "mapper", projectDir);

        autoBuiltFromEntity.createService(entityName, attributes, "service", projectDir);
        autoBuiltFromEntity.createServiceImpl(entityName, attributes, "impl", projectDir);
    }

    /**
     * 创建实体的ServiceImpl
     * @param entityName
     * @param attributes
     * @param locationPath
     * @param projectDir
     */
    private void createServiceImpl(String entityName, Map<String, String> attributes, String locationPath, File projectDir) {
        String className = entityName + "ServiceImpl";
        System.out.println("start creating "+ className + "...");

        //寻找目标文件夹
        String location = findPath(locationPath ,projectDir);
        if (location != null) {
            System.out.println("find "+locationPath+" folder:" + location);
        }else return;

        String packageName = location.substring(location.indexOf("java") + 5, location.lastIndexOf(locationPath)-1).replaceAll("\\\\",".");
        packageName = packageName.replaceAll(".service", "");
        System.out.println("working under the package: " + packageName);

        if (fileExist(location, className)){
            return;
        }

        //目标文件不存在,创建文件
        File aim = new File(location , className + ".java");
        if (!createAimFile(aim, location, className)){
            return;
        }

        try {
            FileWriter fileWriter = new FileWriter(aim);
            BufferedWriter out = new BufferedWriter(fileWriter);
            out.write("package " + packageName + ".service." + locationPath + ";\n");
            out.newLine();
            out.write("import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;\n" +
                    "import lombok.RequiredArgsConstructor;\n" +
                    "import lombok.extern.slf4j.Slf4j;\n" +
                    "import org.springframework.data.redis.core.Cursor;\n" +
                    "import org.springframework.stereotype.Service;");
            out.newLine();
            out.flush();

            out.write("import " + packageName + ".service." + entityName + "Service;\n" +
                    "import " + packageName + ".entity." + entityName + ";\n" +
                    "import " + packageName + ".mapper." + entityName + "Mapper;\n" +
                    "import " + packageName + ".vo." + entityName + "VO;\n" +
                    "import " + packageName + ".vo." + entityName + "DTO;\n");
            out.newLine();
            out.flush();

            out.write("/**\n" +
                    " * " + entityName + "ServiceImpl\n" +
                    " * @author zhongchujie\n" +
                    " * @since "+ LocalDate.now().format(DateTimeFormatter.ofPattern("yy-MM-dd")) +"\n" +
                    " */\n");
            out.newLine();
            out.flush();

            out.write("@Slf4j\n" +
                    "@Service\n" +
                    "@RequiredArgsConstructor");
            out.newLine();
            out.flush();

            out.write("public class "+className +" extends ServiceImpl<"+ entityName +"Mapper" +", " + entityName + "> implements " + entityName + "Service"+" {\n" +
                    "}");
            out.newLine();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("create " + className + " success!");
    }

    /**
     * 创建实体的Service
     * @param entityName
     * @param attributes
     * @param locationPath
     * @param projectDir
     */
    private void createService(String entityName, Map<String, String> attributes, String locationPath, File projectDir) {
        String className = entityName + "Service";
        System.out.println("start creating "+ className + "...");

        //寻找目标文件夹
        String location = findPath(locationPath ,projectDir);
        if (location != null) {
            System.out.println("find "+locationPath+" folder:" + location);
        }else return;

        String packageName = location.substring(location.indexOf("java") + 5, location.lastIndexOf(locationPath)-1).replaceAll("\\\\",".");
        System.out.println("working under the package: " + packageName);

        if (fileExist(location, className)){
            return;
        }

        //目标文件不存在,创建文件
        File aim = new File(location , className + ".java");
        if (!createAimFile(aim, location, className)){
            return;
        }

        try {
            FileWriter fileWriter = new FileWriter(aim);
            BufferedWriter out = new BufferedWriter(fileWriter);
            out.write("package " + packageName + "." + locationPath + ";\n");
            out.newLine();
            out.write("import com.baomidou.mybatisplus.extension.service.IService;\n");
            out.newLine();
            out.flush();

            out.write("import " + packageName + ".entity." + entityName + ";\n" +
                    "import " + packageName + ".vo." + entityName + "VO;\n" +
                    "import " + packageName + ".vo." + entityName + "DTO;\n");
            out.newLine();
            out.flush();

            out.write("/**\n" +
                    " * " + entityName + "Service\n" +
                    " * @author zhongchujie\n" +
                    " * @since "+ LocalDate.now().format(DateTimeFormatter.ofPattern("yy-MM-dd")) +"\n" +
                    " */\n");
            out.newLine();
            out.flush();

            out.write("public interface "+className + " extends IService<" + entityName + "> {\n" +
                    "}");
            out.newLine();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("create " + className + " success!");
    }

    /**
     * 创建实体的mapper
     * @param entityName
     * @param attributes
     * @param locationPath
     * @param projectDir
     */
    private void createMapper(String entityName, Map<String, String> attributes, String locationPath, File projectDir) {
        String className = entityName + "Mapper";
        System.out.println("start creating "+ className + "...");

        //寻找目标文件夹
        String location = findPath(locationPath ,projectDir);
        if (location != null) {
            System.out.println("find "+locationPath+" folder:" + location);
        }else return;

        String packageName = location.substring(location.indexOf("java") + 5, location.lastIndexOf(locationPath)-1).replaceAll("\\\\",".");
        System.out.println("working under the package: " + packageName);

        if (fileExist(location, className)){
            return;
        }

        //目标文件不存在,创建文件
        File aim = new File(location , className + ".java");
        if (!createAimFile(aim, location, className)){
            return;
        }

        try {
            FileWriter fileWriter = new FileWriter(aim);
            BufferedWriter out = new BufferedWriter(fileWriter);
            out.write("package " + packageName + "." + locationPath + ";\n");
            out.newLine();
            out.write("import com.baomidou.mybatisplus.core.mapper.BaseMapper;\n" +
                    "import com.baomidou.mybatisplus.core.metadata.IPage;\n" +
                    "import com.baomidou.mybatisplus.extension.plugins.pagination.Page;\n" +
                    "import org.apache.ibatis.annotations.Mapper;\n" +
                    "import org.apache.ibatis.annotations.Param;\n" +
                    "import org.apache.ibatis.annotations.Select;\n");
            out.newLine();
            out.flush();

            out.write("import " + packageName + ".entity." + entityName + ";\n" +
                    "import " + packageName + ".vo." + entityName + "VO;\n" +
                    "import " + packageName + ".vo." + entityName + "DTO;\n");
            out.newLine();
            out.flush();

            out.write("/**\n" +
                    " * " + entityName + "Mapper\n" +
                    " * @author zhongchujie\n" +
                    " * @since "+ LocalDate.now().format(DateTimeFormatter.ofPattern("yy-MM-dd")) +"\n" +
                    " */\n");
            out.newLine();
            out.flush();

            out.write("@Mapper");
            out.newLine();
            out.flush();

            out.write("public interface "+className + " extends BaseMapper<" + entityName + "> {\n" +
                    "}");
            out.newLine();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("create " + className + " success!");
    }

    /**
     * 创建实体的DTO
     * @param attributes
     */
    private void createDTO(String entityName, Map<String, String> attributes, String locationPath, File projectDir) {
        String className = entityName + "DTO";
        System.out.println("start creating "+ className + "...");

        //寻找目标文件夹
        String location = findPath(locationPath ,projectDir);
        if (location != null) {
            System.out.println("find "+locationPath+" folder:" + location);
        }else return;

        String packageName = location.substring(location.indexOf("java") + 5, location.lastIndexOf(locationPath)-1).replaceAll("\\\\",".");
        System.out.println("working under the package: " + packageName);

        if (fileExist(location, className)){
            return;
        }

        //目标文件不存在,创建文件
        File aim = new File(location , className + ".java");
        if (!createAimFile(aim, location, className)){
            return;
        }

        try{
            FileWriter fileWriter = new FileWriter(aim);
            BufferedWriter out = new BufferedWriter(fileWriter);
            out.write("package "+ packageName +"." + locationPath + ";\n" +
                    "\n" +
                    "import lombok.Data;\n" +
                    "import lombok.experimental.Accessors;\n" +
                    "import java.io.Serializable;\n" +
                    "\n");
            out.newLine();
            out.flush();

            out.write("/**\n" +
                    " * " + entityName + "DTO\n" +
                    " * @author zhongchujie\n" +
                    " * @since "+ LocalDate.now().format(DateTimeFormatter.ofPattern("yy-MM-dd")) +"\n" +
                    " */\n");
            out.newLine();
            out.flush();

            out.write("@Data\n"+
                    "@Accessors(chain = true)\n");
            out.newLine();
            out.flush();

            out.write("public class " + className + " implements Serializable {\n");
            out.newLine();
            out.flush();

            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                out.write("    private "+ entry.getValue() + " " + entry.getKey());
                out.write(";\n");
                out.newLine();
                out.flush();
            }

            out.write("}\n");
            out.newLine();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("create " + className + " success!");
    }

    /**
     * 创建实体的VO
     * @param attributes
     */
    private void createVO(String entityName, Map<String, String> attributes, String locationPath, File projectDir) {
        String className = entityName + "VO";
        System.out.println("start creating "+ className + "...");

        //寻找目标文件夹
        String location = findPath(locationPath ,projectDir);
        if (location != null) {
            System.out.println("find "+locationPath+" folder:" + location);
        }else return;

        String packageName = location.substring(location.indexOf("java") + 5, location.lastIndexOf(locationPath)-1).replaceAll("\\\\",".");
        System.out.println("working under the package: " + packageName);

        if (fileExist(location, className)){
            return;
        }

        //目标文件不存在,创建文件
        File aim = new File(location , className + ".java");
        if (!createAimFile(aim, location, className)){
            return;
        }

        try{
            FileWriter fileWriter = new FileWriter(aim);
            BufferedWriter out = new BufferedWriter(fileWriter);
            out.write("package "+ packageName +"." + locationPath + ";\n" +
                    "\n" +
                    "import lombok.Data;\n" +
                    "import com.fasterxml.jackson.annotation.JsonProperty;\n" +
                    "\n");
            out.newLine();
            out.flush();

            out.write("/**\n" +
                    " * " + entityName + "VO\n" +
                    " * @author zhongchujie\n" +
                    " * @since "+ LocalDate.now().format(DateTimeFormatter.ofPattern("yy-MM-dd")) +"\n" +
                    " */\n");
            out.newLine();
            out.flush();

            out.write("@Data\n");
            out.newLine();
            out.flush();

            out.write("public class "+ className +" {\n");
            out.newLine();
            out.flush();

            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                out.write("    @JsonProperty(\"" + entry.getKey() + "\")\n");
                out.write("    private "+ entry.getValue() + " " + entry.getKey());
                out.write(";\n");
                out.newLine();
                out.flush();
            }

            out.write("}\n");
            out.newLine();
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("create " + className + " success!");
    }

    /**
     * 创建实体控制器
     * @param attributes 实体属性list
     */
    private void createController(String entityName, Map<String, String> attributes, String locationPath, File projectDir) {
        String className = entityName + "Controller";
        System.out.println("start creating "+ className + "...");

        //寻找目标文件夹
        String location = findPath(locationPath ,projectDir);
        if (location != null) {
            System.out.println("find "+locationPath+" folder:" + location);
        }else return;

        String packageName = location.substring(location.indexOf("java") + 5, location.lastIndexOf(locationPath)-1).replaceAll("\\\\",".");
        System.out.println("working under the package: " + packageName);

        if (fileExist(location, className)){
            return;
        }

        //目标文件不存在,创建文件
        File aim = new File(location , className + ".java");
        if (!createAimFile(aim, location, className)){
            return;
        }

        try {
            FileWriter fileWriter = new FileWriter(aim);
            BufferedWriter out = new BufferedWriter(fileWriter);
            out.write("package " + packageName + "." + locationPath + ";\n");
            out.newLine();
            out.write("import lombok.RequiredArgsConstructor;\n" +
                    "import lombok.extern.slf4j.Slf4j;\n" +
                    "import org.springframework.scheduling.annotation.Scheduled;\n" +
                    "import org.springframework.web.bind.annotation.*;\n" +
                    "import javax.servlet.http.HttpServletRequest;");
            out.newLine();
            out.flush();

            out.write("import " + packageName + ".service." + entityName + "Service;\n" +
                    "import " + packageName + ".vo." + entityName + "VO;\n" +
                    "import " + packageName + ".vo." + entityName + "DTO;\n");
            out.newLine();
            out.flush();

            out.write("/**\n" +
                    " * " + entityName + "Controller\n" +
                    " * @author zhongchujie\n" +
                    " * @since "+ LocalDate.now().format(DateTimeFormatter.ofPattern("yy-MM-dd")) +"\n" +
                    " */\n");
            out.newLine();
            out.flush();

            out.write("@Slf4j\n" +
                    "@RestController\n" +
                    "@RequestMapping(\"/"+entityName+"\")\n" +
                    "@RequiredArgsConstructor");
            out.newLine();
            out.flush();

            out.write("public class "+className +" {\n" +
                    "\n" +
                    "    private final " + entityName +"Service redisService;\n" +
                    "}");
            out.newLine();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("create " + className + " success!");
    }

    /**
     * 创建目标文件
     * @param aim
     * @param location
     * @param className
     * @return
     */
    private boolean createAimFile(File aim, String location, String className) {
        try{
            if(aim.createNewFile()){
                System.out.println("create " + className);
            }else {
                System.out.println("create " + className + " failed");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 文件是否存在
     * @param location
     * @param className
     * @return 存在返回true
     */
    private boolean fileExist(String location, String className) {
        File aimFolder = new File(location);
        File[] aimFolderList = aimFolder.listFiles();
        for (File file : aimFolderList) {
            if (file.isFile()) {
                //System.out.println("Compare to " + file.getName().replaceAll("Controller.java", ""));
                if (className.equals(file.getName().replaceAll(".java", ""))) {
                    System.out.println(className + " already exist");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 定位到指定文件夹
     * @param aimPath
     * @return
     */
    private String findPath(String aimPath, File parentDir) {
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if(file.isDirectory()) {
                //System.out.println("in" + file.getPath());
                if(aimPath.equals(file.getName())) return file.getPath();
                String path = findPath(aimPath, file);
                if (path != null) {
                    return path;
                }
            }
        }
        return null;
    }

    /**
     * 获取实体属性
     * @param entity 实体文件
     * @return 属性List
     */
    private Map<String, String> getAttributes(File entity) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(Files.newInputStream(entity.toPath())));
        Map<String, String> attributes = new HashMap<>();
        String line;
        while ((line = in.readLine()) != null) {
            if(line.contains("private") && !line.contains("static")) {
                //System.out.println(line);
                String attribute = line.substring(line.lastIndexOf(" ") + 1 ,line.length() - 1);
                String type = line.replaceAll(" ","").replaceAll(attribute + ";", "").replaceAll("private", "");
                attributes.put(attribute, type);
            }
        }
        return attributes;
    }

    /**
     * 找到实体文件
     * @param entityName 实体名称
     * @return 实体文件
     */
    private File findEntity(String entityName, String projectPath) {
        File projectFile = new File(projectPath);
        return startFindEntity(entityName, projectFile);
    }

    private File startFindEntity(String entityName, File parentDir) {
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if(file.isDirectory()) {
                //System.out.println("in" + file.getPath());
                File file1 = startFindEntity(entityName, file);
                if (file1 != null) {
                    return file1;
                }
            }
            //System.out.println("compare to :" + file.getName());
            if(file.isFile() && entityName.equals(file.getName())) {
                System.out.println("find entity in :" + file.getPath());
                return file;
            }
        }
        return null;
    }
}