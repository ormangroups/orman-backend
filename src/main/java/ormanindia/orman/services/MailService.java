package ormanindia.orman.services;

import ormanindia.orman.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true indicates the content is HTML

            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }

    public String buildOrderConfirmationEmail(Order order) {
        String htmlTemplate = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <style>
                body {
                    font-family: Arial, sans-serif;
                    background-color: #f4f4f4;
                    margin: 0;
                    padding: 20px;
                }
                .container {
                    max-width: 600px;
                    margin: 0 auto;
                    background-color: #ffffff;
                    border: 1px solid #dddddd;
                    padding: 20px;
                }
                .header {
                    text-align: center;
                    padding-bottom: 20px;
                }
                .header img {
                    max-width: 200px;
                }
                .order-details {
                    margin-bottom: 20px;
                }
                .order-details th, .order-details td {
                    padding: 10px;
                    text-align: left;
                    border-bottom: 1px solid #dddddd;
                }
                .footer {
                    text-align: center;
                    color: #777777;
                    padding-top: 20px;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <div class="header">
                    <h1>Order Confirmation</h1>
                    <p>Thank you for your order!</p>
                </div>
                <div class="order-details">
                    <h2>Order Details</h2>
                    <table>
                        <tr>
                            <th>Product Name</th>
                            <th>Quantity</th>
                            <th>Price</th>
                        </tr>
                        <!-- Order items will be inserted here dynamically -->
                        %ORDER_ITEMS%
                    </table>
                    <p><strong>Total:</strong> ₹‎%TOTAL_PRICE%</p>
                </div>
                <div class="footer">
                    <p>If you have any questions, feel free to <a href="mailto:ormangroupindia@gmail.com">contact us</a>.</p>
                    <p>&copy; 2025 ORMAN GROUP INDIA</p>
                </div>
            </div>
        </body>
        </html>
        """;

        // Build the items table rows
        StringBuilder itemsHtml = new StringBuilder();
        order.getItems().forEach(item -> {
            itemsHtml.append("<tr>")
                     .append("<td>").append(item.getProduct().getName()).append("</td>")
                     .append("<td>").append(item.getQuantity()).append("</td>")
                     .append("<td>").append(item.getPrice()).append("</td>")
                     .append("</tr>");
        });

        // Replace placeholders in the template
        String emailContent = htmlTemplate
                .replace("%ORDER_ITEMS%", itemsHtml.toString())
                .replace("%TOTAL_PRICE%", String.valueOf(order.getTotalPrice()));

        return emailContent;
    }
}
