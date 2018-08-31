package uk.gov.companieshouse.document.generator.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

@SpringBootApplication
public class DocumentGeneratorConsumerApplication {

    public static final String APPLICATION_NAME_SPACE = "document-generator-consumer";

    private static final Logger LOGGER = LoggerFactory.getLogger(APPLICATION_NAME_SPACE);

    public static void main(String[] args) {
        Integer port = Integer.getInteger("server.port");

        if (port == null) {
            LOGGER.error("Failed to start service, no port has been configured");
            System.exit(0);
        }

        SpringApplication.run(DocumentGeneratorConsumerApplication.class, args);
    }
}
