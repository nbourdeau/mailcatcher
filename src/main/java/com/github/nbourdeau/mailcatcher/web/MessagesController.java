package com.github.nbourdeau.mailcatcher.web;

import com.github.nbourdeau.mailcatcher.model.Attachment;
import com.github.nbourdeau.mailcatcher.model.Message;
import com.github.nbourdeau.mailcatcher.repositories.AttachmentRepository;
import com.github.nbourdeau.mailcatcher.repositories.MessageRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/")
public class MessagesController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @RequestMapping("")
    public String showMessages(
            @RequestParam(required = false, defaultValue = "0") Integer pageIdx,
            @RequestParam(required = false, defaultValue = "50") Integer pageSize,
            Model model) {
        Page<Message> page = messageRepository.findAll(PageRequest.of(pageIdx, pageSize, Sort.Direction.DESC, "id"));
        model.addAttribute("total", page.getTotalElements());
        model.addAttribute("messages", page.getContent());
        model.addAttribute("page", pageIdx);
        model.addAttribute("pageSize", pageSize);
        return "messages";
    }

    @RequestMapping("/message/{id}")
    public String viewMessage(@PathVariable Long id, Model model) {
        model.addAttribute("message", messageRepository.findById(id).orElseThrow(IllegalArgumentException::new));
        model.addAttribute("attachments", attachmentRepository.findByMessageId(id));
        return "message";
    }

    @RequestMapping(value = "/message/{id}/html", produces = "text/html")
    @ResponseBody
    public String viewMessageHtml(@PathVariable Long id) {
        Message message = messageRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        String html = message.getBodyHtml();
        // Replace images with cid: sources with the internal image url !
        List<Attachment> attachments = attachmentRepository.findByMessageId(id);
        for (Attachment attachment : attachments) {
            if (!StringUtils.isEmpty(attachment.getCid())) {
                String cid = attachment.getCid().replace("<", "").replaceAll(">", "");
                html = html.replaceAll("cid:" + cid, "/message/attachment/" + attachment.getId());
            }
        }
        return html;
    }

    @RequestMapping(value = "/message/attachment/{id}")
    @ResponseBody
    public void viewMessageHtml(@PathVariable Long id, HttpServletResponse httpResponse) throws SQLException, IOException {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        httpResponse.setContentType(attachment.getContentType());
        httpResponse.setHeader("Content-Disposition", "attachment; filename=\"" + attachment.getName() +"\"");
        IOUtils.copy(attachment.getContent().getBinaryStream(), httpResponse.getOutputStream());
        httpResponse.flushBuffer();
    }

    @RequestMapping(value = "/messages/clear")
    @ResponseBody
    public Boolean deleteAll() {
        messageRepository.safeDeleteAll();
        return true;
    }
}
