package hu.dobrosi.jenkinsnotifier;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

public class Main {
	private static String url = "/rssAll";
	private static int delay = 10000;
	private static String jenkinsProjectUrl;

	public static void main(String[] args) {
		jenkinsProjectUrl = args[0];
		new Main().start();
	}

	private Object ptitleText;

	private void start() {
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				reload();
			}
		}, 0, delay);
	}

	private void reload() {
		SAXBuilder builder = new SAXBuilder();

		try {
			InputStream in = new URL(jenkinsProjectUrl + url).openStream();
			Document document = (Document) builder.build(in);
			Element rootNode = document.getRootElement();
			Namespace ns = rootNode.getNamespace();
			Element feed = (Element) rootNode.getChildren("entry", ns).get(0);
			Element title = feed.getChild("title", ns);
			String titleText = title.getText();
			System.out.println(titleText);
			if (!titleText.equals(ptitleText)) {
				String[] cmd;
				if (isStable(titleText)) {
					cmd = new String[] { "notify-send", "-t", "10000", titleText };
				} else {
					cmd = new String[] { "notify-send", "-u", "critical", titleText };
				}

				Runtime.getRuntime().exec(cmd);
			}
			ptitleText = titleText;
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(
					"URL not found: " + jenkinsProjectUrl + "\nPlease configure your project Jenknis URL in pom.xml (jenkinsProjectUrl), or add it as first execution parameter.");
			System.exit(-1);
		}
	}

	private boolean isStable(String titleText) {
		return titleText.contains("(back to normal)") || titleText.contains("(stable)");
	}
}
