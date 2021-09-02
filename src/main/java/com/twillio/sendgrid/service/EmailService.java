package com.twillio.sendgrid.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import com.twillio.sendgrid.model.EmailRequest;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.io.StringWriter;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {
    @Autowired
    private SendGrid sendGrid;
    @Autowired
    private Configuration freemarkerConfiguration;

    public void sendText(EmailRequest emailRequest) throws TemplateException, IOException {
        for (var toMail : emailRequest.getTo()) {
            Mail mail = new Mail(new Email(emailRequest.getFrom()),
                    emailRequest.getSubject(),
                    new Email(toMail),
                    new Content("text/plain", getEmailContent(emailRequest)));
            Response response = sendMail(mail);
        }

    }

    public void sendHTML(EmailRequest emailRequest) throws TemplateException, IOException {
        Mail mail=new Mail();
        for (var toMail : emailRequest.getTo()) {
            Personalization personalization=new Personalization();
            personalization.addTo(new Email(toMail));
            personalization.addSubstitution("$name$","Mariom");
            personalization.addSubstitution("$email$",toMail);
            mail.addPersonalization(personalization);
        }
        mail.setFrom(new Email(emailRequest.getFrom()));
        mail.setSubject(emailRequest.getSubject());
        mail.addContent(new Content("text/html",getEmailContent(emailRequest)));
        Response response = sendMail(mail);

    }

    private Response sendMail(Mail mail) {
        Request request = new Request();
        Response response = new Response();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = sendGrid.api(request);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

    String getEmailContent(EmailRequest emailRequest) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("request", emailRequest);
        freemarkerConfiguration.getTemplate("email.ftl").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }
}
