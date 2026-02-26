package myappsmtpsetup

import grails.gorm.transactions.Transactional

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Transactional
class SmtpService {
    def mailSender

   def mailService

    boolean sendMailWithAttachments(
            Map emailIds,
            String messageText,
            String subjectText,
            List<Map> mailAttachments,
            String fromEmailId) {

        try {

            List toMailIds = emailIds?.toMailIds ?
                    emailIds.toMailIds.replaceAll(';', ',').split(',')*.trim() : []

            List ccMailIds = emailIds?.ccMailIds ?
                    emailIds.ccMailIds.replaceAll(';', ',').split(',')*.trim() : []

            List bccMailIds = emailIds?.bccMailIds ?
                    emailIds.bccMailIds.replaceAll(';', ',').split(',')*.trim() : []

            mailService.sendMail {

                multipart true

                from fromEmailId

                if (toMailIds) to toMailIds
                if (ccMailIds) cc ccMailIds
                if (bccMailIds) bcc bccMailIds

                subject subjectText
                html messageText ?: ""

                // Attachments
                mailAttachments?.each { attachment ->
                    attach(
                            attachment.filename,
                            attachment.contentType ?: "application/octet-stream",
                            attachment.file
                    )
                }
            }

            println "Mail sent successfully ✅"
            return true

        } catch (Exception e) {
            println "Mail error: ${e.message}"
            return false
        }
    }

    
}
