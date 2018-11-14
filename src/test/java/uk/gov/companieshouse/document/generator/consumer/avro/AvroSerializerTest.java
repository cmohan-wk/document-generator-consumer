package uk.gov.companieshouse.document.generator.consumer.avro;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import uk.gov.companieshouse.document.generator.consumer.document.models.avro.DocumentGenerationStarted;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AvroSerializerTest {
    
    @Mock
    private DatumWriter<DocumentGenerationStarted> mockWriter;
    
    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        doNothing().when(mockWriter).setSchema(any());
        doNothing().when(mockWriter).write(any(), any());
    }

    @Test
    @DisplayName("Check that data is serialized")
    public void testSerialize() throws IOException {
        DocumentGenerationStarted data = createTestData();

        AvroSerializer serializer = new AvroSerializer();
        byte[] result = serializer.serialize(mockWriter, data);
        assertNotNull(result);

        verify(mockWriter).setSchema(data.getSchema());
        verify(mockWriter).write(eq(data), any(Encoder.class));
    }

    /**
     * Create a sample document generation started to use as test data
     *
     * @return documentGenerationStarted Data for the start of the document generation process
     */
    private DocumentGenerationStarted createTestData() {
        DocumentGenerationStarted started = new DocumentGenerationStarted();
        started.setId("ID");
        started.setRequesterId("requester-id");

        return started;
    }
}
