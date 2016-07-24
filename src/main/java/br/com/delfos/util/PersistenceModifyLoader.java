package br.com.delfos.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class PersistenceModifyLoader {
	private static final String file = "META-INF/persistence.xml";

	public void setProperties(String url, String user, String password) throws JDOMException, IOException {
		InputStream resourceAsStream = PersistenceModifyLoader.class.getClassLoader().getResourceAsStream(file);

		Element root = new SAXBuilder().build(resourceAsStream).getRootElement();

		root.getChildren().stream().filter(e -> e.getName().equals("persistence-unit")).findFirst()
				.ifPresent(persistenceUnit -> {
					persistenceUnit.getChildren().stream().filter(e -> e.getName().equals("properties")).findFirst()
							.ifPresent(properties -> {
								loadProperties(properties, user, password, url);
							});;
				});

		save(root);
	}

	private void save(Element root) throws IOException {
		try {
			XMLOutputter xmlOutput = new XMLOutputter();
			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(root,
					new FileWriter(PersistenceModifyLoader.class.getClassLoader().getResource(file).toURI().getPath()));
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
	}

	private void loadProperties(Element properties, String userVal, String passwordVal, String urlVal) {
		String urlTag = "hibernate.connection.url";
		String userTag = "hibernate.connection.username";
		String passwordTag = "hibernate.connection.password";
		properties.getChildren().stream().filter(e -> {
			String value = e.getAttribute("name").getValue();
			return value.equals(passwordTag) || value.equals(urlTag) || value.equals(userTag);
		}).forEach(property -> {
			if (property.getAttribute("name").getValue().equals(urlTag)) {
				property.getAttribute("value").setValue(urlVal);
			}
			if (property.getAttribute("name").getValue().equals(userTag)) {
				property.getAttribute("value").setValue(userVal);
			}
			if (property.getAttribute("name").getValue().equals(passwordTag)) {
				property.getAttribute("value").setValue(passwordVal);
			}
		});

	}
}
