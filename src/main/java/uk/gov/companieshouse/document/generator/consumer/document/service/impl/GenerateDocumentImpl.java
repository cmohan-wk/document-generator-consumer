package uk.gov.companieshouse.document.generator.consumer.document.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import uk.gov.companieshouse.document.generator.consumer.DocumentGeneratorConsumerApplication;
import uk.gov.companieshouse.document.generator.consumer.DocumentGeneratorConsumerProperties;
import uk.gov.companieshouse.document.generator.consumer.document.models.GenerateDocumentRequest;
import uk.gov.companieshouse.document.generator.consumer.document.models.GenerateDocumentResponse;
import uk.gov.companieshouse.document.generator.consumer.document.models.avro.DeserialisedKafkaMessage;
import uk.gov.companieshouse.document.generator.consumer.document.service.GenerateDocument;
import uk.gov.companieshouse.document.generator.consumer.exception.GenerateDocumentException;
import uk.gov.companieshouse.environment.EnvironmentReader;
import uk.gov.companieshouse.logging.Logger;
import uk.gov.companieshouse.logging.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Service
public class GenerateDocumentImpl implements GenerateDocument {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentGeneratorConsumerApplication.APPLICATION_NAME_SPACE);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DocumentGeneratorConsumerProperties configuration;

    private EnvironmentReader reader;

    @Override
    public ResponseEntity<GenerateDocumentResponse> requestGenerateDocument(DeserialisedKafkaMessage deserialisedKafkaMessage) throws GenerateDocumentException {

        String url = configuration.getRootUri() + configuration.getBaseUrl();

        LOG.infoContext(deserialisedKafkaMessage.getUserId(), "Sending request to generate document to document" +
                " generator api", setDebugMap(deserialisedKafkaMessage));

        try {
            ResponseEntity<GenerateDocumentResponse> response = restTemplate
                    .postForEntity(url, setRequest(deserialisedKafkaMessage), GenerateDocumentResponse.class);

            return response;

        } catch (Exception e) {
            LOG.error(e);
           throw new GenerateDocumentException("An error occurred when requesting the generation" +
                   " of a document from the document generator api", e);
        }
    }

    private GenerateDocumentRequest populateDocumentRequest(DeserialisedKafkaMessage deserialisedKafkaMessage) {
        GenerateDocumentRequest request = new GenerateDocumentRequest();
        request.setResourceUri(deserialisedKafkaMessage.getResource());
        request.setResourceID(deserialisedKafkaMessage.getResourceId());
        request.setMimeType(deserialisedKafkaMessage.getContentType());
        request.setDocumentType(deserialisedKafkaMessage.getDocumentType());
        return request;
    }

    private Object setRequest(DeserialisedKafkaMessage deserialisedKafkaMessage) {

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", reader.getMandatoryString(DocumentGeneratorConsumerApplication.API_KEY));

        GenerateDocumentRequest generateDocumentRequest = populateDocumentRequest(deserialisedKafkaMessage);

        HttpEntity<GenerateDocumentRequest> request = new HttpEntity<>(generateDocumentRequest, headers);

        return request;
    }

    private Map<String, Object> setDebugMap(DeserialisedKafkaMessage deserialisedKafkaMessage) {

        Map<String, Object> debugMap = new HashMap<>();
        debugMap.put(DocumentGeneratorConsumerApplication.RESOURCE_URI, deserialisedKafkaMessage.getResource());
        debugMap.put(DocumentGeneratorConsumerApplication.RESOURCE_ID, deserialisedKafkaMessage.getResourceId());

        return debugMap;
    }
}
