package org.springframework.samples.mvc.convert;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

public class MaskFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<MaskFormat> {

	public Set<Class<?>> getFieldTypes() {
		Set<Class<?>> fieldTypes = new HashSet<Class<?>>(1, 1);
		fieldTypes.add(String.class);
		return fieldTypes;
	}

	public Parser<?> getParser(MaskFormat annotation) {
		return new MaskFormatter(annotation.value());
	}

	public Printer<?> getPrinter(MaskFormat annotation) {
		return new MaskFormatter(annotation.value());
	}
	
	private static class MaskFormatter implements Formatter<String> {

		private javax.swing.text.MaskFormatter delegate;

		public MaskFormatter(String mask) {
			try {
				this.delegate = new javax.swing.text.MaskFormatter(mask);
				this.delegate.setValueContainsLiteralCharacters(false);
			} catch (ParseException e) {
				throw new IllegalStateException("Mask could not be parsed " + mask, e);
			}
		}

		public String print(String object) {
			try {
				return delegate.valueToString(object);
			} catch (ParseException e) {
				throw new IllegalArgumentException("Unable to print using mask " + delegate.getMask(), e);
			}
		}

		public String parse(String text) throws ParseException {
			return (String) delegate.stringToValue(text);
		}

		@Override
		public String print(String arg0, Locale arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String parse(String arg0, Locale arg1) throws ParseException {
			// TODO Auto-generated method stub
			return null;
		}

	}

	@Override
	public Parser<?> getParser(MaskFormat arg0, Class<?> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Printer<?> getPrinter(MaskFormat arg0, Class<?> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
