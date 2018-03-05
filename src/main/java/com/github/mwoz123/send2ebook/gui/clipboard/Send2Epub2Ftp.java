package com.github.mwoz123.send2ebook.gui.clipboard;

import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.mwoz123.send2ebook.creator.Creator;
import com.github.mwoz123.send2ebook.creator.EpubCreator;
import com.github.mwoz123.send2ebook.input.InputProcessor;
import com.github.mwoz123.send2ebook.input.UrlInputProcessor;
import com.github.mwoz123.send2ebook.model.Ebook;
import com.github.mwoz123.send2ebook.model.EbookData;
import com.github.mwoz123.send2ebook.model.epub.EpubEbook;
import com.github.mwoz123.send2ebook.storage.Connection;
import com.github.mwoz123.send2ebook.storage.Storage;
import com.github.mwoz123.send2ebook.storage.ftp.FtpStorage;
import com.github.mwoz123.send2ebook.util.PropertyConfigImporter;

public class Send2Epub2Ftp {

	private static final String CONNECTION_FILE_PATH_PROPERTY = "connection.file.path";

	private JLabel statusLabel;

	public Send2Epub2Ftp(JLabel statusLabel) {
		this.statusLabel = statusLabel;
	}

	public void process(String url) {

		try {
			InputProcessor<String> inputProcessor = new UrlInputProcessor();
			Creator<EpubEbook> creator = new EpubCreator();

			Storage storage = FtpStorage.getInstance();
			Connection connection = this.getConnectionFromPropertyFile();

			statusLabel.setText("Processing url: " + url);

			boolean processTextOnly = false;
			EbookData ebookData = inputProcessor.transformInput(url,
					processTextOnly);

			statusLabel.setText("Creating Epub");
			Ebook ebook = creator.createOutputEbook(ebookData);

			statusLabel.setText("Connecting to storage server: "
					+ connection.getHost());
			storage.connect(connection);

			statusLabel.setText("Saving file to server: " + ebook.getTitle());
			storage.storeFile(ebook);

			statusLabel.setText("Succesfully finished: " + ebook.getTitle());
		} catch (Exception e) {
			statusLabel.setText("Error: " + e + " " + e.getMessage());
		}

	}

	private Connection getConnectionFromPropertyFile() throws IOException {

		String connectionFilePath = System
				.getProperty("ftpConnection.properties");

		return PropertyConfigImporter.getConnection(connectionFilePath);

	}
}
