package uk.gov.companieshouse.document.generator.consumer.avro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.companieshouse.document.generator.consumer.document.models.avro.DocumentGenerationCompleted;
import uk.gov.companieshouse.document.generator.consumer.document.models.avro.DocumentGenerationFailed;
import uk.gov.companieshouse.document.generator.consumer.document.models.avro.DocumentGenerationStarted;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DocumentGenerationStateAvroSerializerTest {
    
    private static final String STARTED_ENCODED_AVRO_STRING = "Started unit test encoded message";
    private static final String COMPLETED_ENCODED_AVRO_STRING = "Compelted unit test encoded message";
    private static final String FAILED_ENCODED_AVRO_STRING = "Failed unit test encoded message";

    @Mock
    private AvroSerializer avroSerializer;

    @InjectMocks
    private DocumentGenerationStateAvroSerializer serializer;

    @Test
    @DisplayName("Serialize data for document generation started")
    public void testSerializeDocumentGenerationStarted() throws IOException {
        DocumentGenerationStarted started = new DocumentGenerationStarted();
        doReturn(STARTED_ENCODED_AVRO_STRING.getBytes()).when(avroSerializer).serialize(any(), eq(started));

        started.setId("test guid");
        started.setRequesterId("123456");

        byte[] result = serializer.serialize(started);
        assertEquals(STARTED_ENCODED_AVRO_STRING, new String(result));
    }

    @Test
    @DisplayName("Serialize data for document generation completed")
    public void testSerializeDocumentGenerationCompleted() throws IOException {
        DocumentGenerationCompleted completed = new DocumentGenerationCompleted();
        doReturn(COMPLETED_ENCODED_AVRO_STRING.getBytes()).when(avroSerializer).serialize(any(), eq(completed));

        completed.setLocation("test-location");
        completed.setId("test guid");
        completed.setDescription("test description");
        completed.setDescriptionIdentifier("description identifier");
        completed.setRequesterId("123456");
        completed.setDocumentCreatedAt("2017-05-22T00:00:00+01:00");
        completed.setDocumentSize("1234L");

        Map<String, String> descriptionValues = new HashMap<>();
        descriptionValues.put("date", "01/01/1980");

        completed.setDescriptionValues(descriptionValues);

        byte[] result = serializer.serialize(completed);
        assertEquals(COMPLETED_ENCODED_AVRO_STRING, new String(result));
    }

    @Test
    @DisplayName("Serialize data for document generation failed")
    public void testSerializeDocumentGenerationFailed() throws IOException {
        DocumentGenerationFailed failed = new DocumentGenerationFailed();
        doReturn(FAILED_ENCODED_AVRO_STRING.getBytes()).when(avroSerializer).serialize(any(), eq(failed));

        failed.setDescription("test description");
        failed.setDescriptionIdentifier("description identifier");
        failed.setRequesterId("123456");
        failed.setId("an-id");

        Map<String, String> descriptionValues = new HashMap<>();
        descriptionValues.put("date", "01/01/1980");

        failed.setDescriptionValues(descriptionValues);

        byte[] result = serializer.serialize(failed);
        assertEquals(FAILED_ENCODED_AVRO_STRING, new String(result));
    }

    @Test
    @DisplayName("Serialize data for document generation failed mandatory fields only")
    public void testSerializeDocumentGenerationFailedMandatoryFieldsOnly() throws IOException {
    	DocumentGenerationFailed failed = new DocumentGenerationFailed();
    	failed.setRequesterId("123456");
    	failed.setId("an-id");
        doReturn(FAILED_ENCODED_AVRO_STRING.getBytes()).when(avroSerializer).serialize(any(), eq(failed));

        Map<String, String> descriptionValues = new HashMap<>();
        descriptionValues.put("date", "01/01/1980");

        failed.setDescriptionValues(descriptionValues);

        byte[] result = serializer.serialize(failed);
        assertEquals(FAILED_ENCODED_AVRO_STRING, new String(result));
    }
}
