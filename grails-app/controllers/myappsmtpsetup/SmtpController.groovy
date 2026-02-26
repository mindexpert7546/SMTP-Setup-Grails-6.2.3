package myappsmtpsetup


import grails.rest.*
import grails.converters.*

class SmtpController {

    static responseFormats = ['json', 'xml']

    def smtpService 

    def sendEmail() {

        Map emailIds = [
                toMailIds : "Kundan.Kumar@gmail.com",
                ccMailIds : "",
                bccMailIds: ""
        ]

        List mailAttachments = [
                [
                        filename   : "test.txt",
                        contentType: "text/plain",
                        file       : "This is dummy attachment".bytes
                ]
        ]

        boolean status = smtpService.sendMailWithAttachments(
                emailIds,
                "<h3>Hello from SMTP Temp App</h3>",
                "SMTP Dummy Test",
                mailAttachments,
                "kundan@gmail.com"
        )

        if (status) {
            render([message: "Email sent successfully"] as JSON)
        } else {
            render([message: "Email sending failed"] as JSON)
        }
    }
}