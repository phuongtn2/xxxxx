package vn.azteam.tabasupport.converter.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import vn.azteam.tabasupport.core.object.model.BaseModel;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * package vn.azteam.tabasupport.converter.http
 * created 3/8/2017.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see HttpMessageConverter
 * @since 1.0.0
 */
public class ModelHttpMessageConverter implements HttpMessageConverter<Object> {

	private static final LinkedMultiValueMap<String, String> LINKED_MULTI_VALUE_MAP = new LinkedMultiValueMap<>();
	private static final Class<? extends MultiValueMap<String, ?>> LINKED_MULTI_VALUE_MAP_CLASS
			= (Class<? extends MultiValueMap<String, ?>>) LINKED_MULTI_VALUE_MAP.getClass();
	private final FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public boolean canRead(Class clazz, MediaType mediaType) {
		return objectMapper.canSerialize(clazz) && BaseModel.class.isAssignableFrom(clazz);
	}

	@Override
	public boolean canWrite(Class clazz, MediaType mediaType) {
		return false;
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return formHttpMessageConverter.getSupportedMediaTypes();
	}

	@Override
	public Object read(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		Map<String, String> input = formHttpMessageConverter.read(LINKED_MULTI_VALUE_MAP_CLASS, inputMessage).toSingleValueMap();
		return objectMapper.convertValue(input, clazz);
	}

	@Override
	public void write(Object o, MediaType contentType, HttpOutputMessage outputMessage) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("");
	}
}