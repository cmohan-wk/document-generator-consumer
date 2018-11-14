package uk.gov.companieshouse.document.generator.consumer.avro;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gov.companieshouse.document.generator.consumer.document.models.avro.DocumentGenerationCompleted;
import uk.gov.companieshouse.document.generator.consumer.document.models.avro.DocumentGenerationFailed;
import uk.gov.companieshouse.document.generator.consumer.document.models.avro.DocumentGenerationStarted;

@Component
public class DocumentGenerationStateAvroSerializer {
	
	@Autowired
	private AvroSerializer serializer;

    /**
     * Serialize the message for the completion of the document generation.
     *
     * @param document
     * @return
     * @throws IOException
     */
    public byte[] serialize(DocumentGenerationCompleted document) throws IOException {
        return serializer.serialize(AvroDatumFactory.getWriter(), document);
    }

    /**
     * Serialize the message for the start of the document generation.
     *
     * @param started
     * @return
     * @throws IOException
     */
    public byte[] serialize(DocumentGenerationStarted started) throws IOException {
        return serializer.serialize(AvroDatumFactory.getWriter(), started);
    }

    /**
     * Serialize the message for the document generation failure.
     *
     * @param failed
     * @return bytes
     * @throws IOException
     */
    public byte[] serialize(DocumentGenerationFailed failed) throws IOException {
        return serializer.serialize(AvroDatumFactory.getWriter(), failed);
    }
}
