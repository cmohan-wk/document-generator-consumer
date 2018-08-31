/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package uk.gov.companieshouse.document.generator.consumer.document.models;

import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class DocumentGenerationStarted extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -776962671802966651L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"DocumentGenerationStarted\",\"namespace\":\"uk.gov.companieshouse.document.generator.consumer.document.models\",\"fields\":[{\"name\":\"requester_id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"id\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.String requester_id;
  @Deprecated public java.lang.String id;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public DocumentGenerationStarted() {}

  /**
   * All-args constructor.
   * @param requester_id The new value for requester_id
   * @param id The new value for id
   */
  public DocumentGenerationStarted(java.lang.String requester_id, java.lang.String id) {
    this.requester_id = requester_id;
    this.id = id;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return requester_id;
    case 1: return id;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: requester_id = (java.lang.String)value$; break;
    case 1: id = (java.lang.String)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'requester_id' field.
   * @return The value of the 'requester_id' field.
   */
  public java.lang.String getRequesterId() {
    return requester_id;
  }

  /**
   * Sets the value of the 'requester_id' field.
   * @param value the value to set.
   */
  public void setRequesterId(java.lang.String value) {
    this.requester_id = value;
  }

  /**
   * Gets the value of the 'id' field.
   * @return The value of the 'id' field.
   */
  public java.lang.String getId() {
    return id;
  }

  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(java.lang.String value) {
    this.id = value;
  }

  /**
   * Creates a new DocumentGenerationStarted RecordBuilder.
   * @return A new DocumentGenerationStarted RecordBuilder
   */
  public static uk.gov.companieshouse.document.generator.consumer.document.models.DocumentGenerationStarted.Builder newBuilder() {
    return new uk.gov.companieshouse.document.generator.consumer.document.models.DocumentGenerationStarted.Builder();
  }

  /**
   * Creates a new DocumentGenerationStarted RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new DocumentGenerationStarted RecordBuilder
   */
  public static uk.gov.companieshouse.document.generator.consumer.document.models.DocumentGenerationStarted.Builder newBuilder(uk.gov.companieshouse.document.generator.consumer.document.models.DocumentGenerationStarted.Builder other) {
    return new uk.gov.companieshouse.document.generator.consumer.document.models.DocumentGenerationStarted.Builder(other);
  }

  /**
   * Creates a new DocumentGenerationStarted RecordBuilder by copying an existing DocumentGenerationStarted instance.
   * @param other The existing instance to copy.
   * @return A new DocumentGenerationStarted RecordBuilder
   */
  public static uk.gov.companieshouse.document.generator.consumer.document.models.DocumentGenerationStarted.Builder newBuilder(uk.gov.companieshouse.document.generator.consumer.document.models.DocumentGenerationStarted other) {
    return new uk.gov.companieshouse.document.generator.consumer.document.models.DocumentGenerationStarted.Builder(other);
  }

  /**
   * RecordBuilder for DocumentGenerationStarted instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<DocumentGenerationStarted>
    implements org.apache.avro.data.RecordBuilder<DocumentGenerationStarted> {

    private java.lang.String requester_id;
    private java.lang.String id;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(uk.gov.companieshouse.document.generator.consumer.document.models.DocumentGenerationStarted.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.requester_id)) {
        this.requester_id = data().deepCopy(fields()[0].schema(), other.requester_id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.id)) {
        this.id = data().deepCopy(fields()[1].schema(), other.id);
        fieldSetFlags()[1] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing DocumentGenerationStarted instance
     * @param other The existing instance to copy.
     */
    private Builder(uk.gov.companieshouse.document.generator.consumer.document.models.DocumentGenerationStarted other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.requester_id)) {
        this.requester_id = data().deepCopy(fields()[0].schema(), other.requester_id);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.id)) {
        this.id = data().deepCopy(fields()[1].schema(), other.id);
        fieldSetFlags()[1] = true;
      }
    }

    /**
      * Gets the value of the 'requester_id' field.
      * @return The value.
      */
    public java.lang.String getRequesterId() {
      return requester_id;
    }

    /**
      * Sets the value of the 'requester_id' field.
      * @param value The value of 'requester_id'.
      * @return This builder.
      */
    public uk.gov.companieshouse.document.generator.consumer.document.models.DocumentGenerationStarted.Builder setRequesterId(java.lang.String value) {
      validate(fields()[0], value);
      this.requester_id = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'requester_id' field has been set.
      * @return True if the 'requester_id' field has been set, false otherwise.
      */
    public boolean hasRequesterId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'requester_id' field.
      * @return This builder.
      */
    public uk.gov.companieshouse.document.generator.consumer.document.models.DocumentGenerationStarted.Builder clearRequesterId() {
      requester_id = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'id' field.
      * @return The value.
      */
    public java.lang.String getId() {
      return id;
    }

    /**
      * Sets the value of the 'id' field.
      * @param value The value of 'id'.
      * @return This builder.
      */
    public uk.gov.companieshouse.document.generator.consumer.document.models.DocumentGenerationStarted.Builder setId(java.lang.String value) {
      validate(fields()[1], value);
      this.id = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'id' field has been set.
      * @return True if the 'id' field has been set, false otherwise.
      */
    public boolean hasId() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'id' field.
      * @return This builder.
      */
    public uk.gov.companieshouse.document.generator.consumer.document.models.DocumentGenerationStarted.Builder clearId() {
      id = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    public DocumentGenerationStarted build() {
      try {
        DocumentGenerationStarted record = new DocumentGenerationStarted();
        record.requester_id = fieldSetFlags()[0] ? this.requester_id : (java.lang.String) defaultValue(fields()[0]);
        record.id = fieldSetFlags()[1] ? this.id : (java.lang.String) defaultValue(fields()[1]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  private static final org.apache.avro.io.DatumWriter
    WRITER$ = new org.apache.avro.specific.SpecificDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  private static final org.apache.avro.io.DatumReader
    READER$ = new org.apache.avro.specific.SpecificDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}