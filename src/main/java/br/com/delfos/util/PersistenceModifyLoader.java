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
		System.out.println("PersistenceModifyLoader.setProperties() line 1");
		InputStream resourceAsStream = PersistenceModifyLoader.class.getClassLoader().getResourceAsStream(file);

		System.out.println("PersistenceModifyLoader.setProperties() line 2");
		Element root = new SAXBuilder().build(resourceAsStream).getRootElement();

		System.out.println("PersistenceModifyLoader.setProperties() line 3");
		root.getChildren().stream().filter(e -> e.getName().equals("persistence-unit")).findFirst()
				.ifPresent(persistenceUnit -> {
					persistenceUnit.getChildren().stream().filter(e -> e.getName().equals("properties")).findFirst()
							.ifPresent(properties -> {
								loadProperties(properties, user, password, url);
							});;
				});

		System.out.println("PersistenceModifyLoader.setProperties() line 4");
		save(root);
	}

	private void save(Element root) throws IOException {
		try {
			System.out.println("PersistenceModifyLoader.save() line 1");
			XMLOutputter xmlOutput = new XMLOutputter();
			// display nice nice
			System.out.println("PersistenceModifyLoader.save() line 2");
			xmlOutput.setFormat(Format.getPrettyFormat());
			System.out.println("PersistenceModifyLoader.save() line 3");
			// TODO file writer com um stream de arquivo.
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
				System.out.println("Novo valor do atributo: " + property.getAttributeValue("value"));
			}
			if (property.getAttribute("name").getValue().equals(userTag)) {
				property.getAttribute("value").setValue(userVal);
				System.out.println("Novo valor do atributo: " + property.getAttributeValue("value"));
			}
			if (property.getAttribute("name").getValue().equals(passwordTag)) {
				property.getAttribute("value").setValue(passwordVal);
				System.out.println("Novo valor do atributo: " + property.getAttributeValue("value"));
			}
		});

	}
}
