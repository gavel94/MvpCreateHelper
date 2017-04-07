package com.jiahua.xy;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jiahua on 2017/04/07.
 */
public class MvpGenerateAction extends AnAction
{

    private Project project;
    //包名
    private String mPackageName = "";
    private String mAuthor;//作者
    private String mModuleName;//模块名称
    private String mAppName;//app名字  默认为app


    private enum CodeType
    {
        Activity, ActivityContract, ActivityPresenter, ActivityMoudle,
        Fragment, FragmentContract, FragmentPresenter, FragmentMoudle,
    }

    @Override
    public void actionPerformed(AnActionEvent e)
    {
        project = e.getData(PlatformDataKeys.PROJECT);
        mPackageName = getmPackageName();
        mAppName = "app";
        init();
        refreshProject(e);
    }

    /**
     * 刷新项目
     *
     * @param e
     */
    private void refreshProject(AnActionEvent e)
    {
        e.getProject().getBaseDir().refresh(false, true);
    }

    /**
     * 初始化Dialog
     */
    private void init()
    {
        MyDialog myDialog = new MyDialog(new MyDialog.DialogCallBack()
        {
            @Override
            public void ok(String author, String moduleName, String appName, String packageName, boolean isAc)
            {
                if (author == null||moduleName == null||appName == null||packageName == null||moduleName.length()==0||author.length()==0||appName.length()==0||packageName.length()==0)
                {
                    Messages.showInfoMessage(project, "something is empty", "title");
                    return;
                }
                mAuthor = author;
                mModuleName = moduleName;
                mAppName = appName;
                mPackageName = packageName;
                if (isAc)
                {
                    createActivityClassFiles();
                } else
                {
                    createFragmentClassFiles();
                }
//                createClassFiles();
                Messages.showInfoMessage(project, "create mvp code success", "title");
            }
        });
        myDialog.setVisible(true);

    }

    /**
     * 生成类文件
     */
    private void createClassFiles()
    {
        createClassFile(CodeType.Activity);
        createClassFile(CodeType.Fragment);
    }

    private void createActivityClassFiles()
    {
        createClassFile(CodeType.Activity);
        createClassFile(CodeType.ActivityContract);
        createClassFile(CodeType.ActivityPresenter);
        createClassFile(CodeType.ActivityMoudle);
    }

    private void createFragmentClassFiles()
    {
        createClassFile(CodeType.Fragment);
        createClassFile(CodeType.FragmentContract);
        createClassFile(CodeType.FragmentPresenter);
        createClassFile(CodeType.FragmentMoudle);
    }

    /**
     * 生成base类
     *
     * @param codeType
     */
    private void createBaseClassFile(CodeType codeType)
    {
//        String fileName = "";
//        String content = "";
//        String basePath = getAppPath() + "base/";
//        switch (codeType)
//        {
//            case BaseView:
//                if (!new File(basePath + "BaseView.java").exists())
//                {
//                    fileName = "TemplateBaseView.txt";
//                    content = ReadTemplateFile(fileName);
//                    content = dealTemplateContent(content);
//                    writeToFile(content, basePath, "BaseView.java");
//                }
//                break;
//            case BasePresenter:
//                if (!new File(basePath + "BasePresenter.java").exists())
//                {
//                    fileName = "TemplateBasePresenter.txt";
//                    content = ReadTemplateFile(fileName);
//                    content = dealTemplateContent(content);
//                    writeToFile(content, basePath, "BasePresenter.java");
//                }
//                break;
//            case MvpBaseActivity:
//                if (!new File(basePath + "MvpBaseActivity.java").exists())
//                {
//                    fileName = "TemplateMvpBaseActivity.txt";
//                    content = ReadTemplateFile(fileName);
//                    content = dealTemplateContent(content);
//                    writeToFile(content, basePath, "MvpBaseActivity.java");
//                }
//                break;
//            case MvpBaseFragment:
//                if (!new File(basePath + "MvpBaseFragment.java").exists())
//                {
//                    fileName = "TemplateMvpBaseFragment.txt";
//                    content = ReadTemplateFile(fileName);
//                    content = dealTemplateContent(content);
//                    writeToFile(content, basePath, "MvpBaseFragment.java");
//                }
//                break;
//        }
    }

    /**
     * 生成mvp框架代码
     *
     * @param codeType
     */
    private void createClassFile(CodeType codeType)
    {
        String fileName = "";
        String content = "";
        String appPath = getAppPath();
        switch (codeType)
        {
            case Activity:
                fileName = "TemplateActivity.txt";
                content = ReadTemplateFile(fileName);
                content = dealTemplateContent(content);
                writeToFile(content, appPath + mModuleName.toLowerCase(), mModuleName + "Activity.java");
                break;
            case ActivityContract:
                fileName = "TemplateActivityContract.txt";
                content = ReadTemplateFile(fileName);
                content = dealTemplateContent(content);
                writeToFile(content, appPath + mModuleName.toLowerCase(), mModuleName + "Contract.java");
                break;
            case ActivityMoudle:
                fileName = "TemplateActivityMoudle.txt";
                content = ReadTemplateFile(fileName);
                content = dealTemplateContent(content);
                writeToFile(content, appPath + mModuleName.toLowerCase(), mModuleName + "Moudle.java");
                break;
            case ActivityPresenter:
                fileName = "TemplateActivityPresenter.txt";
                content = ReadTemplateFile(fileName);
                content = dealTemplateContent(content);
                writeToFile(content, appPath + mModuleName.toLowerCase(), mModuleName + "Presenter.java");
                break;
            case Fragment:
                fileName = "TemplateFragment.txt";
                content = ReadTemplateFile(fileName);
                content = dealTemplateContent(content);
                writeToFile(content, appPath + mModuleName.toLowerCase(), mModuleName + "Fragment.java");
                break;
            case FragmentContract:
                fileName = "TemplateFragmentContract.txt";
                content = ReadTemplateFile(fileName);
                content = dealTemplateContent(content);
                writeToFile(content, appPath + mModuleName.toLowerCase(), mModuleName + "Contract.java");
                break;
            case FragmentMoudle:
                fileName = "TemplateFragmentMoudle.txt";
                content = ReadTemplateFile(fileName);
                content = dealTemplateContent(content);
                writeToFile(content, appPath + mModuleName.toLowerCase(), mModuleName + "Moudle.java");
                break;
            case FragmentPresenter:
                fileName = "TemplateFragmentPresenter.txt";
                content = ReadTemplateFile(fileName);
                content = dealTemplateContent(content);
                writeToFile(content, appPath + mModuleName.toLowerCase(), mModuleName + "Presenter.java");
                break;
        }
    }

    /**
     * 获取包名文件路径
     *
     * @return
     */
    private String getAppPath()
    {
        String packagePath = mPackageName.replace(".", "/");
        String appPath = project.getBasePath() + "/" + mAppName + "/src/main/java/" + packagePath + "/";
        return appPath;
    }

    /**
     * 替换模板中字符
     *
     * @param content
     * @return
     */
    private String dealTemplateContent(String content)
    {
        content = content.replace("$name", mModuleName);
        if (content.contains("$packagename"))
        {
            content = content.replace("$packagename", mPackageName + "." + mModuleName.toLowerCase());
        }
        content = content.replace("$author", mAuthor);
        content = content.replace("$date", getDate());
        return content;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public String getDate()
    {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    /**
     * 读取模板文件中的字符内容
     *
     * @param fileName 模板文件名
     * @return
     */
    private String ReadTemplateFile(String fileName)
    {
        InputStream in = null;
        in = this.getClass().getResourceAsStream("/Template/" + fileName);
        String content = "";
        try
        {
            content = new String(readStream(in));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return content;
    }


    private byte[] readStream(InputStream inputStream) throws IOException
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        try
        {
            while ((len = inputStream.read(buffer)) != -1)
            {
                outputStream.write(buffer, 0, len);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            outputStream.close();
            inputStream.close();
        }

        return outputStream.toByteArray();
    }


    /**
     * 生成
     *
     * @param content   类中的内容
     * @param classPath 类文件路径
     * @param className 类文件名称
     */
    private void writeToFile(String content, String classPath, String className)
    {
        try
        {
            File floder = new File(classPath);
            if (!floder.exists())
            {
                floder.mkdirs();
            }

            File file = new File(classPath + "/" + className);
            if (!file.exists())
            {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * 从AndroidManifest.xml文件中获取当前app的包名
     *
     * @return
     */
    private String getmPackageName()
    {
        String package_name = "";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try
        {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(project.getBasePath() + "/" + mAppName + "/src/main/AndroidManifest.xml");

            NodeList nodeList = doc.getElementsByTagName("manifest");
            for (int i = 0; i < nodeList.getLength(); i++)
            {
                Node node = nodeList.item(i);
                Element element = (Element) node;
                package_name = element.getAttribute("package");
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return package_name;
    }
}
