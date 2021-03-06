package uk.gov.companieshouse.document.generator.consumer.document.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.companieshouse.document.generator.consumer.avro.DocumentGenerationStateAvroSerializer;
import uk.gov.companieshouse.document.generator.consumer.document.models.GenerateDocumentResponse;
import uk.gov.companieshouse.document.generator.consumer.document.models.avro.DeserialisedKafkaMessage;
import uk.gov.companieshouse.document.generator.consumer.document.models.avro.DocumentGenerationCompleted;
import uk.gov.companieshouse.document.generator.consumer.document.models.avro.DocumentGenerationFailed;
import uk.gov.companieshouse.document.generator.consumer.document.models.avro.DocumentGenerationStarted;
import uk.gov.companieshouse.document.generator.consumer.document.models.Links;
import uk.gov.companieshouse.document.generator.consumer.document.service.impl.MessageServiceImpl;
import uk.gov.companieshouse.document.generator.consumer.exception.MessageCreationException;
import uk.gov.companieshouse.kafka.message.Message;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MessageServiceImplTest {

    @InjectMocks
    private MessageServiceImpl messageService;

    @Mock
    private DocumentGenerationStateAvroSerializer documentGenerationStateAvroSerializer;

    private static final String STARTED_PRODUCER_TOPIC = "document-generation-started";
    private static final String FAILED_PRODUCER_TOPIC = "document-generation-failed";
    private static final String COMPLETED_PRODUCER_TOPIC = "document-generation-completed";

    private static final byte[] STARTED_BYTES = "\f123456test guid".getBytes();
    private static final byte[] FAILED_BYTES = "\f123456  test description , description identifier\nan-id\bdate01/01/1980 ".getBytes();
    private static final byte[] COMPLETED_BYTES = "\f123456test guid test description, description identifier22017-05-22T00:00:00+01:00date01/01/1980 test-location\n1234L".getBytes();

    @Test
    @DisplayName("Test that a create document started message is generated")
    void createDocumentGenerationStartedTest() throws Exception {

        DeserialisedKafkaMessage deserialisedKafkaMessage = createDeserialisedKafkaMessage();

        when(documentGenerationStateAvroSerializer.serialize(any(DocumentGenerationStarted.class))).thenReturn(STARTED_BYTES);

        Message message = messageService.createDocumentGenerationStarted(deserialisedKafkaMessage);

        assertMessageGenerated(message, STARTED_PRODUCER_TOPIC, STARTED_BYTES);
    }

    @Test
    @DisplayName("Test that a create document started message throws exception")
    void createDocumentGenerationStartedExceptionTest() throws Exception {

        DeserialisedKafkaMessage deserialisedKafkaMessage = createDeserialisedKafkaMessage();
        when(documentGenerationStateAvroSerializer.serialize(any(DocumentGenerationStarted.class))).thenThrow(new IOException());

        assertThrows(MessageCreationException.class, () -> messageService.createDocumentGenerationStarted(deserialisedKafkaMessage));
    }

    @Test
    @DisplayName("Test that a create document failed message is generated with populated generateDocumentResponse")
    void createDocumentGenerationFailedPopulatedDocumentResponseTest() throws Exception {
        DeserialisedKafkaMessage deserialisedKafkaMessage = createDeserialisedKafkaMessage();
        GenerateDocumentResponse generateDocumentResponse = createResponse();

        when(documentGenerationStateAvroSerializer.serialize(any(DocumentGenerationFailed.class))).thenReturn(FAILED_BYTES);

        Message message = messageService.createDocumentGenerationFailed(deserialisedKafkaMessage, generateDocumentResponse);

        assertMessageGenerated(message, FAILED_PRODUCER_TOPIC, FAILED_BYTES);
    }

    @Test
    @DisplayName("Test that a create document failed message is generated with null generateDocumentResponse")
    void createDocumentGenerationFailedNullDocumentResponseTest() throws Exception {
        DeserialisedKafkaMessage deserialisedKafkaMessage = createDeserialisedKafkaMessage();
        GenerateDocumentResponse generateDocumentResponse = null;

        when(documentGenerationStateAvroSerializer.serialize(any(DocumentGenerationFailed.class))).thenReturn(FAILED_BYTES);

        Message message = messageService.createDocumentGenerationFailed(deserialisedKafkaMessage, generateDocumentResponse);

        assertMessageGenerated(message, FAILED_PRODUCER_TOPIC, FAILED_BYTES);
    }

    @Test
    @DisplayName("Test that a create document failed message is generated with null generateDocumentResponse && null deserialised kafka message")
    void createDocumentGenerationFailedNullDocumentResponseAndKafkaMessageTest() throws Exception {
        DeserialisedKafkaMessage deserialisedKafkaMessage = null;
        GenerateDocumentResponse generateDocumentResponse = null;

        when(documentGenerationStateAvroSerializer.serialize(any(DocumentGenerationFailed.class))).thenReturn(FAILED_BYTES);

        Message message = messageService.createDocumentGenerationFailed(deserialisedKafkaMessage, generateDocumentResponse);

        assertMessageGenerated(message, FAILED_PRODUCER_TOPIC, FAILED_BYTES);
    }

    @Test
    @DisplayName("Test that a create document failed message throws exception")
    void createDocumentGenerationFailedExceptionTest() throws Exception {
        DeserialisedKafkaMessage deserialisedKafkaMessage = createDeserialisedKafkaMessage();
        GenerateDocumentResponse generateDocumentResponse = createResponse();

        when(documentGenerationStateAvroSerializer.serialize(any(DocumentGenerationFailed.class))).thenThrow(new IOException());

        assertThrows(MessageCreationException.class, () -> messageService.createDocumentGenerationFailed(deserialisedKafkaMessage, generateDocumentResponse));
    }

    @Test
    @DisplayName("Test that a create document completed message is generated")
    void createDocumentGenerationCompletedTest() throws Exception {
        DeserialisedKafkaMessage deserialisedKafkaMessage = createDeserialisedKafkaMessage();
        GenerateDocumentResponse generateDocumentResponse = createResponse();

        when(documentGenerationStateAvroSerializer.serialize(any(DocumentGenerationCompleted.class))).thenReturn(COMPLETED_BYTES);

        Message message = messageService.createDocumentGenerationCompleted(deserialisedKafkaMessage, generateDocumentResponse);

        assertMessageGenerated(message, COMPLETED_PRODUCER_TOPIC, COMPLETED_BYTES);
    }

    @Test
    @DisplayName("Test that a create document completed message throws exception")
    void createDocumentGenerationCompletedExceptionTest() throws Exception {
        DeserialisedKafkaMessage deserialisedKafkaMessage = createDeserialisedKafkaMessage();
        GenerateDocumentResponse generateDocumentResponse = createResponse();

        when(documentGenerationStateAvroSerializer.serialize(any(DocumentGenerationCompleted.class))).thenThrow(new IOException());

        assertThrows(MessageCreationException.class, () -> messageService.createDocumentGenerationCompleted(deserialisedKafkaMessage, generateDocumentResponse));
    }

    /**
     * Populate GenerateDocumentResponse with content
     *
     * @return
     */
    private GenerateDocumentResponse createResponse() {
        GenerateDocumentResponse response = new GenerateDocumentResponse();
        Links links = new Links();
        links.setLocation("location");
        Map<String, String> descriptionValues = new HashMap<>();
        descriptionValues.put("value1", "value2");
        response.setDescription("description");
        response.setDescriptionIdentifier("descriptionIdentifier");
        response.setDescriptionValues(descriptionValues);
        response.setLinks(links);
        response.setSize("size");

        return response;
    }

    /**
     * Populate DeserialisedKafkaMessage with content
     *
     * @return
     */
    private DeserialisedKafkaMessage createDeserialisedKafkaMessage() {
        DeserialisedKafkaMessage deserialisedKafkaMessage = new DeserialisedKafkaMessage();
        deserialisedKafkaMessage.setContentType("contentType");
        deserialisedKafkaMessage.setDocumentType("documentType");
        deserialisedKafkaMessage.setId("id");
        deserialisedKafkaMessage.setResource("resource");
        deserialisedKafkaMessage.setResourceId("resourceId");
        deserialisedKafkaMessage.setUserId("userId");

        return deserialisedKafkaMessage;
    }

    /**
     * Assert message is not null and assert that message topic and message value match
     *
     * @param message
     * @param producerTopic
     * @param valueBytes
     */
    private void assertMessageGenerated(Message message, String producerTopic, byte[] valueBytes) {
        assertNotNull(message);
        assertEquals(producerTopic, message.getTopic());
        assertEquals(valueBytes, message.getValue());
    }
}
