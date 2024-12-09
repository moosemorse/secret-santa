package code

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.model.Message
import java.io.FileReader
import java.util.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import javax.mail.Session
import java.io.ByteArrayOutputStream
import java.util.Base64

fun main() {
    val credentials = getCredentials()
    val service = getGmailService(credentials)

    val toEmail = "my-email@gmail.com" //replace with correct (sender) email
    val subject = "OAuth Test Email"
    val bodyText = "This is a test email sent using OAuth 2.0 with Gmail API."

    sendEmail(service, toEmail, subject, bodyText)
}

fun getCredentials(): Credential {
    val JSON_FACTORY: JsonFactory = com.google.api.client.json.JsonFactory()
    val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()

    // Load private information from your credentials.json file (obtain from Google cloud service)
    val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, FileReader("src/main/resources/credentials.json"))
    val flow = GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, listOf("https://www.googleapis.com/auth/gmail.send"))
        .setAccessType("offline")
        .build()

    // Get the authorization URL
    val credential = AuthorizationCodeInstalledApp(flow, LocalServerReceiver()).authorize("user")
    return credential
}

fun getGmailService(credentials: Credential): Gmail {
    return Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(), JsonFactory(), credentials)
        .setApplicationName("OAuth Gmail Kotlin")
        .build()
}

fun sendEmail(service: Gmail, toEmail: String, subject: String, bodyText: String) {
    val mimeMessage = createEmail(toEmail, subject, bodyText)
    val message = createMessageWithEmail(mimeMessage)
    service.users().messages().send("me", message).execute()
    println("Email sent successfully!")
}

fun createEmail(to: String, subject: String, bodyText: String): MimeMessage {
    val props = Properties()
    val session = Session.getDefaultInstance(props, null)
    val mimeMessage = MimeMessage(session)
    mimeMessage.setFrom(InternetAddress("my-email@gmail.com")) //replace with (correct) sender email
    mimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, InternetAddress(to))
    mimeMessage.subject = subject
    mimeMessage.setText(bodyText)
    return mimeMessage
}

fun createMessageWithEmail(mimeMessage: MimeMessage): Message {
    val buffer = ByteArrayOutputStream()
    mimeMessage.writeTo(buffer)
    val bytes = buffer.toByteArray()
    val encodedEmail = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    val message = Message()
    message.raw = encodedEmail
    return message
}
