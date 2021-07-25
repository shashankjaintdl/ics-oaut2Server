package com.ics.oauth2server.security.email;

import com.amazonaws.services.cognitoidp.model.EmailConfigurationType;
import com.ics.oauth2server.configuration.EmailConfiguration;
import com.ics.oauth2server.security.email.context.AbstractEmailContext;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class EmailServiceImpl implements EmailServices{

    private final JavaMailSender javaMailSender;
    private final FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender,
                            FreeMarkerConfigurer freeMarkerConfigurer) {
        this.javaMailSender = javaMailSender;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    @Override
    public void sendEmail(AbstractEmailContext context) throws MessagingException
    {
        Map model = new HashMap();
        model.put("context",context.getContext());
        CompletableFuture.runAsync(() -> {
            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                        StandardCharsets.UTF_8.name());
                Template template = freeMarkerConfigurer
                        .getConfiguration()
                        .getTemplate(context.getTemplateLocation());
                String html = FreeMarkerTemplateUtils.processTemplateIntoString(template,context);
                mimeMessageHelper.setTo(context.getTo());
                mimeMessageHelper.setSubject(context.getSubject());
                mimeMessageHelper.setFrom(context.getFrom());
                mimeMessageHelper.setText(html, true);
                javaMailSender.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    


}
