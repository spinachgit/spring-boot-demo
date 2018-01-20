package com.spinach.example.util.tools.email;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class SpringMailSender {
    // Spring的邮件工具类，实现了MailSender和JavaMailSender接口
    private JavaMailSenderImpl mailSender;

    //使用Velocity模板，需要Velocity的jar包，然后需要声明一个VelocityEngine对象
    private VelocityEngine velocityEngine;
    
    /**
     * 创建邮件发送器
     */
    public SpringMailSender() {
        // 初始化JavaMailSenderImpl，当然推荐在spring配置文件中配置，这里是为了简单
        mailSender = new JavaMailSenderImpl();
        // 设置参数
        mailSender.setHost("smtp.qq.com");
        mailSender.setUsername("123456789@qq.com");
        mailSender.setPassword("555555");
        
        //声明一个VelocityEngine对象后，需要在构造函数中初始化（IoC is optional）
        // Velocity的参数，通过VelocityEngineFactoryBean创建VelocityEngine，也是推荐在配置文件中配置的
        Properties props = System.getProperties();
        props.put("resource.loader", "class");
        props.put("class.resource.loader.class",
                        "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        VelocityEngineFactoryBean v = new VelocityEngineFactoryBean();
        v.setVelocityProperties(props);
        try {
            velocityEngine = v.createVelocityEngine();
        } catch (VelocityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
    * 方法名: simpleSend
    * 方法作用: TODO 简单邮件发送
    * 创建人：Jxufe HeHaiYang
    * 创建时间：2015-2-7 下午06:47:30   
    * @param @throws Exception    
    * 返回值类型： void    
    * @throws
     */
    public void simpleSend() throws Exception {
        // 构建简单邮件对象
        //SimpleMailMessages实现了MimeMessageHelper，为普通邮件模板，支持文本。
        SimpleMailMessage smm = new SimpleMailMessage();
        // 设定邮件参数
        smm.setFrom(mailSender.getUsername());
        smm.setTo("234567890@qq.com");
        smm.setSubject("Hello world");
        smm.setText("nice !");
        // 发送邮件
        mailSender.send(smm);
    }
    
    
    /**
     * 
    * 方法名: attachedSend
    * 方法作用: TODO 带附件的邮件发送
    * 创建人：Jxufe HeHaiYang
    * 创建时间：2015-2-7 下午06:47:22   
    * @param @throws MessagingException    
    * 返回值类型： void    
    * @throws
     */
    public void attachedSend() throws MessagingException {
        //使用JavaMail的MimeMessage，支付更加复杂的邮件格式和内容
        //MimeMessages为复杂邮件模板，支持文本、附件、html、图片等。
        MimeMessage msg = mailSender.createMimeMessage();
        //创建MimeMessageHelper对象，处理MimeMessage的辅助类
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        //使用辅助类MimeMessage设定参数
        helper.setFrom(mailSender.getUsername());
        helper.setTo("234567890@qq.com");
        helper.setSubject("Hello Attachment");
        helper.setText("This is a mail with attachment");
        //加载文件资源，作为附件
        //文件地址相对应src目录
        ClassPathResource file = new ClassPathResource("ehcache.xsd");
        //加入附件
        helper.addAttachment("ehcache.xsd", file);
        
        //发送邮件
        mailSender.send(msg);
    }
    
    /**
     * 
    * 方法名: richContentSend
    * 方法作用: TODO 发送富文本邮件
    * 创建人：Jxufe HeHaiYang
    * 创建时间：2015-2-7 下午06:47:14   
    * @param @throws MessagingException    
    * 返回值类型： void    
    * @throws
     */
    public void richContentSend() throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();
     
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
     
        helper.setFrom(mailSender.getUsername());
        helper.setTo("234567890@qq.com");
        helper.setSubject("邮件标题");
        //第二个参数true，表示text的内容为html，然后注意<img/>标签，src='cid:file'，'cid'是contentId的缩写，'file'是一个标记，
        //需要在后面的代码中调用MimeMessageHelper的addInline方法替代成文件
        helper.setText(
                "<body><p style='color:red;'>Hello Html Email</p><img src='cid:file'/></body>",
                true);
        //文件地址相对应src目录
        ClassPathResource file = new ClassPathResource("logo.png");
        //文件地址对应系统目录
        //        FileSystemResource file = new FileSystemResource("C:\\Users\\HIYOUNG\\Desktop\\logo.png");
        helper.addInline("file", file);
     
        mailSender.send(msg);
    }
    
    /**
     * 
    * 方法名: templateSend
    * 方法作用: TODO  使用Velocity模板发送邮件
    * 创建人：Jxufe HeHaiYang
    * 创建时间：2015-2-7 下午06:47:05   
    * @param @throws MessagingException    
    * 返回值类型： void    
    * @throws
     */
    public void templateSend() throws MessagingException {
        // 声明Map对象，并填入用来填充模板文件的键值对
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("user", "hehaiyang");
        model.put("content", "good evening !");
        //Spring提供的VelocityEngineUtils将模板进行数据填充，并转换成普通的String对象
        //文件地址相对应src目录
        String emailText = VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, "/velocity/mail.vm", model);
        // 和上面一样的发送邮件的工作
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setFrom(mailSender.getUsername());
        helper.setTo("234567890@qq.com");
        helper.setSubject("Rich content mail");
        helper.setText(emailText, true);
     
        mailSender.send(msg);
    }
    
}