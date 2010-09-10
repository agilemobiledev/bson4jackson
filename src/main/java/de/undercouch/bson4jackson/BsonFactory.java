package de.undercouch.bson4jackson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.ObjectCodec;
import org.codehaus.jackson.io.IOContext;

/**
 * Factory for {@link BsonGenerator} and {@link BsonParser}
 * @author Michel Kraemer
 */
public class BsonFactory extends JsonFactory {
	/**
	 * The BSON generator features enabled by default
	 */
	private static final int DEFAULT_BSON_GENERATOR_FEATURE_FLAGS = 0;
	
	/**
	 * The BSON generator features to be enabled when a new
	 * generator is created
	 */
	protected int _bsonGeneratorFeatures = DEFAULT_BSON_GENERATOR_FEATURE_FLAGS;
	
	/**
	 * @see JsonFactory#JsonFactory()
	 */
	public BsonFactory() {
		this(null);
	}

	/**
	 * @see JsonFactory#JsonFactory(ObjectCodec)
	 */
    public BsonFactory(ObjectCodec oc) {
    	super(oc);
    }
    
    /**
     * Method for enabling/disabling specified generator features
     * (check {@link BsonGenerator.Feature} for list of features)
     * @param f the feature to enable or disable
     * @param state true if the feature should be enabled, false otherwise
     */
    public final BsonFactory configure(BsonGenerator.Feature f, boolean state) {
    	if (state) {
    		return enable(f);
    	}
    	return disable(f);
    }

    /**
     * Method for enabling specified generator features
     * (check {@link BsonGenerator.Feature} for list of features)
     * @param f the feature to enable
     */
    public BsonFactory enable(BsonGenerator.Feature f) {
    	_bsonGeneratorFeatures |= f.getMask();
    	return this;
    }

    /**
     * Method for disabling specified generator features
     * (check {@link BsonGenerator.Feature} for list of features)
     * @param f the feature to disable
     */
    public BsonFactory disable(BsonGenerator.Feature f) {
    	_bsonGeneratorFeatures &= ~f.getMask();
    	return this;
    }

    /**
     * @return true if the specified generator feature is enabled
     */
    public final boolean isEnabled(BsonGenerator.Feature f) {
    	return (_bsonGeneratorFeatures & f.getMask()) != 0;
    }
    
    @Override
    public BsonGenerator createJsonGenerator(OutputStream out, JsonEncoding enc)
    	throws IOException {
    	return createJsonGenerator(out);
    }
    
    public BsonGenerator createJsonGenerator(OutputStream out) throws IOException {
    	BsonGenerator result = new BsonGenerator(_generatorFeatures,
    			_bsonGeneratorFeatures, _objectCodec, out);
    	result.putHeader();
    	return result;
    }
    
    @Override
    protected JsonParser _createJsonParser(InputStream in, IOContext ctxt)
    	throws IOException, JsonParseException {
    	//TODO
    	return null;
    }

    @Override
    protected JsonParser _createJsonParser(Reader r, IOContext ctxt)
    	throws IOException, JsonParseException {
    	//TODO
    	return null;
    }
    
    @Override
    protected JsonParser _createJsonParser(byte[] data, int offset, int len, IOContext ctxt)
    	throws IOException, JsonParseException {
    	//TODO
    	return null;
    }
    
    @Override
    protected JsonGenerator _createJsonGenerator(Writer out, IOContext ctxt)
    	throws IOException {
    	throw new UnsupportedOperationException("Can not create generator for non-byte-based target");
    }
    
    @Override
    protected Writer _createWriter(OutputStream out, JsonEncoding enc, IOContext ctxt)
    	throws IOException {
    	throw new UnsupportedOperationException("Can not create generator for non-byte-based target");
    }
}